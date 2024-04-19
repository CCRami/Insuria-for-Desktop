package tn.esprit.applicatiopnpi.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.SinistreService;

public class AffichageSinistre implements Initializable {
    @FXML private TableView<Sinistre> tab;
    @FXML private TableColumn<Sinistre, String> nom;
    @FXML private TableColumn<Sinistre, String> description_sinistre;
    @FXML private TableColumn<Sinistre, String> path;
    @FXML
    private TableColumn<Sinistre, Void> actionCol;


    @FXML private TextField sinName;
    @FXML private TextField sinDescription;
    @FXML private TextField sinImagePath;

    @FXML private Button addButton, deleteButton, editButton;
    @FXML
    ImageView eventView;



    @FXML
    private BorderPane myBorderPane;

    @FXML
    private VBox vboxdash;
    @FXML
    private VBox contentArea;


    SinistreService sinistreService = new SinistreService();

    int selectedId;
    private void initializeActionColumn() {
        actionCol.setCellFactory(param -> new TableCell<Sinistre, Void>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox pane = new HBox(editBtn, deleteBtn);

            {
                pane.setSpacing(10);
                editBtn.setOnAction(event -> {
                    Sinistre sinistre = getTableView().getItems().get(getIndex());
                    openEditDialog(sinistre);
                });
                deleteBtn.setOnAction(event -> {
                    Sinistre sinistre = getTableView().getItems().get(getIndex());
                    deleteSinistre(sinistre.getId()); // Utilise l'ID pour supprimer l'entrée
                    getTableView().getItems().remove(sinistre); // Met à jour l'interface immédiatement
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });

    }

    private void deleteSinistre(int id) {
        sinistreService.supprimer(id); // Ici, sinistreService doit être l'instance de ton service qui contient la méthode supprimer
    }

            @Override
    public void initialize(URL location, ResourceBundle resources) {
        nom.setCellValueFactory(new PropertyValueFactory<>("sin_name"));
        description_sinistre.setCellValueFactory(new PropertyValueFactory<>("description_sin"));
        path.setCellValueFactory(new PropertyValueFactory<>("image_path"));

        initializeActionColumn();
        loadTableData();
    }

    private void loadTableData() {
        ObservableList<Sinistre> data = FXCollections.observableArrayList(sinistreService.getAll());
        tab.setItems(data);
    }

    @FXML
    private void addButtonAction(ActionEvent event) {
        Sinistre sinistre = new Sinistre(0, sinName.getText(), sinDescription.getText(), sinImagePath.getText());
        sinistreService.ajouter(sinistre);
        updateTable();
    }

    @FXML
    private void editButtonAction(ActionEvent event) {
        Sinistre sinistre = tab.getSelectionModel().getSelectedItem();
        sinistre.setSin_name(sinName.getText());
        sinistre.setDescription_sin(sinDescription.getText());
        sinistre.setImage_path(sinImagePath.getText());
        sinistreService.modifier(sinistre);
        updateTable();
    }

    @FXML
    private void deleteButtonAction(ActionEvent event) {
        int selectedId = tab.getSelectionModel().getSelectedItem().getId();
        sinistreService.supprimer(selectedId);
        updateTable();
    }

    public void updateTable() {
        ObservableList<Sinistre> data = FXCollections.observableArrayList(sinistreService.getAll());
        tab.setItems(data);
    }

    private void clearFields() {
        sinName.clear();
        sinDescription.clear();
        sinImagePath.clear();
    }
    private void editSinistre(Sinistre sinistre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/EditSinistre.fxml"));
            Parent root = loader.load();

            EditSinister controller = loader.getController();
            controller.initData(sinistre);

            Stage stage = new Stage();
            stage.setTitle("Edit Sinistre");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openEditDialog(Sinistre sinistre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/EditSinister.fxml"));
            Parent root = loader.load();
            EditSinister controller = loader.getController();
            controller.initData(sinistre);
            controller.setUpdateCallback(this::updateSinistreInTableView);  // Utilisez la bonne méthode de callback

            Stage stage = new Stage();
            stage.setTitle("Edit Sinistre");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateSinistreInTableView(Sinistre updatedSinistre) {
        ObservableList<Sinistre> sinistres = tab.getItems();
        for (int i = 0; i < sinistres.size(); i++) {
            if (sinistres.get(i).getId() == updatedSinistre.getId()) {
                sinistres.set(i, updatedSinistre);
                break;
            }
        }
        tab.refresh();  // N'oubliez pas de rafraîchir la TableView
    }


    @FXML
    private void handleAddNew(MouseEvent event) {
        try {
            // Charger le fichier FXML pour ajouter un sinistre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/addSiniter.fxml"));
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
