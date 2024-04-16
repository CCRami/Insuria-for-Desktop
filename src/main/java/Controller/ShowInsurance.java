package Controller;

import Entity.InsuranceCategory;
import Entity.police;
import Service.InsuranceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import Entity.Insurance;

import java.util.ArrayList;

public class ShowInsurance {

    @FXML
    private BorderPane rootPane;

    @FXML
    private TableView<Insurance> InsuriaTab;

    @FXML
    private TableColumn<Insurance, String> nameins;

    @FXML
    private TableColumn<Insurance, Double> amount;
    @FXML
    private TableColumn<Insurance, String> insimage;
    @FXML
    private TableColumn<Insurance, ArrayList> doa;
    @FXML
    private TableColumn<Insurance, InsuranceCategory> catinsid;
    @FXML
    private TableColumn<Insurance, police> polid;

    @FXML
    private Button addinsurance;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;
    private InsuranceService ins = new InsuranceService();

    //public Insurance insuria = new Insurance(nameins, amount, insimage, doa, catinsid, polid);
    @FXML
    private void ajouterButton() {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddInsurance.fxml"));
            Parent root = loader.load();
// Assuming you have access to the current scene
            Scene currentScene = addinsurance.getScene();
// Set the new root to the current scene


            currentScene.setRoot(root);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // You can add constructor or initialize method here if needed
    private void loadTableData() {
        ObservableList<Insurance> data = FXCollections.observableArrayList(ins.getAllInsurances());
        InsuriaTab.setItems(data);
    }


}

