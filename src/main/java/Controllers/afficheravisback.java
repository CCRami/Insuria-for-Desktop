package Controllers;

import Entities.Avis;
import Services.AvisService;
import util.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class afficheravisback  {
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
    TextField AVnote, AVdate, AVclient, AVAge;

    @FXML
    TextArea AVcommentaire;




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
    @FXML
    private ListView<Avis> tab1;
    @FXML
    private VBox contentArea;
    @FXML
    private GridPane container;
    int selectedId;
    @FXML
    private AnchorPane AA;


    //AvisService s = new AvisService();
    public Avis r = new Avis(commentaire, note, date_avis, avis_id, agenceav_id, etat);
    private final AvisService serviceAvis = new AvisService();
    private List<Avis> avis;
    @FXML
    public void home_totalEmployees() {
        String sql = "SELECT COUNT(id) FROM Agence";
        this.cnx = DataSource.getInstance().getCnx();
        ;
        int countData = 0;

        try {
            this.pst = this.cnx.prepareStatement(sql);

            for (this.result = this.pst.executeQuery(); this.result.next(); countData = this.result.getInt("COUNT(id)")) {
            }

            this.home_totalEmployees.setText(String.valueOf(countData));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @FXML
    private TextField searchField;
    @FXML
    void aa(KeyEvent event) {
      /*  FilteredList<Avis> filteredList = new FilteredList<>(data, avis -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(avis -> {
                // Si la recherche est vide, affichez tous les avis
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convertir le texte de recherche en minuscule pour comparaison
                String lowerCaseFilter = newValue.toLowerCase();

                // Filtrer les avis par nom d'agence, commentaire, ou note
                return avis.getAgenceav_id().getNomage().toLowerCase().contains(lowerCaseFilter) ||
                        avis.getCommentaire().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(avis.getNote()).contains(lowerCaseFilter);
            });

            tab1.setItems(filteredList);


        });*/
    }
    @FXML
    public void homeTotalAvis() {
        String sql = "SELECT COUNT(id) FROM Avis";
        this.cnx = DataSource.getInstance().getCnx();
        ;
        int countData = 0;

        try {
            this.pst = this.cnx.prepareStatement(sql);

            for (this.result = this.pst.executeQuery(); this.result.next(); countData = this.result.getInt("COUNT(id)")) {
            }

            this.home_totalAvis.setText(String.valueOf(countData));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
    private void initializeListView() {
        tab1.setCellFactory(param -> new ListCell<Avis>() {
            @Override
            protected void updateItem(Avis item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    home_totalEmployees();
                    homeTotalAvis();

                    // Création d'un HBox pour contenir les labels et les boutons
                    HBox hbox = new HBox(5); // Espace de 10px entre les éléments
                    hbox.setAlignment(Pos.CENTER_LEFT);

                    Label nameLabel = new Label(item.getAgenceav_id().getNomage());
                    nameLabel.setMinWidth(150);
                    nameLabel.setMaxWidth(150);
                    Label conditionLabel = new Label();
                if(item.getNote()==1){
conditionLabel.setText("★");
                    conditionLabel.setMinWidth(170);
                    conditionLabel.setMaxWidth(180);
                    conditionLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    conditionLabel.setStyle("-fx-padding: 10px; -fx-text-fill: gold;");}
                    if(item.getNote()==2){
                        conditionLabel.setText("★★");
                        conditionLabel.setMinWidth(170);
                        conditionLabel.setMaxWidth(180);
                        conditionLabel.setWrapText(true); // Activer le retour à la ligne automatique
                        conditionLabel.setStyle("-fx-padding: 10px; -fx-text-fill: gold;");}
                    if(item.getNote()==3){ conditionLabel.setText("★★★");

                        conditionLabel.setMinWidth(170);
                        conditionLabel.setMaxWidth(180);
                        conditionLabel.setWrapText(true); // Activer le retour à la ligne automatique
                        conditionLabel.setStyle("-fx-padding: 10px; -fx-text-fill: gold;");}
                    if(item.getNote()==4){ conditionLabel.setText("★★★★");

                        conditionLabel.setMinWidth(170);
                        conditionLabel.setMaxWidth(180);
                        conditionLabel.setWrapText(true); // Activer le retour à la ligne automatique
                        conditionLabel.setStyle("-fx-padding: 10px; -fx-text-fill: gold;");}
                    if(item.getNote()==5){ conditionLabel.setText("★★★★★");

                        conditionLabel.setMinWidth(170);
                        conditionLabel.setMaxWidth(180);
                        conditionLabel.setWrapText(true); // Activer le retour à la ligne automatique
                        conditionLabel.setStyle("-fx-padding: 10px; -fx-text-fill: gold;");}




                    Label durationlabel = new Label(item.getCommentaire());
                    durationlabel.setMinWidth(250);
                    durationlabel.setMaxWidth(260);
                    durationlabel.setWrapText(true); // Activer le retour à la ligne automatique
                    durationlabel.setStyle("-fx-padding: 10px;");



                    Label discountLabel = new Label(String.valueOf(item.getAvis_id()));
                    discountLabel.setMinWidth(120);
                    discountLabel.setMaxWidth(120);
                    discountLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    discountLabel.setStyle("-fx-padding: 10px;");

                    Label dateLabel = new Label(item.getDate_avis());
                    dateLabel.setMinWidth(170);
                    dateLabel.setMaxWidth(180);
                    dateLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    dateLabel.setStyle("-fx-padding: 10px;");




                    // Configuration des boutons avec icônes et texte

                    ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/icons8-delete-24.png")));
                    deleteIcon.setFitHeight(20); // Taille de l'icône
                    deleteIcon.setFitWidth(20);
                    Button deleteButton = new Button("Delete");
                    deleteIcon.setId("update");
                    deleteButton.getStyleClass().add("buttonn");


                    HBox actionBox = new HBox( deleteButton);
                    actionBox.setSpacing(5);
                    actionBox.setMinWidth(300);
                    actionBox.setMaxWidth(300);




                    deleteButton.setOnAction(event -> {
                        serviceAvis.supprimerav(item.getIdAV());
                        //List<Avis> agences =  serviceAvis.getAllavis();
                       // tab1.getItems();
                        updateTable_r();
                        contentArea.setVisible(false);
                        contentArea.setVisible(true);
                      //  tab1.getItems().remove(item); // Mise à jour immédiate de l'interface
                  });

                    // Ajout des composants au HBox
                    hbox.getChildren().addAll(nameLabel, conditionLabel,durationlabel,discountLabel,dateLabel, actionBox);
                    setGraphic(hbox); // Utiliser le HBox comme graphique pour la cellule
                }

            }
        });
    }



    public void initialize() {
        home_totalEmployees();
        homeTotalAvis();

        ObservableList<Avis> data = FXCollections.observableArrayList(serviceAvis.getAllavis());
        tab1.setItems(data);
        System.out.println("rr"+data);
        tab1.setItems(data);
      initializeListView();
// Créer une FilteredList à partir de data
        FilteredList<Avis> filteredList = new FilteredList<>(data, avis -> true);
        searchField.setOnKeyReleased(e->{


                    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                        filteredList.setPredicate(avis -> {
                            // Si la recherche est vide, affichez tous les avis
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }

                            // Convertir le texte de recherche en minuscule pour comparaison
                            String lowerCaseFilter = newValue.toLowerCase();

                            // Filtrer les avis par nom d'agence, commentaire, ou note
                            return avis.getAgenceav_id().getNomage().toLowerCase().contains(lowerCaseFilter) ||
                                    avis.getCommentaire().toLowerCase().contains(lowerCaseFilter) ||
                                    String.valueOf(avis.getNote()).contains(lowerCaseFilter);
                        });

                        tab1.setItems(filteredList);


                    });
            //ok let's check it
        });
        // Associer le champ de recherche au filtre de la FilteredList
        /*searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(avis -> {
                // Si la recherche est vide, affichez tous les avis
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convertir le texte de recherche en minuscule pour comparaison
                String lowerCaseFilter = newValue.toLowerCase();

                // Filtrer les avis par nom d'agence, commentaire, ou note
                return avis.getAgenceav_id().getNomage().toLowerCase().contains(lowerCaseFilter) ||
                        avis.getCommentaire().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(avis.getNote()).contains(lowerCaseFilter);
            });
            tab1.setItems(filteredList);


            //initialize();
           // tab1.refresh();


        });*/

        // Lier la FilteredList à la ListView



    }

    public void updateTable_r() {

        ObservableList<Avis> data = FXCollections.observableArrayList(serviceAvis.getAllavis());

        tab1.setItems(data);
    }
      /*  commentaire.setCellValueFactory(new PropertyValueFactory<Avis, String>("commentaire"));
        note.setCellValueFactory(new PropertyValueFactory<Avis, Integer>("note"));
        date_avis.setCellValueFactory(new PropertyValueFactory<Avis, String>("date_avis"));
        avis_id.setCellValueFactory(new PropertyValueFactory<Avis, Integer>("avis_id"));
        agenceav_id.setCellValueFactory(new PropertyValueFactory<Avis, Integer>("agenceav_id"));
        homeTotalEmployees();
        homeTotalAvis();
        loadTableData();



        tab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Avis selectedAvis = tab.getSelectionModel().getSelectedItem();
                selectedId = selectedAvis.getIdAV();
                System.out.println(selectedId);
                AVcommentaire.setText(selectedAvis.getCommentaire());

                AVnote.setText(String.valueOf(selectedAvis.getNote())); System.out.println(AVnote);
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

        clearFields();
    }

    private void loadTableData() {
        ObservableList<Avis> data = FXCollections.observableArrayList(s.getAllavis());
        tab.setItems(data);
    }

    public void updateTable_r() {
        List<Avis> aviss = s.getAllavis();
        tab.getItems().setAll(aviss);
    }

    private void clearFields() {
        AVcommentaire.clear();
        AVnote.clear();
        AVdate.clear();
        AVclient.clear();
        AVAge.clear();
    }*/
    }
