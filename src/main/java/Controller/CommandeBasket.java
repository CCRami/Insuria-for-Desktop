package Controller;

import Entity.Commande;
import Service.CommandeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CommandeBasket implements Initializable {
    private List<Commande> selectedCommandes;

    @FXML
    private ListView<Commande> selectedCommandesListView;

    public void setSelectedCommandes(List<Commande> selectedCommandes) {
        this.selectedCommandes = selectedCommandes;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CommandeService commandeService = new CommandeService();
        List<Commande> commandes = commandeService.getAllCommandes();

        // Convert list to observable list
        ObservableList<Commande> observableCommandes = FXCollections.observableArrayList(commandes);

        // Set items to the ListView
        selectedCommandesListView.setItems(observableCommandes);

        // Set cell factory to display Commande ID
        selectedCommandesListView.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Commande commande, boolean empty) {
                super.updateItem(commande, empty);
                if (empty || commande == null) {
                    setText(null);
                } else {
                    setText("Commande ID: " + commande.getId());
                }
            }
        });
    }
    @FXML
    private void handleCheckout(ActionEvent event) {
        // Add Stripe code here
        // Stripe.apiKey = "sk_test_*****";
        // Rest of the Stripe code from your provided snippet

        // Process payment
        processPayment();
    }

    private void processPayment() {
        try {
            // Set your secret key here

            Stripe.apiKey = "sk_test_51Op4l7FzhzWkLRqpfqRHoH1RXw8VdOYqKCJTfrC1kiouqFRRM390lfRq7L1RiywX8FqEtuc5otbrVNKtRqY9H1wA00xP2AAmnQ";
            // Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(1000L) // Amount in cents (e.g., $10.00)
                    .setCurrency("usd")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // If the payment was successful, display a success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Payment successful. PaymentIntent ID: " + intent.getId());
        } catch (StripeException e) {
            // If there was an error processing the payment, display the error message
            showAlert(Alert.AlertType.ERROR, "Error", "Payment failed. Error: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}




