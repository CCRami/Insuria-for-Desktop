package tn.esprit.applicatiopnpi.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import tn.esprit.applicatiopnpi.models.Police;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.PoliceService;
import tn.esprit.applicatiopnpi.services.SinistreService;
import javafx.collections.FXCollections;

import java.util.List;

public class AddPolice {

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

    private void clearForm() {
        txtPoliceName.clear();
        txtDescription.clear();
        comboSinistre.getSelectionModel().clearSelection();
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
    }
}
