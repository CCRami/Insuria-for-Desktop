package Controller;

import Entity.InsuranceCategory;
import Service.InsuranceCatService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.function.Consumer;

public class EditCatInsurance {

    @FXML
    private TextField sinNameField;
    @FXML private TextField sinDescriptionField;
    @FXML private TextField sinImagePathField;
    @FXML
    private Label errorName;
    @FXML
    private Label errorDescription;
    @FXML
    private Label errorImagePath;

    private InsuranceCatService servicecat = new InsuranceCatService();
    private InsuranceCategory currentCatIns;
    private Consumer<InsuranceCategory> updateCallback;
    @FXML private ImageView imageView;
    public void initData(InsuranceCategory inscat) {
        currentCatIns = inscat;
        sinNameField.setText(inscat.getName_cat_ins());
        sinDescriptionField.setText(inscat.getDesc_cat_ins());


        // Charger et afficher l'image si le chemin n'est pas vide

    }


    @FXML
    private void handleSave() {
        if (validateInput()) {  // Assurez-vous que la validation retourne true avant de continuer
            currentCatIns.setName_cat_ins(sinNameField.getText().trim());
            currentCatIns.setDesc_cat_ins(sinDescriptionField.getText().trim());


            try {
                servicecat.updateCatIns(currentCatIns);
                if (updateCallback != null) {
                    updateCallback.accept(currentCatIns);
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


        errorName.setText("");
        errorDescription.setText("");


        if (name.isEmpty()) {
            errorName.setText("Name cannot be empty!");
            isValid = false;
        } else if (!Character.isUpperCase(name.charAt(0))) {
            errorName.setText("Name must start with an uppercase letter!");
            isValid = false;
        } else if (name.length() > 30) {
            errorName.setText("Name cannot exceed 30 characters!");
            isValid = false;
        }

        if (description.isEmpty()) {
            errorDescription.setText("Description cannot be empty!");
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


    public void setUpdateCallback(Consumer<InsuranceCategory> callback) {
        this.updateCallback = callback;
    }

    public void saveChanges() {
        // Enregistre les modifications dans la base de données
        servicecat.updateCatIns(currentCatIns);
        // Appelle le callback pour mettre à jour l'affichage
        if (updateCallback != null) {
            updateCallback.accept(currentCatIns);
        }

    }
    /**
     * Checks if the given name is unique in the system.
     * @param name The name to check.
     * @return true if the name is unique, false otherwise.
     */

}
