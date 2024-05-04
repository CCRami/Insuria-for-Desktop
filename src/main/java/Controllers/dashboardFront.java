package Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class dashboardFront implements Initializable{
    @FXML
    ImageView eventView;



    @FXML
    private BorderPane myBorderPane;

    @FXML
    private VBox vboxdash;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // eventView.setOnMouseClicked(event -> loadFXML("/gererevenement.fxml"));
    }

    @FXML
    void showAvisAgence(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficheravis.fxml"));
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




    @FXML
    void showAgence(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficheragences.fxml"));
            Node eventFXML = loader.load();
//afficheragences
            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }

    @FXML
    void showajouteragence(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouteragence.fxml"));
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