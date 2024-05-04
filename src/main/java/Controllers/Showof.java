package Controllers;
import Entities.Offre;
import Services.OffreService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Pagination;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class Showof implements Initializable {
    @FXML
    private GridPane container;

    @FXML
    private Label Advantage;

    @FXML
    private Label Condition;

    @FXML
    private Label Discount;

    @FXML
    private Label Duration;

    @FXML
    private Button butonAvis;

    private final OffreService offreService = new OffreService();
    private List<Offre> offres;
    @FXML
    private Pagination pagination;
    private List<String> items;
    private final int itemsPerPage = 3;



    public void initialize(URL location, ResourceBundle resources) {
        offres = offreService.getAll();
        int column = 0;
        int row = 1;


        offres = offreService.getAll();
        int itemsPerPage = 3;
        int pageCount = (int) Math.ceil((double) offres.size() / itemsPerPage);

        items = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            items.add("Item " + i);
        }
        offres = offreService.getAll();
        pagination.setPageCount(getPageCount());
        pagination.setPageFactory(this::createPage);
    }

    private int getPageCount() {
        return (int) Math.ceil((double) offres.size() / itemsPerPage);
    }

    private Node createPage(int pageIndex) {
        GridPane pageGrid = new GridPane();
        pageGrid.setHgap(10);
        pageGrid.setVgap(10);

        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, offres.size());

        int column = 0;
        int row = 0;

        for (int i = startIndex; i < endIndex; i++) {
            Offre offre = offres.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/of.fxml"));
            try {
                AnchorPane anchorPane = fxmlLoader.load();
                of off = fxmlLoader.getController();
                off.setData(offre);

                pageGrid.add(anchorPane, column, row);

                // Update column and row for the next item
                column++;
                if (column >= itemsPerPage) {
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



















