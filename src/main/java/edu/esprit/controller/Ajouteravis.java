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

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import edu.esprit.Helper.AlertHelper;

import javafx.stage.Window;

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
    public void initialize(Agence agence ) {
        assert commentArea != null : "fx:id=\"commentArea\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert ajouteravi != null : "fx:id=\"save\" was not injected: check your FXML file 'ajouteragence.fxml'.";
Parametre2=agence;
    }
    @FXML
    private Text errorNom;
    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (commentArea.getText().isEmpty() ) {
            errorNom.setText("Comment is required ");
            isValid = false;
        } else {
            errorNom.setText("");
        }



        return isValid;
    }

    private Runnable closeCallback;

    public void setCloseCallback(Runnable callback) {
        this.closeCallback = callback;
    }

    public String bad_words(String badWord) {

        List<String> badListW = Arrays.asList("fuck", "bitch","motherfucker","merde","putin","mo8t","bhim");
        String badNew = "";
        List<String> newList = new ArrayList<>();
        for (String str : badListW) {
            if (badWord.contains(str)) {
                badNew += " " + str;
                if (str.length() >= 1) {
                    StringBuilder result = new StringBuilder();
                    result.append(str.charAt(0));
                    for (int i = 0; i < str.length() - 2; ++i) {
                        result.append("*");
                    }
                    result.append(str.charAt(str.length() - 1));
                    str = result.toString();
                    if (!str.isEmpty()) {
                        System.out.println("ATTENTION !! Vous avez écrit un gros mot  : " + result + " .C'est un avertissement ! Priére d'avoir un peu de respect ! Votre description sera envoyée comme suit :");
                        System.out.println(badWord.replace(badNew, "") + " ");
                        AlertHelper.showAlert(Alert.AlertType.ERROR, SystemColor.window, "Error",
                                "ATTENTION !! Vous avez écrit un gros mot");
                    }
                }

            }
        }
        return (badWord.replace(badNew, "") + " ");
    }


    @FXML
    void ajouterav(ActionEvent event) {

        if (isInputValid()) {
            String commentaire = commentArea.getText();
            commentaire = bad_words(commentaire);
            int note = rating;
            Agence agence =Parametre2;
            System.out.println("aa"+commentaire);

            Date create_at = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String creation = format.format(create_at);
            Avis avis =new Avis(commentaire,note,creation,111,agence,false);
            System.out.println("dd"+avis);
            AvisService service = new AvisService();
           service.ajouteravis(avis);


            commentArea.clear();




            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("review added successfully");
            alert.showAndWait();
            commentArea.setDisable(true);
            star1.setDisable(true);
            star2.setDisable(true);

            star3.setDisable(true);

            star4.setDisable(true);

            star5.setDisable(true);

            //dialogStage.close();

        }

    }
    public void myparametre(Agence id) {
        // Utilisez le paramètre comme vous le souhaitez ici
        System.out.println("Selected ID: " + id);
        this.Parametre2=id;

        System.out.println("ss"+Parametre2);


    }
}