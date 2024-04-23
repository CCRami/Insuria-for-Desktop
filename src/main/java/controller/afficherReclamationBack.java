package controller;

import entity.Indemnissation;
import entity.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import services.IndemnisationService;
import services.ReclamationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class afficherReclamationBack implements Initializable {
    private Reclamation selectedReclamation;

        private Stage stage;


        @FXML
    private HBox reclamations;
    @FXML
    private HBox claim;

    @FXML
    private Text contenu;

    @FXML
    private Text dateReclamation;

    @FXML
    private Text dateSinistre;

    @FXML
    private Text label;

    @FXML
    private Text reponse;
    @FXML
    private Button cancel;

    @FXML
    private ChoiceBox<String> choixReponse;
    @FXML
    private Text choixerror;
    @FXML
    private Button enregistre;

    IndemnisationService serviceindemnisation = new IndemnisationService();
ReclamationService service=new ReclamationService();
    private String[] rep={"refused","accepted"};



    public void initData(Reclamation reclamation) {
        selectedReclamation = reclamation;

        label.setText(selectedReclamation.getLibelle());
        dateReclamation.setText(selectedReclamation.getDateReclamation());
        dateSinistre.setText(selectedReclamation.getDateSinitre());
        contenu.setText(selectedReclamation.getContenu_rec());
        reponse.setText(selectedReclamation.getReponse());

    }

    @FXML
    void cancelAction(ActionEvent event) { try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationsBack.fxml"));
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
    void enregistreAction(ActionEvent event) throws SQLException {
        String selectedResponse = choixReponse.getValue();
        if (selectedResponse != null) {
            // Mettre à jour la réponse de la réclamation
            selectedReclamation.setReponse(selectedResponse);

            service.modifierReclamationReponse(selectedReclamation);


            String interfacePath = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateIndemnisation = format.format(new Date());

            Indemnissation indemnisation = new Indemnissation(0.0F,dateIndemnisation ,"----");


            int k = 0;
            try {
              k=serviceindemnisation.addIndemnisation(indemnisation);
            } catch (SQLException e) {
                System.out.println("Error adding packs: " + e.getMessage());
            }
            indemnisation.setId(k);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Success");

            alert.showAndWait();
            selectedReclamation.setIndemnisation(indemnisation);
            System.out.println(selectedReclamation.getIndemnisation());
            service.modifierReclamationIndemnisation(selectedReclamation);
            if (selectedResponse.equals("accepted")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/indemnisationAccepted.fxml"));
                    Parent root = loader.load();
                    IndemnisationAccepted controller = loader.getController();
                    controller.initData(indemnisation,selectedReclamation); // Transférez la réclamation à l'interface d'édition
                    // Utilisez votre objet Stage pour afficher la nouvelle interface
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    // Gérer l'erreur si nécessaire
                }
            }


            else if (selectedResponse.equals("refused")) {



                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/indemnisationRefused.fxml"));
                    Parent root = loader.load();
                    indemnisationRefused controller = loader.getController();
                    controller.initData(indemnisation,selectedReclamation); // Transférez la réclamation à l'interface d'édition
                    // Utilisez votre objet Stage pour afficher la nouvelle interface
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    // Gérer l'erreur si nécessaire
                }
            }


            // Fermez la fenêtre actuelle
            Stage currentStage = (Stage) enregistre.getScene().getWindow();
            currentStage.close();
        } else {
            choixerror.setText("Please select an answer.");
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choixReponse.getItems().addAll(rep);
    }
}


