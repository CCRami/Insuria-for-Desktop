package tn.esprit.applicatiopnpi.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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

    private SinistreService service = new SinistreService();
    private Sinistre currentSinistre;
    private Consumer<Sinistre> updateCallback;
    public void initData(Sinistre sinistre) {
        currentSinistre = sinistre;
        sinNameField.setText(sinistre.getSin_name());
        sinDescriptionField.setText(sinistre.getDescription_sin());
        sinImagePathField.setText(sinistre.getImage_path());
    }

    @FXML
    private void handleSave() {
        currentSinistre.setSin_name(sinNameField.getText());
        currentSinistre.setDescription_sin(sinDescriptionField.getText());
        currentSinistre.setImage_path(sinImagePathField.getText());
        service.modifier(currentSinistre);

        if (updateCallback != null) {
            updateCallback.accept(currentSinistre);
        }
        closeStage();
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

}
