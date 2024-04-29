package tn.esprit.applicatiopnpi.controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.SinistreService;

public class AffichageSinistre implements Initializable {
    @FXML
    private ListView<Sinistre> tab;


    @FXML
    private TextField searchField;



    private FilteredList<Sinistre> filteredData;



    @FXML
    private VBox contentArea;


    SinistreService sinistreService = new SinistreService();


    private void initializeListView() {
        tab.setCellFactory(param -> new ListCell<Sinistre>() {
            @Override
            protected void updateItem(Sinistre item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Création d'un HBox pour contenir les labels et les boutons
                    HBox hbox = new HBox(10); // Espace de 10px entre les éléments
                    hbox.setAlignment(Pos.CENTER);

                    Label nameLabel = new Label(item.getSin_name());
                    nameLabel.setMinWidth(185.0);
                    nameLabel.setMaxWidth(185.0);

                    Label descriptionLabel = new Label(item.getDescription_sin());
                    descriptionLabel.setMinWidth(173.0);
                    descriptionLabel.setMaxWidth(173.0);
                    descriptionLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    descriptionLabel.setStyle("-fx-padding: 5px;");

                    Label pathLabel = new Label(item.getImage_path());
                    pathLabel.setMinWidth(167.0);
                    pathLabel.setMaxWidth(167.0);

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

                    // Configuration des actions des boutons
                    editButton.setOnAction(event -> openEditDialog(item));
                    deleteButton.setOnAction(event -> {
                        deleteSinistre(item.getId());
                        tab.getItems().remove(item); // Mise à jour immédiate de l'interface
                    });

                    // Ajout des composants au HBox
                    hbox.getChildren().addAll(nameLabel, descriptionLabel, pathLabel, actionBox);
                    setGraphic(hbox); // Utiliser le HBox comme graphique pour la cellule
                }
            }
        });
    }



    private void deleteSinistre(int id) {
        sinistreService.supprimer(id); // Ici, sinistreService doit être l'instance de ton service qui contient la méthode supprimer
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Sinistre> data = FXCollections.observableArrayList(sinistreService.getAll());
        tab.setItems(data);
        initializeListView();
        // Configurez le ListView avec un CellFactory personnalisé
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                rechercherSIN();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    private void rechercherSIN() throws SQLException {
        // Clear existing items from the ListView
        tab.getItems().clear();

        // Fetch the search term entered by the user
        String searchTerm = searchField.getText().trim().toLowerCase();

        // Fetch all sinistres from the database
        List<Sinistre> sinistreList = sinistreService.getAll(); // Assuming afficherSinistres() is the correct method

        // If the search field is empty, display all sinistres
        if (searchTerm.isEmpty()) {
            tab.getItems().addAll(sinistreList);
        } else {
            // Otherwise, filter sinistres that match the search term
            for (Sinistre sinistre : sinistreList) {
                // Adapt this condition based on your search logic
                if (sinistre.getSin_name().toLowerCase().contains(searchTerm)
                        || sinistre.getDescription_sin().toLowerCase().contains(searchTerm)) {
                    tab.getItems().add(sinistre);
                }
            }
        }
    }














    private void openEditDialog(Sinistre sinistre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/EditSinister.fxml"));
            Parent root = loader.load();
            EditSinister controller = loader.getController();
            controller.initData(sinistre);
            controller.setUpdateCallback(this::updateSinistreInListView);  // Utilisez la bonne méthode de callback

            Stage stage = new Stage();
            stage.setTitle("Edit Sinistre");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateSinistreInListView(Sinistre updatedSinistre) {
        // Mettre à jour un sinistre spécifique dans la ListView
        ObservableList<Sinistre> sinistres = tab.getItems();
        int index = sinistres.indexOf(updatedSinistre);
        if (index != -1) {
            sinistres.set(index, updatedSinistre);
            tab.refresh();
        }
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


    public void handleSearch(MouseEvent mouseEvent) {
    }
}
