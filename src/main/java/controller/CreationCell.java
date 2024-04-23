package controller;


import entity.Indemnissation;
import entity.Reclamation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class CreationCell extends TableCell<Reclamation, Void> {
    private final Button firstButton = new Button("Add Compensation");
    private final Button secondButton = new Button("show Compensations");

    public CreationCell() {
        firstButton.setStyle("-fx-background-color: #051B46; -fx-text-fill: white;");
        secondButton.setStyle("-fx-background-color: #051B46; -fx-text-fill: white;");

        firstButton.setOnAction(event -> {
            Reclamation reclamation = getTableView().getItems().get(getIndex());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherReclamaationBack.fxml"));
                Parent root = loader.load();
                afficherReclamationBack controller = loader.getController();
                controller.initData(reclamation); // Transférez la réclamation à l'interface d'édition
                // Utilisez votre objet Stage pour afficher la nouvelle interface
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                Stage currentStage = (Stage) getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
                // Gérer l'erreur si nécessaire
            }
        });

        secondButton.setOnAction(event -> {
            Reclamation reclamation = getTableView().getItems().get(getIndex());
            Indemnissation indemnisation = reclamation.getIndemnisation();
            System.out.println(reclamation.getIndemnisation());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/showCompensationBack.fxml"));
                Parent root = loader.load();


                showCompensation controller = loader.getController();


                controller.iniData(indemnisation);


                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();


                Stage currentStage = (Stage) getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        });
    }

    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            Reclamation reclamation = getTableView().getItems().get(getIndex());
            if ("refused".equals(reclamation.getReponse()) || "accepted".equals(reclamation.getReponse())) {
                System.out.println("Affichage du deuxième bouton");
                setGraphic(new HBox(secondButton));
            } else if ("Currently being processed".equals(reclamation.getReponse())) {
                System.out.println("Affichage du premier bouton");
                setGraphic(new HBox(firstButton));
            } else {
                System.out.println("Aucun bouton affiché");
                setGraphic(null);
            }
        }
    }


}

