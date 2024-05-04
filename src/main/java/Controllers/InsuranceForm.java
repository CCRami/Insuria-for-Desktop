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


public class InsuranceForm {
    @FXML
    private ComboBox<String> ageComboBox;

    @FXML
    private ComboBox<String> incomeComboBox;

    @FXML
    private ComboBox<String> maritalstatusComboBox;
    @FXML
    private ComboBox<String> employmentstatusComboBox;
    @FXML
    private ComboBox<String> healthstatusComboBox;
    @FXML
    private ComboBox<String> risktoleranceComboBox;
    @FXML
    private ComboBox<String> financialgoalsComboBox;
    @FXML
    private ComboBox<String> coverageLevelComboBox;
    @FXML
    private ComboBox<String> geographicfactorsComboBox;

    @FXML
    private TextField assetsTextField;
    @FXML
    private TextField liabilitiesTextField;

    @FXML
    private CheckBox smokerCheckBox;

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
                String marital = maritalstatusComboBox.getValue();
                String employement = employmentstatusComboBox.getValue();
                String health = healthstatusComboBox.getValue();
                String risk = risktoleranceComboBox.getValue();
                String financial = financialgoalsComboBox.getValue();
                String coverage = coverageLevelComboBox.getValue();
                String geographic = geographicfactorsComboBox.getValue();
                String assets = assetsTextField.getText();
                String liabilities = liabilitiesTextField.getText();
                String apiKey = "";

                // Get insurance recommendation from the service
                return HandleInsuranceForm.getInsuranceRecommendation(age, income, marital, employement,health,risk,financial,coverage,geographic,assets,liabilities, apiKey);
            }
        };

        recommendationTask.setOnSucceeded(event -> {
            // Set the recommendation in the text area when the task succeeds
            answerTextArea.setText(recommendationTask.getValue());
        });

        recommendationTask.setOnFailed(event -> {
            // Handle failure appropriately
            answerTextArea.setText("Error generating insurance recommendation");
        });

        // Run the task on a separate thread
        Thread thread = new Thread(recommendationTask);
        thread.setDaemon(true);
        thread.start();
    }


    // Define your other methods and FXML elements here...
}
