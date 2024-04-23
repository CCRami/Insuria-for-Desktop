package controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class dashboard implements Initializable{
    @FXML
    ImageView eventView;

    @FXML
    private HBox reclamations;

    @FXML
    private BorderPane myBorderPane;

    @FXML
    private VBox vboxdash;

    @FXML
    private HBox compensation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // eventView.setOnMouseClicked(event -> loadFXML("/gererevenement.fxml"));
    }
    @FXML
    void showCompensation(MouseEvent event) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/indemnisationsBack.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)

        }}
    @FXML
    void showReclamations(MouseEvent event) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationsBack.fxml"));
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