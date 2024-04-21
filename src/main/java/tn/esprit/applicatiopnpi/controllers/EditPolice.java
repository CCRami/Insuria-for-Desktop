package tn.esprit.applicatiopnpi.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.applicatiopnpi.models.Police;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.PoliceService;
import tn.esprit.applicatiopnpi.services.SinistreService;

import java.util.List;
import java.util.function.Consumer;

public class EditPolice {
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
        initializeComboBox(); // Prepare the ComboBox with Sinistre data
        // Set the selection to the Sinistre associated with this Police
        if (police.getSinistre() != null) {
            comboSinistre.getSelectionModel().select(police.getSinistre());
        }
    }


    private void initializeComboBox() {
        List<Sinistre> sinistres = sinistreService.getAll();
        comboSinistre.getItems().setAll(sinistres);
        ObservableList<Sinistre> observableSinistres = FXCollections.observableArrayList(sinistres);
        // Use a cell factory to define how the list cells in the combo box should be displayed
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
        // Validate input before proceeding
        if (!validateInput()) {
            showError("Please fill in all fields correctly.");
            return;
        }

        currentPolice.setPoliceName(txtPoliceName.getText().trim());
        currentPolice.setDescriptionPolice(txtDescription.getText().trim());
        currentPolice.setSinistre(comboSinistre.getSelectionModel().getSelectedItem());

        try {
            policeService.modifier(currentPolice);
            if (updateCallback != null) {
                updateCallback.accept(currentPolice);
            }
            closeStage();  // Close the window only if the update is successful
        } catch (Exception e) {
            showError("Failed to update Police: " + e.getMessage());
        }
    }

    private boolean validateInput() {
        return !txtPoliceName.getText().trim().isEmpty() && !txtDescription.getText().trim().isEmpty() && comboSinistre.getSelectionModel().getSelectedItem() != null;
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
