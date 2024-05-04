package Controllers.User;

import Services.UserService;
import helper.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

public class ConfirmEmailController {

    @FXML
    private Button cancelbtn;

    @FXML
    private Button confirmbtn;

    @FXML
    private TextField secretfield;

    Window window;
    @FXML
    void handleCancel(ActionEvent event) {
        secretfield.getScene().getWindow().hide();
    }


    @FXML
    void handleconfirm(ActionEvent event) {
        UserService us= new UserService();
        String alert= us.updateVerifiedBySecret(secretfield.getText());
        us.updateSecretToNull(secretfield.getText());
        Alert expiredAlert = new Alert(Alert.AlertType.INFORMATION);
        expiredAlert.setTitle("Email Confirmation");
        expiredAlert.setHeaderText("Status");
        expiredAlert.setContentText(alert);
        expiredAlert.showAndWait();
        handleCancel(null);
    }

}
