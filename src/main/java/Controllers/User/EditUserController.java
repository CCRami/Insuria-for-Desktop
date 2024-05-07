package Controllers.User;

import Controllers.dashboard;
import Entities.User;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class EditUserController {

    @FXML
    private TextField emailtxt;

    @FXML
    private VBox vboxdash;

    @FXML
    private TextField nomtxt;

    @FXML
    private TextField prenomtxt;

    @FXML
    private TextField teltxt;

    private UserService UserService = new UserService();
    private User currentUser;
    private Consumer<User> updateCallback;

    @FXML
    private ImageView profileimg;

    @FXML
    void chooseImageAction(ActionEvent event) {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image"); // Set the title of the file chooser dialog
        fileChooser.setInitialDirectory(new File("C:\\Users\\Mon Pc\\Desktop\\Insuria-for-Desktop\\src\\main\\resources\\images"));


        // Show the file chooser dialog
        Stage stage = (Stage) nomtxt.getScene().getWindow(); // Assuming save button is in the same stage
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Check if a file was selected
        if (selectedFile != null) {
            // Create an Image object from the selected file
            Image image = new Image(selectedFile.toURI().toString());
            profileimg.setImage(image);
            profileimg.setFitWidth(134);
            profileimg.setFitHeight(123);
        }
    }

    public void initData(User user) {
        currentUser = user;
        emailtxt.setText(user.getEmail());
        nomtxt.setText(user.getLast_name());
        prenomtxt.setText(user.getFirst_name());
        teltxt.setText(String.valueOf(user.getPhone_number()));
        if (!currentUser.getAvatar().isEmpty()) {
            profileimg.setImage(new Image(currentUser.getAvatar()));
        }
        else {
            profileimg.setImage(new Image("https://i.imgur.com/x5co7s8.png"));
        }


    }


    @FXML
    private void handleSave() {
        if (validateInput()) {  // Assurez-vous que la validation retourne true avant de continuer
            currentUser.setEmail(emailtxt.getText().trim());
            currentUser.setLast_name(nomtxt.getText().trim());
            currentUser.setFirst_name(prenomtxt.getText().trim());

            Image image = profileimg.getImage();
            String ofImageUrl = image != null ? image.getUrl() : null;
            currentUser.setAvatar(ofImageUrl);


            try {
                UserService.update(currentUser);
                if (updateCallback != null) {
                    updateCallback.accept(currentUser);
                }
                closeStage();  // Fermer la fenêtre uniquement si la mise à jour est réussie
            } catch (Exception e) {
                // Gérer l'exception, par exemple afficher un message d'erreur
                showError("Failed to update User: " + e.getMessage());
            }
        }
    }
    private boolean validateInput() {
        boolean isValid = true;

        if (nomtxt.getText().isEmpty() || prenomtxt.getText().isEmpty() || teltxt.getText().isEmpty() || emailtxt.getText().isEmpty()) {
            Alert expiredAlert = new Alert(Alert.AlertType.ERROR);
            expiredAlert.setTitle("Error");
            expiredAlert.setHeaderText("Error");
            expiredAlert.setContentText("Please fill in all required fields.");
            expiredAlert.showAndWait();
            isValid = false;
        }

        return isValid;
    }
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    private void handleCancel() {
        closeStage();
    }

    private void closeStage() {
        nomtxt.getScene().getWindow().hide();
    }


    public void setUpdateCallback(Consumer<User> callback) {
        this.updateCallback = callback;
    }

    public void saveChanges() {
        // Enregistre les modifications dans la base de données
        UserService.update(currentUser);
        // Appelle le callback pour mettre à jour l'affichage
        if (updateCallback != null) {
            updateCallback.accept(currentUser);
        }

    }



}