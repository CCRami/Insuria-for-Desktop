package Controller;

import Entity.Commande;
import Entity.Insurance;
import Service.CommandeService;
import Service.InsuranceService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        System.out.println("DOA Details: " + doaDetails); // Debug statement
        if (doaDetails != null && !doaDetails.isEmpty()) {
            System.out.println("DOA Details size: " + doaDetails.size()); // Debug statement
            for (String detail : doaDetails) {
                System.out.println("DOA Detail: " + detail); // Debug statement
                Label label = new Label(detail + ":");
                TextField textField = new TextField();
                textField.setPromptText("Enter " + detail);
                doaFormContainer.getChildren().addAll(label, textField);
            }
        } else {
            Text message = new Text("No DOA details available.");
            message.setStyle("-fx-fill: red;");
            doaFormContainer.getChildren().add(message);
        }
    }


    public Map<String, String> gatherDoaInput() {
        Map<String, String> doaInput = new HashMap<>();

        for (int i = 0; i < doaFormContainer.getChildren().size(); i += 2) {
            Label label = (Label) doaFormContainer.getChildren().get(i);
            TextField textField = (TextField) doaFormContainer.getChildren().get(i + 1);
            String key = label.getText().replace(":", "");
            String value = textField.getText();
            doaInput.put(key, value);
        }

        return doaInput;
    }


    @FXML
    private void saveCommandeAction(ActionEvent event) {
        String insValue = insValueField.getText();

        // Check if Ins Value is empty


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
                        new ArrayList<>(doaInput.values()), // DOA Full
                        insurance, // DOA com ID
                        null, // User ID (null for now)
                        insValueFloat // Ins Value again
                );

                // Print Commande details
                System.out.println("New Commande: " + commande);

                // Now you can save the Commande object to your database
                CommandeService commandeService = new CommandeService();
                commandeService.addCommande(commande);

                // Print success message
                System.out.println("Commande saved successfully!");
            } else {
                // Print error message if insurance is null
                System.out.println("Insurance is null");
            }
        }
    }

