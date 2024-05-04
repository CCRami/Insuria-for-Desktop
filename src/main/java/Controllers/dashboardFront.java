package Controllers;


import Controllers.User.UserProfileController;
import Entities.User;
import Entities.UserSession;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class dashboardFront implements Initializable{
    @FXML
    ImageView eventView;



    @FXML
    private BorderPane myBorderPane;

    @FXML
    private VBox vboxdash;

    @FXML
    private Button Logoutbtn;

    @FXML
    private Button Profilebtn;

    @FXML
    private ImageView profileimg;

    @FXML
    private Label username;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserService us= new UserService();
        User u= us.displayByid(Integer.parseInt(UserSession.id));
        System.out.println(UserSession.id);
        System.out.println(u.getFirst_name());
        username.setText(u.getFirst_name());
        if (u.getAvatar() != null) {
            profileimg.setImage(new Image(u.getAvatar()));
        }
        else {
            profileimg.setImage(new Image("https://i.imgur.com/x5co7s8.png"));
        }
    }

    @FXML
    void showAvisAgence(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficheravis.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }







    @FXML
    void showAgence(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficheragences.fxml"));
            Node eventFXML = loader.load();
//afficheragences
            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }

    @FXML
    void showajouteragence(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouteragence.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }
    @FXML
    void GotoLogout(ActionEvent event) throws IOException {
        UserSession.cleanUserSession();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Node eventFXML = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) eventFXML);
        stage.setScene(scene);
    }

    @FXML
    void GotoProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserProfile.fxml"));
        AnchorPane eventFXML = loader.load();
        UserProfileController controller = loader.getController();
        controller.setImageViewSize(vboxdash.getWidth(), vboxdash.getHeight());
        vboxdash.getChildren().clear();
        vboxdash.getChildren().add(eventFXML);
    }

    public void showComp(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationsFront.fxml"));
        AnchorPane eventFXML = loader.load();
        reclamationsFront controller = loader.getController();
        vboxdash.getChildren().clear();
        vboxdash.getChildren().add(eventFXML);
    }
    public void showPolice(ActionEvent actionEvent) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/affichagefrontPolice.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }

    public void showSinistre(ActionEvent actionEvent) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/affichagefrontSinistre.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }

    }
}