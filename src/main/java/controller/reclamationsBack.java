package controller;

import entity.Reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ReclamationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class reclamationsBack implements Initializable {
    @FXML
    private HBox claim;
    @FXML
    private HBox rec;


    @FXML
    private TableColumn<Reclamation, Void> creation;

    @FXML
    private TableColumn<Reclamation, String> dateReclamation;

    @FXML
    private TableColumn<Reclamation, String> dateSinistre;

    @FXML
    private TableColumn<Reclamation, String> libelle;

    @FXML
    private TextField reclamation_search;

    @FXML
    private TableColumn<Reclamation, String> reponse;

    @FXML
    private TableView<Reclamation> tab;
    @FXML
    private VBox vboxdash;
ReclamationService service=new ReclamationService();

    private List<Reclamation> reclamations;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        libelle.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("libelle"));
        dateReclamation.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("dateReclamation"));
        dateSinistre.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("dateSinitre"));
        reponse.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("reponse"));

        TableColumn<Reclamation, Void> creationCol = new TableColumn<>("Show ");
        creationCol.setCellFactory(param -> new CreationCell());
        tab.getColumns().add(creationCol);
        try {
            loadTableData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void loadTableData() throws SQLException {
        ObservableList<Reclamation> data = FXCollections.observableArrayList(service.afficherReclamations());
        tab.setItems(data);
    }

    public void updateTable_r() throws SQLException {
        List<Reclamation> rec = service.afficherReclamations();
        tab.getItems().setAll(rec);
    }



    @FXML
    private HBox compensation;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationsBack.fxml"));
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
