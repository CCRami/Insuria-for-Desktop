package Controllers;

import Entities.Commande;
import Services.CommandeService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsuranceChart extends Application {
    @FXML
    private PieChart pieChart;


    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InsuranceChart.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Insurance Statistics Pie Chart");
        stage.show();

        initialize();
    }
    @FXML
    private void initialize() {
        List<Commande> commandes = fetchCommandeData();
        Map<String, Integer> insuranceCountMap = calculateInsuranceStatistics(commandes);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        insuranceCountMap.forEach((insuranceName, count) ->
                pieChartData.add(new PieChart.Data(insuranceName, count)));

        pieChart.setData(pieChartData);
    }
    // Method to fetch data from the Commande entity (replace with your actual data fetching logic)
    private List<Commande> fetchCommandeData() {
        CommandeService commandService = new CommandeService(); // Instantiate CommandService
        List<Commande> commandeList = commandService.getAllCommandes(); // Call getAllCommands() method
        return commandeList;
    }


    // Method to calculate insurance statistics
    private Map<String, Integer> calculateInsuranceStatistics(List<Commande> commandes) {
        System.out.println("calc");
        Map<String, Integer> insuranceCountMap = new HashMap<>();
        // Iterate over commandes and calculate insurance statistics
        for (Commande commande : commandes) {
            String insuranceName = commande.getDoa_com_id().getName_ins();
            insuranceCountMap.put(insuranceName, insuranceCountMap.getOrDefault(insuranceName, 0) + 1);
        }
        return insuranceCountMap;
    }


}
