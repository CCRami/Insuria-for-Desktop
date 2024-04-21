/*package Controller;

import Entity.Offre;
import Service.OffreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class Showoffer {

    @FXML
    private ListView<Offre> offreListView;

    @FXML
    private Button Addoffer;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private OffreService offreService = new OffreService();

    public void initialize() {
        setupListView();
        setupButtons();

        try {
            loadListViewData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupListView() {
        offreListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Offre item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox vbox = new VBox();
                    vbox.setSpacing(5);

                    Label advantageLabel = new Label("Advantage: " + item.getAdvantage());
                    Label conditionsLabel = new Label("Conditions: " + item.getConditions());
                    Label durationLabel = new Label("Duration: " + item.getDuration());
                    Label discountLabel = new Label("Discount: " + item.getDiscount());
                    Label imageLabel = new Label("Image: " + item.getOffreimg());
                    Label categoryLabel = new Label("Category: " + item.getId_catoff());

                    vbox.getChildren().addAll(advantageLabel, conditionsLabel, durationLabel, discountLabel, imageLabel, categoryLabel);

                    HBox buttonBox = new HBox();
                    buttonBox.setSpacing(10);

                    Button editButton = new Button("Edit");
                    Button deleteButton = new Button("Delete");

                    editButton.setOnAction(event -> editButtonClicked(item));
                    deleteButton.setOnAction(event -> deleteButtonClicked(item));

                    buttonBox.getChildren().addAll(editButton, deleteButton);

                    VBox containerVBox = new VBox();
                    containerVBox.getChildren().addAll(vbox, buttonBox);

                    setGraphic(containerVBox);
                }
            }
        });
    }

    private void setupButtons() {
        Addoffer.getStyleClass().add("custom-button");
        offreListView.getStyleClass().add("custom-list-view");
    }



    private void loadListViewData() {
        OffreService offreService = new OffreService(); // Instantiate OffreService
        List<Offre> offreList = offreService.getAll(); // Call getAll() method

        // Convert the list to an ObservableList
        ObservableList<Offre> data = FXCollections.observableArrayList(offreList);

        // Assuming you have already initialized offreListView in your controller
        offreListView.setItems(data);
    }



    @FXML
    private void AddoffButton() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Addoffer.fxml"));
            Parent root = loader.load();
            Scene currentScene = Addoffer.getScene();
            currentScene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void editButtonClicked(Offre offre) {
        // Handle edit button action here
    }

    private void deleteButtonClicked(Offre offre) {
        // Handle delete button action here
    }
}
*/