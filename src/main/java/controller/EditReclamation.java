package controller;

import entity.Reclamation;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import services.ReclamationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class EditReclamation implements Initializable {
    @FXML
    private VBox vboxdash;
    @FXML
    private Button add;

    @FXML
    private Button cancel;

    @FXML
    private TextField contenu;

    @FXML
    private DatePicker date;

    @FXML
    private Text errorContenu;

    @FXML
    private Text errorDate;

    @FXML
    private Text errorLabel;

    @FXML
    private TextField nom;
    private Reclamation reclamation;

    @FXML
    void cancelAction(ActionEvent event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationsFront.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) cancel.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Gérer l'erreur si nécessaire
            }

    }

    @FXML
    void editReclamationAction(ActionEvent event) {
        if (isInputValid()) {
           reclamation.setLibelle(nom.getText());
           reclamation.setContenu_rec(contenu.getText());
           reclamation.setDateSinitre(date.getValue().toString());

            ReclamationService service = new ReclamationService();

            try {
                service.modifierReclamation(reclamation);

                // Afficher une confirmation de la mise à jour réussie
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Reclamation edited successfully");
                successAlert.showAndWait();


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationsFront.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) cancel.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
                stage.setScene(new Scene(root));
                stage.show();
            } catch (SQLException e) {
                // Afficher une alerte en cas d'erreur
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("An error occurred while editing the reclamation.");
                errorAlert.showAndWait();
                System.out.println("Error editing reclamation: " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Réinitialiser les champs et les messages d'erreur
            nom.clear();
            contenu.clear();
            date.getEditor().clear();
            errorContenu.setText("");
            errorDate.setText("");
            errorLabel.setText("");
        }

}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = map.getEngine();
        webEngine.load(getClass().getResource("/map.html").toExternalForm());
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        updateDetails();
    }

    private void updateDetails() {
        if (reclamation != null) {
            nom.setText(reclamation.getLibelle());
            contenu.setText(reclamation.getContenu_rec());
            LocalDate parsedDate = LocalDate.parse(reclamation.getDateSinitre());
            date.setValue(parsedDate);
            try {
                Locale.setDefault(Locale.US); // Ensure using dot as decimal separator
                double latitude = Double.parseDouble(reclamation.getLatitude());
                double longitude = Double.parseDouble(reclamation.getLongitude());
                setCoordinates(latitude, longitude);
            } catch (NumberFormatException e) {
                System.err.println("Invalid latitude or longitude format: " + e.getMessage());
            }
        }

    }

    public void setCoordinates(double latitude, double longitude) {
        if (webEngine != null) {
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    webEngine.executeScript(String.format("updateMap(%f, %f);", latitude, longitude));
                }
            });
        }
    }
    private WebEngine webEngine;

    @FXML
    private WebView map;







    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (nom.getText().isEmpty() || !nom.getText().matches("^[a-zA-Z]+$")) {
            errorLabel.setText("Name is required and should not contain numbers");
            isValid = false;
        } else {
            errorLabel.setText("");
        }

        if (contenu.getText().isEmpty() || contenu.getText().length() < 10) {
            errorContenu.setText("contenu is required and must contain at least 10 characters");
            isValid = false;
        } else {
            errorContenu.setText("");
        }
        LocalDate currentDate = LocalDate.now();

        if (date.getValue() == null) {
            errorDate.setText("Date must not be empty");
            isValid = false;
        } else if (date.getValue().isAfter(currentDate)) {

            errorDate.setText("Date must be in the past");
            isValid = false;
        } else {
            // Clear the error message
            errorDate.setText("");
        }
        return isValid;

    }


}
