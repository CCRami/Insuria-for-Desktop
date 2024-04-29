package tn.esprit.applicatiopnpi.controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AffichagePolice implements Initializable {
    @FXML
    private ListView<Police> policeList;
    @FXML
    private VBox contentArea;
    @FXML
    private TextField searchField;




    private PoliceService policeService = new PoliceService(); // Assuming you have a service class to handle database operations
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListView();
        loadPoliceData();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                rechercherPol();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    private void rechercherPol() throws SQLException {
        // Clear existing items from the ListView
        policeList.getItems().clear();

        // Fetch the search term entered by the user
        String searchTerm = searchField.getText().trim().toLowerCase();

        // Fetch all sinistres from the database
        List<Police> sinistreList = policeService.getAll(); // Assuming afficherSinistres() is the correct method

        // If the search field is empty, display all sinistres
        if (searchTerm.isEmpty()) {
            policeList.getItems().addAll(sinistreList);
        } else {
            // Otherwise, filter sinistres that match the search term
            for (Police police : sinistreList) {
                // Adapt this condition based on your search logic
                if (police.getPoliceName().toLowerCase().contains(searchTerm)
                        || police.getDescriptionPolice().toLowerCase().contains(searchTerm)) {
                    policeList.getItems().add(police);
                }
            }
        }
    }

    private void initializeListView() {
        policeList.setCellFactory(param -> new ListCell<Police>() {
            @Override
            protected void updateItem(Police police, boolean empty) {
                super.updateItem(police, empty);
                if (empty || police == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10);
                    hbox.setAlignment(Pos.CENTER);
                    Label nameLabel = new Label(police.getPoliceName());
                    nameLabel.setMinWidth(185.0);
                    nameLabel.setMaxWidth(185.0);
                    Label descriptionLabel = new Label(police.getDescriptionPolice());
                    descriptionLabel.setMinWidth(173.0);
                    descriptionLabel.setMaxWidth(173.0);
                    descriptionLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    descriptionLabel.setStyle("-fx-padding: 5px;");
                    Label sinistreNameLabel = new Label(police.getSinistre() != null ? police.getSinistre().getSin_name() : "No Sinistre");
                    sinistreNameLabel.setMinWidth(167.0);
                    sinistreNameLabel.setMaxWidth(167.0);

                    // Configuration des boutons avec icônes et texte
                    ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/tn/esprit/applicatiopnpi/edit_icon.png")));
                    editIcon.setFitHeight(20); // Taille de l'icône
                    editIcon.setFitWidth(20);
                    Button editButton = new Button("Edit", editIcon);
                    editButton.getStyleClass().add("button2");

                    ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/tn/esprit/applicatiopnpi/delet_icon.png")));
                    deleteIcon.setFitHeight(20); // Taille de l'icône
                    deleteIcon.setFitWidth(20);
                    Button deleteButton = new Button("Delete", deleteIcon);
                    deleteButton.getStyleClass().add("button2");

                    HBox actionBox = new HBox(editButton, deleteButton);
                    actionBox.setSpacing(10);
                    actionBox.setMinWidth(150.0);
                    actionBox.setMaxWidth(150.0);

                    editButton.setOnAction(e -> openDialog(police));
                    deleteButton.setOnAction(e -> {
                        policeService.supprimer(police.getId());
                        policeList.getItems().remove(police);
                    });

                    hbox.getChildren().addAll(nameLabel, descriptionLabel, sinistreNameLabel,actionBox);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void loadPoliceData() {
        ObservableList<Police> data = FXCollections.observableArrayList(policeService.getAll());
        policeList.setItems(data);
    }

    private void openDialog(Police police) {
        // Open a dialog or another stage to edit Police
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/EditPolice.fxml"));
            Parent root = loader.load();
            EditPolice controller = loader.getController();
            controller.intData(police);
            controller.setUpdateCallback(this::updatePoliceInListView);  // Utilisez la bonne méthode de callback

            Stage stage = new Stage();
            stage.setTitle("Edit Police");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updatePoliceInListView(Police updatedPolice) {
        // Mettre à jour un sinistre spécifique dans la ListView
        ObservableList<Police> polices = policeList.getItems();
        int index = polices.indexOf(updatedPolice);
        if (index != -1) {
            polices.set(index, updatedPolice);
            policeList.refresh();
        }
    }




    @FXML
    private void handleAddNew(MouseEvent event) {
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
