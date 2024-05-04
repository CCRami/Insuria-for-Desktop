package Controllers;

import Entities.Agence;
import Entities.Avis;
import Services.AvisService;
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
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Helper.AlertHelper;

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
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public String filterBadWords(String input) {
        // Define a set of bad words for efficient lookup
        Set<String> badWords = new HashSet<>(Arrays.asList("fuckoff","fuck", "bitch", "motherfucker", "merde", "putin", "mo8t", "bhim"));
        boolean badWordDetected = false;
        // Create a regex pattern to match bad words, handling word boundaries and ignoring case
        StringBuilder patternBuilder = new StringBuilder();
        for (String badWord : badWords) {
            if (patternBuilder.length() > 0) {
                patternBuilder.append("|");
            }
            patternBuilder.append("\\b").append(Pattern.quote(badWord)).append("\\b");
        }
        Pattern pattern = Pattern.compile(patternBuilder.toString(), Pattern.CASE_INSENSITIVE);

        // Use StringBuffer for efficient string modifications during replacement
        StringBuffer result = new StringBuffer();

        // Replace each bad word with asterisks pattern (keeping first and last character)
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            badWordDetected = true;
            String replacement = "*".repeat(matcher.group().length());
            /*String word = matcher.group();
            StringBuilder replacement = new StringBuilder();
            replacement.append(word.charAt(0));
            for (int i = 1; i < word.length() - 1; i++) {
                replacement.append("*");
            }
            if (word.length() > 1) {
                replacement.append(word.charAt(word.length() - 1));
            }*/
            matcher.appendReplacement(result, replacement.toString());
        }
        if (badWordDetected) {
            // Show an alert message to the user
            AlertHelper.showAlert(Alert.AlertType.ERROR, SystemColor.window, "Error",
                    "ATTENTION !! Vous avez écrit un gros mot");
            scheduler.schedule(() -> {
                // Send email notification
                serviceemail.sentemail("aymenkhelifa01@gmail.com");
            }, 5, TimeUnit.SECONDS);
            // Send an email notification
        }

        matcher.appendTail(result);

        return result.toString();
    }
    @FXML
    void ajouterav(ActionEvent event) {

        if (isInputValid()) {
            String commentaire = commentArea.getText();
            commentaire = filterBadWords(commentaire);
            int note = rating;
            Agence agence =Parametre2;
       //     System.out.println("aa"+commentaire);

            Date create_at = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String creation = format.format(create_at);
            Avis avis =new Avis(commentaire,note,creation,111,agence,false);
          //  System.out.println("dd"+avis);
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