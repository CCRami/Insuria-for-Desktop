package edu.esprit.controller;



import edu.esprit.entities.Agence;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;

public class Age {



    @FXML
    private Button Rateus11;

    @FXML
    private Label adress;

    @FXML
    private VBox agevbox;

    @FXML
    private Label creation;

    @FXML
    private Label email;

    @FXML
    private Label nom;

    @FXML
    private Label phone;

    @FXML
    private Button reviews;



    @FXML
    public void Rateus1(Agence agence) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouteravis.fxml"));
            Parent root = loader.load();
// Assuming you have access to the current scene
            Ajouteravis controller = loader.getController();
            controller.myparametre(agence);
             controller.initialize();
            Scene currentScene = Rateus11.getScene();
// Set the new root to the current scene


            currentScene.setRoot(root);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(Agence agence){
        adress.setText(agence.getAddresse());
        nom.setText(agence.getNomage());
        phone.setText(String.valueOf(agence.getTel()));
        email.setText(agence.getEmail());
        creation.setText(agence.getCreate_at());
    }


   /* public void Rateus1(Agence agence) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouteravis.fxml"));
            Parent root = loader.load();
// Assuming you have access to the current scene
            Ajouteravis controller = loader.getController();

            controller.myparametre(agence);
            controller.initialize();
            Scene currentScene = Rateus.getScene();
// Set the new root to the current scene


            currentScene.setRoot(root);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}



