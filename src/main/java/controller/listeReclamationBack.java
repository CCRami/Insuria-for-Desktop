package controller;
import entity.Indemnissation;
import entity.Reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.IndemnisationService;
import services.ReclamationService;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class listeReclamationBack implements Initializable {
    @FXML
    private HBox rec;
    @FXML
    private Button all;
    @FXML
    private Button accepted;
    @FXML
    private HBox compensation;
    @FXML
    private VBox contentArea;

    @FXML
    private Button filtrageREfused;

    @FXML
    private Button add;
   IndemnisationService indemnisationService=new IndemnisationService();

    @FXML
    private ListView<Reclamation> recList;

    @FXML
    private TextField searchField;

    private ReclamationService service = new ReclamationService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListView();
        try {
            loadRecData();
            checkIfAnyReclamationRefused(); // Appel de la méthode pour vérifier les réclamations refusées
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add a listener to the search field's text property
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                rechercherRec();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }





    private void initializeListView() {
        recList.setCellFactory(param -> new ListCell<Reclamation>() {
            @Override
            protected void updateItem(Reclamation rec, boolean empty) {
                super.updateItem(rec, empty);
                if (empty || rec == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(0);
                    hbox.setAlignment(Pos.BASELINE_LEFT);
                    Label libelleLabel = new Label(rec.getLibelle());
                    libelleLabel.setMinWidth(185.0);
                    libelleLabel.setMaxWidth(185.0);
                    Label datReclamationLabel = new Label(rec.getDateReclamation().toString());
                    datReclamationLabel.setMinWidth(173.0);
                    datReclamationLabel.setMaxWidth(173.0);
                    datReclamationLabel.setStyle("-fx-padding: 2px;");
                    Label dateSinistreLabel = new Label(rec.getDateSinitre().toString());
                    dateSinistreLabel.setMinWidth(150.0);
                    dateSinistreLabel.setMaxWidth(150.0);
                    dateSinistreLabel.setStyle("-fx-padding: 2px;");
                    Label reponseLabel = new Label(rec.getReponse());
                    reponseLabel.setMinWidth(180.0);
                    reponseLabel.setMaxWidth(180.0);

                    Button button;
                    if (rec.getReponse().equals("refused") || rec.getReponse().equals("accepted")) {
                        button = new Button("Show Compensation");
                    } else {
                        button = new Button("Show Claim");
                    }
                    button.setMinWidth(150.0);
                    button.setMaxWidth(150.0);
                    button.setOnAction(event -> {

                            if (button.getText().equals("Show Claim")) {
                                // Logique pour Button 1
                                Reclamation reclamation = getItem();
                                System.out.println(reclamation);
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherReclamaationBack.fxml"));
                                    Parent root = loader.load();
                                    afficherReclamationBack controller = loader.getController();
                                    controller.initData(reclamation);
                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(root));
                                    stage.show();
                                    Stage currentStage = (Stage) button.getScene().getWindow();
                                    currentStage.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    // Gérer l'erreur si nécessaire
                                }
                            }
                            else if (button.getText().equals("Show Compensation")) {
                                // Logique pour Button 2
                                Reclamation reclamation = getItem();
                                int id;
                                try {
                                    id = service.selectId(reclamation);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                System.out.println(id);

                                Indemnissation ind;
                                try {
                                    ind = indemnisationService.afficherUneIndemnisation(id);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }

                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/showCompensationBack.fxml"));
                                    Parent root = loader.load();

                                    showCompensation controller = loader.getController();
                                    controller.iniData(ind);

                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(root));
                                    stage.show();

                                    Stage currentStage = (Stage) button.getScene().getWindow();
                                    currentStage.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    hbox.getChildren().addAll(libelleLabel, datReclamationLabel, dateSinistreLabel, reponseLabel, button);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void loadRecData() throws SQLException {
        ObservableList<Reclamation> data = FXCollections.observableArrayList(service.afficherReclamations());
        recList.setItems(data);
    }

    public void refreshList() {
    }
    @FXML
    void showCompensations(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/indemnisationsBack.fxml"));
            Parent root = loader.load();

            // Utilisez votre objet Stage pour afficher la nouvelle interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermez la fenêtre actuelle
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void showReclamations(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listReclamationBack.fxml"));
            Parent root = loader.load();

            // Utilisez votre objet Stage pour afficher la nouvelle interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermez la fenêtre actuelle
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void addAction(ActionEvent event) {

     try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/addReclamation.fxml"));
        Parent root = loader.load();

        // Utilisez votre objet Stage pour afficher la nouvelle interface
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        // Fermez la fenêtre actuelle
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }}



    @FXML
    private void rechercherRec() throws SQLException {

        recList.getItems().clear();


        String searchTerm = searchField.getText().trim().toLowerCase();

        // Récupérer tous les reclamations depuis la base de données
        List<Reclamation> reclamationsList = service.afficherReclamations();

        // Si le champ de recherche est vide, afficher tous les reclamations
        if (searchTerm.isEmpty()) {
            recList.getItems().addAll(reclamationsList);
        } else {

            for (Reclamation recla : reclamationsList) {

                if (recla.getLibelle().toLowerCase().contains(searchTerm)|| recla.getDateReclamation().contains(searchTerm)) {
                    recList.getItems().add(recla);
                }
            }
        }
    }


    @FXML
    void filtrerReclamationsRefusees(ActionEvent event) {

        try {
            List<Reclamation> reclamations= service.afficherReclamationsRefusees();// Récupérer les réclamations refusées
            afficherReclamations(reclamations); // Afficher les réclamations refusées
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherReclamations(List<Reclamation> reclamations) {
        ObservableList<Reclamation> data = FXCollections.observableArrayList(reclamations);
        recList.setItems(data); // Mettre à jour la liste des réclamations dans la vue
    }
    @FXML
    void afficherlistAccepted(ActionEvent event) {

        try {
            List<Reclamation> reclamations= service.afficherReclamationsAccepted();// Récupérer les réclamations refusées
            afficherReclamations(reclamations); // Afficher les réclamations refusées
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button cours;

    @FXML

    void afficherAll(ActionEvent event) {
        try {
            List<Reclamation> reclamations= service.afficherReclamations();// Récupérer les réclamations refusées
            afficherReclamations(reclamations); // Afficher les réclamations refusées
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void afficherCoursDETraitement(ActionEvent event) {
        try {
            List<Reclamation> reclamations= service.afficherReclamationsEnCourDeTraitement();// Récupérer les réclamations refusées
            afficherReclamations(reclamations); // Afficher les réclamations refusées
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void checkIfAnyReclamationRefused() throws SQLException {
        boolean anyRefused = false;
        boolean anyAccepted = false;
        boolean anyBeingProcessed = false;

        List<Reclamation> reclamationsList = service.afficherReclamations();

        for (Reclamation recla : reclamationsList) {
            if ("refused".equals(recla.getReponse())) {
                anyRefused = true;
            } else if ("accepted".equals(recla.getReponse())) {
                anyAccepted = true;
            } else if ("Currently being processed".equals(recla.getReponse())) {
                anyBeingProcessed = true;
            }
        }
        filtrageREfused.setVisible(anyRefused);
        filtrageREfused.setManaged(anyRefused);
        accepted.setVisible(anyAccepted);
        accepted.setManaged(anyAccepted);
        cours.setVisible(anyBeingProcessed);
        cours.setManaged(anyBeingProcessed);
    }




}