package controller;

import Entity.Indemnissation;
import Entity.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.IndemnisationService;
import services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;

public class reclamationData {
    @FXML
    private Button delete;

    @FXML
    private Button edit;
    @FXML
    private VBox agevbox;

    @FXML
    private Label dateReclaamtion;

    @FXML
    private Label dateSinitre;

    @FXML
    private Label label;


    @FXML
    private Label reponse;

    @FXML
    private Button show;

    private Reclamation reclamation;
    private Indemnissation ind;
    private int id;

    public void setData(Reclamation reclamation) {
        this.reclamation = reclamation;
        label.setText(reclamation.getLibelle());
        dateReclaamtion.setText(reclamation.getDateReclamation());
        dateSinitre.setText(reclamation.getDateSinitre());
        reponse.setText(reclamation.getReponse());
        show.setVisible(reclamation.getReponse().equals("refused") || reclamation.getReponse().equals("accepted"));
        delete.setVisible(reclamation.getReponse().equals("Currently being processed"));
        edit.setVisible(reclamation.getReponse().equals("Currently being processed"));
    }


    ReclamationService service = new ReclamationService();
    IndemnisationService is = new IndemnisationService();

    @FXML
    void delete(ActionEvent event) {

        try {

            service.supprimerReclamation(reclamation.getId());

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    @FXML
    void edit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editReclamation.fxml"));
            Parent root = loader.load();
            EditReclamation controller = loader.getController();
            controller.setReclamation(reclamation); // Transférez la réclamation à l'interface d'édition
            // Utilisez votre objet Stage pour afficher la nouvelle interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur si nécessaire
        }
    }


    @FXML
    void show(ActionEvent event) {


        try {
            id = service.selectId(reclamation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(id);
        try {
            ind = is.afficherUneIndemnisation(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ind);
        if (reclamation.getReponse().equals("refused")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/showCompensationRefused.fxml"));
                Parent root = loader.load();


                showCompensationRefused controller = loader.getController();

                controller.iniData(ind);


                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                Stage currentStage = (Stage) show.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        } else if (reclamation.getReponse().equals("accepted")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/showCompensationAccepted.fxml"));
                Parent root = loader.load();


                showCompensationAccepted controller = loader.getController();

                controller.iniData(ind);


                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                Stage currentStage = (Stage) show.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }

    }

}

