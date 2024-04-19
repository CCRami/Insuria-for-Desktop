package tn.esprit.applicatiopnpi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
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
    private TextField descriptionField;
    @FXML
    private TextField imagePathField;
    @FXML
    private BorderPane myBorderPane;

    @FXML
    private VBox vboxdash;
    SinistreService sinistreService = new SinistreService();

    @FXML
    private void handleSave(ActionEvent event) {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String imagePath = imagePathField.getText().trim();

        if (!name.isEmpty() && !description.isEmpty() && !imagePath.isEmpty()) {
            Sinistre newSinistre = new Sinistre();
            newSinistre.setSin_name(name);
            newSinistre.setDescription_sin(description);
            newSinistre.setImage_path(imagePath);

            try {
                sinistreService.add(newSinistre);
                showAlert("Success", "Sinistre added successfully!");
            } catch (RuntimeException e) {
                showAlert("Error", "Failed to add Sinistre: " + e.getMessage());
            }
        } else {
            showAlert("Validation Error", "Please fill in all fields.");
        }
    }

    @FXML
    private void handleBrowse(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePathField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        nameField.clear();
        descriptionField.clear();
        imagePathField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
