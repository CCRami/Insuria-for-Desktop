package Controllers.User;

import Controllers.dashboardFront;
import Entities.Commande;
import Entities.User;
import Entities.UserSession;
import Services.AvisService;
import Services.CommandeService;
import Services.ReclamationService;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base32;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.List;
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
    void gotocomp(MouseEvent event) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
            Node eventFXML = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML and set it on the current stage
            Scene scene = new Scene((Parent) eventFXML);
            stage.setScene(scene);

            // Get the controller of the scene and use it if necessary
            dashboardFront controller = loader.getController();
            controller.showComp(event);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }

    }

    @FXML
    void gotodelete(ActionEvent event) {
        UserSession session = UserSession.getInstance(null,null);
        UserService us= new UserService();
        User u= us.displayByid(Integer.parseInt(session.getId()));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to delete your account ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            us.delete(u);
            UserSession.cleanUserSession();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
    void gotoins(MouseEvent event) {

    }

    @FXML
    void gotorev(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserService us= new UserService();
        User u= us.displayByid(Integer.parseInt(UserSession.id));
        bigname.setText(u.getFirst_name()+" "+u.getLast_name());
        prenomlb.setText(u.getFirst_name());
        nomlb.setText(u.getLast_name());
        emaillb.setText(u.getEmail());
        CommandeService cs= new CommandeService();
        List<Commande> comlist = cs.getCommandesByUserId(Integer.parseInt(UserSession.id));
        insnb.setText(String.valueOf(comlist.size()));
        ReclamationService rs= new ReclamationService();
        int i=0;
        for (Commande com: comlist)
        {
            try {
                if (rs.isCommandIdInReclamation(com.getId()))
                    i++;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        comnb.setText(String.valueOf(i));
        AvisService as= new AvisService();
        revnb.setText(String.valueOf(as.getAvisByUserId(Integer.parseInt(UserSession.id)).size()));

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
