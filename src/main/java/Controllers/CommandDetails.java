package Controllers;

import Entities.Commande;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.ArrayList;

public class CommandDetails {
    @FXML
    private Label montantLabel;

    @FXML
    private Label dateEffetLabel;

    @FXML
    private Label dateExpLabel;
    @FXML
    private VBox doaDetailsContainer;
    // Other labels for displaying DOA details, user details, etc.

    public void initialize() {
        // Initialize your controller
    }

    public void initData(Commande commande) {
        // Display the details of the commande object
        montantLabel.setText(String.valueOf(commande.getMontant()));
        dateEffetLabel.setText(commande.getDate_effet() != null ? commande.getDate_effet().toString() : "N/A");
        dateExpLabel.setText(commande.getDate_exp() != null ? commande.getDate_exp().toString() : "N/A");

        // Display DOA details
        ArrayList<String> doaFull = commande.getDoa_full();
        if (doaFull != null && !doaFull.isEmpty()) {
            for (String json : doaFull) {
                // Remove curly braces from the JSON string
                json = json.replaceAll("[{}]", "");
                // Split the JSON string by commas
                String[] keyValuePairs = json.split(",");
                for (String pair : keyValuePairs) {
                    // Split each key-value pair by colon
                    String[] keyValue = pair.split(":");
                    // Extract key and value
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    Label label = new Label(key + ": " + value);
                    // Add the label to the VBox for DOA details
                    doaDetailsContainer.getChildren().add(label);
                }
            }
        } else {
            // Handle case where DOA details are empty
        }

        // Populate other labels with user details, etc.
    }

    @FXML
    private void viewSelectedInsurances(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommandeBasket.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
