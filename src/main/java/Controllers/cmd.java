package Controllers;

import Entities.Commande;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Services.ReclamationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class cmd implements Initializable {

    @FXML
    private Button add;

    @FXML
    private Text id;

    @FXML
    private Text insValue;
private Commande command;
ReclamationService s=new ReclamationService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        init();


    }
    double fl;
    private void init() {
        try {

            fl = s.afficherUneCommande(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        insValue.setText(String.valueOf(fl));
        id.setText(   String.valueOf(1));
    }


    @FXML
    void addRec(ActionEvent event) {

        try {this.command= s.afficher(1);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addReclamation.fxml"));
            Parent root = loader.load();
            addReclamation controller = loader.getController();
            controller.initData(command); // Transférez la réclamation à l'interface d'édition
            // Utilisez votre objet Stage pour afficher la nouvelle interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur si nécessaire
        }
    }
}
