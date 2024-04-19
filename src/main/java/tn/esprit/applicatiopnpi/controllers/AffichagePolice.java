package tn.esprit.applicatiopnpi.controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.applicatiopnpi.models.Police;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.PoliceService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AffichagePolice implements Initializable {
    @FXML
    private TableView<Police> policeTable;
    @FXML
    private VBox contentArea;


    @FXML
    private TableColumn<Police, String> policeNameColumn;

    @FXML
    private TableColumn<Police, String> policeDescriptionColumn;

    @FXML
    private TableColumn<Police, String> sinistreNameColumn; // Assuming there is a Sinistre name field
    @FXML
    private TableColumn<Police, Void> actionCol;

    private PoliceService policeService = new PoliceService(); // Assuming you have a service class to handle database operations
    private void initializeActionColumn() {
        actionCol.setCellFactory(param -> new TableCell<Police, Void>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox pane = new HBox(editBtn, deleteBtn);

            {

                deleteBtn.setOnAction(event -> {
                    Police police  = getTableView().getItems().get(getIndex());
                    deletePolice(police.getId()); // Utilise l'ID pour supprimer l'entrée
                    getTableView().getItems().remove(police); // Met à jour l'interface immédiatement
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });

    }
    private void deletePolice(int id) {
        policeService.supprimer(id); // Ici, sinistreService doit être l'instance de ton service qui contient la méthode supprimer
    }
    public
    void initialize(URL location, ResourceBundle resources) {
        policeNameColumn.setCellValueFactory(new PropertyValueFactory<>("policeName"));
        policeDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionPolice"));
        sinistreNameColumn.setCellValueFactory(cellData -> {
            Police police = cellData.getValue();
            Sinistre sinistre = police.getSinistre();
            if (sinistre != null) {
                return new ReadOnlyStringWrapper(sinistre.getSin_name());
            }
            return new ReadOnlyStringWrapper(""); // Retourne une chaîne vide si aucun sinistre n'est associé
        });



        initializeActionColumn();
        loadTableData();
    }

    private void loadTableData() {
        ObservableList<Police> data = FXCollections.observableArrayList(policeService.getAll());
        policeTable.setItems(data);
    }

    public void updateTable() {
        ObservableList<Police> data = FXCollections.observableArrayList(policeService.getAll());
        policeTable.setItems(data);
    }






    @FXML
    private void handleAddAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/AddPolice.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Police");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            loadTableData(); // Reload data after adding
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleDeleteAction(ActionEvent event) {
        Police selectedPolice = policeTable.getSelectionModel().getSelectedItem();
        if (selectedPolice != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this police?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                policeService.supprimer(selectedPolice.getId());
                policeTable.getItems().remove(selectedPolice);
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Police deleted successfully.", ButtonType.OK);
                infoAlert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No Police selected for deletion!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAdd(MouseEvent event) {
        try {
            // Charger le fichier FXML pour ajouter un sinistre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/addPolice.fxml"));
            Node eventFXML = loader.load();

            if (contentArea != null) {
                contentArea.getChildren().clear(); // Nettoyer le contenu existant
                contentArea.getChildren().add(eventFXML); // Ajouter le contenu chargé
            } else {
                System.out.println("Erreur : contentArea est null, vérifiez votre fichier FXML.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
