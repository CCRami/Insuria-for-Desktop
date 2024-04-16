package edu.esprit.controller;

import edu.esprit.entities.Agence;
import edu.esprit.service.AgenceService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
    private Button butonAvis;

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
