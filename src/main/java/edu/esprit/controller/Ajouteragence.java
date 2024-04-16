package edu.esprit.controller;

import edu.esprit.entities.Agence;
import edu.esprit.service.AgenceService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javafx.scene.image.ImageView;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.stage.FileChooser;
import javafx.util.Duration;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Ajouteragence {

    @FXML
    private TextField adresse;

    @FXML
    private TextField emailage;

    @FXML
    private TextField nom;

    @FXML
    private TextField phone;

    @FXML
    private Button save;

    @FXML
    private Text errorNom;

    @FXML
    private Text errorAdresse;

    @FXML
    private Text errorEmail;

    @FXML
    private Text errorPhone;

    @FXML
    private VBox vboxdash;

    @FXML
    private Button selectImage;
    @FXML
    private ImageView imageView;

    private File selectedImageFile;


    @FXML
    void ajouteragenceAction(ActionEvent event) {
         if (isInputValid()) {
            String nomage = nom.getText();
            String addresse = adresse.getText();
            String email = emailage.getText();
            int tel = Integer.parseInt(phone.getText());

            Date create_at = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String creation = format.format(create_at);

           Agence agence = new Agence(nomage, addresse, email, tel,creation );

            AgenceService service = new AgenceService();
            service.ajouteragence(agence);


            nom.clear();
            adresse.clear();
            phone.clear();
            emailage.clear();
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Success");
             alert.setHeaderText(null);
             alert.setContentText("agency added successfully");
             alert.showAndWait();



        }


    }

    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (nom.getText().isEmpty() || !nom.getText().matches("^[a-zA-Z]+$")) {
            errorNom.setText("Name is required and should not contain numbers");
            isValid = false;
        } else {
            errorNom.setText("");
        }

        if (adresse.getText().isEmpty() || !nom.getText().matches("^[a-zA-Z]+$")) {
            errorAdresse.setText("Adress is required and should not contain numbers ");
            isValid = false;
        } else {
            errorAdresse.setText("");
        }

        if (emailage.getText().isEmpty()) {
            errorEmail.setText("Email is required");
            isValid = false;
        } else if (!emailage.getText().contains("@")) {
            errorEmail.setText("Invalid email format");
            isValid = false;
        } else {
            errorEmail.setText("");
        }

        if (phone.getText().isEmpty() || !phone.getText().matches("^[0-9]{8}$")) {
            errorPhone.setText("Phone is required and should be 8 digits long");
            isValid = false;
        } else {
            errorPhone.setText("");
        }

        return isValid;
    }




    @FXML
    void initialize()  {

        assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert emailage != null : "fx:id=\"email\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'ajouteragence.fxml'.";

    }

    public void showAgence( ) {
        try {
            // Load user.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Agencesback.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }

    }

    public void showAvisAgence(MouseEvent mouseEvent) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/avisback.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }
}
