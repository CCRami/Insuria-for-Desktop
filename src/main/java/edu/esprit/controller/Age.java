package edu.esprit.controller;



import edu.esprit.service.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import edu.esprit.entities.Agence;
import javafx.scene.layout.VBox;

public class Age {



    @FXML
    private Button Rateus;

    @FXML
    private Label adress;

    @FXML
    private VBox agevbox;

    @FXML
    private Label creation;

    @FXML
    private Label email;

    @FXML
    private Label nom;

    @FXML
    private Label phone;

    @FXML
    private Button reviews;

    public void setData(Agence agence){
        adress.setText(agence.getAddresse());
        nom.setText(agence.getNomage());
        phone.setText(String.valueOf(agence.getTel()));
        email.setText(agence.getEmail());
        creation.setText(agence.getCreate_at());
    }




}



