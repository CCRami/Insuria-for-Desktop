package edu.esprit.controller;

import edu.esprit.entities.Agence;
import edu.esprit.entities.Avis;
//import edu.esprit.service.AvisService;
import edu.esprit.service.AvisService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ajouteravis {
    @FXML
    private TextArea commentArea;

    @FXML
    private ToggleButton star1;

    @FXML
    private ToggleButton star2;

    @FXML
    private ToggleButton star3;

    @FXML
    private ToggleButton star4;

    @FXML
    private ToggleButton star5;

    @FXML
    private HBox starBox;

    @FXML
    private Button ajouteravi;
    private int rating = 0; // Variable pour stocker la note actuelle
    private Agence Parametre2;

    @FXML
    void star11(ActionEvent event) {
        rating=1;

    }

    @FXML
    void star22(ActionEvent event) {
        rating=2;
    }

    @FXML
    void star33(ActionEvent event) {
        rating=3;
    }

    @FXML
    void star44(ActionEvent event) {
        rating=4;
    }

    @FXML
    void star55(ActionEvent event) {
        rating=5;
    }
    @FXML
    public void initialize( ) {
        assert commentArea != null : "fx:id=\"commentArea\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert ajouteravi != null : "fx:id=\"save\" was not injected: check your FXML file 'ajouteragence.fxml'.";

    }
    @FXML
    private Text errorNom;
    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (commentArea.getText().isEmpty() || !commentArea.getText().matches("^[a-zA-Z]+$")) {
            errorNom.setText("Comment is required ");
            isValid = false;
        } else {
            errorNom.setText("");
        }



        return isValid;
    }


    @FXML
    void ajouterav(ActionEvent event) {

        if (isInputValid()) {
            String commentaire = commentArea.getText();
            int note = rating;
            Agence agence =Parametre2;
            System.out.println("aa"+commentaire);

            Date create_at = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String creation = format.format(create_at);
            Avis avis =new Avis(commentaire,note,creation,111,agence,false);
            System.out.println("dd"+avis);
            AvisService service = new AvisService();
           service.ajouteravis(avis,Parametre2);


            commentArea.clear();



            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("review added successfully");
            alert.showAndWait();



        }

    }
    public void myparametre(Agence id) {
        // Utilisez le paramètre comme vous le souhaitez ici
        System.out.println("Selected ID: " + id);
        this.Parametre2=id;

        System.out.println("ss"+Parametre2);


    }
}