package edu.esprit.controller;

import edu.esprit.entities.Agence;
import edu.esprit.service.AgenceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class updateagence {@FXML
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
    private TextField Ageadresse;

    @FXML
    private TextField Agedate;

    @FXML
    private TextField Ageemail;

    @FXML
    private TextField Agenom;

    @FXML
    private TextField Agephone;

    @FXML
    private VBox vboxdash;

    @FXML
    private Button selectImage;
    @FXML
    private ImageView imageView;

    private File selectedImageFile;
    private Agence Parametre2;
    private Agence agence;

    Agence SelectedAgence;
    private int Idage;

    @FXML
    public void modif(ActionEvent event) {
        if (isInputValid()) {
            String nomage = Agenom.getText();
            String addresse = Ageadresse.getText();
            String email = Ageemail.getText();
            String creation = Agedate.getText();
            int tel = Integer.parseInt(Agephone.getText());





            AgenceService service = new AgenceService();
            Agence agence = new Agence(nomage, addresse, email, tel, creation);

            System.out.println("bb"+ SelectedAgence);
            System.out.println( "bb"+Idage);
            agence.setIdage(Idage);
                    agence.setTel(tel);
                    agence.setNomage(nomage);
                    agence.setCreate_at(Agedate.getText());
                    agence.setAddresse(addresse);
                    agence.setEmail(email);
                    service.modifierage(agence);
                    System.out.println(agence);






            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("agency updated successfully");
            alert.showAndWait();


        }


    }

    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (Agenom.getText().isEmpty() || !Agenom.getText().matches("^[a-zA-Z]+$")) {
            errorNom.setText("Name is required and should not contain numbers");
            isValid = false;
        } else {
            errorNom.setText("");
        }

        if (Ageadresse.getText().isEmpty() || !Ageadresse.getText().matches("^[a-zA-Z0-9]+$")) {
            errorAdresse.setText("Adress is required and should not contain numbers ");
            isValid = false;
        } else {
            errorAdresse.setText("");
        }

        if (Ageemail.getText().isEmpty()) {
            errorEmail.setText("Email is required");
            isValid = false;
        } else if (!Ageemail.getText().contains("@")) {
            errorEmail.setText("Invalid email format");
            isValid = false;
        } else {
            errorEmail.setText("");
        }

        if (Agephone.getText().isEmpty() || !Agephone.getText().matches("^[0-9]{8}$")) {
            errorPhone.setText("Phone is required and should be 8 digits long");
            isValid = false;
        } else {
            errorPhone.setText("");
        }

        return isValid;
    }




    @FXML
    void initialize(Agence selectedAgence)  {
        System.out.println(selectedAgence);
        SelectedAgence=selectedAgence;
        Idage = selectedAgence.getIdage();
        Agenom.setText(selectedAgence.getNomage());
        Ageadresse.setText(selectedAgence.getAddresse());
        Agedate.setText(selectedAgence.getCreate_at());
        Ageemail.setText(selectedAgence.getEmail());
        Agephone.setText(String.valueOf(selectedAgence.getTel()));

        assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'updateagence.fxml'.";
        assert emailage != null : "fx:id=\"email\" was not injected: check your FXML file 'updateagence.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'updateagence.fxml'.";
        assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'updateagence.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'updateagence.fxml'.";

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

    public void myparametre(Agence agence) {
        // Utilisez le paramètre comme vous le souhaitez ici
        System.out.println("Selected ID: " + agence);
        this.Parametre2=agence;


        System.out.println("ss"+Parametre2);


    }
}
