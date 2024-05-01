package controller;

import entity.Indemnissation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.IndemnisationService;

import java.io.IOException;

public class showCompensationRefused {

    @FXML
    private Button cancel;

    @FXML
    private Label montant;

    @FXML
    private Label note;

    @FXML
    private Text reponse;




    private Indemnissation selectedIndemnisation;

    public void iniData(Indemnissation indemnisation) {

        this.selectedIndemnisation = indemnisation;


        note.setText(selectedIndemnisation.getBeneficitaire());

    }

    public void enregistreRefusedAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationsFront.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) cancel.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
        stage.setScene(new Scene(root));
        stage.show();



    }
}
