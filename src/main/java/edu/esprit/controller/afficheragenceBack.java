package edu.esprit.controller;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import edu.esprit.entities.Agence;
import edu.esprit.util.DataSource;





import edu.esprit.util.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import edu.esprit.entities.Agence;
import edu.esprit.service.AgenceService;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class afficheragenceBack implements Initializable {
    @FXML
    private TableView<Agence> tab;
    @FXML
    private TableColumn<Agence, Integer> id;
    @FXML
    private TableColumn<Agence, String> addresse;

    @FXML
    private TableColumn<Agence, String> email;

    @FXML
    private TableColumn<Agence, String> creation;

    @FXML
    private TableColumn<Agence, String> nomage;

    @FXML
    private TableColumn<Agence, Integer> tel;

    @FXML
    TextField Agenom;
    @FXML
    TextField Ageadresse;
    @FXML
    TextField Ageemail;
    @FXML
    TextField Agephone;
    @FXML
    TextField Agedate;

    @FXML
    Button modif, supprimer, pdf;

    @FXML
    private Button ajouteragence;

    @FXML
    private Button consulteravis;

    int selectedId;

    AgenceService s = new AgenceService();



    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet result;
    @FXML
    private Label home_totalEmployees;
    @FXML
    private Label home_totalAvis;



    @FXML
    public void homeTotalEmployees() {
        String sql = "SELECT COUNT(id) FROM Agence";
        this.cnx = DataSource.getInstance().getConnection();;
        int countData = 0;

        try {
            this.pst = this.cnx.prepareStatement(sql);

            for(this.result = this.pst.executeQuery(); this.result.next(); countData = this.result.getInt("COUNT(id)")) {
            }

            this.home_totalEmployees.setText(String.valueOf(countData));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @FXML
    public void homeTotalAvis() {
        String sql = "SELECT COUNT(id) FROM Avis";
        this.cnx = DataSource.getInstance().getConnection();;
        int countData = 0;

        try {
            this.pst = this.cnx.prepareStatement(sql);

            for(this.result = this.pst.executeQuery(); this.result.next(); countData = this.result.getInt("COUNT(id)")) {
            }

            this.home_totalAvis.setText(String.valueOf(countData));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @FXML
    private void consulterButton() {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/avisbackbyagence.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur associé à la nouvelle page
            afficheravisbackbyagence controller = loader.getController();

            // Appeler la méthode du contrôleur pour passer le paramètre selectedId
            System.out.println("fff"+selectedId);
            controller.myparametre(selectedId);
            controller.initialize();// Assuming you have access to the current scene
            Scene currentScene = consulteravis.getScene();
            // Set the new root to the current scene
            currentScene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void ajouterButton() {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouteragence.fxml"));
            Parent root = loader.load();
// Assuming you have access to the current scene
            Scene currentScene = ajouteragence.getScene();
// Set the new root to the current scene


            currentScene.setRoot(root);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

String selectedemail;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomage.setCellValueFactory(new PropertyValueFactory<Agence, String>("nomage"));
        addresse.setCellValueFactory(new PropertyValueFactory<Agence, String>("addresse"));
        email.setCellValueFactory(new PropertyValueFactory<Agence, String>("email"));
        tel.setCellValueFactory(new PropertyValueFactory<Agence, Integer>("tel"));
        creation.setCellValueFactory(new PropertyValueFactory<Agence, String>("create_at"));
        homeTotalEmployees();
        homeTotalAvis();
        loadTableData();

        tab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Agence selectedAgence = tab.getSelectionModel().getSelectedItem();
                selectedId = selectedAgence.getIdage();
                System.out.println(selectedId);
                selectedemail= selectedAgence.getEmail();
                Agenom.setText(selectedAgence.getNomage());
                Ageadresse.setText(selectedAgence.getAddresse());
                Agedate.setText(selectedAgence.getCreate_at());
                Ageemail.setText(selectedAgence.getEmail());
                Agephone.setText(String.valueOf(selectedAgence.getTel()));
            }
        });

        modif.setOnAction(new EventHandler<ActionEvent>() {
          @Override
            public void handle(ActionEvent actionEvent) {

              Agence agence = new Agence(nomage, addresse, email, tel, creation);
                agence.setIdage(selectedId);
                agence.setTel(Integer.parseInt(Agephone.getText()));
                agence.setNomage(Agenom.getText());
                agence.setCreate_at(Agedate.getText());
                agence.setAddresse(Ageadresse.getText());
                agence.setEmail(Ageemail.getText());
                s.modifierage(agence);
              System.out.println(agence);
                updateTable_r();
                clearFields();
            }
        });

        supprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                s.supprimerage(selectedId);
                updateTable_r();
                clearFields();
            }
        });

        /*pdf.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save PDF");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (.pdf)", ".pdf"));

                File file = fileChooser.showSaveDialog(null);
                if (file != null) {
                    generatePdf(file.getAbsolutePath());
                }
            }
        });*/


        clearFields();
    }

    private void loadTableData() {
        ObservableList<Agence> data = FXCollections.observableArrayList(s.getAll());
        tab.setItems(data);
    }

   public void updateTable_r() {
        List<Agence> agences = s.getAll();
        tab.getItems().setAll(agences);
    }

    private void clearFields() {
        Agephone.clear();
        Ageemail.clear();
        Agenom.clear();
        Ageadresse.clear();
        Agedate.clear();
    }


}
