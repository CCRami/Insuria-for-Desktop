package controller;

import entity.Reclamation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.ReclamationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class reclamationsFront implements Initializable {


    @FXML
    private GridPane container;
    @FXML
    private Button ajout;
    @FXML
    private Label nom;

    @FXML
    private Label dateSinistre;

    @FXML
    private Label dateReclamation;

    @FXML
    private Label reponse;

    @FXML
    private Button show;
    @FXML
    private Button delete;

    @FXML
    private Button edit;
    int selectedId;
    private final ReclamationService service = new ReclamationService();
    private List<Reclamation> reclamations;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            reclamations = service.afficherReclamations();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int column = 0;
        int row = 1;
        try {
            for (Reclamation reclamation : reclamations) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reclamation.fxml"));
                try {
                    Object anchorPane = fxmlLoader.load();
                    reclamationData recData = fxmlLoader.getController();
                    System.out.println(reclamations);
                    recData.setData(reclamation);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    container.add((Node) anchorPane, column++, row);
                    GridPane.setMargin((Node) anchorPane, new Insets(10));
                } finally {
                    // Assurez-vous de fermer les flux pour éviter les fuites de ressources
                    fxmlLoader = null;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du FXML", e);
        }
    }
    @FXML
    void ajout(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addReclamation.fxml"));
            Parent root = loader.load();

            // Utilisez votre objet Stage pour afficher la nouvelle interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermez la fenêtre actuelle
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}