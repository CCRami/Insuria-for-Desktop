package Controllers;

import Entities.Commande;
import Services.CommandeService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.lang.reflect.Type;
import java.util.*;

public class EditCommande {

    @FXML
    private TextField insValueField;

    @FXML
    private GridPane doaGrid;

    private Commande commande;
    private Map<String, TextField> doaFieldsMap;

    public void setCommandeData(Commande commande) {
        this.commande = commande;
        doaFieldsMap = new HashMap<>();

        if (commande != null) {
            // Set ins_value
            insValueField.setText(String.valueOf(commande.getIns_value()));

            // Handle each DOA JSON String in the list
            List<String> doaJsonList = commande.getDoa_full();
            if (doaJsonList != null && !doaJsonList.isEmpty()) {
                doaJsonList.forEach(doaJson -> handleDoaJson(doaJson));
            }
        }
    }

    private void handleDoaJson(String doaJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> doaMap = new HashMap<>();

        try {
            // Ensure the string is a valid JSON format
            if (doaJson != null && !doaJson.isEmpty()) {
                // Check if the string is not enclosed in braces
                if (!doaJson.trim().startsWith("{")) {
                    doaJson = "{" + doaJson + "}";
                }
                doaMap = gson.fromJson(doaJson, type);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            // Handle JSON parsing error
        }

        // Display DOA entries in GridPane
        doaMap.forEach((key, value) -> addDoaField(key, value));
    }

    private void addDoaField(String key, String value) {
        // Create label and text field for each DOA entry
        Label label = new Label(key + ":");

        // Apply CSS styling to the label
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;"); // Example styling

        TextField textField = new TextField(value);
        doaFieldsMap.put(key, textField);

        int row = doaGrid.getRowCount(); // Get the current row count to add the new row
        doaGrid.add(label, 0, row);
        doaGrid.add(textField, 1, row);
    }


    // In EditCommande.java

    @FXML
    private void handleSave() {
        try {
            System.out.println("Inside handleSave method.");

            float newInsValue = Float.parseFloat(insValueField.getText());
            System.out.println("New insurance value: " + newInsValue);

            // Gather updated DOA values
            Map<String, String> updatedDoaMap = new HashMap<>();
            doaFieldsMap.forEach((key, textField) -> {
                String fieldValue = textField.getText();
                updatedDoaMap.put(key, fieldValue);
                System.out.println("Updated DOA " + key + ": " + fieldValue);
            });

            Gson gson = new Gson();
            String updatedDoaJson = gson.toJson(updatedDoaMap);
            System.out.println("Updated DOA JSON: " + updatedDoaJson);

            // Update the Commande object
            commande.setIns_value(newInsValue);
            ArrayList<String> doaList = new ArrayList<>();
            doaList.add(updatedDoaJson); // Adding the JSON string to the list
            commande.setDoa_full(doaList);
            System.out.println("Commande object updated: " + commande);

            // Debug: Print the DOA list being set
            System.out.println("DOA list to be set: " + doaList);

            // Update the Commande in the database
            CommandeService commandeService = new CommandeService();
            commandeService.updateCommande(commande, commande.getId());
            System.out.println("Commande updated in the database.");

            // Close the pop-up
            Stage stage = (Stage) insValueField.getScene().getWindow();
            stage.close();
            System.out.println("Popup closed.");
        } catch (NumberFormatException e) {
            // Handle invalid input
            e.printStackTrace();
        }
    }





}