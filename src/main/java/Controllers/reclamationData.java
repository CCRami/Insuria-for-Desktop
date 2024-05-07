package Controllers;

import Entities.Indemnissation;
import Entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Services.IndemnisationService;
import Services.ReclamationService;

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


    private Reclamation reclamation;
    private Indemnissation ind;
    private int id;

    public void setData(Reclamation reclamation) {
        this.reclamation = reclamation;
        label.setText(reclamation.getLibelle());
        dateReclaamtion.setText(reclamation.getDateReclamation());
        dateSinitre.setText(reclamation.getDateSinitre());
        reponse.setText(reclamation.getReponse());
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




}

