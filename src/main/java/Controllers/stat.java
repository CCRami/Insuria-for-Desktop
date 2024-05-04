package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.net.URL;

//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;

import java.util.ResourceBundle;
//import edu.esprit.service.AvisService;
import javafx.scene.chart.PieChart;

public class stat implements Initializable{
    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Apples", 2),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Grapes", 50),
                        new PieChart.Data("Melons", 3));


    /*    pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " amount: ", data.pieValueProperty()
                        )
                )
        );*/

        pieChart.getData().addAll(pieChartData);
    }
}
