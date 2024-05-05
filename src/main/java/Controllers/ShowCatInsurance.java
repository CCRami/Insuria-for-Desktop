package Controllers;

import Entities.InsuranceCategory;
import Services.InsuranceCatService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowCatInsurance implements Initializable {

    @FXML
    private ListView<InsuranceCategory> tab;

    @FXML
    private VBox contentArea;


    InsuranceCatService InsCatService = new InsuranceCatService();


    private void initializeListView() {
        tab.setCellFactory(param -> new ListCell<InsuranceCategory>() {
            @Override
            protected void updateItem(InsuranceCategory item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Création d'un HBox pour contenir les labels et les boutons
                    HBox hbox = new HBox(10); // Espace de 10px entre les éléments
                    hbox.setAlignment(Pos.CENTER);

                    Label nameLabel = new Label(item.getName_cat_ins());
                    nameLabel.setMinWidth(185.0);
                    nameLabel.setMaxWidth(185.0);

                    Label descriptionLabel = new Label(item.getDesc_cat_ins());
                    descriptionLabel.setMinWidth(173.0);
                    descriptionLabel.setMaxWidth(173.0);
                    descriptionLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    descriptionLabel.setStyle("-fx-padding: 5px;");



                    // Configuration des boutons avec icônes et texte
                    ImageView editIcon = null;
                    ImageView deleteIcon = null;
                    try {
                        editIcon = new ImageView(new Image(getClass().getResourceAsStream("/tn/esprit/applicatiopnpi/edit_icon.png")));
                        editIcon.setFitHeight(20);
                        editIcon.setFitWidth(20);
                        deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/tn/esprit/applicatiopnpi/delet_icon.png")));
                        deleteIcon.setFitHeight(20);
                        deleteIcon.setFitWidth(20);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        // Handle the error gracefully, for example:
                        // editIcon = new ImageView(); // Provide a default image
                        // deleteIcon = new ImageView(); // Provide a default image
                    }

                    Button editButton = new Button("Edit", editIcon);
                    editButton.getStyleClass().add("button2");

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
                    hbox.getChildren().addAll(nameLabel, descriptionLabel, actionBox);
                    setGraphic(hbox); // Utiliser le HBox comme graphique pour la cellule
                }
            }
        });
    }



    private void deleteSinistre(int id) {
        InsCatService.deleteCatIns(id); // Ici, sinistreService doit être l'instance de ton service qui contient la méthode supprimer
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<InsuranceCategory> data = FXCollections.observableArrayList(InsCatService.getAll());
        tab.setItems(data);
        initializeListView(); // Configurez le ListView avec un CellFactory personnalisé
    }












    private void openEditDialog(InsuranceCategory Inscat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditCatInsurance.fxml"));
            Parent root = loader.load();
            EditCatInsurance controller = loader.getController();
            controller.initData(Inscat);
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
    public void updateSinistreInListView(InsuranceCategory updatedSinistre) {
        // Mettre à jour un sinistre spécifique dans la ListView
        ObservableList<InsuranceCategory> sinistres = tab.getItems();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCatInsurance.fxml"));
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
