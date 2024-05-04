package Controllers.User;

import Entities.User;
import Entities.UserSession;
import Services.UserService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class QRController implements Initializable {

    @FXML
    private ImageView imageview;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSession session = UserSession.getInstance(null,null);
        UserService us= new UserService();
        User u= us.displayByid(Integer.parseInt(session.getId()));
        String secretKey = u.getSecret();
        String barCodeData = getGoogleAuthenticatorBarCode(secretKey, u.getEmail(), "Insuria");
        try {
            imageview.setImage(createQRCode(barCodeData, 200, 200));
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
    public static Image createQRCode(String barCodeData, int height, int width) throws WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(matrix, "png", outputStream);
            byte[] imageData = outputStream.toByteArray();
            return new Image(new ByteArrayInputStream(imageData));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
