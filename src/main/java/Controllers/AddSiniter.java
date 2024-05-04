package Controllers;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.FacebookType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import Entities.Sinistre;
import Services.SinistreService;

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
    private void postToFacebook(String message) {
        // Utilisez le jeton d'accès pour créer une instance de FacebookClient
        String accessToken = "EAAF8tZAjICZBsBO1bhWbyIQnMMo0OJGopDxWWEi3yNys2w0c8vOFLWZAr4u1mDodZBkEdS0bf3zfbZBLYGjfg4uI2oJ1slQeNJbT9AkZBxIcKoCA2CkCQxHjBhXIoBgfgeJpOZAd8hB2S340eOPbm17yq4HwTyUkeTuydktAsiWh24KXxfQWZAvof5MbnJXkD6gZD";  // Vous devez sécuriser ce jeton, par exemple, le stocker de manière sécurisée et le charger de la configuration
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);

        try {
            facebookClient.publish("me/feed", FacebookType.class, Parameter.with("message", message));
        } catch (FacebookOAuthException e) {
            showAlert("Error Posting to Facebook", "Failed to post to Facebook: " + e.getErrorMessage());
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }
    @FXML
    private void handleSave(ActionEvent event) {
        if (validateInput()) {
            Sinistre newSinistre = new Sinistre();
            newSinistre.setSin_name(nameField.getText().trim());
            newSinistre.setDescription_sin(descriptionField.getText().trim());
            newSinistre.setImage_path(imagePathField.getText().replace("\\", "/").trim());

            try {
                sinistreService.add(newSinistre);
                // Préparez un message ou utilisez une description du sinistre
                String fbMessage = "Nouveau sinistre ajouté : " + newSinistre.getSin_name() + " - " + newSinistre.getDescription_sin();
                postToFacebook(fbMessage);  // Publier sur Facebook
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
