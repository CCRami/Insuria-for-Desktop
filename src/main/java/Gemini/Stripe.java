package Gemini;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Stripe extends Application {

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Load the URL of your Stripe payment form
        webEngine.load("https://your-stripe-payment-form-url");

        // Set up the WebView
        StackPane root = new StackPane();
        root.getChildren().add(webView);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Stripe Payment Form");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
