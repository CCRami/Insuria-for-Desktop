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
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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

    @FXML
    private Pagination pagination;


    private static final int ITEMS_PER_PAGE = 3;


    public void initialize(URL location, ResourceBundle resources) {
        offerCategories = offreCatService.getAll();
        int column = 0;
        int row = 1;



    offerCategories = offreCatService.getAll();

    // Calculate number of pages
    int pageCount = (int) Math.ceil((double) offerCategories.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);

    // Set page factory to create pages dynamically
        pagination.setPageFactory(this::createPage);
}
    private int getPageCount() {
        return (int) Math.ceil((double) offerCategories.size() / ITEMS_PER_PAGE);
    }

    private Node createPage(int pageIndex) {
        GridPane pageGrid = new GridPane();
        pageGrid.setHgap(10);
        pageGrid.setVgap(10);

        int startIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, offerCategories.size());

        int column = 0;
        int row = 0;

        for (int i = startIndex; i < endIndex; i++) {
            OfferCategory offrecat = offerCategories.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cat.fxml"));
            try {
                AnchorPane anchorPane = fxmlLoader.load();
                cat offcat = fxmlLoader.getController();
                offcat.setData(offrecat);

                pageGrid.add(anchorPane, column, row);

                // Update column and row for the next item
                column++;
                if (column >= ITEMS_PER_PAGE) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return pageGrid;
    }
}

