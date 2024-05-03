package Controllers;


import Controllers.User.HomeController;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class dashboard implements Initializable {
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




    @FXML
    void showAvisRestau(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backavis.fxml"));
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
    public void showUsers(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserList.fxml"));
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
    public void showDash(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));

            Node eventFXML = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML and set it on the current stage
            Scene scene = new Scene((Parent) eventFXML);
            stage.setScene(scene);

        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSession session = UserSession.getInstance(null,null);
        UserService us= new UserService();
        User u= us.displayByid(Integer.parseInt(session.getId()));
        username.setText(u.getFirst_name());
        if (u.getAvatar() != null) {
            profileimg.setImage(new Image(u.getAvatar()));
        }
        else {
            profileimg.setImage(new Image("https://i.imgur.com/x5co7s8.png"));
        }


    }


    @FXML
    void GotoLogout(ActionEvent event) throws IOException {
        UserSession.cleanUserSession();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));

        Node eventFXML = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a new scene with the loaded FXML and set it on the current stage
        Scene scene = new Scene((Parent) eventFXML);
        stage.setScene(scene);
    }

    @FXML
    void GotoProfile(ActionEvent event) {

    }
}