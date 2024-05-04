package Controllers;

import Entities.Insurance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class InsurancesList {

    @FXML
    private Label insName;

    @FXML
    private VBox agevbox;

    @FXML
    private ImageView insImage;

    @FXML
    private Label insAmount;

    @FXML
    private Button insButton;

    @FXML
    private AnchorPane rootPane; // Inject the root pane


    private Insurance insurance;
    @FXML
    private ListView<Insurance> listView;

    @FXML
    private Label errorMessage;


    @FXML
    private void handleInsuranceButtonClick(ActionEvent actionEvent) throws IOException {
        if (insurance != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCommande.fxml"));
            AnchorPane addCommandeRoot = loader.load();

            // Obtain the controller instance
            AddCommande addCommandeController = loader.getController();

            // Pass the selected insurance to the controller
            addCommandeController.setInsurance(insurance);

            // Get the current scene and stage
            Scene currentScene = rootPane.getScene();
            Stage stage = (Stage) currentScene.getWindow();

            // Create a new scene with the AddCommande page
            Scene addCommandeScene = new Scene(addCommandeRoot, currentScene.getWidth(), currentScene.getHeight());

            // Set the new scene
            stage.setScene(addCommandeScene);
            stage.show();
        } else {
            errorMessage.setText("Please select an insurance.");
        }
    }










    public void setData(Insurance insurance) {
        this.insurance = insurance;
        if (insurance != null) {
            insName.setText(insurance.getName_ins());

            try {
                String imagePath = "file:///" + insurance.getIns_image().replace(" ", "%20");
                Image image = new Image(imagePath);
                insImage.setImage(image);
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace(); // Print the full stack trace for debugging
                // Optionally, provide a fallback image or handle the error gracefully
            }

            insAmount.setText(String.valueOf(insurance.getMontant()));
        }
    }


}
