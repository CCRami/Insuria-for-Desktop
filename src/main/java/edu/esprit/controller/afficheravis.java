package edu.esprit.controller;

import edu.esprit.entities.Agence;
import edu.esprit.entities.Avis;
import edu.esprit.service.AgenceService;
import edu.esprit.service.AvisService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class afficheravis implements Initializable {

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
    private Button butonAvis;

    private final AvisService serviceAvis = new AvisService();
    private List<Avis> aviss;
    @FXML
    void agen1(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficheragences.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur associé à la nouvelle page
            //afficheravisbackbyagence controller = loader.getController();

            // Appeler la méthode du contrôleur pour passer le paramètre selectedId
            // System.out.println("fff"+selectedId);
            //controller.myparametre(selectedId);
            // controller.initialize();// Assuming you have access to the current scene
            Scene currentScene = agen.getScene();
            // Set the new root to the current scene
            currentScene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button agen;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aviss = serviceAvis.getAllavis();
        int column = 0;
        int row = 1;
        try {
            for (Avis aviss : aviss) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/unAvis1.fxml"));
                try {
                    Object anchorPane = fxmlLoader.load();
                    avis1 age = fxmlLoader.getController();
                    System.out.println(aviss);
                    age.setData(aviss);

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
