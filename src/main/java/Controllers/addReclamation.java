package Controllers;


import Entities.Commande;
import Entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Services.ReclamationService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class addReclamation {

    @FXML
    private Button add;
    @FXML
    private TextField imagePath;
private Commande cmd;
    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField contenu;

    @FXML
    private DatePicker date;

    @FXML
    private Text errorContenu;

    @FXML
    private Text errorDate;
    @FXML
    private Text errorImage;

    @FXML
    private Text errorLabel;

    @FXML
    private TextField nom;

    @FXML
    private TextField latitude;

    @FXML
    private TextField longitude;
    @FXML
    private VBox vboxdash;

    @FXML
    void initialize()  {

        assert contenu != null : "fx:id=\"adresse\" was not injected: check your FXML file 'addReclamation.fxml'.";
        assert date != null : "fx:id=\"email\" was not injected: check your FXML file 'addReclamation.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'addReclamation.fxml'.";

    }
    public void initData(Commande commande ){
        this.cmd=commande;
    }
    @FXML
    void ajouterReclamationAction(ActionEvent event) throws IOException {

        if (isInputValid()) {
            String label=nom.getText();
            String contenuRec = contenu.getText();
            String dateSinistre = date.getValue().toString();
             String lon =longitude.getText();
            String lan =latitude.getText();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String dateReclamation = format.format(new Date());
String image =(imagePath.getText().replace("\\", "/").trim());
            // Créez votre objet Reclamation avec les données récupérées
            Reclamation reclamation = new Reclamation(label,contenuRec, "Currently being processed",dateSinistre, dateReclamation,lan,lon,image);

         reclamation.setCommande(cmd);

            ReclamationService service = new ReclamationService();

            // Ajoutez la réclamation à votre base de données
            try {
               service.addReclamation(reclamation);
            } catch (SQLException e) {
                System.out.println("Error adding packs: " + e.getMessage());
            }
             nom.clear();
            contenu.clear();
            date.getEditor().clear();
            imagePath.clear();
            imageV.setImage(null);
            errorContenu.setText("");
            errorDate.setText("");
            errorLabel.setText("");

            longitude.clear();
            latitude.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Reclamation added successfully");

            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) errorDate.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
            stage.setScene(new Scene(root));
            stage.show();
            dashboardFront controller = loader.getController();
            controller.GotoProfile(null);


        }
    }


    @FXML
    private ImageView retour;



    @FXML

    private ImageView imageV;
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

    @FXML
    void retour(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationsFront.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) retour.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
        stage.setScene(new Scene(root));
        stage.show();

    }
    @FXML
    private Button ajouterIMage;
    @FXML
    void ajouterIMage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePath.setText(file.getAbsolutePath().replace("\\", "/"));
            try {
                Image image = new Image(file.toURI().toURL().toExternalForm());
                imageV.setImage(image);
            } catch (Exception e) {
                errorImage.setText("Loading Error");
                imagePath.setText("");
            }
        }
    }


}
