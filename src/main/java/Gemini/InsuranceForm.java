package Gemini;

import Gemini.GeminiRecords;
import Gemini.GeminiService;
import jakarta.annotation.PostConstruct;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;


@SpringBootApplication
@Component
public class InsuranceForm implements Initializable {
    @FXML
    private ComboBox<String> ageComboBox;

    @FXML
    private ComboBox<String> incomeComboBox;

    @FXML
    private Button submitButton;

    @FXML
    private TextArea answerTextArea;

    // Autowire GeminiService

    private GeminiService geminiService;

    public InsuranceForm() {
        this.geminiService = null; // or initialize it properly if needed
    }
    @Autowired
    public InsuranceForm(GeminiService geminiService) {
        this.geminiService = geminiService;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize combo box options
        ageComboBox.setItems(FXCollections.observableArrayList("Under 30", "30-40", "40-50"));
        incomeComboBox.setItems(FXCollections.observableArrayList("Low", "Medium", "High"));
    }

    // PostConstruct method to ensure GeminiService is injected after construction
    @PostConstruct
    public void init() {
        System.out.println("GeminiService injected successfully: " + geminiService);
    }

    @FXML
    public void handleSubmitButton(ActionEvent event) {
        String age = ageComboBox.getValue();
        String income = incomeComboBox.getValue();

        String prompt = "What is your age? " + age + "\nWhat is your annual income? " + income + "\nAnswer me with one word what is the best insurance for me";

        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                System.out.println("Executing chatGemini method...");

                return chatGemini(prompt);

            }

            @Override
            protected void succeeded() {
                System.out.println("Task succeeded!");
                String response = getValue();
                answerTextArea.setText(response);
            }

            @Override
            protected void failed() {
                System.out.println("Task failed!");
                Throwable exception = getException();
                Platform.runLater(() -> answerTextArea.setText("Error: " + exception.getMessage()));
            }
        };

        System.out.println("Starting task...");
        new Thread(task).start();
    }

    private String chatGemini(String prompt) {
        try {
            System.out.println("Prompt sent to Gemini: " + prompt);
            System.out.println("Creating GeminiRequest...");
            // Create a GeminiRequest
            GeminiRecords.GeminiRequest request = new GeminiRecords.GeminiRequest(
                    List.of(new GeminiRecords.Content(List.of(new GeminiRecords.TextPart(prompt)))));

            // Get completion from GeminiService
            System.out.println("Getting completion from GeminiService...");
            return geminiService.getCompletion(request).toString();
        } catch (Exception e) {
            System.out.println("An exception occurred during the API request: " + e.getMessage());
            return "An exception occurred during the API request: " + e.getMessage();
        }
    }

}