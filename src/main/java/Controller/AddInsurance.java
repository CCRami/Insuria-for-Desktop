package Controller;

import Entity.Insurance;
import Entity.InsuranceCategory;
import Entity.police;
import Service.InsuranceCatService;
import Service.InsuranceService;
import Service.policeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class AddInsurance {

    @FXML
    private TextField nameins; // Field for insurance name

    @FXML
    private TextField amount; // Field for insurance amount

    @FXML
    private ImageView insuranceImageView; // Field for insurance image path

    @FXML
    private Button save; // Save button

    @FXML
    private VBox fieldsContainer; // Reference to the VBox containing dynamic fields

    @FXML
    private ComboBox<InsuranceCategory> categoryComboBox;

    @FXML
    private ComboBox<police> policeComboBox;

    private ArrayList<String> dynamicFieldValues = new ArrayList<>();

    private InsuranceCatService insuranceCatService;
    private InsuranceService insuranceService;
    private policeService policeService;

    public AddInsurance() {
        this.insuranceCatService = new InsuranceCatService();
        this.insuranceService = new InsuranceService();
        this.policeService = new policeService();
    }

    @FXML
    private void addDynamicField() {
        // Create a new TextField for the dynamic field
        TextField newTextField = new TextField();
        newTextField.setPrefHeight(34.0);
        newTextField.setPrefWidth(229.0);
        newTextField.setPromptText("Enter another field");

        // Apply the same style classes as the existing fields
        newTextField.getStyleClass().addAll("search", "shadow");

        // Create a new HBox to hold the dynamic field
        HBox newHBox = new HBox();
        newHBox.setSpacing(10.0);
        newHBox.getChildren().add(newTextField);

        // Add the new HBox with the dynamic field to the fieldsContainer VBox
        fieldsContainer.getChildren().add(newHBox);

        // Add event listener to dynamically added TextField
        newTextField.setOnAction(event -> {
            // When Enter key is pressed in the TextField, add its value to the ArrayList
            String value = newTextField.getText();
            dynamicFieldValues.add(value);
            // Optionally, you can perform any additional actions here
        });
    }

    @FXML
    private void initialize() {
        populateCategoryComboBox();
        populatePoliceComboBox();
    }

    private void populateCategoryComboBox() {
        List<InsuranceCategory> categories = insuranceCatService.getAll();
        categoryComboBox.getItems().addAll(categories);
    }

    private void populatePoliceComboBox() {
        List<police> policeList = policeService.getAll();
        policeComboBox.getItems().addAll(policeList);
    }

    @FXML
    void addInsuranceAction(ActionEvent event) {
        if (isInputValid()) {
            String insuranceName = nameins.getText();
            float insuranceAmount = 0.0f;

            try {
                insuranceAmount = Float.parseFloat(amount.getText());
            } catch (NumberFormatException e) {

                e.printStackTrace();

            }

            Image image = insuranceImageView.getImage();
            String insuranceImageUrl = image != null ? image.getUrl() : null;

            // Get selected InsuranceCategory
            InsuranceCategory selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();

            // Get selected police
            police selectedPolice = policeComboBox.getSelectionModel().getSelectedItem();

            // Initialize ArrayList for dynamic fields
            ArrayList<String> dynamicFields = new ArrayList<>();

            // Add dynamic field values to the ArrayList
            for (Node node : fieldsContainer.getChildren()) {
                if (node instanceof HBox) {
                    HBox hBox = (HBox) node;
                    for (Node childNode : hBox.getChildren()) {
                        if (childNode instanceof TextField) {
                            TextField textField = (TextField) childNode;
                            String fieldValue = textField.getText();
                            dynamicFields.add(fieldValue);
                        }
                    }
                }
            }

            // Create an instance of your Insurance entity
            Insurance insurance = new Insurance(insuranceName, insuranceAmount, insuranceImageUrl, dynamicFields, selectedCategory, selectedPolice);

            // Perform any other actions, such as saving to a database
            insuranceService.addInsurance(insurance);

            // Clear input fields
            nameins.clear();
            amount.clear();

            // Show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Insurance added successfully");
            alert.showAndWait();
        }
    }



    @FXML
    public void chooseImageAction(ActionEvent actionEvent) {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        // Show the file chooser dialog
        Stage stage = (Stage) save.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Check if a file was selected
        if (selectedFile != null) {
            try {
                // Create the images directory if it doesn't exist
                File imagesDir = new File("images");
                if (!imagesDir.exists()) {
                    imagesDir.mkdir();
                }

                // Copy the selected image file to the images directory
                Path sourcePath = selectedFile.toPath();
                Path targetPath = Paths.get("images", selectedFile.getName());
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

                // Load the copied image and display it
                Image image = new Image(targetPath.toUri().toString());
                insuranceImageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error saving image: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private boolean isInputValid() {
        // Perform input validation here if needed
        return true;
    }
}
