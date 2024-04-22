package edu.esprit.controller;

import edu.esprit.entities.Avis;
import edu.esprit.service.AvisService;
import edu.esprit.util.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

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


    int Parametre1; int Parametre2;

    AvisService s = new AvisService();
    public Avis r = new Avis(commentaire, note, date_avis, avis_id, agenceav_id,etat);

    private final AvisService serviceAvis = new AvisService();
    private List<Avis> avis;
    @FXML
    public void homeTotalEmployees() {
        String sql = "SELECT COUNT(id) FROM Agence";
        this.cnx = DataSource.getInstance().getConnection();;
        int countData = 0;

        try {
            this.pst = this.cnx.prepareStatement(sql);

            for(this.result = this.pst.executeQuery(); this.result.next(); countData = this.result.getInt("COUNT(id)")) {
            }

            this.home_totalEmployees.setText(String.valueOf(countData));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @FXML
    public void homeTotalAvis() {
        String sql = "SELECT COUNT(id) FROM Avis";
        this.cnx = DataSource.getInstance().getConnection();;
        int countData = 0;

        try {
            this.pst = this.cnx.prepareStatement(sql);

            for(this.result = this.pst.executeQuery(); this.result.next(); countData = this.result.getInt("COUNT(id)")) {
            }

            this.home_totalAvis.setText(String.valueOf(countData));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }


    public void initialize() {
        homeTotalAvis();homeTotalEmployees();
        avis = serviceAvis.getAllavisbyagence(Parametre2);
        int column = 0;
        int row = 1;
        try {
            for (Avis avis : avis) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/unAvis.fxml"));
                try {
                    Object anchorPane = fxmlLoader.load();
                    avis age = fxmlLoader.getController();

                    age.setData(avis);
                    age.supprimer(avis.getIdAV());
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
        /*
        commentaire.setCellValueFactory(new PropertyValueFactory<Avis, String>("commentaire"));
        note.setCellValueFactory(new PropertyValueFactory<Avis, Integer>("note"));
        date_avis.setCellValueFactory(new PropertyValueFactory<Avis, String>("date_avis"));
        avis_id.setCellValueFactory(new PropertyValueFactory<Avis, Integer>("avis_id"));
        agenceav_id.setCellValueFactory(new PropertyValueFactory<Avis, Integer>("agenceav_id"));
        homeTotalEmployees();
        homeTotalAvis();

        int Parametre;

        loadTableData();



        tab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Avis selectedAvis = tab.getSelectionModel().getSelectedItem();
                selectedId = selectedAvis.getIdAV();
                AVcommentaire.setText(selectedAvis.getCommentaire());
                AVnote.setText(String.valueOf(selectedAvis.getNote()));
                AVdate.setText(selectedAvis.getDate_avis());
                AVclient.setText(String.valueOf(selectedAvis.getAvis_id()));
                AVAge.setText(String.valueOf(selectedAvis.getAgenceav_id()));
            }
        });


        supprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                s.supprimerav(selectedId);
                updateTable_r();
                clearFields();
            }
        });

        clearFields();*/
    }

    private void loadTableData() {

        System.out.println("dd"+Parametre2);

        ObservableList<Avis> data = FXCollections.observableArrayList(s.getAllavisbyagence(Parametre2));
        tab.setItems(data);
    }

    public void updateTable_r() {
        List<Avis> aviss = s.getAllavisbyagence(Parametre1);
        tab.getItems().setAll(aviss);
    }

    private void clearFields() {
        AVcommentaire.clear();
        AVnote.clear();
        AVdate.clear();
        AVclient.clear();
        AVAge.clear();
    }



    public void myparametre(int id) {
        // Utilisez le paramètre comme vous le souhaitez ici
        System.out.println("Selected ID: " + id);
        this.Parametre2=id;
        int ResultSet = Parametre2;

        System.out.println("ss"+Parametre2);


    }
}
