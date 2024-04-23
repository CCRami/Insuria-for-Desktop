package Controller;
import Entity.Offre;
import Service.OffreService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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


    public void initialize(URL location, ResourceBundle resources) {
        offres = offreService.getAll();
        int column = 0;
        int row = 1;
        try {
            for (Offre offre : offres) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/of.fxml"));
                try {
                    Object anchorPane = fxmlLoader.load();
                    of off = fxmlLoader.getController();
                    System.out.println(offres);
                    off.setData(offre);

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