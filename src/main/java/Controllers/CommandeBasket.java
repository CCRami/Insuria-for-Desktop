package Controllers;

import Entities.Commande;
import Services.CommandeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Commande> data = FXCollections.observableArrayList(com.getAllCommandes());
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
                    GridPane grid = new GridPane();
                    grid.setHgap(10); // Horizontal gap between columns
                    grid.setVgap(4); // Vertical gap between rows
                    grid.setAlignment(Pos.CENTER_LEFT);

                    // Labels for each attribute
                    Label nameLabel = new Label("Name: ");
                    nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
                    GridPane.setConstraints(nameLabel, 0, 0);

                    Label nameValueLabel = new Label(item.getDoa_com_id().getName_ins());
                    nameValueLabel.setStyle("-fx-font-size: 12px;");
                    GridPane.setConstraints(nameValueLabel, 1, 0);

                    Label amountLabel = new Label("Amount: ");
                    amountLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
                    GridPane.setConstraints(amountLabel, 0, 1);

                    Label amountValueLabel = new Label(String.format("%.2f €", item.getMontant()));
                    amountValueLabel.setStyle("-fx-font-size: 12px;");
                    GridPane.setConstraints(amountValueLabel, 1, 1);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Label effectiveDateLabel = new Label("Effective Date: ");
                    effectiveDateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
                    GridPane.setConstraints(effectiveDateLabel, 0, 2);

                    Label effectiveDateValueLabel = new Label(dateFormat.format(item.getDate_effet()));
                    effectiveDateValueLabel.setStyle("-fx-font-size: 12px;");
                    GridPane.setConstraints(effectiveDateValueLabel, 1, 2);

                    Label expirationDateLabel = new Label("Expiration Date: ");
                    expirationDateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
                    GridPane.setConstraints(expirationDateLabel, 0, 3);

                    Label expirationDateValueLabel = new Label(dateFormat.format(item.getDate_exp()));
                    expirationDateValueLabel.setStyle("-fx-font-size: 12px;");
                    GridPane.setConstraints(expirationDateValueLabel, 1, 3);

                    // Adding all labels to the grid
                    grid.getChildren().addAll(
                            nameLabel, nameValueLabel,
                            amountLabel, amountValueLabel,
                            effectiveDateLabel, effectiveDateValueLabel,
                            expirationDateLabel, expirationDateValueLabel
                    );
                    setGraphic(grid);
                    setText(null);
                }
            }
        });
    }


    @FXML
    private void handleCheckout(ActionEvent event) {
        processPayment();
    }

    private void processPayment() {
        try {


            // Calculate total amount or get it from the selected orders
            long totalAmount = calculateTotalAmount();

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
                                                    .setUnitAmount(totalAmount)
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

            Session session = Session.create(params);

            // Open a web view with the session's URL
            openStripePaymentWebView(session.getUrl());
        } catch (StripeException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Payment failed. Error: " + e.getMessage());
        }
    }

    private void openStripePaymentWebView(String url) {
        Stage stage = new Stage();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url);

        StackPane root = new StackPane();
        root.getChildren().add(webView);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Stripe Payment Form");
        stage.show();
    }

    private long calculateTotalAmount() {
        long totalAmount = 0;
        if (selectedCommandes != null) {
            for (Commande commande : selectedCommandes) {
                totalAmount += commande.getMontant();
            }
        }
        return totalAmount;  // Return the calculated total amount
    }




    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
