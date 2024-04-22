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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowInsurance {

    @FXML
    private ListView<Insurance> insuranceListView;

    @FXML
    private Button addinsurance;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private InsuranceService ins = new InsuranceService();

    public void initialize() {
        // Set cell factory for the ListView to display Insurance objects
        insuranceListView.setCellFactory(new Callback<ListView<Insurance>, ListCell<Insurance>>() {
            @Override
            public ListCell<Insurance> call(ListView<Insurance> param) {

                return new ListCell<Insurance>() {
                    @Override
                    protected void updateItem(Insurance item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // Create a VBox to hold the insurance details
                            VBox vbox = new VBox();
                            vbox.setSpacing(5);

                            // Display insurance details
                            Label nameLabel = new Label("Name: " + item.getName_ins());
                            Label amountLabel = new Label("Amount: " + item.getMontant());
                            Label imageLabel = new Label("Image: " + item.getIns_image());
                            Label categoriesLabel = new Label("Categories: " + item.getCatins_id());
                            Label policeLabel = new Label("Police: " + item.getPol_id());

                            // Add insurance details to the VBox
                            vbox.getChildren().addAll(nameLabel, amountLabel, imageLabel, categoriesLabel, policeLabel);

                            // Create an HBox to hold the buttons
                            HBox buttonBox = new HBox();
                            buttonBox.setSpacing(10);

                            // Create Edit and Delete buttons
                            Button editButton = new Button("Edit");
                            Button deleteButton = new Button("Delete");

                            // Set actions for Edit and Delete buttons
                            editButton.setOnAction(event -> {
                                // Handle edit action here
                            });
                            deleteButton.setOnAction(event -> {
                                // Handle delete action here
                            });

                            // Add buttons to the HBox
                            buttonBox.getChildren().addAll(editButton, deleteButton);

                            // Create a VBox to hold the insurance details VBox and the buttons HBox
                            VBox containerVBox = new VBox();
                            containerVBox.getChildren().addAll(vbox, buttonBox);

                            // Set the container VBox as the graphic for the ListCell
                            setGraphic(containerVBox);
                        }
                    }



                };
            }
        });
        addinsurance.getStyleClass().add("custom-button");
        insuranceListView.getStyleClass().add("custom-list-view");

        // Populate ListView data
        try {
            loadListViewData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Handle ListView selection
        insuranceListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Insurance selectedInsurance = insuranceListView.getSelectionModel().getSelectedItem();
                // Here you can access the selected insurance attributes and perform any actions
            }
        });
        Platform.runLater(() -> {
            Scene scene = insuranceListView.getScene();
            if (scene != null) {
                scene.getStylesheets().add(getClass().getResource("ShowInsuranceStyle.css").toExternalForm());
            }
        });

    }

    private void loadListViewData() throws SQLException {
        ObservableList<Insurance> data = FXCollections.observableArrayList(ins.getAllInsurances());
        insuranceListView.setItems(data);
    }

    @FXML
    private void AddInsButton() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddInsurance.fxml"));
            Parent root = loader.load();
            Scene currentScene = addinsurance.getScene();
            currentScene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void editButtonClicked() {
        // Handle edit button action here
    }

    @FXML
    private void deleteButtonClicked() {
        // Handle delete button action here
    }
}
