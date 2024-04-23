package tn.esprit.applicatiopnpi.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.applicatiopnpi.models.Police;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.PoliceService;
import tn.esprit.applicatiopnpi.services.SinistreService;

import java.util.List;
import java.util.function.Consumer;

public class EditPolice {
    @FXML private Label errorName;
    @FXML private Label errorDescription;
    @FXML private Label errorSinistre;
    @FXML
    private TextField txtPoliceName;
    @FXML
    private TextField txtDescription;
    @FXML
    private ComboBox<Sinistre> comboSinistre;

    private PoliceService policeService = new PoliceService();
    private SinistreService sinistreService = new SinistreService();
    private Police currentPolice;
    private Consumer<Police> updateCallback;

    public void intData(Police police) {
        currentPolice = police;
        txtPoliceName.setText(police.getPoliceName());
        txtDescription.setText(police.getDescriptionPolice());
        initializeComboBox();
        if (police.getSinistre() != null) {
            comboSinistre.getSelectionModel().select(police.getSinistre());
        }
    }


    private void initializeComboBox() {
        List<Sinistre> sinistres = sinistreService.getAll();
        comboSinistre.getItems().setAll(sinistres);
        ObservableList<Sinistre> observableSinistres = FXCollections.observableArrayList(sinistres);

        comboSinistre.setConverter(new javafx.util.StringConverter<Sinistre>() {
            @Override
            public String toString(Sinistre sinistre) {
                return sinistre != null ? sinistre.getSin_name() : "";
            }

            @Override
            public Sinistre fromString(String string) {
                return sinistres.stream().filter(s -> s.getSin_name().equals(string)).findFirst().orElse(null);
            }
        });
    }


    public void handleUpdateAction() {
        // Validate input before proceeding with the update
        if (!validateInput()) {
            return;  // Stop the update if validation fails
        }

        // Proceed if validation is successful
        currentPolice.setPoliceName(txtPoliceName.getText().trim());
        currentPolice.setDescriptionPolice(txtDescription.getText().trim());
        Sinistre selectedSinistre = comboSinistre.getSelectionModel().getSelectedItem();

        if (selectedSinistre == null) {
            showAlert("Error", "No Sinistre selected. Please select a Sinistre before saving.");
            return;  // Stop the update if no Sinistre is selected
        }

        currentPolice.setSinistre(selectedSinistre);
        try {
            policeService.moddifier(currentPolice);
            if (updateCallback != null) {
                updateCallback.accept(currentPolice);
            }
            closeStage();  // Close the window only if the update is successful
        } catch (Exception e) {
            showError("Failed to update Police: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateInput() {
        boolean isValid = true;
        String name = txtPoliceName.getText().trim();
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
        if (txtDescription.getText().trim().isEmpty()) {
            errorDescription.setText("Description field cannot be empty!");
            isValid = false;
        } else {
            String description = txtDescription.getText().trim();

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


        return isValid;
    }


    public void handleCancelAction() {
        closeStage();
    }

    private void closeStage() {
        ((Stage) txtPoliceName.getScene().getWindow()).close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void setUpdateCallback(Consumer<Police> callback) {
        this.updateCallback = callback;
    }
}
