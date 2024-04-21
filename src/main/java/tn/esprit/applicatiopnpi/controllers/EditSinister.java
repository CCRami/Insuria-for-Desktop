package tn.esprit.applicatiopnpi.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.SinistreService;

import java.io.File;
import java.util.function.Consumer;

public class EditSinister {
    @FXML private TextField sinNameField;
    @FXML private TextField sinDescriptionField;
    @FXML private TextField sinImagePathField;
    @FXML
    private Label errorName;
    @FXML
    private Label errorDescription;
    @FXML
    private Label errorImagePath;

    private SinistreService service = new SinistreService();
    private Sinistre currentSinistre;
    private Consumer<Sinistre> updateCallback;
    @FXML private ImageView imageView;
    public void initData(Sinistre sinistre) {
        currentSinistre = sinistre;
        sinNameField.setText(sinistre.getSin_name());
        sinDescriptionField.setText(sinistre.getDescription_sin());
        sinImagePathField.setText(sinistre.getImage_path());

        // Charger et afficher l'image si le chemin n'est pas vide
        if (sinistre.getImage_path() != null && !sinistre.getImage_path().isEmpty()) {
            File imageFile = new File(sinistre.getImage_path());
            if (imageFile.exists()) { // Vérifiez que le fichier existe avant de tenter de le charger
                Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
            } else {
                imageView.setImage(null); // Aucune image ou chemin invalide
                System.out.println("Image file not found: " + sinistre.getImage_path());
            }
        } else {
            imageView.setImage(null); // Aucun chemin d'image fourni
        }
    }


    @FXML
    private void handleSave() {
        if (validateInput()) {  // Assurez-vous que la validation retourne true avant de continuer
            currentSinistre.setSin_name(sinNameField.getText().trim());
            currentSinistre.setDescription_sin(sinDescriptionField.getText().trim());
            currentSinistre.setImage_path(sinImagePathField.getText().trim());

            try {
                service.modifier(currentSinistre);
                if (updateCallback != null) {
                    updateCallback.accept(currentSinistre);
                }
                closeStage();  // Fermer la fenêtre uniquement si la mise à jour est réussie
            } catch (Exception e) {
                // Gérer l'exception, par exemple afficher un message d'erreur
                showError("Failed to update Sinistre: " + e.getMessage());
            }
        }
    }
    private boolean validateInput() {
        boolean isValid = true;
        String name = sinNameField.getText().trim();
        String description = sinDescriptionField.getText().trim();
        String imagePath = sinImagePathField.getText().trim();

        errorName.setText("");
        errorDescription.setText("");
        errorImagePath.setText("");

        if (name.isEmpty()) {
            errorName.setText("Name cannot be empty!");
            isValid = false;
        } else if (!Character.isUpperCase(name.charAt(0))) {
            errorName.setText("Name must start with an uppercase letter!");
            isValid = false;
        } else if (name.length() > 30) {
            errorName.setText("Name cannot exceed 30 characters!");
            isValid = false;
        } else if (!isNameUnique(name)) {
            errorName.setText("Name must be unique!");
            isValid = false;
        }

        if (description.isEmpty()) {
            errorDescription.setText("Description cannot be empty!");
            isValid = false;
        }

        if (imagePath.isEmpty()) {
            errorImagePath.setText("Image path cannot be empty!");
            isValid = false;
        }

        return isValid;
    }
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    private void handleCancel() {
        closeStage();
    }

    private void closeStage() {
        sinNameField.getScene().getWindow().hide();
    }
    @FXML
    private void handleBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        Stage stage = (Stage) sinImagePathField.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            sinImagePathField.setText(file.getAbsolutePath());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void setUpdateCallback(Consumer<Sinistre> callback) {
        this.updateCallback = callback;
    }

    public void saveChanges() {
        // Enregistre les modifications dans la base de données
        service.modifier(currentSinistre);
        // Appelle le callback pour mettre à jour l'affichage
        if (updateCallback != null) {
            updateCallback.accept(currentSinistre);
        }

    }
    /**
     * Checks if the given name is unique in the system.
     * @param name The name to check.
     * @return true if the name is unique, false otherwise.
     */
    private boolean isNameUnique(String name) {
        return !service.isNameTaken(name, currentSinistre.getId());
    }



}
