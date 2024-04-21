package Controller;

import Entity.OfferCategory;
import Service.OffreCatService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;


public class Showoffercat  {

    @FXML
    private ListView<OfferCategory> categoryListView;

    @FXML
    private Button addCategoryButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private OffreCatService categoryService = new OffreCatService();

    public void initialize() {
        // Set cell factory for the ListView to display OfferCategory objects
        categoryListView.setCellFactory(new Callback<ListView<OfferCategory>, ListCell<OfferCategory>>() {
            @Override
            public ListCell<OfferCategory> call(ListView<OfferCategory> param) {
                return new ListCell<OfferCategory>() {
                    @Override
                    protected void updateItem(OfferCategory item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // Create a VBox to hold the offer category details
                            VBox vbox = new VBox();
                            vbox.setSpacing(5);

                            // Display offer category details
                            Label nameLabel = new Label("Name: " + item.getCategorie_name());
                            Label descriptionLabel = new Label("Description: " + item.getDescription_cat());

                            // Add offer category details to the VBox
                            vbox.getChildren().addAll(nameLabel, descriptionLabel);

                            // Create an ImageView to display the image
                            public class Showoffcat {
                                @FXML
                                private ImageView piccatoff; // Assuming you have an ImageView named piccatoff in your Showoffcat.fxml

                                public void setImage(Image image) {
                                    // Set the image to the ImageView in your Showoffcat layout
                                    piccatoff.setImage(image);
                                }
                            }





                            // Create an HBox to hold the buttons
                            HBox buttonBox = new HBox();
                            buttonBox.setSpacing(10);

                            // Create Edit and Delete buttons
                            Button editButton = new Button("Edit");
                            Button deleteButton = new Button("Delete");

                            // Set actions for Edit and Delete buttons
                            editButton.setOnAction(event -> editCategory(item));
                            deleteButton.setOnAction(event -> deleteCategory(item));

                            // Add buttons to the HBox
                            buttonBox.getChildren().addAll(editButton, deleteButton);

                            // Create a VBox to hold the offer category details VBox and the buttons HBox
                            VBox containerVBox = new VBox();
                            containerVBox.getChildren().addAll(vbox, buttonBox);

                            // Set the container VBox as the graphic for the ListCell
                            setGraphic(containerVBox);
                        }
                    }

                };
            }
        });

        // Populate ListView data
        try {
            loadListViewData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            Stage stage = (Stage) categoryListView.getScene().getWindow();
            stage.setTitle("Offer Categories");
        });
    }

    private void loadListViewData() throws SQLException {
        ObservableList<OfferCategory> data = FXCollections.observableArrayList(categoryService.getAll());
        categoryListView.setItems(data);
    }

    @FXML
    private void addCategoryButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Addoffercat.fxml"));
            Stage stage = (Stage) addCategoryButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editCategory(OfferCategory category) {
        // Handle edit action here
    }

    private void deleteCategory(OfferCategory category) {
        // Handle delete action here
    }
}