package tn.esprit.applicatiopnpi.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.chart.PieChart;
import javafx.application.Platform;
import tn.esprit.applicatiopnpi.services.PoliceService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class AdminView {
    @FXML
    private ListView<String> unfoundSearchesList;
    @FXML
    private PieChart statPieChart;
    private PoliceService policeService;

    public AdminView() {
        this.policeService = new PoliceService();  // You need to properly initialize this service
    }
    public void initialize() {
        loadUnfoundSearches();
        configureListView();
        loadStatistics();
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
            private final Button deleteButton = new Button("Delete");
            private final HBox hBox = new HBox();
            private final Label label = new Label();

            {   // initializer block for layout setup
                hBox.setSpacing(10);
                hBox.getChildren().addAll(label, deleteButton);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    label.setText(item);
                    deleteButton.setOnAction(event -> {
                        unfoundSearchesList.getItems().remove(item);
                        deleteSearchFromFile(item);
                    });
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
        try {
            List<Object[]> stats = policeService.countPolicesBySinistre();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            stats.forEach(result -> pieChartData.add(new PieChart.Data((String) result[0], ((Number) result[1]).doubleValue())));

            // Update the UI on the JavaFX application thread
            Platform.runLater(() -> {
                statPieChart.setData(pieChartData);
                statPieChart.setTitle("Nombre de Polices par Sinistre");
                pieChartData.forEach(data -> {
                    Node node = data.getNode();
                    Tooltip tooltip = new Tooltip(String.format("%s : %,.0f", data.getName(), data.getPieValue()));
                    Tooltip.install(node, tooltip);
                });
            });
        } catch (Exception e) {
            System.err.println("Failed to load statistics: " + e.getMessage());
            e.printStackTrace();
            // Optionally update the UI to reflect the error
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading statistics: " + e.getMessage());
                alert.showAndWait();
            });
        }
    }

}
