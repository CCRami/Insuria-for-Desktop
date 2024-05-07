package Controllers;

import Entities.Insurance;
import Entities.InsuranceCategory;
import Services.InsuranceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayInsuranceF implements Initializable {

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

    @FXML
    private HBox categoryFlowPane;

    @FXML
    private VBox contentArea;

    private final InsuranceService serviceInsurance = new InsuranceService();
    private List<Insurance> insurance;

    private double currentMinPrice = Double.MIN_VALUE;
    private double currentMaxPrice = Double.MAX_VALUE;

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing DisplayInsuranceF controller...");

        insurance = serviceInsurance.getAllInsurances();

        // Populate the container with all insurances
        populateContainer(insurance);

        // Populate the categoryFlowPane with category buttons
        List<String> categories = getCategories();
        populateCategoryFlowPane(categories);
    }

    @FXML
    void askme(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InsuranceForm.fxml"));
        try {
            Node askMe = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(askMe);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void increasePrice(ActionEvent event) {
        // Implement filtering by ascending price here
        insurance.sort(Comparator.comparingDouble(Insurance::getMontant));
        populateContainer(insurance);
    }

    @FXML
    private void decreasePrice(ActionEvent event) {
        // Implement filtering by descending price here
        insurance.sort(Comparator.comparingDouble(Insurance::getMontant).reversed());
        populateContainer(insurance);
    }


    private void filterInsurancesByPrice(double minPrice, double maxPrice) {
        List<Insurance> filteredInsurances = new ArrayList<>();
        for (Insurance ins : insurance) {
            double insurancePrice = ins.getMontant(); // Assuming getPrice() method exists in your 'Insurance' entity
            if (insurancePrice >= minPrice && insurancePrice <= maxPrice) {
                filteredInsurances.add(ins);
            }
        }

        populateContainer(filteredInsurances);
    }


    private void populateContainer(List<Insurance> insurances) {
        container.getChildren().clear(); // Clear existing insurances from the container

        int column = 0;
        int row = 1;
        for (Insurance ins : insurances) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/InsurancesList.fxml"));
            try {
                Node anchorPane = fxmlLoader.load();
                InsurancesList insl = fxmlLoader.getController();

                insl.setData(ins);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                container.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException e) {
                System.err.println("Error loading InsurancesList.fxml: " + e.getMessage()); // Debug: Print loading error
                e.printStackTrace(); // Debug: Print stack trace for detailed error analysis
                throw new RuntimeException("Erreur lors du chargement du FXML", e);
            }
        }
    }

    private List<String> getCategories() {
        List<String> categories = new ArrayList<>();

        for (Insurance ins : insurance) {
            String category = ins.getCatins_id().getName_cat_ins(); // Assuming 'getCategory()' method exists in your 'Insurance' entity
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }

        return categories;
    }

    private void populateCategoryFlowPane(List<String> categories) {
        categoryFlowPane.setStyle(TABS_STYLE);
        categoryFlowPane.getChildren().clear(); // Clear the FlowPane at the beginning

        // Create and add the "All" button
        Button allButton = new Button("All");
        applyButtonStyle(allButton);
        categoryFlowPane.getChildren().add(allButton);

        // Iterate over the categories and add each as a button
        for (String category : categories) {
            Button categoryButton = new Button(category);
            categoryButton.setOnAction(this::handleCategoryButtonAction);
            applyButtonStyle(categoryButton); // Optional: Apply style to each category button
            categoryFlowPane.getChildren().add(categoryButton);
        }
    }

    private void applyButtonStyle(Button button) {
        button.setStyle(TAB_STYLE);
        button.setOnMouseEntered(e -> button.setStyle(TAB_HOVER_STYLE));
        button.setOnMouseExited(e -> button.setStyle(TAB_STYLE));
    }


    private void handleCategoryButtonAction(ActionEvent event) {
        Button categoryButton = (Button) event.getSource();
        String category = categoryButton.getText();
        filterInsurancesByCategory(category);
    }

    private void filterInsurancesByCategory(String category) {
        List<Insurance> filteredInsurances = new ArrayList<>();
        if (category == null || category.isEmpty()) {
            filteredInsurances.addAll(insurance); // No category selected, show all insurances
        } else {
            for (Insurance ins : insurance) {
                InsuranceCategory insuranceCategory = ins.getCatins_id();
                if (insuranceCategory != null && insuranceCategory.getName_cat_ins().equalsIgnoreCase(category)) {
                    filteredInsurances.add(ins);
                }
            }
        }

        populateContainer(filteredInsurances);
    }

    // Add other methods and event handlers as needed
    @FXML
    private void handleAllButtonAction(ActionEvent event) {
        // Reset the filters to show all insurances
        populateContainer(insurance);
    }

    private static final String TABS_STYLE = "-fx-background-color: #fff; " +
            "-fx-padding: 12px; " + // Converted from 0.75rem assuming 16px = 1rem
            "-fx-background-radius: 50px; " + // 99px is visually similar to fully rounded corners
            "-fx-effect: dropshadow(three-pass-box, rgba(24, 94, 224, 0.15), 6, 0.1, 0, 6);";

    private static final String TAB_STYLE = "-fx-background-color: transparent; " +
            "-fx-font-size: 0.8rem; " +
            "-fx-text-fill: black; " +
            "-fx-font-weight: bold; " +
            "-fx-cursor: hand; " +
            "-fx-background-radius: 50px; " +
            "-fx-padding: 5 10;";

    private static final String TAB_HOVER_STYLE = "-fx-background-color: #e6eef9;";






}
