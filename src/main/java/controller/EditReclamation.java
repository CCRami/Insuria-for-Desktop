package controller;

import Entity.Reclamation;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ReclamationService;

import java.io.File;
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
    private TextField latitude;

    @FXML
    private TextField longitude;

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
               reclamation.setLatitude(latitude.getText());
               reclamation.setLongitude(longitude.getText());
                  reclamation.setImage_file(imagepath.getText());
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
            latitude.clear();
            longitude.clear();
            errorLatitude.setText("");
            errorLongitude.setText("");
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
            latitude.setText(reclamation.getLatitude());
           longitude.setText(reclamation.getLongitude());
           imagepath.setText(reclamation.getImage_file());
            try {
                Image image = new Image(new File(reclamation.getImage_file()).toURI().toString());
                imageV.setImage(image);
            } catch (Exception e) {
                // Handle the exception if the image cannot be loaded
                System.err.println("Failed to load image: " + e.getMessage());
                imageV.setImage(null); // Clear the image view
            }

        try{
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
    @FXML
    private ImageView imageV;

    @FXML
    private Text imagepath;


    @FXML
    void  changerIMage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagepath.setText(file.getAbsolutePath().replace("\\", "/"));
            try {
                Image image = new Image(file.toURI().toURL().toExternalForm());
                imageV.setImage(image);
            } catch (Exception e) {
                imagepath.setText("");
            }
        }
    }






    @FXML
    private Text errorLatitude;

    @FXML
    private Text errorLongitude;


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
        if (latitude.getText().isEmpty() || !latitude.getText().matches("\\d+\\.\\d+")) {
            errorLatitude.setText("Latitude must be a number with .");
            isValid = false;
        } else {
            errorLatitude.setText("");
        }

        // Validate longitude
        if (longitude.getText().isEmpty() || !longitude.getText().matches("\\d+\\.\\d+")) {
            errorLongitude.setText("Longitude must be a number with .");
            isValid = false;
        } else {
            errorLongitude.setText("");
        }
        return isValid;

    }


}
