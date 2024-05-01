package controller;


import entity.Indemnissation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.IndemnisationService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class showCompensation  {
    private Text choixerror;

    @FXML
    private Label date;

    @FXML
    private Button enrecancelgistre;

    @FXML
    private Label montant;

    @FXML
    private Label msg;

    @FXML
    private Text reponse;

    @FXML
    void enregistrcancelActioneAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listReclamationBack.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) enrecancelgistre.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {            e.printStackTrace();
            // Gérer l'erreur si nécessaire
        }
    }

    IndemnisationService serviceindemnisation = new IndemnisationService();
    private Indemnissation selectedIndemnisation;

    public void iniData(Indemnissation indemnisation) {

            this.selectedIndemnisation = indemnisation;

            montant.setText(String.valueOf(selectedIndemnisation.getMontant()));
            date.setText(selectedIndemnisation.getDate());
            msg.setText(selectedIndemnisation.getBeneficitaire());

    }


}
