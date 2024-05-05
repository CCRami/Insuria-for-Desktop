package Controllers;

import Entities.Commande;
import Services.CommandeService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.chart.PieChart;
import javafx.application.Platform;
import javafx.scene.layout.Priority;
import Services.PoliceService;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminView extends Application {
    @FXML
    private ListView<String> unfoundSearchesList;
    @FXML
    private PieChart statPieChart;
    private PoliceService policeService;
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
    public AdminView() {
        this.policeService = new PoliceService();  // You need to properly initialize this service
    }
    public void initialize() {
        loadUnfoundSearches();
        configureListView();
        loadStatistics();
        setupPieChart();
    }

    private void setupPieChart() {
        List<Commande> commandes = fetchCommandeData();
        Map<String, Integer> insuranceCountMap = calculateInsuranceStatistics(commandes);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        insuranceCountMap.forEach((insuranceName, count) ->
                pieChartData.add(new PieChart.Data(insuranceName, count)));
        pieChart.setData(pieChartData);
    }
    // Method to fetch data from the Commande entity (replace with your actual data fetching logic)
    private List<Commande> fetchCommandeData() {
        CommandeService commandService = new CommandeService();
        return commandService.getAllCommandes();
    }
    private Map<String, Integer> calculateInsuranceStatistics(List<Commande> commandes) {
        Map<String, Integer> countMap = new HashMap<>();
        for (Commande commande : commandes) {
            String insuranceName = commande.getDoa_com_id().getName_ins();
            countMap.put(insuranceName, countMap.getOrDefault(insuranceName, 0) + 1);
        }
        return countMap;
    }


    private void loadUnfoundSearches() {
        ObservableList<String> searches = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader("unfoundSearches.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!searches.contains(line)) {  // Check if the line is already in the list before adding
                    searches.add(line);
                }
            }
            unfoundSearchesList.setItems(searches);
        } catch (IOException e) {
            System.err.println("Failed to load unfound searches: " + e.getMessage());
        }
    }

    private void configureListView() {
        unfoundSearchesList.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(10); // Ensure this spacing matches the spacing in your FXML if needed
                    hBox.setAlignment(Pos.CENTER_LEFT); // Align content to the left

                    Label nameLabel = new Label(item);
                    nameLabel.setMinWidth(185); // Ensure this width matches the ListView header
                    nameLabel.setMaxWidth(Double.MAX_VALUE); // Allow name to grow as needed
                    HBox.setHgrow(nameLabel, Priority.ALWAYS); // Name label grows to fill space

                    Button deleteButton = new Button("Delete");
                    deleteButton.setOnAction(event -> {
                        unfoundSearchesList.getItems().remove(item);
                        deleteSearchFromFile(item);
                    });

                    hBox.getChildren().addAll(nameLabel, deleteButton);
                    setGraphic(hBox);
                }
            }
        });
    }


    private void deleteSearchFromFile(String item) {
        ObservableList<String> remainingItems = FXCollections.observableArrayList(unfoundSearchesList.getItems());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("unfoundSearches.txt"))) {
            for (String line : remainingItems) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("Failed to update the file: " + e.getMessage());
        }
    }
    public void loadStatistics() {
        // Load statistics for `statPieChart`
        List<Object[]> statData = policeService.countPolicesBySinistre();
        ObservableList<PieChart.Data> statChartData = FXCollections.observableArrayList();
        statData.forEach(data -> statChartData.add(new PieChart.Data((String) data[0], ((Number) data[1]).doubleValue())));
        statPieChart.setData(statChartData);

        // Assume `pieChart` is for displaying command data
        List<Commande> commandes = fetchCommandeData();
        Map<String, Integer> insuranceCountMap = calculateInsuranceStatistics(commandes);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        insuranceCountMap.forEach((name, count) -> pieChartData.add(new PieChart.Data(name, count)));
        pieChart.setData(pieChartData);
    }


}
