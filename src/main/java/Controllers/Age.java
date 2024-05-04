package Controllers;



import Entities.Agence;


import util.DataSource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

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


    /*public Button Rateus1(Agence agence) {
        System.out.println("zz");
        Button btn = new Button("More Details");
        btn.getStyleClass().add("btn");
        btn.setOnAction(event -> openEditDialog(agence));
        return btn;  // Retournez le bouton pour permettre à la méthode initialize de l'ajouter à container
    }*/
    private Connection cnx;
    private ResultSet result;
    private Statement ste;
    @FXML
    private Label creation1;
    private PreparedStatement pst;
    public void setData(Agence agence){
        adress.setText(agence.getAddresse());
        nom.setText(agence.getNomage());
        phone.setText(String.valueOf(agence.getTel()));
        email.setText(agence.getEmail());
        creation.setText(agence.getCreate_at());

        String sql = "SELECT AVG(note) FROM Avis WHERE agenceav_id=?";

        // Connexion à la base de données
        this.cnx = DataSource.getInstance().getConnection();
        double averageRating = 0.0;

        try {
            // Préparation de la requête SQL
            this.pst = this.cnx.prepareStatement(sql);
            pst.setInt(1, agence.getIdage());

            // Exécution de la requête et obtention du résultat
            this.result = this.pst.executeQuery();
            if (this.result.next()) {
                // Récupération de la moyenne des avis
                averageRating = this.result.getDouble("AVG(note)");
            }
            DecimalFormat df = new DecimalFormat("#.00");
            String formattedRating = df.format(averageRating);
            // Affichage de la moyenne des avis dans l'interface utilisateur
            this.creation1.setText(String.valueOf(formattedRating));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   public void openEditDialog(Agence agence) {
        try {
            System.out.println("mm");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ajouteravis.fxml"));
            Parent root = fxmlLoader.load();
            //EditSinister controller = loader.getController();
           // controller.initData(agence);
           // controller.setUpdateCallback(this::updateSinistreInListView);  // Utilisez la bonne méthode de callback

            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void supprimera(Agence id) {
        System.out.println(id);
        Rateus11.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {   System.out.println(id);

                System.out.println("mm");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ajouteravis.fxml"));
              //  Parent root = null;

                  //  root = fxmlLoader.load();
                    Parent root = fxmlLoader.load();
                   // controller.initData(agence);
                   // controller.setUpdateCallback(this::updateSinistreInListView);  // Utilisez la bonne méthode de callback
                    Ajouteravis controller = fxmlLoader.getController();
                    controller.initialize(id);
                 //   controller.setCloseCallback(() -> stage.close());

                    //controller.setUpdateCallback(this::updatePoliceInListView);  // Utilisez la bonne méthode de callback

                    Stage stage = new Stage();

                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



            }
        });


    }

    public void reviewsbyagence(Agence id) {
        System.out.println(id);
        reviews.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {   System.out.println(id);

                    System.out.println("mmj");

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/avisbackbyagence.fxml"));
                    //  Parent root = null;

                    //  root = fxmlLoader.load();
                    Parent root = fxmlLoader.load();
                    // controller.initData(agence);
                    // controller.setUpdateCallback(this::updateSinistreInListView);  // Utilisez la bonne méthode de callback
                    afficheravisbackbyagence controller = fxmlLoader.getController();
                    controller.initialize(id);
                    //   controller.setCloseCallback(() -> stage.close());

                    //controller.setUpdateCallback(this::updatePoliceInListView);  // Utilisez la bonne méthode de callback

                    Stage stage = new Stage();

                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



            }
        });


    }


/*
    public void Rateus1(Agence agence) {
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



