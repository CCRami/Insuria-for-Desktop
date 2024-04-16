package Controller;

import Entity.InsuranceCategory;
import Service.InsuranceCatService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Date;

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
            alert.setContentText("agency added successfully");
            alert.showAndWait();



        }


    }

    private boolean isInputValid() {
        // Perform validation checks here
        // For example, you could check if the input fields are not empty

        if (CatinsuranceNameField.getText().isEmpty() || desccatinsField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return false;
        }

        // Add more validation checks as needed

        return true;
    }


}

