package Controllers;

import Entities.Commande;
import Entities.Reclamation;
import Entities.UserSession;
import Services.CommandeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Services.ReclamationService;
import javafx.scene.control.Pagination;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class reclamationsFront implements Initializable {


    @FXML
    private GridPane container;
    @FXML
    private Button ajout;
    @FXML
    private Label nom;

    @FXML
    private Label dateSinistre;

    @FXML
    private Label dateReclamation;

    @FXML
    private Label reponse;

    @FXML
    private Button show;
    @FXML
    private Button delete;

    @FXML
    private Button edit;
    int selectedId;
    private final ReclamationService service = new ReclamationService();
    private List<Reclamation> reclamations;
    @FXML
    private Pagination pagination;
    private List<String> items;
    private final int itemsPerPage = 3;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReclamationService rs= new ReclamationService();

        try {
            reclamations = rs.getReclamationsByUserId(Integer.parseInt(UserSession.id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int column = 0;
        int row = 1;


 pagination.setPageCount(getPageCount());
        pagination.setPageFactory(this::createPage);
}

private int getPageCount() {
    return (int) Math.ceil((double) reclamations.size() / itemsPerPage);
}

private Node createPage(int pageIndex) {
    GridPane pageGrid = new GridPane();
    pageGrid.setHgap(10);
    pageGrid.setVgap(10);

    int startIndex = pageIndex * itemsPerPage;
    int endIndex = Math.min(startIndex + itemsPerPage, reclamations.size());

    int column = 0;
    int row = 0;

    for (int i = startIndex; i < endIndex; i++) {
        Reclamation reclamation = reclamations.get(i);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reclamation.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            reclamationData recc = fxmlLoader.getController();
            recc.setData(reclamation);

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