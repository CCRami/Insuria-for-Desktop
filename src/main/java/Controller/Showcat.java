package Controller;

import Entity.OfferCategory;
import Entity.Offre;
import Service.OffreCatService;
import Service.OffreService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Showcat implements Initializable {
    @FXML
    private GridPane container;

    @FXML
    private Label Categoryname;

    @FXML
    private Label Description;


    private Button butonAvis;

    private final OffreCatService offreCatService = new OffreCatService();
    private List<OfferCategory> offerCategories;


    public void initialize(URL location, ResourceBundle resources) {
        offerCategories = offreCatService.getAll();
        int column = 0;
        int row = 1;
        try {
            for (OfferCategory offerCategory : offerCategories) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cat.fxml"));
                try {
                    Object anchorPane = fxmlLoader.load();
                    cat offcat = fxmlLoader.getController();
                    System.out.println(offerCategories);
                    offcat.setData(offerCategory);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    container.add((Node) anchorPane, column++, row);
                    GridPane.setMargin((Node) anchorPane, new Insets(10));
                } finally {
                    // Assurez-vous de fermer les flux pour éviter les fuites de ressources
                    fxmlLoader = null;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du FXML", e);
        }
    }
}
