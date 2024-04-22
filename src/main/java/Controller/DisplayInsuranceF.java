package Controller;


import Entity.Insurance;
import Service.InsuranceService;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayInsuranceF implements Initializable{

    @FXML
    private GridPane container;

    @FXML
    private Label nomRestau;

    @FXML
    private Label adresse;

    @FXML
    private Label email;

    @FXML
    private Label phone;

    @FXML
    private Button butonAvis;

    private final InsuranceService serviceInsurance = new InsuranceService();
    private List<Insurance> insurance;


    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing DisplayInsuranceF controller...");

        insurance = serviceInsurance.getAllInsurances();

        int column = 0;
        int row = 1;
        try {
            for (Insurance ins : insurance) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/InsurancesList.fxml"));
                try {
                    Object anchorPane = fxmlLoader.load();
                    InsurancesList insl = fxmlLoader.getController();
                    System.out.println("Insurance: " + ins); // Debug: Print details of each insurance
                    insl.setData(ins);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    container.add((Node) anchorPane, column++, row);
                    GridPane.setMargin((Node) anchorPane, new Insets(10));
                } finally {
                    fxmlLoader = null;
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading InsurancesList.fxml: " + e.getMessage()); // Debug: Print loading error
            e.printStackTrace(); // Debug: Print stack trace for detailed error analysis
            throw new RuntimeException("Erreur lors du chargement du FXML", e);
        }
    }


}
