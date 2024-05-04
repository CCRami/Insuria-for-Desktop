package Controllers;

import Entities.Commande;
import Entities.Insurance;
import Entities.UserSession;
import Services.CommandeService;
import Services.InsuranceService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class AddCommande {

    @FXML
    private TextField insValueField;

    @FXML
    private Text errorInsValue;

    @FXML
    private Button saveButton;

    @FXML
    private VBox doaFormContainer;

    private InsuranceService insuranceService;

    public AddCommande() {
        insuranceService = new InsuranceService(); // Initialize InsuranceService
    }

    @FXML
    private void initialize() {
        // Initialize your controller
    }

    private Insurance insurance;

    public void setInsurance(Insurance insurance) {
        if (insurance != null) {
            // Call getInsuranceById method to fetch insurance details including DOA
            Insurance fetchedInsurance = insuranceService.getInsuranceById(insurance.getId());
            if (fetchedInsurance != null) {
                this.insurance = fetchedInsurance;
                System.out.println("Fetched Insurance: " + fetchedInsurance);
                System.out.println("Insurance DOA Details: " + fetchedInsurance.getDoa());
                displayDoaForm(fetchedInsurance.getDoa());
                System.out.println("Selected Insurance: " + fetchedInsurance);
            } else {
                // Print error message if insurance details are not fetched
                errorInsValue.setText("Failed to fetch insurance details");
            }
        } else {
            // Print error message if insurance object is null
            errorInsValue.setText("Insurance object is null");
        }
    }

    private void displayDoaForm(ArrayList<String> doaDetails) {
        // Clear existing input fields
        doaFormContainer.getChildren().clear();
        if (doaDetails != null && !doaDetails.isEmpty()) {
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(10));

            int row = 0;
            for (String detail : doaDetails) {
                Label label = new Label(detail + ":");
                label.getStyleClass().add("label"); // Apply label style

                TextField textField = new TextField();
                textField.setPromptText("Enter " + detail);
                textField.getStyleClass().add("text-field"); // Apply text field style

                gridPane.addRow(row++, label, textField);
            }

            doaFormContainer.getChildren().add(gridPane);
            doaFormContainer.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm()); // Add CSS file
        } else {
            Text message = new Text("No DOA details available.");
            message.setStyle("-fx-fill: red;");
            doaFormContainer.getChildren().add(message);
        }
    }

    public Map<String, String> gatherDoaInput() {
        Map<String, String> doaInput = new HashMap<>();
        if (doaFormContainer.getChildren().get(0) instanceof GridPane) {
            GridPane gridPane = (GridPane) doaFormContainer.getChildren().get(0);
            for (int i = 0; i < gridPane.getChildren().size(); i += 2) {
                Label label = (Label) gridPane.getChildren().get(i);
                TextField textField = (TextField) gridPane.getChildren().get(i + 1);
                String key = label.getText().replace(":", "");
                String value = textField.getText();
                doaInput.put(key, value);
            }
        }
        return doaInput;
    }

    public ArrayList<String> convertDoaInputToList(Map<String, String> doaInput) {
        ArrayList<String> doaList = new ArrayList<>();
        for (Map.Entry<String, String> entry : doaInput.entrySet()) {
            doaList.add(entry.getKey() + ": " + entry.getValue());
        }
        return doaList;
    }





    @FXML
    private void saveCommandeAction(ActionEvent event) {
        String insValue = insValueField.getText();

        // Check if Ins Value is empty
        if (!insValue.isEmpty()) {
            // Gather DOA input details
            Map<String, String> doaInput = gatherDoaInput();

            // Print Ins Value and DOA Details
            System.out.println("Ins Value: " + insValue);
            System.out.println("DOA Details:");
            for (Map.Entry<String, String> entry : doaInput.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            // Check if insurance is not null
            if (insurance != null) {
                System.out.println("Insurance ID: " + insurance.getId());

                // Create a new Commande object
                float insValueFloat = Float.parseFloat(insValue);
                Commande commande = new Commande(
                        insValueFloat, // Ins Value
                        new Date(), // Date effet (current date)
                        null, // Date exp (null for now)
                        convertDoaInputToList(doaInput), // DOA Full
                        insurance, // DOA com ID
                        null, // User ID (null for now)
                        insValueFloat // Ins Value again
                );

                // Print Commande details
                System.out.println("New Commande: " + commande);

                // Now you can save the Commande object to your database
                CommandeService commandeService = new CommandeService();
                commandeService.addCommande(commande, Integer.parseInt(UserSession.id));

                // Navigate to the new page
                navigateToCommandeDetailsPage(commande); // You need to implement this method

                // Print success message
                System.out.println("Commande saved successfully!");
            } else {
                // Print error message if insurance is null
                System.out.println("Insurance is null");
            }
        } else {
            // Print error message if Ins Value is empty
            System.out.println("Ins Value is empty");
        }
    }

    private void navigateToCommandeDetailsPage(Commande commande) {
        try {
            // Load the FXML file for the commande details page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayMyCommande.fxml"));
            Parent root = loader.load();

            // Get the controller instance from the loader
            CommandDetails controller = loader.getController();

            // Pass the commande object to the controller
            controller.initData(commande);

            // Create a new scene with the loaded FXML content
            Scene scene = new Scene(root);

            // Get the stage from the current scene
            Stage stage = (Stage) insValueField.getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

