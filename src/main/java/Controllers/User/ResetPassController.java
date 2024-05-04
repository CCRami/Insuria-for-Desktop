package Controllers.User;

import Entities.User;
import Services.MailService;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.RandomStringUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ResetPassController {

    @FXML
    private Button cancelbtn;

    @FXML
    private Button confirmbtn;

    @FXML
    private TextField newpass;

    @FXML
    private TextField secretfield;

    public String email;
    public String pass;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    @FXML
    void handleCancel(ActionEvent event) {
        confirmbtn.getScene().getWindow().hide();
    }

    @FXML
    void handleconfirm(ActionEvent event) {
        UserService us = new UserService();
        int id = us.getUserIdByEmail(email);
        User u = us.displayByid(id);
        if(secretfield.getText().equals(pass)){
            us.updateUserPassword(u.getId(),newpass.getText());
            confirmbtn.getScene().getWindow().hide();
        }
        else {
            Alert expiredAlert = new Alert(Alert.AlertType.ERROR);
            expiredAlert.setTitle("Error");
            expiredAlert.setHeaderText("Error");
            expiredAlert.setContentText("Invalid secret key.");
            expiredAlert.showAndWait();
        }

    }


}
