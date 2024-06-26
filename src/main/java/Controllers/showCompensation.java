package Controllers;


import Entities.Indemnissation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class showCompensation  {
    @FXML
    private Label amount;

    @FXML
    private Button cancel;

    @FXML
    private HBox compensation;

    @FXML
    private Label date;

    @FXML
    private Label note;

    @FXML
    private HBox rec;
    @FXML
    private AnchorPane anchor;

    @FXML
    void enregistreRefusedAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listReclamationBack.fxml"));
            Parent root = loader.load();
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
        } catch (IOException e) {            e.printStackTrace();
            // Gérer l'erreur si nécessaire
        }
    }

    @FXML
    void showCompensations(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/indemnisationsBack.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) cancel.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur si nécessaire
        }
    }

    @FXML
    void showReclamations(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listReclamationBack.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) cancel.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {            e.printStackTrace();
            // Gérer l'erreur si nécessaire
        }
    }



    private Indemnissation selectedIndemnisation;

    public void initData(Indemnissation indemnisation) {

            this.selectedIndemnisation = indemnisation;

            amount.setText(String.valueOf(selectedIndemnisation.getMontant()));
            date.setText(selectedIndemnisation.getDate());
            note.setText(selectedIndemnisation.getBeneficitaire());

    }


}
