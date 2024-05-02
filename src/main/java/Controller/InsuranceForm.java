package Controller;

import Gemini.HandleInsuranceForm;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.util.ArrayList;
import java.util.List;


public class InsuranceForm {
    @FXML
    private ComboBox<String> ageComboBox;

    @FXML
    private ComboBox<String> incomeComboBox;

    @FXML
    private ComboBox<String> dependentsComboBox;

    @FXML
    private TextField occupationTextField;

    @FXML
    private CheckBox smokerCheckBox;

    @FXML
    private TextArea answerTextArea;

    @FXML
    public void handleSubmitButton() {
        String age = ageComboBox.getValue();
        String income = incomeComboBox.getValue();
        String dependents = dependentsComboBox.getValue();
        String occupation = occupationTextField.getText();


        // Get insurance recommendation from the service
        String recommendation = HandleInsuranceForm.getInsuranceRecommendation(age, income, dependents, occupation);

        // Set the recommendation in the text area
        answerTextArea.setText(recommendation);
    }

    // Define your other methods and FXML elements here...
}
