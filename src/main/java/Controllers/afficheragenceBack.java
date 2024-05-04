package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import Entities.Agence;
import util.DataSource;


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

import Services.AgenceService;

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
            //afficheravisbackbyagence controller = loader.getController();

            // Appeler la méthode du contrôleur pour passer le paramètre selectedId
            System.out.println("fff"+selectedId);
            //controller.myparametre(selectedId);
          // controller.initialize();// Assuming you have access to the current scene
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
    Agence SelectedAgence;

    @FXML
    private void modifierButton() {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateagence.fxml"));
            Parent root = loader.load();
// Assuming you have access to the current scene
            updateagence controller = loader.getController();

            // Appeler la méthode du contrôleur pour passer le paramètre selectedId
            System.out.println("fff"+SelectedAgence);

            controller.myparametre(SelectedAgence);
            controller.initialize(SelectedAgence);
            Scene currentScene = modif.getScene();
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
                 SelectedAgence = tab.getSelectionModel().getSelectedItem();
                selectedId = selectedAgence.getIdage();
                System.out.println(selectedId);
                selectedemail= selectedAgence.getEmail();

            }
        });



        supprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                s.supprimerage(selectedId);
                updateTable_r();

            }
        });





    }


    private void loadTableData() {
        ObservableList<Agence> data = FXCollections.observableArrayList(s.getAllage());
        tab.setItems(data);
    }

   public void updateTable_r() {
        List<Agence> agences = s.getAllage();
        tab.getItems().setAll(agences);
    }



}
