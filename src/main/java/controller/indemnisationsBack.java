package controller;

import entity.Indemnissation;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.IndemnisationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class indemnisationsBack implements Initializable {

    @FXML
    private TableColumn<Indemnissation, String> montant;

    @FXML
    private TableColumn<Indemnissation, String> date;

    @FXML
    private TableColumn<Indemnissation, String> note;



    @FXML
    private TableView<Indemnissation> tab;
    IndemnisationService servicee =new IndemnisationService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        montant.setCellValueFactory(new PropertyValueFactory<Indemnissation, String>("montant"));
        date.setCellValueFactory(new PropertyValueFactory<Indemnissation, String>("date"));
        note.setCellValueFactory(new PropertyValueFactory<Indemnissation, String>("beneficitaire"));

        TableColumn<Indemnissation, Void> creationCol = new TableColumn<>("Action ");
        creationCol.setCellFactory(param -> new CreationButto());
        tab.getColumns().add(creationCol);
        try {
            loadTableData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            loadTableData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private void loadTableData() throws SQLException {
        ObservableList<Indemnissation> data = FXCollections.observableArrayList(servicee.afficherIndemnisation());
        tab.setItems(data);
    }

    public void updateTable_r() throws SQLException {
        List<Indemnissation> rec = servicee.afficherIndemnisation();
        tab.getItems().setAll(rec);
    }

    @FXML
    private HBox compensation;
    @FXML
    private HBox reclamations;


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
