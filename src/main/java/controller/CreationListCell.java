package controller;

import entity.Indemnissation;
import entity.Reclamation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.IndemnisationService;
import services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;

public class CreationListCell extends ListCell<Reclamation> {
    private final Button firstButton = new Button("Add Compensation");
    private final Button secondButton = new Button("Show Compensations");
    private final ReclamationService reclamationService = new ReclamationService();
    private final IndemnisationService indemnisationService = new IndemnisationService();
    private int id;
    private Indemnissation ind;

    public CreationListCell() {
        firstButton.setStyle("-fx-background-color: #051B46; -fx-text-fill: white;");
        secondButton.setStyle("-fx-background-color: #051B46; -fx-text-fill: white;");

        firstButton.setOnAction(event -> {
            Reclamation reclamation = getItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherReclamaationBack.fxml"));
                Parent root = loader.load();
                afficherReclamationBack controller = loader.getController();
                controller.initData(reclamation);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                Stage currentStage = (Stage) firstButton.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
                // Gérer l'erreur si nécessaire
            }
        });

        secondButton.setOnAction(event -> {
            Reclamation reclamation = getItem();
            try {
                id = reclamationService.selectId(reclamation);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println(id);
            try {
                ind = indemnisationService.afficherUneIndemnisation(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/showCompensationBack.fxml"));
                Parent root = loader.load();

                showCompensation controller = loader.getController();
                controller.iniData(ind);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                Stage currentStage = (Stage) secondButton.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void updateItem(Reclamation item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            if ("refused".equals(item.getReponse()) || "accepted".equals(item.getReponse())) {
                System.out.println("Affichage du deuxième bouton");
                setGraphic(new HBox(secondButton));
            } else if ("Currently being processed".equals(item.getReponse())) {
                System.out.println("Affichage du premier bouton");
                setGraphic(new HBox(firstButton));
            } else {
                System.out.println("Aucun bouton affiché");
                setGraphic(null);
            }
        }
    }
}