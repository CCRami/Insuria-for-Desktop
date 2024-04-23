package controller;


import entity.Indemnissation;
import entity.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.IndemnisationService;

import java.sql.SQLException;
import java.time.LocalDate;

public class IndemnisationAccepted {
private Indemnissation  selectedIndemnisation;
    private Reclamation  selectedReclaamtion;

    @FXML
    private Label label;
private Reclamation rec;

    @FXML
    private DatePicker date;

    @FXML
    private Text dateError;

    @FXML
    private Button enregistre;

    @FXML
    private TextField montant;

    @FXML
    private Text montantError;

    @FXML
    private TextField beneficitaire;

    @FXML
    private Text msgError;

    @FXML
    private BorderPane myBorderPane;

    @FXML
    private VBox vboxdash;
    @FXML
    void initialize()  {

        assert montant != null : "fx:id=\"montant\" was not injected: check your FXML file 'indemnisationAccepted.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'indemnisationAccepted.fxml'.";
        assert beneficitaire != null : "fx:id=\"beneficitaire\" was not injected: check your FXML file 'indemnisationAccepted.fxml'.";

    }






    @FXML
    void enregistreAction(ActionEvent event) {

        if (isInputValid()) {
            String montantText = montant.getText();
            selectedIndemnisation.setMontant(Float.parseFloat(montantText));
            selectedIndemnisation.setDate(date.getValue().toString());
            selectedIndemnisation.setBeneficitaire(beneficitaire.getText());


            IndemnisationService service = new IndemnisationService();

            try {
                service.modifierIndemnisation(selectedIndemnisation);

               selectedReclaamtion.setIndemnisation(selectedIndemnisation);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("sinistre added successfully");
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

            montant.clear();
            beneficitaire.clear();
            date.getEditor().clear();
            msgError.setText("");
            dateError.setText("");
            montantError.setText("");




        }}

    private boolean isInputValid() {
        boolean isValid = true;

        if (montant.getText().isEmpty() || Float.parseFloat(montant.getText()) <= 0) {
            montantError.setText("Montant is required and should be > 0");
            isValid = false;
        } else {
            montantError.setText("");
        }


        if (beneficitaire.getText().isEmpty() || beneficitaire.getText().length() <= 10) {
            msgError.setText("Message is required and should be longer than 10 characters");
            isValid = false;
        } else {
            msgError.setText("");
        }
        LocalDate currentDate = LocalDate.now();

        if (date.getValue() == null) {
            dateError.setText("Date must not be empty");
            isValid = false;
        } else if (date.getValue().isBefore(currentDate)) {

            dateError.setText("Date must be in the futur");
            isValid = false;
        } else {
            // Clear the error message
            dateError.setText("");
        }
        return isValid;

    }


    public void initData(Indemnissation indemnisation,Reclamation rec) throws SQLException {
       selectedIndemnisation = indemnisation;
       selectedReclaamtion=rec;
        montant.setText(String.valueOf(selectedIndemnisation.getMontant()));
label.setText(selectedReclaamtion.getReponse());
        beneficitaire.setText(selectedIndemnisation.getBeneficitaire());
        LocalDate parsedDate = LocalDate.parse(selectedIndemnisation.getDate());

        date.setValue(parsedDate);

    }
}



