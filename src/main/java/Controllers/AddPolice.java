package Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Entities.Police;
import Entities.Sinistre;
import Services.PoliceService;
import Services.SinistreService;
import javafx.collections.FXCollections;

import java.util.List;

public class AddPolice {
    @FXML private Label errorName;
    @FXML private Label errorDescription;
    @FXML private Label errorSinistre;

    @FXML
    private TextField txtPoliceName;
    @FXML
    private TextField txtDescription;
    @FXML
    private ComboBox<Sinistre> comboSinistre;  // Use ComboBox<Sinistre> to store Sinistre objects directly

    private PoliceService policeService = new PoliceService();
    private SinistreService sinistreService = new SinistreService();

    @FXML
    public void initialize() {
        loadSinistreNamesIntoComboBox();
    }

    private void loadSinistreNamesIntoComboBox() {
        List<Sinistre> sinistres = sinistreService.getAll();
        ObservableList<Sinistre> observableSinistres = FXCollections.observableArrayList(sinistres);
        comboSinistre.setItems(observableSinistres);
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

    @FXML
    private void handleSaveAction() {
        if (validateInput()) {
            Police newPolice = new Police();
            newPolice.setPoliceName(txtPoliceName.getText().trim());
            newPolice.setDescriptionPolice(txtDescription.getText().trim());

            Sinistre selectedSinistre = comboSinistre.getSelectionModel().getSelectedItem();
            if (selectedSinistre != null) {
                newPolice.setSinistre(selectedSinistre);
                try {
                    policeService.add(newPolice);
                    showAlert("Success", "Police added successfully!");
                    clearForm();
                } catch (Exception e) {
                    showAlert("Error", "Failed to add new police: " + e.getMessage());
                }
            } else {
                showAlert("Error", "No Sinistre selected. Please select a Sinistre before saving.");
            }
        }
    }

    private void clearForm() {
        txtPoliceName.clear();
        txtDescription.clear();
        comboSinistre.getSelectionModel().clearSelection();
        errorName.setText("");
        errorDescription.setText("");
        errorSinistre.setText("");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleCancelAction(ActionEvent actionEvent) {
        txtPoliceName.clear();
        txtDescription.clear();
        comboSinistre.getSelectionModel().clearSelection();
        errorName.setText("");
        errorDescription.setText("");
        errorSinistre.setText("");
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

}
