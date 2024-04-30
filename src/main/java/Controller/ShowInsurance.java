package Controller;

import Entity.Insurance;
import Entity.InsuranceCategory;
import Entity.police;
import Service.InsuranceService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowInsurance implements Initializable {

    @FXML
    private ListView<Insurance> tabins;

    @FXML
    private VBox contentArea;


    InsuranceService insuranceService = new InsuranceService();


    private void initializeListView() {
        tabins.setCellFactory(param -> new ListCell<Insurance>() {
            @Override
            protected void updateItem(Insurance item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {


                    // Création d'un HBox pour contenir les labels et les boutons
                    HBox hbox = new HBox(2); // Espace de 10px entre les éléments
                    hbox.setAlignment(Pos.CENTER);

                    Label nameLabel = new Label(item.getName_ins());
                    nameLabel.setMinWidth(120.0);
                    nameLabel.setMaxWidth(130.0);

                    Label descriptionLabel = new Label(Float.toString(item.getMontant())); // Convert float to String
                    descriptionLabel.setMinWidth(120.0);
                    descriptionLabel.setMaxWidth(130.0);
                    descriptionLabel.setWrapText(true); // Enable automatic wrapping
                    descriptionLabel.setStyle("-fx-padding: 5px;");


                    Label categorylabel = new Label(Integer.toString(item.getCatins_id().getId()));
                    categorylabel.setMinWidth(120.0);
                    categorylabel.setMaxWidth(130.0);


                    Label policylabel = new Label(Integer.toString(item.getPol_id().getId()));
                    policylabel.setMinWidth(120.0);
                    policylabel.setMaxWidth(130.0);

                    ArrayList<String> doaList = item.getDoa(); // Retrieve the list of DOA from the Insurance item
                    String firstDoa = ""; // Initialize with an empty string
                    if (doaList != null && !doaList.isEmpty()) {
                        firstDoa = doaList.get(0); // Get the first DOA from the list if it's not empty
                    }
                    Label DOAlabel = new Label(firstDoa);
                    DOAlabel.setMinWidth(120.0);
                    DOAlabel.setMaxWidth(130.0);




// Create the image view
                    String imagePath = "file:///" + item.getIns_image().replace(" ", "%20");
                    System.out.println("Image path: " + imagePath);

// Create the image view
                    ImageView imageView = new ImageView(new Image(imagePath));
// Set the fit width and fit height properties
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);

// Create a label and set the image view as its graphic
                    Label imageLabel = new Label();
                    imageLabel.setGraphic(imageView);



                    // Configuration des boutons avec icônes et texte
                    ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/edit_icon.png")));
                    editIcon.setFitHeight(20); // Taille de l'icône
                    editIcon.setFitWidth(30);
                    Button editButton = new Button("Edit", editIcon);
                    editButton.getStyleClass().add("buttonn");

                    ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/delet_icon.png")));
                    deleteIcon.setFitHeight(20); // Taille de l'icône
                    deleteIcon.setFitWidth(30);
                    Button deleteButton = new Button("Delete", deleteIcon);
                    deleteButton.getStyleClass().add("buttonn");

                    HBox actionBox = new HBox(editButton, deleteButton);
                    actionBox.setSpacing(10);
                    actionBox.setMinWidth(150.0);
                    actionBox.setMaxWidth(150.0);

                    // Configuration des actions des boutons
                    editButton.setOnAction(event -> openEditDialog(item));
                    deleteButton.setOnAction(event -> {
                        deleteSinistre(item.getId());
                        tabins.getItems().remove(item); // Mise à jour immédiate de l'interface
                    });

                    // Ajout des composants au HBox
                    hbox.getChildren().addAll(nameLabel, descriptionLabel,DOAlabel,categorylabel,policylabel, imageLabel, actionBox);
                    setGraphic(hbox); // Utiliser le HBox comme graphique pour la cellule
                    setText(null);
                }
            }
        });
    }



    private void deleteSinistre(int id) {
        insuranceService.deleteInsurance(id); // Ici, sinistreService doit être l'instance de ton service qui contient la méthode supprimer
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Insurance> data = FXCollections.observableArrayList(insuranceService.getAllInsurances());
        tabins.setItems(data);
        initializeListView(); // Configurez le ListView avec un CellFactory personnalisé
        //tabins.setMaxWidth(500);
    }

    private void openEditDialog(Insurance insurance) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditInsurance.fxml"));
            Parent root = loader.load();
            EditInsurance controller = loader.getController();
            controller.initData(insurance);
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
    public void updateSinistreInListView(Insurance updatedSinistre) {
        // Mettre à jour un sinistre spécifique dans la ListView
        ObservableList<Insurance> sinistres = tabins.getItems();
        int index = sinistres.indexOf(updatedSinistre);
        if (index != -1) {
            sinistres.set(index, updatedSinistre);
            tabins.refresh();
        }
    }


    @FXML
    private void handleAddNew(MouseEvent event) {
        try {
            // Charger le fichier FXML pour ajouter un sinistre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddInsurance.fxml"));
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
