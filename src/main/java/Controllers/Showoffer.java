package Controllers;

import Entities.Offre;
import Services.OffreService;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;


public class Showoffer implements Initializable {
    @FXML
    public ListView<Offre> listView;

    private ObservableList<Offre> allUsers;

    @FXML
    private Button nextButton;
    private final int itemsPerPage = 7;
    private int currentPageIndex = 0;
    @FXML
    private Button previousButton;
    @FXML
    private ListView<Offre> tab;


    @FXML
    private VBox contentArea;
    @FXML
    private ComboBox<String> sortComboBox;

    private ObservableList<Offre> allOffres;


    OffreService offreService = new OffreService();
    private FilteredList<Offre> filteredoffres;
    @FXML
    private TextField searchField;
    @FXML
    private Pagination pagination;


    private static final int ITEMS_PER_PAGE = 3;


    private void initializeListView() {
        tab.setCellFactory(param -> new ListCell<Offre>() {
            @Override
            protected void updateItem(Offre item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Création d'un HBox pour contenir les labels et les boutons
                    HBox hbox = new HBox(5); // Espace de 10px entre les éléments
                    hbox.setAlignment(Pos.CENTER_LEFT);

                    Label nameLabel = new Label(item.getAdvantage());
                    nameLabel.setMinWidth(170);
                    nameLabel.setMaxWidth(180);

                    Label conditionLabel = new Label(item.getConditions());
                    conditionLabel.setMinWidth(170);
                    conditionLabel.setMaxWidth(180);
                    conditionLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    conditionLabel.setStyle("-fx-padding: 5px;");


                    Label durationlabel = new Label(item.getDuration());
                    durationlabel.setMinWidth(170);
                    durationlabel.setMaxWidth(180);
                    durationlabel.setWrapText(true); // Activer le retour à la ligne automatique
                    durationlabel.setStyle("-fx-padding: 5px;");



                    Label discountLabel = new Label(String.valueOf(item.getDiscount()));
                    discountLabel.setMinWidth(170);
                    discountLabel.setMaxWidth(180);
                    discountLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    discountLabel.setStyle("-fx-padding: 5px;");



                    ImageView imageView = new ImageView(new Image(item.getOffreimg()));
                    imageView.setFitWidth(100); // Set the width of the image
                    imageView.setFitHeight(100); // Set the height of the image
                    Label imageLabel = new Label();
                    imageLabel.setGraphic(imageView); // Set the ImageView as the graphic content of the label


                    // Configuration des boutons avec icônes et texte
                    ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/tn/esprit/applicatiopnpi/edit_icon.png")));
                    editIcon.setFitHeight(20); // Taille de l'icône
                    editIcon.setFitWidth(20);
                    Button editButton = new Button("", editIcon);
                    editButton.getStyleClass().add("button2");

                    ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/tn/esprit/applicatiopnpi/delet_icon.png")));
                    deleteIcon.setFitHeight(20); // Taille de l'icône
                    deleteIcon.setFitWidth(20);
                    Button deleteButton = new Button("", deleteIcon);
                    deleteButton.getStyleClass().add("button2");

                    HBox actionBox = new HBox(editButton, deleteButton);
                    actionBox.setSpacing(10);
                    actionBox.setMinWidth(150.0);
                    actionBox.setMaxWidth(150.0);

                    // Configuration des actions des boutons
                    editButton.setOnAction(event -> openEditDialog(item));
                    deleteButton.setOnAction(event -> {
                        deleteOffre(item.getId_off());
                        tab.getItems().remove(item); // Mise à jour immédiate de l'interface
                    });

                    // Ajout des composants au HBox
                    hbox.getChildren().addAll(nameLabel, conditionLabel,durationlabel,discountLabel, imageLabel, actionBox);
                    setGraphic(hbox); // Utiliser le HBox comme graphique pour la cellule
                }
            }
        });
    }



    private void deleteOffre(int offreId) {
        offreService.deleteOffre(offreId); // Ici, sinistreService doit être l'instance de ton service qui contient la méthode supprimer
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Offre> data = FXCollections.observableArrayList(offreService.getAll());
        tab.setItems(data);
        initializeListView(); // Configurez le ListView avec un CellFactory personnalisé
        allOffres = FXCollections.observableArrayList(offreService.getAll());
        tab.setItems(allOffres);

        sortComboBox.getItems().addAll("Avantage (A-Z)", "Duration (LOW-HIGH)");
        sortComboBox.setValue("Avantage (A-Z)");

        sortComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Avantage (A-Z)".equals(newValue)) {
                allOffres.sort(Comparator.comparing(Offre::getAdvantage));
            } else if ("Duration (LOW-HIGH)".equals(newValue)) {
                allOffres.sort(Comparator.comparing(Offre::getDuration));
            }
            tab.refresh(); // Refresh the ListView after sorting
        });

        allOffres = FXCollections.observableArrayList(offreService.getAll());
        filteredoffres= new FilteredList<>(allOffres, p -> true);
        tab.setItems(filteredoffres);

        // Apply search filter when text changes in the searchField
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredoffres.setPredicate(offre -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true; // Display all items if the search text is empty
                }

                String searchText = newValue.toLowerCase();

                // Check if any attribute of Offre contains the search text
                return offre.getAdvantage().toLowerCase().contains(searchText)
                        || offre.getConditions().toLowerCase().contains(searchText)
                        || offre.getDuration().toLowerCase().contains(searchText)
                        || String.valueOf(offre.getDiscount()).toLowerCase().contains(searchText);
            });
        });

    }





    private void openEditDialog(Offre offre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Updateoffer.fxml"));
            Parent root = loader.load();
            Updateoff controller = loader.getController();
            controller.initData(offre);
            controller.setUpdateCallback(this::updateOffreInListView);  // Utilisez la bonne méthode de callback

            Stage stage = new Stage();
            stage.setTitle("Edit Offer");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateOffreInListView(Offre updatedOffre) {
        // Mettre à jour un sinistre spécifique dans la ListView
        ObservableList<Offre> offres = tab.getItems();
        int index = offres.indexOf(updatedOffre);
        if (index != -1) {
            offres.set(index, updatedOffre);
            tab.refresh();
        }
    }


    @FXML
    private void handleAddNew(MouseEvent event) {
        try {
            // Charger le fichier FXML pour ajouter un sinistre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Addoffer.fxml"));
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