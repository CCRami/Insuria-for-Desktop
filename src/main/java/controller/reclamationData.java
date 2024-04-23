package controller;

import entity.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    reclamationsFront recF = new reclamationsFront();

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
            controller.initData(reclamation); // Transférez la réclamation à l'interface d'édition
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


        int indemnisation = reclamation.getIndemnisation().getId();

        if (indemnisation != 0) {
            // Afficher les détails de l'indemnisation
            System.out.println("Montant de l'indemnisation : ");

            // Autres opérations pour afficher les détails de l'indemnisation
        } else {
            System.out.println("Aucune indemnisation associée à cette réclamation.");
            // Gérer le cas où aucune indemnisation n'est associée à cette réclamation
        }

    }
}