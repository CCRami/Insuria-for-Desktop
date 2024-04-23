package controller;


import entity.Indemnissation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import services.IndemnisationService;

public class showCompensation {
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

    }

    IndemnisationService serviceindemnisation = new IndemnisationService();
    private Indemnissation selectedIndemnisation;

    public void iniData(Indemnissation indemnisation) {
        if (indemnisation != null) {
            selectedIndemnisation = indemnisation;

            montant.setText(String.valueOf(selectedIndemnisation.getMontant()));
            date.setText(selectedIndemnisation.getDate());
            msg.setText(selectedIndemnisation.getBeneficitaire());
        } else {

            }
    }

}
