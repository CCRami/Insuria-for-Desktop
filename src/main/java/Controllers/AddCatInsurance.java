package Controllers;

import Entities.InsuranceCategory;
import Services.InsuranceCatService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AddCatInsurance {



    @FXML
    private TextField CatinsuranceNameField;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox fieldsContainer;

    @FXML
    private TextField desccatinsField;

    @FXML
    private Button save;

    @FXML
    private VBox vboxdash;

    @FXML
    void addcatinsAction(ActionEvent event) {

        if (isInputValid()) {
            String namecat = CatinsuranceNameField.getText();
            String descins = desccatinsField.getText();

            InsuranceCategory inscat = new InsuranceCategory(namecat, descins );

            InsuranceCatService service = new InsuranceCatService();
            service.AddCatIns(inscat);


            CatinsuranceNameField.clear();
            desccatinsField.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Category added successfully");
            alert.showAndWait();



        }


    }

    private boolean isInputValid() {
        String catName = CatinsuranceNameField.getText();
        String catDesc = desccatinsField.getText();

        if (catName.isEmpty() || catDesc.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return false;
        }

        // Additional validation checks
        if (catName.length() > 50) { // Example: Limiting the length to 50 characters
            showAlert("Error", "Insurance name is too long. Maximum 50 characters allowed.");
            return false;
        }

        // Check if the insurance name contains numbers
        if (containsNumbers(catName)) {
            showAlert("Error", "Insurance name should not contain numbers.");
            return false;
        }

        // Example of format check using regex (assuming catDesc should not contain special characters)
        if (!catDesc.matches("[a-zA-Z0-9 ]+")) {
            showAlert("Error", "Description should contain only letters, numbers, and spaces.");
            return false;
        }

        // Add more validation checks as needed

        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean containsNumbers(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }



}

