package Controllers.User;

import Entities.User;
import Entities.UserSession;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base32;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {

    @FXML
    private Label comnb;

    @FXML
    private Label bigname;

    @FXML
    private Label emaillb;

    @FXML
    private ImageView imageView;

    @FXML
    private Label insnb;

    @FXML
    private Label name;

    @FXML
    private Label nomlb;

    @FXML
    private Label prenomlb;

    @FXML
    private Label revnb;

    @FXML
    private Button fabtn;

    @FXML
    private ImageView bgimg;

    public void setImageViewSize(double width, double height) {
        bgimg.setFitWidth(width);
        bgimg.setFitHeight(height);
    }
    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }


    @FXML
    void goto2fa(ActionEvent event) {
        UserSession session = UserSession.getInstance(null,null);
        UserService us= new UserService();
        User u= us.displayByid(Integer.parseInt(session.getId()));
        if (u.getSecret()==null) {
            try {

                String secret = generateSecretKey();
                us.updateSecret(u.getId(), secret);
                fabtn.setText("Deactivate 2FA");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/qrcode.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("2FA");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            us.updateSecret(u.getId(), null);
            fabtn.setText("Activate 2FA");
        }
    }

    @FXML
    void gotocomp(ActionEvent event) {

    }

    @FXML
    void gotodelete(ActionEvent event) {

    }

    @FXML
    void gotoedit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditUser.fxml"));
            Parent root = loader.load();
            EditUserController controller = loader.getController();
            UserSession session = UserSession.getInstance(null,null);
            UserService us= new UserService();
            User u= us.displayByid(Integer.parseInt(session.getId()));
            controller.initData(u);
            Stage stage = new Stage();
            stage.setTitle("Edit User");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            initialize(null,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gotoins(ActionEvent event) {

    }

    @FXML
    void gotorev(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserService us= new UserService();
        User u= us.displayByid(Integer.parseInt(UserSession.id));
        bigname.setText(u.getFirst_name()+" "+u.getLast_name());
        prenomlb.setText(u.getFirst_name());
        nomlb.setText(u.getLast_name());
        emaillb.setText(u.getEmail());
        if (u.getSecret()==null){
            fabtn.setText("Activate 2FA");
        }
        else {
            fabtn.setText("Deactivate 2FA");
        }
        if (u.getAvatar() != null) {
            imageView.setImage(new Image(u.getAvatar()));
        }
        else {
            imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/homme.png"))));
        }
    }
}
