package controller;

import Entity.Indemnissation;
import Entity.Reclamation;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
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
import java.util.Locale;
import java.util.ResourceBundle;
public class afficherReclamationBack  implements Initializable {
    private Reclamation reclamation;

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

    private double latitude;
    private double longitude;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = map.getEngine();

        webEngine.load(getClass().getResource("/map.html").toExternalForm());
        choixReponse.setItems(FXCollections.observableArrayList("refused", "accepted"));

        // Add event handler to show options when clicked
        choixReponse.setOnMouseClicked(event -> {
            choixReponse.show();
        });
        imageV.setOnMouseEntered(this::agrandirImage);
      }

    private void reduireImage(MouseEvent mouseEvent) {
        imageV.setScaleX(1.0); // Ajustez le facteur d'agrandissement selon vos besoins
        imageV.setScaleY(1.0);
        imageV.setOnMouseExited(null);
    }

    private void agrandirImage(MouseEvent mouseEvent) {
        imageV.setScaleX(3); // Réglez l'échelle X à sa valeur normale
        imageV.setScaleY(3);
        imageV.setOnMouseExited(this::reduireImage);
    }


    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        updateDetails();
    }

    @FXML
    private ImageView imageV;
    private void updateDetails() {
        if (reclamation != null) {
            label.setText(reclamation.getLibelle());
            dateReclamation.setText(reclamation.getDateReclamation());
            dateSinistre.setText(reclamation.getDateSinitre());
            contenu.setText(reclamation.getContenu_rec());
            reponse.setText(reclamation.getReponse());
            try {
                Image image = new Image(new File(reclamation.getImage_file()).toURI().toString());
                imageV.setImage(image);
            } catch (Exception e) {
                // Handle the exception if the image cannot be loaded
                System.err.println("Failed to load image: " + e.getMessage());
                imageV.setImage(null); // Clear the image view
            }

            try {
                Locale.setDefault(Locale.US); // Ensure using dot as decimal separator
                double latitude = Double.parseDouble(reclamation.getLatitude());
                double longitude = Double.parseDouble(reclamation.getLongitude());
                setCoordinates(latitude, longitude);
            } catch (NumberFormatException e) {
                System.err.println("Invalid latitude or longitude format: " + e.getMessage());
            }
        }

    }

    public void setCoordinates(double latitude, double longitude) {
        if (webEngine != null) {
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    webEngine.executeScript(String.format("updateMap(%f, %f);", latitude, longitude));
                }
            });
        }
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
    void enregistreAction(ActionEvent event) throws SQLException {
        String selectedResponse = choixReponse.getValue();
        if (selectedResponse != null) {
            // Mettre à jour la réponse de la réclamation
            reclamation.setReponse(selectedResponse);

            service.modifierReclamationReponse(reclamation);


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
            reclamation.setIndemnisation(indemnisation);
            System.out.println(reclamation.getIndemnisation());
            service.modifierReclamationIndemnisation(reclamation);
            int int_cmd=service.select_id_cmd(reclamation);
            double insValue= service.afficherUneCommande(int_cmd);
            System.out.println(insValue);
            if (selectedResponse.equals("accepted")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/indemnisationAccepted.fxml"));
                    Parent root = loader.load();
                    IndemnisationAccepted controller = loader.getController();
                    controller.initData(indemnisation, reclamation,insValue); // Transférez la réclamation à l'interface d'édition
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
                    controller.initData(indemnisation, reclamation); // Transférez la réclamation à l'interface d'édition
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


    @FXML
    void initialize() {
        // Initialize choice box with options
        choixReponse.setItems(FXCollections.observableArrayList("refused", "accepted"));

        // Add event handler to show options when clicked
        choixReponse.setOnMouseClicked(event -> {
            choixReponse.show();
        });
    }


}