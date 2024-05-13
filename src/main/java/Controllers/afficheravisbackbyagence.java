package Controllers;

import Entities.Agence;
import Entities.Avis;
import Services.AvisService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class afficheravisbackbyagence {
    @FXML
    private TableView<Avis> tab;


    @FXML
    private TableColumn<Avis, Integer> note;

    @FXML
    private TableColumn<Avis, String> date_avis;

    @FXML
    private TableColumn<Avis, Boolean> etat;

    @FXML
    private TableColumn<Avis, String> commentaire;

    @FXML
    private TableColumn<Avis, Integer> avis_id;

    @FXML
        private TableColumn<Avis, Integer> agenceav_id;

    @FXML
    TextField  AVnote, AVdate, AVclient, AVAge;

    @FXML
    TextArea AVcommentaire;



    @FXML
    private GridPane container;
    @FXML
    Button modif, supprimer, pdf;

    @FXML
    private Button ajouteragence;
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet result;
    @FXML
    private Label home_totalEmployees;

    @FXML
    private Label home_totalAvis;
    int selectedId;


    Agence Parametre1; Agence Parametre2;

    AvisService s = new AvisService();
    public Avis r = new Avis(commentaire, note, date_avis, avis_id, agenceav_id,etat);

    private final AvisService serviceAvis = new AvisService();
    private List<Avis> avis;


    public void initialize(Agence id) {
        avis = serviceAvis.getAllavisbyagenceuser(id);
        int column = 0;
        int row = 1;
        try {
            for (Avis avis : avis) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/unAvis.fxml"));
                try {
                    Object anchorPane = fxmlLoader.load();
                    avis age = fxmlLoader.getController();

                    age.setData(avis);
                   age.supprimer.setVisible(false);

                    if (column == 2) {
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
