package edu.esprit.controller;
import java.io.File;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import edu.esprit.entities.Avis;
import edu.esprit.service.AvisService;
import edu.esprit.entities.Agence;
import edu.esprit.entities.Avis;
import edu.esprit.service.AgenceService;
//import edu.esprit.service.AvisService;
import edu.esprit.util.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class agencedash implements  Initializable{
  /* @FXML
    private TableView<Agence> tab;
    @FXML
    private TableColumn<Agence, Integer> id;
    @FXML
    private TableColumn<Agence, String> addresse;

    @FXML
    private TableColumn<Agence, String> email;

    @FXML
    private TableColumn<Agence, String> creation;

    @FXML
    private TableColumn<Agence, String> nomage;

    @FXML
    private TableColumn<Agence, Integer> tel;*/
    @FXML
    private TextField Ageadresse;

    @FXML
    private TextField Agedate;

    @FXML
    private TextField Ageemail;

    @FXML
    private TextField Agenom;

    @FXML
    private TextField Agephone;

    @FXML
    private TextField addEmployee_search;



    @FXML
    private TextField adresse;

    @FXML
    private Button ajouteragence;


    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane update;

    @FXML
    private AnchorPane list;
    @FXML
    private AnchorPane aajouter;
    @FXML
    private Button consulteravis;



    @FXML
    private TextField emailage;

    @FXML
    private Text errorAdresse;

    @FXML
    private Text errorAdresse1;

    @FXML
    private Text errorEmail;

    @FXML
    private Text errorEmail1;

    @FXML
    private Text errorNom;

    @FXML
    private Text errorNom1;

    @FXML
    private Text errorPhone;

    @FXML
    private Text errorPhone1;

    @FXML
    private Label home_totalAvis;

    @FXML
    private Label home_totalAvis1;

    @FXML
    private Label home_totalEmployees;

    @FXML
    private Label home_totalEmployees1;

    @FXML
    private Button modif;
    @FXML
    private AnchorPane listt;
    @FXML
    private Button modif1;

    @FXML
    private TextField nom;



    @FXML
    private TextField phone;

    @FXML
    private Button save;

    @FXML
    private Button supprimer;

    @FXML
    TextField  AVnote, AVdate, AVclient, AVAge;

    @FXML
    TextArea AVcommentaire;



    @FXML
    private GridPane container;

    //public Avis r = new Avis(commentaire, note, date_avis, avis_id, agenceav_id,etat);

   // private final AvisService serviceAvis = new AvisService();
    private List<Avis> avis;
    int selectedId;
    @FXML
    private ListView<Agence> tab1;

    @FXML
    private Button back1;

    @FXML
    private Button back2;

    @FXML
    private VBox contentArea;

    AgenceService s = new AgenceService();

    AvisService a =new AvisService();
    @FXML
    private VBox vboxdash;

    Agence SelectedAgence;
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    @FXML
    private Button back;

    private ResultSet result;
    @FXML
    private AnchorPane consulter;
    @FXML
    void ajouterButton(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouteragence.fxml"));
            Parent root = loader.load();
// Assuming you have access to the current scene
            Scene currentScene = ajouteragence.getScene();
// Set the new root to the current scene


            currentScene.setRoot(root);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String extractAddressNameFromURL(String url) {
        // L'adresse est souvent contenue entre les balises "/place/" et "/"
        int placeIndex = url.indexOf("/place/");
        if (placeIndex != -1) {
            int startIndex = placeIndex + "/place/".length();
            int endIndex = url.indexOf("/", startIndex);
            if (endIndex != -1) {
                String addressNameEncoded = url.substring(startIndex, endIndex);
                try {
                    // Décoder les caractères encodés en UTF-8
                    return URLDecoder.decode(addressNameEncoded, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // Gérer l'exception d'encodage non pris en charge
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    private void saveAddressName(String address) {
        System.out.println("Adresse enregistrée : " + address);
    }

    @FXML
    private void addressChoose() throws IOException {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Charger Google Maps
        webEngine.load("https://www.google.com/maps");

        // Ajouter un écouteur de changement d'URL
        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si l'URL contient des informations d'adresse
            if (newValue.startsWith("https://www.google.com/maps/place/")) {
                // Extraire le nom de l'adresse de l'URL
                String addressName = extractAddressNameFromURL(newValue);
                // Enregistrer le nom de l'adresse dans votre application
                saveAddressName(addressName);
                adresse.setText(addressName);
            }

        });

        StackPane root = new StackPane();
        root.getChildren().add(webView);

        Scene scene = new Scene(root, 800, 600);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Google Maps in JavaFX");
        primaryStage.show();

    }
    @FXML
    private TextField searchField;
    private boolean isInputValid1() {
        boolean isValid = true;

        // Validate and display error messages
        if (Agenom.getText().isEmpty() || !Agenom.getText().matches("^[a-zA-Z]+$")) {
            errorNom.setText("Name is required and should not contain numbers");
            isValid = false;
        } else {
            errorNom.setText("");
        }

        if (Ageadresse.getText().isEmpty() || !Ageadresse.getText().matches("^[a-zA-Z0-9]+$")) {
            errorAdresse.setText("Adress is required and should not contain numbers ");
            isValid = false;
        } else {
            errorAdresse.setText("");
        }

        if (Ageemail.getText().isEmpty()) {
            errorEmail.setText("Email is required");
            isValid = false;
        } else if (!Ageemail.getText().contains("@")) {
            errorEmail.setText("Invalid email format");
            isValid = false;
        } else {
            errorEmail.setText("");
        }

        if (Agephone.getText().isEmpty() || !Agephone.getText().matches("^[0-9]{8}$")) {
            errorPhone.setText("Phone is required and should be 8 digits long");
            isValid = false;
        } else {
            errorPhone.setText("");
        }

        return isValid;
    }

    private boolean isInputValid() {
        System.out.println("ee");
        boolean isValid = true;

        // Validate and display error messages
        if (nom.getText().isEmpty() || !nom.getText().matches("^[a-zA-Z]+$")) {
            errorNom1.setText("Name is required and should not contain numbers");
            isValid = false;
            System.out.println("ee");
        } else {
            errorNom1.setText("");
        }

        if (adresse.getText().isEmpty() || !nom.getText().matches("^[a-zA-Z]+$")) {
            errorAdresse1.setText("Adress is required and should not contain numbers ");
            isValid = false;
        } else {
            errorAdresse1.setText("");
        }

        if (emailage.getText().isEmpty()) {
            errorEmail1.setText("Email is required");
            isValid = false;
        } else if (!emailage.getText().contains("@")) {
            errorEmail1.setText("Invalid email format");
            isValid = false;
        } else {
            errorEmail1.setText("");
        }

        if (phone.getText().isEmpty() || !phone.getText().matches("^[0-9]{8}$")) {
            errorPhone1.setText("Phone is required and should be 8 digits long");
            isValid = false;
        } else {
            errorPhone1.setText("");
        }

        return isValid;
    }

    @FXML
    void ajouteragenceAction(ActionEvent event) {
        if (isInputValid()) {
            String nomage = nom.getText();
            String addresse = adresse.getText();
            String email = emailage.getText();
            int tel = Integer.parseInt(phone.getText());

            Date create_at = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String creation = format.format(create_at);

            Agence agence = new Agence(nomage, addresse, email, tel,creation );

            AgenceService service = new AgenceService();
            service.ajouteragence(agence);


            nom.clear();
            adresse.clear();
            phone.clear();
            emailage.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("agency added successfully");
            alert.showAndWait();

            //updateTable_r();
            aajouter.setVisible(false);
            update.setVisible(false);
            listt.setVisible(true);
consulter.setVisible(false);



        }
    }

    @FXML
    void consulterButton(ActionEvent event) {


    }
    private int Idage;
    @FXML
    void modif(ActionEvent event) {
        if (isInputValid1()) {
            String nomage = Agenom.getText();
            String addresse = Ageadresse.getText();
            String email = Ageemail.getText();
            String creation = Agedate.getText();
            int tel = Integer.parseInt(Agephone.getText());





            AgenceService service = new AgenceService();
            Agence agence = new Agence(nomage, addresse, email, tel, creation);

            System.out.println("bb"+ SelectedAgence);
            System.out.println( "bb"+Idage);
            agence.setIdage(Idage);
            agence.setTel(tel);
            agence.setNomage(nomage);
            agence.setCreate_at(Agedate.getText());
            agence.setAddresse(addresse);
            agence.setEmail(email);
            service.modifierage(agence);
            System.out.println(agence);






            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("agency updated successfully");
            alert.showAndWait();

            updateTable_r();
            aajouter.setVisible(false);
            update.setVisible(false);
            listt.setVisible(true);
            consulter.setVisible(false);


        }
    }


    @FXML
    private Label home_totalAvis2;
    @FXML
    public void homeTotalEmployees2() {
        String sql = "SELECT COUNT(id) FROM Agence";
        this.cnx = DataSource.getInstance().getConnection();;
        int countData = 0;

        try {
            this.pst = this.cnx.prepareStatement(sql);

            for(this.result = this.pst.executeQuery(); this.result.next(); countData = this.result.getInt("COUNT(id)")) {
            }

            this.home_totalEmployees2.setText(String.valueOf(countData));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
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
    private Label home_totalEmployees2;
    @FXML
    public void homeTotalEmployees1() {
        String sql = "SELECT COUNT(id) FROM Agence";
        this.cnx = DataSource.getInstance().getConnection();;
        int countData = 0;

        try {
            this.pst = this.cnx.prepareStatement(sql);

            for(this.result = this.pst.executeQuery(); this.result.next(); countData = this.result.getInt("COUNT(id)")) {
            }

            this.home_totalEmployees1.setText(String.valueOf(countData));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
    @FXML
    public void homeTotalAvis2() {
        String sql = "SELECT COUNT(id) FROM Avis";
        this.cnx = DataSource.getInstance().getConnection();;
        int countData = 0;

        try {
            this.pst = this.cnx.prepareStatement(sql);

            for(this.result = this.pst.executeQuery(); this.result.next(); countData = this.result.getInt("COUNT(id)")) {
            }

            this.home_totalAvis2.setText(String.valueOf(countData));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
    @FXML
    public void homeTotalAvis1() {
        String sql = "SELECT COUNT(id) FROM Avis";
        this.cnx = DataSource.getInstance().getConnection();;
        int countData = 0;

        try {
            this.pst = this.cnx.prepareStatement(sql);

            for(this.result = this.pst.executeQuery(); this.result.next(); countData = this.result.getInt("COUNT(id)")) {
            }

            this.home_totalAvis1.setText(String.valueOf(countData));
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



    private void initializeListView() {
        tab1.setCellFactory(param -> new ListCell<Agence>() {
            @Override
            protected void updateItem(Agence item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    homeTotalEmployees();
                    homeTotalAvis();
                    homeTotalEmployees1();
                    homeTotalAvis1();
                    homeTotalEmployees2();
                    homeTotalAvis2();
                    // Création d'un HBox pour contenir les labels et les boutons
                    HBox hbox = new HBox(5); // Espace de 10px entre les éléments
                    hbox.setAlignment(Pos.CENTER_LEFT);

                    Label nameLabel = new Label(item.getNomage());
                    nameLabel.setMinWidth(150);
                    nameLabel.setMaxWidth(150);

                    Label conditionLabel = new Label(item.getEmail());
                    conditionLabel.setMinWidth(170);
                    conditionLabel.setMaxWidth(180);
                    conditionLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    conditionLabel.setStyle("-fx-padding: 10px;");


                    Label durationlabel = new Label(item.getAddresse());
                    durationlabel.setMinWidth(170);
                    durationlabel.setMaxWidth(180);
                    durationlabel.setWrapText(true); // Activer le retour à la ligne automatique
                    durationlabel.setStyle("-fx-padding: 10px;");



                    Label discountLabel = new Label(String.valueOf(item.getTel()));
                    discountLabel.setMinWidth(120);
                    discountLabel.setMaxWidth(120);
                    discountLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    discountLabel.setStyle("-fx-padding: 10px;");

                    Label dateLabel = new Label(item.getCreate_at());
                    dateLabel.setMinWidth(170);
                    dateLabel.setMaxWidth(180);
                    dateLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    dateLabel.setStyle("-fx-padding: 10px;");




                    // Configuration des boutons avec icônes et texte
                   ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/icons8-edit-24.png")));
                   editIcon.setFitHeight(20); // Taille de l'icône
                   editIcon.setFitWidth(20);
                    Button editButton = new Button("Edit");
                    editButton.setId("edit");
                    editButton.getStyleClass().add("buttonn");

                    ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/icons8-delete-24.png")));
                   deleteIcon.setFitHeight(20); // Taille de l'icône
                    deleteIcon.setFitWidth(20);
                    Button deleteButton = new Button("Delete");
                    deleteIcon.setId("update");
                    deleteButton.getStyleClass().add("buttonn");

                   ImageView deleteIcon1 = new ImageView(new Image(getClass().getResourceAsStream("/images/icons8-review-50.png")));
                   deleteIcon1.setFitHeight(20); // Taille de l'icône
                   deleteIcon1.setFitWidth(20);
                    Button consulteravisbutton = new Button("List reviews");
                    deleteIcon1.setId("list");
                    consulteravisbutton.getStyleClass().add("buttonn");

                    HBox actionBox = new HBox(editButton, deleteButton,consulteravisbutton);
                    actionBox.setSpacing(5);
                    actionBox.setMinWidth(300);
                    actionBox.setMaxWidth(300);

                    editButton.setOnAction(event -> {
                        homeTotalEmployees1();
                        homeTotalAvis1();
                        System.out.println("r");
                        Idage = item.getIdage();
                        Agenom.setText(item.getNomage());
                        Ageadresse.setText(item.getAddresse());
                        Agedate.setText(item.getCreate_at());
                        Ageemail.setText(item.getEmail());
                        Agephone.setText(String.valueOf(item.getTel()));

                        homeTotalEmployees1();
                        homeTotalAvis1();
                        aajouter.setVisible(false);
                        update.setVisible(true);
                        listt.setVisible(false);
                        consulter.setVisible(false);


                     //   tab1.getItems();
                        //availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
                        //dashboard_btn.setStyle("-fx-background-color: transparent");
                        //purchase_btn.setStyle("-fx-background-color: transparent");
                        assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'updateagence.fxml'.";
                        assert emailage != null : "fx:id=\"email\" was not injected: check your FXML file 'updateagence.fxml'.";
                        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'updateagence.fxml'.";
                        assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'updateagence.fxml'.";
                        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'updateagence.fxml'.";


                    });
                    consulteravisbutton.setOnAction(event->{
                        homeTotalEmployees2();
                        homeTotalAvis2();
                        avis = a.getAllavisbyagence(item);
                        System.out.println(avis);
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
                        aajouter.setVisible(false);
                        update.setVisible(false);
                        listt.setVisible(false);
                        consulter.setVisible(true);
                        homeTotalEmployees2();
                        homeTotalAvis2();});

                 deleteButton.setOnAction(event -> {
                        s.supprimerage(item.getIdage());
                     updateTable_r();
                     //  tab1.getItems().remove(item); // Mise à jour immédiate de l'interface
                    });

                    // Ajout des composants au HBox
                    hbox.getChildren().addAll(nameLabel, conditionLabel,durationlabel,discountLabel,dateLabel, actionBox);
                    setGraphic(hbox); // Utiliser le HBox comme graphique pour la cellule
                }

            }
        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeTotalEmployees();
        homeTotalAvis();
        homeTotalEmployees1();
        homeTotalAvis1();
        homeTotalEmployees2();
        homeTotalAvis2();
        ObservableList<Agence> data = FXCollections.observableArrayList(s.getAll());
        tab1.setItems(data);
        System.out.println("rr"+data);
        tab1.setItems(data);

        initializeListView();
      /*  nomage.setCellValueFactory(new PropertyValueFactory<Agence, String>("nomage"));
        addresse.setCellValueFactory(new PropertyValueFactory<Agence, String>("addresse"));
        email.setCellValueFactory(new PropertyValueFactory<Agence, String>("email"));
        tel.setCellValueFactory(new PropertyValueFactory<Agence, Integer>("tel"));
        creation.setCellValueFactory(new PropertyValueFactory<Agence, String>("create_at"));
        homeTotalEmployees();
        homeTotalAvis();
        homeTotalEmployees1();
        homeTotalAvis1();
        homeTotalEmployees2();
        homeTotalAvis2();
        loadTableData();

        tab1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Agence selectedAgence = tab1.getSelectionModel().getSelectedItem();
                SelectedAgence = tab1.getSelectionModel().getSelectedItem();
                selectedId = selectedAgence.getIdage();
               // System.out.println(selectedId);
                //selectedemail= selectedAgence.getEmail();

            }
        });
*/

/*
        supprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                s.supprimerage(selectedId);
                //updateTable_r();

            }
        });*/
        FilteredList<Agence> filteredList = new FilteredList<>(data, avis -> true);
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
                    return avis.getNomage().toLowerCase().contains(lowerCaseFilter) ||
                            avis.getEmail().toLowerCase().contains(lowerCaseFilter) ||
                            avis.getAddresse().toLowerCase().contains(lowerCaseFilter)||

                            String.valueOf(avis.getTel()).contains(lowerCaseFilter);
                });

                tab1.setItems(filteredList);


            });
            //ok let's check it
        });

        assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert emailage != null : "fx:id=\"email\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'ajouteragence.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'ajouteragence.fxml'.";


         //homeTotalEmployees();
        //homeTotalAvis();



        //SelectedAgence=selectedAgence;


        assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'updateagence.fxml'.";
        assert emailage != null : "fx:id=\"email\" was not injected: check your FXML file 'updateagence.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'updateagence.fxml'.";
        assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'updateagence.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'updateagence.fxml'.";



    }

  /*  private void loadTableData() {
        ObservableList<Agence> data = FXCollections.observableArrayList(s.getAll());
        tab.setItems(data);
    }*/

    public void updateTable_r() {
        ObservableList<Agence> data = FXCollections.observableArrayList(s.getAll());

        tab1.setItems(data);
    }


    public void switchForm(ActionEvent event){

        if(event.getSource() == ajouteragence){

            aajouter.setVisible(true);
            listt.setVisible(false);
            update.setVisible(false);
            consulter.setVisible(false);


               //    homeTotalEmployees();
           //  homeTotalAvis();
           // updateTable_r();
            assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'ajouteragence.fxml'.";
            assert emailage != null : "fx:id=\"email\" was not injected: check your FXML file 'ajouteragence.fxml'.";
            assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'ajouteragence.fxml'.";
            assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'ajouteragence.fxml'.";
            assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'ajouteragence.fxml'.";

             aajouter.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
            listt.setStyle("-fx-background-color: transparent");
           update.setStyle("-fx-background-color: transparent");


        }else if(event.getSource() == modif){
            homeTotalEmployees1();
            homeTotalAvis1();
            System.out.println("r");
            Idage = SelectedAgence.getIdage();
            Agenom.setText(SelectedAgence.getNomage());
            Ageadresse.setText(SelectedAgence.getAddresse());
            Agedate.setText(SelectedAgence.getCreate_at());
            Ageemail.setText(SelectedAgence.getEmail());
            Agephone.setText(String.valueOf(SelectedAgence.getTel()));
            aajouter.setVisible(false);
            update.setVisible(true);
            listt.setVisible(false);
            consulter.setVisible(false);

            homeTotalEmployees1();
            homeTotalAvis1();
            // updateTable_r();

            //availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
            //dashboard_btn.setStyle("-fx-background-color: transparent");
            //purchase_btn.setStyle("-fx-background-color: transparent");
            assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert emailage != null : "fx:id=\"email\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'updateagence.fxml'.";


        }else if(event.getSource() == consulteravis){
            homeTotalEmployees2();
            homeTotalAvis2();
            avis = a.getAllavisbyagence(SelectedAgence);
            System.out.println(avis);
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
            aajouter.setVisible(false);
            update.setVisible(false);
            listt.setVisible(false);
            consulter.setVisible(true);
            homeTotalEmployees2();
            homeTotalAvis2();
            // updateTable_r();
            //availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
            //dashboard_btn.setStyle("-fx-background-color: transparent");
            //purchase_btn.setStyle("-fx-background-color: transparent");

        }else if(event.getSource() == back){
            homeTotalEmployees1();
            homeTotalAvis1();
            System.out.println("r");

            aajouter.setVisible(false);
            update.setVisible(false);
            listt.setVisible(true);
            consulter.setVisible(false);
            updateTable_r();
            homeTotalEmployees1();
            homeTotalAvis1();
            // updateTable_r();

            //availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
            //dashboard_btn.setStyle("-fx-background-color: transparent");
            //purchase_btn.setStyle("-fx-background-color: transparent");
            assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert emailage != null : "fx:id=\"email\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'updateagence.fxml'.";


        }
        else if(event.getSource() == back1){
            homeTotalEmployees1();
            homeTotalAvis1();
            System.out.println("r");

            aajouter.setVisible(false);
            update.setVisible(false);
            listt.setVisible(true);
            consulter.setVisible(false);

            homeTotalEmployees1();
            homeTotalAvis1();
             updateTable_r();

            //availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
            //dashboard_btn.setStyle("-fx-background-color: transparent");
            //purchase_btn.setStyle("-fx-background-color: transparent");
            assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert emailage != null : "fx:id=\"email\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'updateagence.fxml'.";


        }
        else if(event.getSource() == back2){
            homeTotalEmployees1();
            homeTotalAvis1();
            System.out.println("r");

            aajouter.setVisible(false);
            update.setVisible(false);
            listt.setVisible(true);
            consulter.setVisible(false);

            homeTotalEmployees1();
            homeTotalAvis1();
             updateTable_r();

            //availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
            //dashboard_btn.setStyle("-fx-background-color: transparent");
            //purchase_btn.setStyle("-fx-background-color: transparent");
            assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert emailage != null : "fx:id=\"email\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'updateagence.fxml'.";
            assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'updateagence.fxml'.";


        }
    }

}
