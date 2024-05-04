package Controllers.User;

import Entities.User;
import Entities.UserSession;
import Services.UserService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import helper.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FAController {

    @FXML
    private Button cancelbtn;

    @FXML
    private Button confirmbtn;

    @FXML
    private TextField secretfield;

    public String email;
    public boolean validate=false;

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    void handleCancel(ActionEvent event) {
        confirmbtn.getScene().getWindow().hide();
    }

    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }
    @FXML
    void handleconfirm(ActionEvent event) {
        UserService us = new UserService();
        int id = us.getUserIdByEmail(email);
        User u = us.displayByid(id);
        String secretKey = u.getSecret();
        if (secretfield.getText().equals(getTOTPCode(secretKey))) {
            validate = true;
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