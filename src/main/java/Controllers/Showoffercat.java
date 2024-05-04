package Controllers;

import Entities.OfferCategory;

import Services.OffreCatService;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;


public class Showoffercat implements Initializable {

    @FXML
    private ListView<OfferCategory> tab;


    @FXML
    private VBox contentArea;


    @FXML
    private ComboBox<String> sortComboBox;

    OffreCatService offreCatService = new OffreCatService();

    private ObservableList<OfferCategory> allCategories;
    private FilteredList<OfferCategory> filteredCategories;
    @FXML
    private TextField searchField;


    private void initializeListView() {
        tab.setCellFactory(param -> new ListCell<OfferCategory>() {
            @Override
            protected void updateItem(OfferCategory item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Création d'un HBox pour contenir les labels et les boutons
                    HBox hbox = new HBox(10); // Espace de 10px entre les éléments
                    hbox.setAlignment(Pos.CENTER);

                    Label catnameLabel = new Label(item.getCategorie_name());
                    catnameLabel.setMinWidth(170);
                    catnameLabel.setMaxWidth(180);

                    Label descriptioncatLabel = new Label(item.getDescription_cat());
                    descriptioncatLabel.setMinWidth(170);
                    descriptioncatLabel.setMaxWidth(200);
                    descriptioncatLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    descriptioncatLabel.setStyle("-fx-padding: 5px;");

                    ImageView imageView = new ImageView(new Image(item.getCatimg()));
                    imageView.setFitWidth(170); // Set the width of the image
                    imageView.setFitHeight(180); // Set the height of the image
                    Label imageLabel = new Label();
                    imageLabel.setGraphic(imageView);

                    // Configuration des boutons avec icônes et texte
                    ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/edit_icon.png")));
                    editIcon.setFitHeight(20); // Taille de l'icône
                    editIcon.setFitWidth(20);
                    Button editButton = new Button("", editIcon);
                    editButton.getStyleClass().add("buttonn");

                    ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/delet_icon.png")));
                    deleteIcon.setFitHeight(20); // Taille de l'icône
                    deleteIcon.setFitWidth(20);
                    Button deleteButton = new Button("", deleteIcon);
                    deleteButton.getStyleClass().add("buttonn");

                    HBox actionBox = new HBox(editButton, deleteButton);
                    actionBox.setSpacing(10);
                    actionBox.setMinWidth(150.0);
                    actionBox.setMaxWidth(150.0);

                    // Configuration des actions des boutons
                    editButton.setOnAction(event -> openEditDialog(item));
                    deleteButton.setOnAction(event -> {
                        DeleteCatOff(item.getId());
                        tab.getItems().remove(item); // Mise à jour immédiate de l'interface
                    });

                    // Ajout des composants au HBox
                    hbox.getChildren().addAll(catnameLabel,descriptioncatLabel,imageLabel,actionBox);
                    setGraphic(hbox); // Utiliser le HBox comme graphique pour la cellule
                }
            }
        });
    }


    private void DeleteCatOff(int id) {
        offreCatService.DeleteCatOff(id); // Ici, sinistreService doit être l'instance de ton service qui contient la méthode supprimer
    }


    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<OfferCategory> data = FXCollections.observableArrayList(offreCatService.getAll());
        tab.setItems(data);
        initializeListView(); // Configurez le ListView avec un CellFactory personnalisé
        allCategories = FXCollections.observableArrayList(offreCatService.getAll());
        tab.setItems(allCategories);

        sortComboBox.getItems().addAll("Name (A-Z)","Description (A-Z)");
        sortComboBox.setValue("Name (A-Z)");

        sortComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Name (A-Z)".equals(newValue)) {
                allCategories.sort(Comparator.comparing(OfferCategory::getCategorie_name));
            }else if ("Description (A-Z)".equals(newValue)) {
                allCategories.sort(Comparator.comparing(OfferCategory::getDescription_cat));
            }
            tab.refresh(); // Refresh the ListView after sorting
        });
        allCategories = FXCollections.observableArrayList(offreCatService.getAll());
        filteredCategories = new FilteredList<>(allCategories, p -> true);
        tab.setItems(filteredCategories);

        // Apply search filter when text changes in the searchField
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCategories.setPredicate(category -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true; // Display all items if the search text is empty
                }

                String searchText = newValue.toLowerCase();

                // Check if category name or description contains the search text
                return category.getCategorie_name().toLowerCase().contains(searchText)
                        || category.getDescription_cat().toLowerCase().contains(searchText);
            });
        });
    }


    private void openEditDialog(OfferCategory offerCategory) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Updateoffcat.fxml"));
            Parent root = loader.load();
            Updateoffcat controller = loader.getController();
            controller.initData(offerCategory);
            controller.setUpdateCallback(this::updateOffreCatInListView);  // Utilisez la bonne méthode de callback

            Stage stage = new Stage();
            stage.setTitle("Edit Offer Category");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateOffreCatInListView(OfferCategory updatedOffrecat) {
        // Mettre à jour un sinistre spécifique dans la ListView
        ObservableList<OfferCategory> offerCategories = tab.getItems();
        int index = offerCategories.indexOf(updatedOffrecat);
        if (index != -1) {
            offerCategories.set(index, updatedOffrecat);
            tab.refresh();
        }
    }


    @FXML
    private void handleAddNew(MouseEvent event) {
        try {
            // Charger le fichier FXML pour ajouter un sinistre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Addoffercat.fxml"));
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
