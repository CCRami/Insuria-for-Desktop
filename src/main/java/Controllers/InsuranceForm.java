package Controllers;

import Gemini.HandleInsuranceForm;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import java.util.logging.Logger;

import java.util.logging.Level;




public class InsuranceForm {
    @FXML
    private ComboBox<String> ageComboBox;

    @FXML
    private ComboBox<String> incomeComboBox;

    @FXML
    private ComboBox<String> maritalStatusComboBox;
    @FXML
    private ComboBox<String> employmentStatusComboBox;
    @FXML
    private ComboBox<String> healthStatusComboBox;
    @FXML
    private ComboBox<String> riskToleranceComboBox;
    @FXML
    private ComboBox<String> financialGoalsComboBox;
    @FXML
    private ComboBox<String> coverageLevelComboBox;
    @FXML
    private ComboBox<String> geographicLocationComboBox;

    @FXML
    private TextField assetsTextField;
    @FXML
    private TextField liabilitiesTextField;



    @FXML
    private TextArea answerTextArea;


    public void initialize() {

    }
    @FXML
    public void handleSubmitButton() {
        Task<String> recommendationTask = new Task<>() {
            @Override
            protected String call() {
                String age = ageComboBox.getValue();
                String income = incomeComboBox.getValue();
                String marital = maritalStatusComboBox.getValue();
                String employement = employmentStatusComboBox.getValue();
                String health = healthStatusComboBox.getValue();
                String risk = riskToleranceComboBox.getValue();
                String financial = financialGoalsComboBox.getValue();
                String coverage = coverageLevelComboBox.getValue();
                String geographic = geographicLocationComboBox.getValue();
                String assets = assetsTextField.getText();
                String liabilities = liabilitiesTextField.getText();
                String apiKey = "";

                // Get insurance recommendation from the service
                try{
                return HandleInsuranceForm.getInsuranceRecommendation(age, income, marital, employement,health,risk,financial,coverage,geographic,assets,liabilities, apiKey);
                } catch (Exception e) {


                    throw new RuntimeException("Error contacting the insurance recommendation service: " + e.getMessage());
                }
            }
        };

        recommendationTask.setOnSucceeded(event -> {
            // Set the recommendation in the text area when the task succeeds
            answerTextArea.setText(recommendationTask.getValue());
        });

        recommendationTask.setOnFailed(event -> {
            // Handle failure appropriately
            Throwable th = recommendationTask.getException();
            answerTextArea.setText(th.getMessage());

        });

        // Run the task on a separate thread
        Thread thread = new Thread(recommendationTask);
        thread.setDaemon(true);
        thread.start();
    }



}
