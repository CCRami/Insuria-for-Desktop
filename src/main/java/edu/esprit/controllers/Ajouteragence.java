package edu.esprit.controllers;

import edu.esprit.entities.Agence;
import edu.esprit.service.AgenceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javafx.scene.image.ImageView;
import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.stage.Stage;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;


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
    private String extractAddressNameFromURL(String url) {
        // L'adresse est souvent contenue entre les balises "/place/" et "/"
        int placeIndex = url.indexOf("/place/");
        if (placeIndex != -1) {
            int startIndex = placeIndex + "/place/".length();
            int endIndex = url.indexOf("/", startIndex);
            if (endIndex != -1) {
                String addressNameEncoded = url.substring(startIndex, endIndex);
                try {
                    // Décoder les caractères encodés en UTF-8
                    return URLDecoder.decode(addressNameEncoded, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // Gérer l'exception d'encodage non pris en charge
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    private void saveAddressName(String address) {
        System.out.println("Adresse enregistrée : " + address);
    }


    @FXML
    private void addressChoose() throws IOException {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Charger Google Maps
        webEngine.load("https://www.google.com/maps");

        // Ajouter un écouteur de changement d'URL
        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si l'URL contient des informations d'adresse
            if (newValue.startsWith("https://www.google.com/maps/place/")) {
                // Extraire le nom de l'adresse de l'URL
                String addressName = extractAddressNameFromURL(newValue);
                // Enregistrer le nom de l'adresse dans votre application
                saveAddressName(addressName);
                adresse.setText(addressName);
            }

        });

        StackPane root = new StackPane();
        root.getChildren().add(webView);

        Scene scene = new Scene(root, 800, 600);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Google Maps in JavaFX");
        primaryStage.show();

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
