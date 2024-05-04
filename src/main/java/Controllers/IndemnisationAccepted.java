package Controllers;


import Entities.Indemnissation;
import Entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Services.IndemnisationService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class IndemnisationAccepted {
private Indemnissation  selectedIndemnisation;
    private Reclamation  selectedReclaamtion;

    @FXML
    private Label label;
private double value;

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
    void enregistreAction(ActionEvent event) throws IOException {
        if (isInputValid()) {
            String montantText = montant.getText();
            selectedIndemnisation.setMontant(Float.parseFloat(montantText));
            selectedIndemnisation.setDate(date.getValue().toString());
            selectedIndemnisation.setBeneficitaire(beneficitaire.getText());

            IndemnisationService service = new IndemnisationService();

            try {
                service.modifierIndemnisation(selectedIndemnisation);

                // Assuming selectedReclaamtion is initialized elsewhere
                selectedReclaamtion.setIndemnisation(selectedIndemnisation);

                String emailValue = "farah.adad2001@gmail.com";
                MailService.sendConfirmationEmail(emailValue,selectedReclaamtion);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Indemnisation ajoutée avec succès. Email has been sent.");
                successAlert.showAndWait();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/listReclamationBack.fxml"));
                Parent root = loader.load();

                // Close the current window
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

                // Show the new FXML
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();

            } catch (SQLException e) {
                // Handle SQLException appropriately, e.g., logging or displaying an error message
                e.printStackTrace();

                // Clear input fields and error messages
                montant.clear();
                beneficitaire.clear();
                date.getEditor().clear();
                msgError.setText("");
                dateError.setText("");
                montantError.setText("");
            }
        }
    }
    @FXML
    private HBox compensation;


    @FXML
    void showCompensations(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/indemnisationsBack.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) enregistre.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
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
            Stage stage = (Stage) enregistre.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur si nécessaire
        }

    }


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


    public void initData(Indemnissation indemnisation,Reclamation rec,double ins_value) throws SQLException {
       selectedIndemnisation = indemnisation;
       selectedReclaamtion=rec;
       float f =(float)ins_value;
        montant.setText(String.valueOf(f));
label.setText(selectedReclaamtion.getReponse());
        beneficitaire.setText(selectedIndemnisation.getBeneficitaire());
        LocalDate parsedDate = LocalDate.parse(selectedIndemnisation.getDate());

        date.setValue(parsedDate);

    }
}



