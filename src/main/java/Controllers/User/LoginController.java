package Controllers.User;


//import entities.UserSession;
import Controllers.User.UserProfileController;
import Controllers.dashboard;
import Entities.UserSession;
import helper.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import Services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    private final UserService us=new UserService();

    @FXML
    private Button rest;

    @FXML
    private Button btn;
    @FXML
    private Button btnaz;

    @FXML
    private TextField mail;


    @FXML
    private PasswordField password;
    Window window;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    void AjouterUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Signup.fxml"));
        Parent root=loader.load();
        SignupController auc=loader.getController();
        btnaz.getScene().setRoot(root);


    }


    @FXML
    void gotoConfimer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Email.fxml"));
        Parent root=loader.load();
        confirmController auc= loader.getController();
        rest.getScene().setRoot(root);
    }


    void goToHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserProfile.fxml"));
        Parent root=loader.load();
        dashboard auc= loader.getController();
        rest.getScene().setRoot(root);
    }
    void goToAdmin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserProfile.fxml"));
        Parent root=loader.load();
        dashboard auc=loader.getController();
        rest.getScene().setRoot(root);
    }

    @FXML
    void login(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserProfile.fxml"));
        Parent root = loader.load();
        us.displayAll();
        //  UserSession.cleanUserSession();
        UserProfileController auc = loader.getController();
        System.out.println("hello");
        if (us.authenticate(mail.getText(), password.getText()) != 0) {
            UserSession u = UserSession.getInstace(mail.getText(), us.role(us.authenticate(mail.getText(), password.getText())));
            System.out.println(u);
            if (us.role(us.authenticate(mail.getText(), password.getText())).equals("[\"ROLE_CLIENT\"]")) {
                auc.setAfficherTF(" bienvnue Client");
                btn.getScene().setRoot(root);
                //goToHome();
            } else if (us.role(us.authenticate(mail.getText(), password.getText())).equals("Admin")) {
                auc.setAfficherTF(" bienvnue Admin");
                btn.getScene().setRoot(root);
                //goToAdmin();
            }

            } else if (mail.getText().isEmpty() || password.getText().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "insert l'email et mot de passe.");
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "Invalid email and password.");

            }
        }
    }


