package Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

import javafx.scene.layout.BorderPane;

public class Dashboardfront implements Initializable {
    @FXML
    private ToggleButton darkModeToggle;

    private boolean isDarkMode = false;


    @FXML
    private BorderPane myBorderPane;

    @FXML
    private VBox vboxdash;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // eventView.setOnMouseClicked(event -> loadFXML("/gererevenement.fxml"));

        // Apply initial mode
        applyMode();
    }
    public void Showcatoff(ActionEvent actionEvent) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Showcat.fxml"));
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



    public void Showoff(ActionEvent actionEvent) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Showof.fxml"));
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






    public void initialize() {
        // eventView.setOnMouseClicked(event -> loadFXML("/gererevenement.fxml"));
        darkModeToggle.setOnAction(this::ToggleDarkMode);
        // Apply initial mode
        applyMode();
    }

    @FXML
    private void ToggleDarkMode(ActionEvent actionEvent) {
        isDarkMode = !isDarkMode; // Toggle the mode
        applyMode(); // Apply the mode
    }

    private void applyMode() {
        if (isDarkMode) {
            // Apply dark mode stylesheet
            myBorderPane.getStylesheets().clear();
            myBorderPane.getStylesheets().add(getClass().getResource("/style/dark_style.css").toExternalForm());
        } else {
            // Apply light mode stylesheet
            myBorderPane.getStylesheets().clear();
            myBorderPane.getStylesheets().add(getClass().getResource("/style/light_style.css").toExternalForm());
        }
    }
}



