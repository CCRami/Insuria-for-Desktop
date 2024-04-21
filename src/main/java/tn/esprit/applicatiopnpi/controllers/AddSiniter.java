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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AddSiniter {
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField imagePathField;
    @FXML
    private BorderPane myBorderPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Label errorName;
    @FXML
    private Label errorDescription;
    @FXML
    private Label errorImagePath;


    @FXML
    private VBox vboxdash;
    SinistreService sinistreService = new SinistreService();

    @FXML
    private void handleSave(ActionEvent event) {
        boolean isValid = validateInput(); // Assume this method sets error labels appropriately

        if (isValid) {
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();
            String imagePath = imagePathField.getText().trim();

            Sinistre newSinistre = new Sinistre();
            newSinistre.setSin_name(name);
            newSinistre.setDescription_sin(description);
            newSinistre.setImage_path(imagePath);

            try {
                sinistreService.add(newSinistre);
                clearFields(); // Clear the fields after successful addition
            } catch (RuntimeException e) {
                errorName.setText("Failed to add Sinistre: " + e.getMessage()); // Display error message
            }
        }
    }

    private void clearFields() {
        nameField.clear();
        descriptionField.clear();
        imagePathField.clear();
        errorName.setText("");
        errorDescription.setText("");
        errorImagePath.setText("");
    }




    @FXML
    private void handleBrowse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null); // Cela ouvre le dialogue pour choisir le fichier
        if (file != null) {
            imagePathField.setText(file.getAbsolutePath()); // Met à jour le TextField avec le chemin du fichier

            Image image = new Image(file.toURI().toString(), true); // Crée un nouvel objet Image
            imageView.setImage(image); // Met à jour l'ImageView avec l'image choisie
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        // Clear all input fields
        nameField.clear();
        descriptionField.clear();
        imagePathField.clear();

        // Clear all error messages
        errorName.setText("");
        errorDescription.setText("");
        errorImagePath.setText("");

        // Clear the image displayed in the ImageView
        if (imageView != null) {
            imageView.setImage(null);
        }
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
            errorName.setText("Name field cannot be empty!");
            isValid = false;
        } else {
            // Check if the first character is uppercase
            if (Character.isLowerCase(name.charAt(0))) {
                errorName.setText("Name must start with an uppercase letter!");
                isValid = false;
            }

            // Check if the length is more than 30 characters
            if (name.length() > 30) {
                errorName.setText("Name must not exceed 30 characters!");
                isValid = false;
            }

            // Check for uniqueness
            if (sinistreService.isNameExist(name)) {
                errorName.setText("Name must be unique!");
                isValid = false;
            }
        }

        if (descriptionField.getText().trim().isEmpty()) {
            errorDescription.setText("Description field cannot be empty!");
            isValid = false;
        }

        if (imagePathField.getText().trim().isEmpty()) {
            errorImagePath.setText("Image path cannot be empty!");
            isValid = false;
        }

        return isValid;
    }




}
