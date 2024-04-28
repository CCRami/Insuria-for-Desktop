package edu.esprit.controller;

import edu.esprit.entities.Agence;
import edu.esprit.service.AgenceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Afficher implements Initializable {

    @FXML
    private GridPane container;

    @FXML
    private Label nomRestau;

    @FXML
    private Label adresse;

    @FXML
    private Label email;

    @FXML
    private Label phone;


    @FXML
    private Button aviss;

    @FXML
    void aviss2(ActionEvent event) {

        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficheravis.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur associé à la nouvelle page
            //afficheravisbackbyagence controller = loader.getController();

            // Appeler la méthode du contrôleur pour passer le paramètre selectedId
           // System.out.println("fff"+selectedId);
            //controller.myparametre(selectedId);
            // controller.initialize();// Assuming you have access to the current scene
            Scene currentScene = aviss.getScene();
            // Set the new root to the current scene
            currentScene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private final AgenceService serviceAgence = new AgenceService();
    private List<Agence> agences;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       agences = serviceAgence.getAll();
        int column = 0;
        int row = 1;
        try {
            for (Agence agence : agences) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/agence.fxml"));
                try {
                    Object anchorPane = fxmlLoader.load();
                    Age age = fxmlLoader.getController();
                    System.out.println(agences);
                    age.setData(agence);
                    age.supprimer(agence);
                  //  age.Rateus1(agence);
                   // Button rateusButton = age.Rateus1(agence);

                    // Définir un gestionnaire d'événements sur le bouton pour ouvrir la boîte de dialogue d'édition
                 //  rateusButton.setOnAction(event -> age.openEditDialog(agence));


                    //age.openEditDialog(agence);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    container.add((Node) anchorPane, column++, row);
                    GridPane.setMargin((Node) anchorPane, new Insets(10));
                } finally {
                    // Assurez-vous de fermer les flux pour éviter les fuites de ressources
                    fxmlLoader = null;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du FXML", e);
        }
    }
}
