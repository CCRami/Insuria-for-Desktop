package controller;

import entity.Indemnissation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class showCompensationAccepted {

    @FXML
    private Label amount;

    @FXML
    private Button cancel;

    @FXML
    private Label date;

    @FXML
    private Label note;



    private Indemnissation selectedIndemnisation;

    public void iniData(Indemnissation indemnisation) {

        this.selectedIndemnisation = indemnisation;
        String montantText = String.valueOf(selectedIndemnisation.getMontant());
amount.setText(montantText);
date.setText(selectedIndemnisation.getDate().toString());
        note.setText(selectedIndemnisation.getBeneficitaire());

    }
    @FXML
    void enregistreRefusedAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationsFront.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) cancel.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
        stage.setScene(new Scene(root));
        stage.show();
    }
}
