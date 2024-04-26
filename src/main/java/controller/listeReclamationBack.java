package controller;
import entity.Indemnissation;
import entity.Reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ResourceBundle;

public class listeReclamationBack implements Initializable {
    @FXML
    private HBox rec;
    @FXML
    private HBox compensation;
    @FXML
    private VBox contentArea;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                    dateSinistreLabel.setMinWidth(173.0);
                    dateSinistreLabel.setMaxWidth(173.0);
                    dateSinistreLabel.setStyle("-fx-padding: 2px;");
                    Label reponseLabel = new Label(rec.getReponse());
                    reponseLabel.setMinWidth(185.0);
                    reponseLabel.setMaxWidth(185.0);

                    Button button;
                    if (rec.getReponse().equals("refused") || rec.getReponse().equals("accepted")) {
                        button = new Button("Show Compensation");
                    } else {
                        button = new Button("Show Claim");
                    }
                    button.setOnAction(event -> {

                            if (button.getText().equals("show Claim")) {
                                // Logique pour Button 1
                                Reclamation reclamation = getItem();
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
                            } else if (button.getText().equals("Show Compensation")) {
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
}