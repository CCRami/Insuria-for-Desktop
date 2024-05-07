package Controllers;


import Entities.Indemnissation;
import Entities.Reclamation;
import Entities.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Services.IndemnisationService;

import java.io.IOException;
import java.sql.SQLException;

public class indemnisationRefused {

    @FXML
    private TextField beneficitaire;
    private Indemnissation selectedIndemnisation;
private Reclamation selectedReclamation;
    @FXML
    private AnchorPane anchor;


    @FXML
    void initialize()  {


        assert beneficitaire != null : "fx:id=\"beneficitaire\" was not injected: check your FXML file 'indemnisationAccepted.fxml'.";

    }
    @FXML
    private Text msgError;

    @FXML
    void enregistreRefusedAction(ActionEvent event) throws IOException {
        if(itsCorrect()){
            selectedIndemnisation.setMontant(0);
            selectedIndemnisation.setBeneficitaire(beneficitaire.getText());
            IndemnisationService service = new IndemnisationService();

            try {
                service.modifierIndemnisation(selectedIndemnisation);
                  selectedReclamation.setIndemnisation(selectedIndemnisation);
               System.out.println(selectedReclamation.getIndemnisation());

                String emailValue = UserSession.email;
                MailService.sendConfirmationEmail(emailValue,selectedReclamation);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText(" added successfully , an email has been sent");
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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listReclamationBack.fxml"));
            Node root = loader.load();
            anchor.getChildren().clear();
            anchor.getChildren().add(root);


            beneficitaire.clear();

            msgError.setText("");





        }
    }
    @FXML
    private Button enregistre;
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
