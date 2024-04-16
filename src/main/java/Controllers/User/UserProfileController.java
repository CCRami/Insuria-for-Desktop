package Controllers.User;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UserProfileController {

    @FXML
    private TextField AfficherTF;

    public void setAfficherTF(String afficherTF) {
        this.AfficherTF.setText(afficherTF);
    }
}