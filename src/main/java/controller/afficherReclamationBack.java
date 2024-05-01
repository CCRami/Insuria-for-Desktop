package controller;

import entity.Indemnissation;
import entity.Reclamation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import services.IndemnisationService;
import services.ReclamationService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
public class afficherReclamationBack  {
    private Reclamation selectedReclamation;

    private Stage stage;


    @FXML
    private WebView map;
    private WebEngine webEngine;
    @FXML
    private HBox reclamations;
    @FXML
    private HBox claim;

    @FXML
    private Text contenu;

    @FXML
    private Text dateReclamation;

    @FXML
    private Text dateSinistre;

    @FXML
    private Text label;

    @FXML
    private Text reponse;
    @FXML
    private Button cancel;
    @FXML
    private Text path;

    @FXML
    private ChoiceBox<String> choixReponse;
    @FXML
    private Text choixerror;
    @FXML
    private Button enregistre;

    IndemnisationService serviceindemnisation = new IndemnisationService();
    ReclamationService service = new ReclamationService();
    private String[] rep = {"refused", "accepted"};

    public void initData(Reclamation reclamation) {
        selectedReclamation = reclamation;

        label.setText(selectedReclamation.getLibelle());
        dateReclamation.setText(selectedReclamation.getDateReclamation());
        dateSinistre.setText(selectedReclamation.getDateSinitre());
        contenu.setText(selectedReclamation.getContenu_rec());
        reponse.setText(selectedReclamation.getReponse());
        path.setText(selectedReclamation.getImage_file());

        // Récupérer les coordonnées de latitude et longitude
        float latitude = Float.parseFloat(selectedReclamation.getLatitude());
        float longitude = Float.parseFloat((selectedReclamation.getLongitude()));
        loadMap(latitude, longitude);
    }





    private void loadMap(float latitude, float longitude) {
        WebEngine webEngine = map.getEngine();
        String html = "<html>"
                + "<head>"
                + "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet/dist/leaflet.css\" />"
                + "<script src=\"https://unpkg.com/leaflet/dist/leaflet.js\"></script>"
                + "</head>"
                + "<body>"
                + "<div id=\"map\" style=\"height: 400px;\"></div>"
                + "<script>"
                + "var map = L.map('map').setView([" + latitude + ", " + longitude + "], 13);"
                + "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {"
                + "attribution: '© OpenStreetMap contributors'"
                + "}).addTo(map);"
                + "L.marker([" + latitude + ", " + longitude + "]).addTo(map)"
                + ".bindPopup('Sinistre Here').openPopup();"
                + "</script>"
                + "</body>"
                + "</html>";
        webEngine.loadContent(html);
    }
    @FXML
    void cancelAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listReclamationBack.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) cancel.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur si nécessaire
        }

    }
    @FXML
    void initialize() {
        // Initialize choice box with options
        choixReponse.setItems(FXCollections.observableArrayList("refused", "accepted"));

        // Add event handler to show options when clicked
        choixReponse.setOnMouseClicked(event -> {
            choixReponse.show();
        });
    }


    @FXML
    void enregistreAction(ActionEvent event) throws SQLException {
        String selectedResponse = choixReponse.getValue();
        if (selectedResponse != null) {
            // Mettre à jour la réponse de la réclamation
            selectedReclamation.setReponse(selectedResponse);

            service.modifierReclamationReponse(selectedReclamation);


            String interfacePath = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateIndemnisation = format.format(new Date());

            Indemnissation indemnisation = new Indemnissation(0.0F, dateIndemnisation, "----");


            int k = 0;
            try {
                k = serviceindemnisation.addIndemnisation(indemnisation);
            } catch (SQLException e) {
                System.out.println("Error adding packs: " + e.getMessage());
            }
            indemnisation.setId(k);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Success");

            alert.showAndWait();
            selectedReclamation.setIndemnisation(indemnisation);
            System.out.println(selectedReclamation.getIndemnisation());
            service.modifierReclamationIndemnisation(selectedReclamation);
            if (selectedResponse.equals("accepted")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/indemnisationAccepted.fxml"));
                    Parent root = loader.load();
                    IndemnisationAccepted controller = loader.getController();
                    controller.initData(indemnisation, selectedReclamation); // Transférez la réclamation à l'interface d'édition
                    // Utilisez votre objet Stage pour afficher la nouvelle interface
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    // Gérer l'erreur si nécessaire
                }
            } else if (selectedResponse.equals("refused")) {


                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/indemnisationRefused.fxml"));
                    Parent root = loader.load();
                    indemnisationRefused controller = loader.getController();
                    controller.initData(indemnisation, selectedReclamation); // Transférez la réclamation à l'interface d'édition
                    // Utilisez votre objet Stage pour afficher la nouvelle interface
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    // Gérer l'erreur si nécessaire
                }
            }


            // Fermez la fenêtre actuelle
            Stage currentStage = (Stage) enregistre.getScene().getWindow();
            currentStage.close();
        } else {
            choixerror.setText("Please select an answer.");
        }
    }


    @FXML
    void ouvrirImage(MouseEvent event) {
        String imagePath = path.getText();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // Chargez l'image à partir du chemin spécifié
                Image image = new Image(new File(imagePath).toURI().toString());

                // Afficher l'image dans une fenêtre ou un conteneur approprié
                ImageView imageView = new ImageView(image);

                // Créer une nouvelle fenêtre pour afficher l'image
                Stage stage = new Stage();
                stage.setScene(new Scene(new StackPane(imageView)));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace(); // Gérer l'erreur en fonction de vos besoins
            }
        }


    }

    @FXML
    void showCompensations(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/indemnisationsBack.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) cancel.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur si nécessaire
        }

    }

    @FXML
    void showReclamations(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listReclamationBack.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) cancel.getScene().getWindow(); // Obtenez la référence à la fenêtre actuelle
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur si nécessaire
        }

    }


}



