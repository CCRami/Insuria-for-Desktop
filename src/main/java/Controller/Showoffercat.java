package Controller;

import Entity.OfferCategory;
import Service.OffreCatService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;


public class Showoffercat {

    @FXML
    private Label catNameLabel;

    @FXML
    private Label catDescriptionLabel;

    public void initializeCategoryData(OfferCategory category) {
        // Populate UI components with data from the OfferCategory object
        catNameLabel.setText(category.getCategorie_name());
        catDescriptionLabel.setText(category.getDescription_cat());
        // You can add more code here to set data for other UI components if needed
    }
}



public class ShowOfferCat implements Initializable {

    @FXML
    private GridPane container;

    private final OffreCatService offerCatService = new OffreCatService();
    private List<OfferCategory> offerCategories;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        offerCategories = offerCatService.getAll(); // Retrieve all offer categories
        int row = 1;
        try {
            for (OfferCategory category : offerCategories) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/showoffercat.fxml"));
                try {
                    Node anchorPane = fxmlLoader.load();
                    ShowOfferCatController controller = fxmlLoader.getController();
                    controller.initializeCategoryData(category); // Call initializeCategoryData

                    container.add(anchorPane, 0, row++);
                    GridPane.setMargin(anchorPane, new Insets(10));
                } finally {
                    fxmlLoader = null;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML", e);
        }
    }
}
