package tn.esprit.applicatiopnpi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.SinistreService;

import java.io.File;

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
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();
            String imagePath = imagePathField.getText().trim();

            Sinistre newSinistre = new Sinistre();
            newSinistre.setSin_name(name);
            newSinistre.setDescription_sin(description);
            newSinistre.setImage_path(imagePath);

            try {
                sinistreService.add(newSinistre);
                showAlert("Success", "Sinistre added successfully!");
                clearFields();
            } catch (RuntimeException e) {
                showAlert("Failed to add Sinistre", e.getMessage());
            }
        }
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

    @FXML
    private void handleBrowse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePathField.setText(file.getAbsolutePath());
            imageView.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        clearFields();
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
        errorName.setText("");
        errorDescription.setText("");
        errorImagePath.setText("");

        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            errorName.setText("Name cannot be empty!");
            isValid = false;
        } else if (!Character.isUpperCase(name.charAt(0))) {
            errorName.setText("Name must start with an uppercase letter!");
            isValid = false;
        } else if (name.length() > 30) {
            errorName.setText("Name cannot exceed 30 characters!");
            isValid = false;
        } else if (sinistreService.isNameTaken(name, null)) { // Properly handle null for new entries
            errorName.setText("Name must be unique!");
            isValid = false;
        }

        if (descriptionField.getText().trim().isEmpty()) {
            errorDescription.setText("Description cannot be empty!");
            isValid = false;
        }

        if (imagePathField.getText().trim().isEmpty()) {
            errorImagePath.setText("Image path cannot be empty!");
            isValid = false;
        }

        return isValid;
    }

    private boolean isNameUnique(String name) {
        // This method should check against the database or service to ensure the name is unique.
        return sinistreService.isNameTaken(name);
    }
}