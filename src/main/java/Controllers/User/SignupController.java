package Controllers.User;


import Entities.User;
import helper.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import Services.UserService;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.util.regex.Pattern;

public class SignupController {

    private final UserService us=new UserService();
    @FXML
    private RadioButton Admin;

    @FXML
    private RadioButton Client;


    @FXML
    private TextField emailTF;

    @FXML
    private PasswordField mdpTF;

    @FXML
    private TextField DBTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private TextField telTF;
    @FXML
    private TextField IDFX;
    Window window;
    @FXML
    void AjouterUser(ActionEvent event) throws IOException {

        if(!patternMatches(emailTF.getText())){
            AlertHelper.showAlert(Alert.AlertType.ERROR,  window, "Error",
                    "Invalid email .");
        }
        if(!isNumeric(telTF.getText())){
            AlertHelper.showAlert(Alert.AlertType.ERROR,  window, "Error",
                    "Invalid phone number .");
            return;
        }
        if((emailTF.getText().equals("") &&mdpTF.getText().equals("")&&DBTF.getText().equals("")&&nomTF.getText().equals(""))){
            AlertHelper.showAlert(Alert.AlertType.ERROR,  window, "Error",
                    "Empty Fields .");
            return;
        }
        if (us.exsitemail(emailTF.getText())){
            AlertHelper.showAlert(Alert.AlertType.ERROR,  window, "Error",
                    "User Already Exist.");
        }
        else {

            if (Admin.isSelected()) {
                us.add(new User(nomTF.getText(), prenomTF.getText(), emailTF.getText(), mdpTF.getText(), Integer.parseInt(telTF.getText()), DBTF.getText(), "[\"ROLE_ADMIN\"]"));
            }

            if (Client.isSelected()) {
                us.add(new User(nomTF.getText(), prenomTF.getText(), emailTF.getText(), mdpTF.getText(), Integer.parseInt(telTF.getText()), DBTF.getText(), "[\"ROLE_CLIENT\"]"));
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            LoginController auc = loader.getController();
            nomTF.getScene().setRoot(root);

        }
    }

    @FXML
    void supprimer(ActionEvent event) {

        us.delete(new User(Integer.parseInt(IDFX.getText())));
    }
    public boolean patternMatches(String emailAddress) {
        final String regex = "^(.+)@(.+)$";
        return Pattern.compile(regex) .matcher(emailAddress) .matches(); }

    public static boolean isNumeric(String str) {
        if (str == null) return false; // handle null-pointer
        if (str.length() == 0) return false; // handle empty strings
        // to make sure that the input is numeric, we have to go through the characters
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false; // we found a non-digit character so we can early return
        }
        return true;
    }


}