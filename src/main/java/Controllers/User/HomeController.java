package Controllers.User;
import Controllers.dashboard;
import Entities.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController {
    @FXML
    private Text emailtxt;

    public String email;

    public void setEmailtxt(String emailtxt) {
        this.emailtxt.setText(emailtxt);
    }


    @FXML
    void showUsers(MouseEvent event) {

        System.out.println(UserSession.getInstance("",""));

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Node eventFXML = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML and set it on the current stage
            Scene scene = new Scene((Parent) eventFXML);
            stage.setScene(scene);

            // Get the controller of the scene and use it if necessary
            dashboard controller = loader.getController();
            controller.showUsers(event);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }

}
