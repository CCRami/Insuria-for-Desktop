package Controllers;

import Entities.Commande;
import Entities.UserSession;
import Services.CommandeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CommandeBasket implements Initializable {
    private List<Commande> selectedCommandes = new ArrayList<>();

    private CommandeService com = new CommandeService();
    @FXML
    private Label totalPriceLabel;
    @FXML
    private ListView<Commande> selectedCommandesListView;
    float totalAmount =0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedCommandes = com.getUncheckedCommandesByUserId(Integer.parseInt(UserSession.id));


        // Calculate total amount
        totalAmount = calculateTotalAmount();

        // Update total price label
        totalPriceLabel.setText(String.format(String.valueOf(totalAmount)));

        ObservableList<Commande> data = FXCollections.observableArrayList(selectedCommandes);
        selectedCommandesListView.setItems(data);
        initializeListView();
    }
    private void initializeListView() {
        selectedCommandesListView.setCellFactory(param -> new ListCell<Commande>() {
            @Override
            protected void updateItem(Commande item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10);// Vertical gap between rows
                    hbox.setAlignment(Pos.CENTER_LEFT);

                    // Labels for each attribute


                    Label nameValueLabel = new Label(item.getDoa_com_id().getName_ins());
                    nameValueLabel.setMinWidth(185.0);
                    nameValueLabel.setMaxWidth(185.0);
                    nameValueLabel.setStyle("-fx-font-size: 12px;");



                    Label amountValueLabel = new Label(String.format("%.2f DT", item.getMontant()));
                    amountValueLabel.setMinWidth(173.0);
                    amountValueLabel.setMaxWidth(173.0);
                    amountValueLabel.setStyle("-fx-font-size: 12px;");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


                    Label effectiveDateValueLabel = new Label(dateFormat.format(item.getDate_effet()));
                    effectiveDateValueLabel.setMinWidth(167.0);
                    effectiveDateValueLabel.setMaxWidth(167.0);
                    effectiveDateValueLabel.setStyle("-fx-font-size: 12px;");




                    Label expirationDateValueLabel = new Label(dateFormat.format(item.getDate_exp()));
                    expirationDateValueLabel.setMinWidth(150.0);
                    expirationDateValueLabel.setMaxWidth(150.0);
                    expirationDateValueLabel.setStyle("-fx-font-size: 12px;");
                    Button deleteButton = new Button("Delete");
                    deleteButton.getStyleClass().add("button2"); // Apply a CSS class if needed
                    deleteButton.setOnAction(event -> {
                        // Action to remove the item from the list or perform other deletion logic
                        delete(item.getId()); // Assuming you have a delete method that handles deletion
                        selectedCommandesListView.getItems().remove(item);
                        if (totalAmount-item.getMontant()<1)
                            totalPriceLabel.setText("0.0 DT");
                    });
                    Button updateButton = new Button("Update");
                    updateButton.getStyleClass().add("button2"); // Apply a CSS class if needed
                    updateButton.setOnAction(event -> {
                        openModifyPopup(item);
                    });

                    // Adding all labels to the grid
                    hbox.getChildren().addAll(
                             nameValueLabel,
                            amountValueLabel,
                             effectiveDateValueLabel,
                             expirationDateValueLabel,
                            deleteButton,
                            updateButton
                    );
                    setGraphic(hbox);
                    setText(null);
                }

            }
        });
    }
    @FXML
    private void openModifyPopup(Commande cmd) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditCommande.fxml"));
            Parent root = loader.load();

            System.out.println("Opening modify popup for Commande: " + cmd);

            EditCommande controller = loader.getController();
            controller.setCommandeData(cmd);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modify Commande");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            totalAmount=0;
            initialize(null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void delete(int id) {
        com.deleteCommande(id); // Ici, sinistreService doit être l'instance de ton service qui contient la méthode supprimer
    }

    @FXML
    public void handleCheckout(ActionEvent event) {
        System.out.println("Handling checkout...");
        Session session = processPayment();

        // After the payment process, retrieve the session from Stripe and check its payment status
        try {
            Session retrievedSession = Session.retrieve(session.getId());
            String paymentStatus = retrievedSession.getPaymentStatus();
            if ("paid".equals(paymentStatus)) {
                // The payment was successful
                System.out.println("The payment was successful.");
                com.setAllUserCommandsChecked(Integer.parseInt(UserSession.id));
                showAlert(Alert.AlertType.INFORMATION, "Success", "The payment was successful.");
                ObservableList<Commande> data = FXCollections.observableArrayList(selectedCommandes);
                selectedCommandesListView.getItems().removeAll(data);
                totalPriceLabel.setText("0.0 DT");

            } else {
                // The payment failed
                System.out.println("The payment failed.");
                showAlert(Alert.AlertType.ERROR, "Error", "The payment failed.");
            }
        } catch (StripeException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to retrieve the session. Error: " + e.getMessage());
        }
    }

    private Session processPayment() {
        Stripe.apiKey="sk_test_51Op4l7FzhzWkLRqpfqRHoH1RXw8VdOYqKCJTfrC1kiouqFRRM390lfRq7L1RiywX8FqEtuc5otbrVNKtRqY9H1wA00xP2AAmnQ";
        System.out.println("Total amount calculated: " + totalAmount);
        System.out.println("Total price label updated with: " + String.format("%.2f DT", totalAmount ));
        // Create a Checkout Session for the payment
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://your-domain.com/success")
                .setCancelUrl("https://your-domain.com/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount((long) (totalAmount)*100)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Total Order")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();
        try {
            Session session = Session.create(params);

            // Open a web view with the session's URL
            openStripePaymentWebView(session.getUrl());

            return session;
        } catch (StripeException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Payment failed. Error: " + e.getMessage());
            return null;
        }
    }
    private float calculateTotalAmount() {
        System.out.println("Calculating total amount...");

        System.out.println(selectedCommandes);
        if (selectedCommandes != null) {
            for (Commande commande : selectedCommandes) {
                totalAmount += (long) commande.getMontant();
                System.out.println(totalAmount);
            }
        }
        return totalAmount;  // Return the calculated total amount
    }


    private void openStripePaymentWebView(String url) {
        Stage stage = new Stage();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true); // Enable JavaScript execution

        // Add a change listener to the location property of the webEngine
        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("https://your-domain.com/success")) {
                // Close the stage when the success URL is loaded
                stage.close();
            }
        });
        webEngine.load(url);

        StackPane root = new StackPane();
        root.getChildren().add(webView);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Stripe Payment Form");
        stage.showAndWait();
    }







    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void backshop(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
        Parent root = loader.load();
        MouseEvent event1 = null;
        Scene scene = new Scene(root);
        Stage stage = (Stage) totalPriceLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        dashboardFront controller = loader.getController();
        controller.Showins(event1);

    }

    public void setDiscount(double discount) {
        System.out.println("Discount " + discount);
        System.out.println("Total amount before discount: " + totalAmount);
        totalAmount = totalAmount- (float) (totalAmount * discount / 100);
        System.out.println((totalAmount * discount / 100));
        System.out.println("Setting discount: " + totalAmount);
        totalPriceLabel.setText(String.format("%.2f DT", totalAmount));
    }

}
