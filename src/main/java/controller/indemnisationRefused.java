package controller;


import entity.Indemnissation;
import entity.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.IndemnisationService;

import java.sql.SQLException;

public class indemnisationRefused {

    @FXML
    private TextField beneficitaire;
    private Indemnissation selectedIndemnisation;
private Reclamation selectedReclamation;

    @FXML
    void initialize()  {


        assert beneficitaire != null : "fx:id=\"beneficitaire\" was not injected: check your FXML file 'indemnisationAccepted.fxml'.";

    }
    @FXML
    private Text msgError;

    @FXML
    void enregistreRefusedAction(ActionEvent event) {
        if(itsCorrect()){
            selectedIndemnisation.setBeneficitaire(beneficitaire.getText());
            IndemnisationService service = new IndemnisationService();

            try {
                service.modifierIndemnisation(selectedIndemnisation);
selectedReclamation.setIndemnisation(selectedIndemnisation);
               System.out.println(selectedReclamation.getIndemnisation());
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText(" added successfully");
                successAlert.showAndWait();

            } catch (SQLException e) {
                // Afficher une alerte en cas d'erreur
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("An error occurred while editing the sinistre.");
                errorAlert.showAndWait();
                System.out.println("Error editing reclamation: " + e.getMessage());
            }

            beneficitaire.clear();

            msgError.setText("");





        }
    }
    private boolean itsCorrect() {
        boolean coorect =true ;
        if (beneficitaire.getText().isEmpty() || beneficitaire.getText().length() <= 10) {
            msgError.setText("Message is required and should be longer than 10 characters");
            coorect = false;
        } else {
            msgError.setText("");
        }
        return coorect;
    }
    @FXML
    private Label label;

    public void initData(Indemnissation indemnisation,Reclamation rec) {
        selectedIndemnisation = indemnisation;
selectedReclamation=rec;

        beneficitaire.setText(selectedIndemnisation.getBeneficitaire());
        label.setText(selectedReclamation.getReponse());


    }
}
