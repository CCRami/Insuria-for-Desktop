package edu.esprit.controllers;

import edu.esprit.entities.Avis;
import edu.esprit.service.AvisService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class avis1 {
    @FXML
    private VBox agevbox;
    AvisService s = new AvisService();
    @FXML
    private Label clientname;

    @FXML
    private Label comment;

    @FXML
    Button supprimer;

    @FXML
    private Label nomagence;

    @FXML
    private Label note;

    @FXML
    private Label note1;

    @FXML
    private Label note2;

    @FXML
    private Label note3;

    @FXML
    private Label note4;

    @FXML
    private Label creation;

    public void setData(Avis avis) {
        clientname.setText(String.valueOf(avis.getAvis_id()));
        comment.setText(avis.getCommentaire());
        int notea = avis.getNote();
        if (notea == 1) {
            note.setVisible(true);
            note1.setVisible(false);
            note2.setVisible(false);
            note3.setVisible(false);
            note4.setVisible(false);
        }
        if (notea == 2) {
            note.setVisible(true);
            note1.setVisible(true);
            note2.setVisible(false);
            note3.setVisible(false);
            note4.setVisible(false);
        }
        if (notea == 3) {
            note.setVisible(true);
            note1.setVisible(true);
            note2.setVisible(true);
            note3.setVisible(false);
            note4.setVisible(false);
        }
        if (notea == 4) {
            note.setVisible(true);
            note1.setVisible(true);
            note2.setVisible(true);
            note3.setVisible(true);
            note4.setVisible(false);
        }
        if (notea == 5) {
            note.setVisible(true);
            note1.setVisible(true);
            note2.setVisible(true);
            note3.setVisible(true);
            note4.setVisible(true);
        }
        nomagence.setText(avis.getAgenceav_id().getNomage());
        creation.setText(avis.getDate_avis());
    }

    public void supprimer(int id) {
        System.out.println(id);
        supprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(id);
                s.supprimerav(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("review deleted successfully");
                alert.showAndWait();


            }
        });


    }


}