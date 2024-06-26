package Controllers;


import Controllers.User.UserProfileController;
import Entities.Insurance;
import Entities.User;
import Entities.UserSession;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class dashboardFront implements Initializable{
    @FXML
    ImageView eventView;



    @FXML
    private BorderPane myBorderPane;

    @FXML
    private VBox vboxdash;

    @FXML
    private Button Logoutbtn;

    @FXML
    private Button Profilebtn;

    @FXML
    private ImageView profileimg;

    @FXML
    private Label username;

    public int idoff;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserService us= new UserService();
        User u= us.displayByid(Integer.parseInt(UserSession.id));
        System.out.println(UserSession.id);
        System.out.println(u.getFirst_name());
        username.setText(u.getFirst_name());
        if (!u.getAvatar().isEmpty()) {
            profileimg.setImage(new Image(u.getAvatar()));
        }
        else {
            profileimg.setImage(new Image("https://i.imgur.com/x5co7s8.png"));
        }
    }

    @FXML
    void showAvisAgence(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficheravis.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }







    @FXML
    void showAgence(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficheragences.fxml"));
            Node eventFXML = loader.load();
//afficheragences
            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }

    @FXML
    void showajouteragence(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouteragence.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }
    @FXML
    void GotoLogout(ActionEvent event) throws IOException {
        UserSession.cleanUserSession();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Node eventFXML = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) eventFXML);
        stage.setScene(scene);
    }

    @FXML
    void GotoProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserProfile.fxml"));
        AnchorPane eventFXML = loader.load();
        UserProfileController controller = loader.getController();
        controller.setImageViewSize(vboxdash.getWidth(), vboxdash.getHeight());
        vboxdash.getChildren().clear();
        vboxdash.getChildren().add(eventFXML);
    }

    public void showComp(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamationsFront.fxml"));
        AnchorPane eventFXML = loader.load();
        reclamationsFront controller = loader.getController();
        vboxdash.getChildren().clear();
        vboxdash.getChildren().add(eventFXML);
    }
    public void showPolice(ActionEvent actionEvent) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/affichagefrontPolice.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }

    public void showSinistre(ActionEvent actionEvent) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/affichagefrontSinistre.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }

    }
    public void Showcatoff(MouseEvent actionEvent) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Showcat.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }
    public void Showoff(ActionEvent actionEvent) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Showof.fxml"));
            Node eventFXML = loader.load();
            Showof controller = loader.getController();
            controller.id=idoff;
            System.out.println("fildashf"+idoff);
            controller.initialize();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }

    public void Showins(MouseEvent actionEvent) {
        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayInsuranceF.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }

    public void showadd(Insurance ins)
    {
        try {
            // Charger le fichier FXML pour ajouter un sinistre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCommande.fxml"));
            Node eventFXML = loader.load();
            AddCommande controller = loader.getController();
            controller.setInsurance(ins);
            controller.initialize(ins);

            if (vboxdash != null) {
                vboxdash.getChildren().clear(); // Nettoyer le contenu existant
                vboxdash.getChildren().add(eventFXML); // Ajouter le contenu chargé
            } else {
                System.out.println("Erreur : contentArea est null, vérifiez votre fichier FXML.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showbasket(double disc)
    {
        try {
            // Charger le fichier FXML pour ajouter un sinistre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommandeBasket.fxml"));
            Node eventFXML = loader.load();
            CommandeBasket controller = loader.getController();
            controller.setDiscount(disc);
            if (vboxdash != null) {
                vboxdash.getChildren().clear(); // Nettoyer le contenu existant
                vboxdash.getChildren().add(eventFXML); // Ajouter le contenu chargé
            } else {
                System.out.println("Erreur : contentArea est null, vérifiez votre fichier FXML.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void gotobasket(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommandeBasket.fxml"));
            Node eventFXML = loader.load();
            if (vboxdash != null) {
                vboxdash.getChildren().clear(); // Nettoyer le contenu existant
                vboxdash.getChildren().add(eventFXML); // Ajouter le contenu chargé
            } else {
                System.out.println("Erreur : contentArea est null, vérifiez votre fichier FXML.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}