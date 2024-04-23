package tn.esprit.applicatiopnpi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.SinistreService;

import java.io.File;
import java.net.URL;

public class AddSiniter {
    @FXML private TextField nameField;
    @FXML private TextArea descriptionField;
    @FXML private TextField imagePathField;
    @FXML private ImageView imageView;
    @FXML private Label errorName;
    @FXML private Label errorDescription;
    @FXML private Label errorImagePath;

    private SinistreService sinistreService = new SinistreService();

    @FXML
    private void handleSave(ActionEvent event) {
        if (validateInput()) {
            Sinistre newSinistre = new Sinistre();
            newSinistre.setSin_name(nameField.getText().trim());
            newSinistre.setDescription_sin(descriptionField.getText().trim());
            newSinistre.setImage_path(imagePathField.getText().replace("\\", "/").trim());

            try {
                sinistreService.add(newSinistre);
                clearFields();
            } catch (RuntimeException e) {
                errorName.setText("Failed to add Sinistre: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleBrowse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePathField.setText(file.getAbsolutePath().replace("\\", "/"));
            try {
                Image image = new Image(file.toURI().toURL().toExternalForm());
                imageView.setImage(image);
            } catch (Exception e) {
                showAlert("Loading Error", "Failed to load the image file.");
                imagePathField.setText("");
            }
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        nameField.clear();
        descriptionField.clear();
        imagePathField.clear();
        imageView.setImage(null);
        errorName.setText("");
        errorDescription.setText("");
        errorImagePath.setText("");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean validateInput() {
        boolean isValid = true;
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            errorName.setText("Name field cannot be empty!");
            isValid = false;
        } else {
            if (Character.isLowerCase(name.charAt(0))) {
                errorName.setText("Name must start with an uppercase letter!");
                isValid = false;
            }
            if (name.length() > 30) {
                errorName.setText("Name must not exceed 30 characters!");
                isValid = false;
            }
            if (sinistreService.isNameExist(name)) {
                errorName.setText("Name must be unique!");
                isValid = false;
            }
        }
        if (descriptionField.getText().trim().isEmpty()) {
            errorDescription.setText("Description field cannot be empty!");
            isValid = false;
        } else {
            String description = descriptionField.getText().trim();

            // Check if the description exceeds 400 characters
            if (description.length() > 400) {
                errorDescription.setText("Description must not exceed 400 characters!");
                isValid = false;
            }
            // Check if the description starts with an uppercase letter
            else if (!Character.isUpperCase(description.charAt(0))) {
                errorDescription.setText("Description must start with an uppercase letter!");
                isValid = false;
            }
            // Check if the description ends with a period
            else if (!description.endsWith(".")) {
                errorDescription.setText("Description must end with a period!");
                isValid = false;
            }
        }
        if (imagePathField.getText().trim().isEmpty()) {
            errorImagePath.setText("Image path cannot be empty!");
            isValid = false;
        }

        return isValid;
    }
}
