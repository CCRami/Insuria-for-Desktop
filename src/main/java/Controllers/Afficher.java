package Controllers;

import Entities.Agence;
import Entities.Avis;
import Services.AgenceService;
import Services.AvisService;
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
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Pagination;

public class Afficher implements Initializable {

    @FXML
    private GridPane container;
    int parametres;

    @FXML
    private Pagination pagination;
    @FXML
    private Label nomRestau;

    @FXML
    private Label adresse;
    @FXML
    private VBox agencesfr;
    @FXML
    private GridPane container1;
    @FXML
    private ScrollPane scroll1;




    @FXML
    private VBox reviewsbyage;

    @FXML
    private Label email;

    @FXML
    private Label phone;
    @FXML
    private Button reviews;

    Agence parametre;
    private static final int ITEMS_PER_PAGE = 3;
    private final AgenceService serviceAgence = new AgenceService();
    private final AvisService serviceAvis = new AvisService();
    private List<Agence> agences;
    private List<Avis> aviss;
    @FXML
    private Button back;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
   aviss=serviceAvis.getAllavis();
       agences = serviceAgence.getAllage();
        int column = 0;
        int row = 1;
        try {
            for (Agence agence : agences) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/agence.fxml"));
                try {
                    Object anchorPane = fxmlLoader.load();
                    Age age = fxmlLoader.getController();
                    //System.out.println(agences);
                    age.setData(agence);
                   // parametre=agence;
                    for (Avis avis : aviss) {
                        // Vérifier si l'agence correspond à celle recherchée
                        if (avis.getAvis_id()!=111 && avis.getAgenceav_id() != agence) {
                            // Ajouter l'avis correspondant à la liste des avis filtrés
                            age.supprimera(agence);
                        }
                    }



                    age.reviewsbyagence(agence);
                  //  age.Rateus1(agence);
                   // Button rateusButton = age.Rateus1(agence);

                    // Définir un gestionnaire d'événements sur le bouton pour ouvrir la boîte de dialogue d'édition
                 //  rateusButton.setOnAction(event -> age.openEditDialog(agence));


                    //age.openEditDialog(agence);

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
    /*public void switchForm(ActionEvent event){

        if(event.getSource() == reviews){

            aviss = serviceAvis.getAllavisbyagence(parametre);
            int column = 0;
            int row = 1;
            try {
                for (Avis avis : aviss) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/unAvis.fxml"));
                    try {
                        Object anchorPane = fxmlLoader.load();
                        avis age = fxmlLoader.getController();

                        age.setData(avis);

                        if (column == 3) {
                            column = 0;
                            row++;
                        }

                        container1.add((Node) anchorPane, column++, row);
                        GridPane.setMargin((Node) anchorPane, new Insets(10));

                    } finally {
                        // Assurez-vous de fermer les flux pour éviter les fuites de ressources
                        fxmlLoader = null;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors du chargement du FXML", e);
            }
            reviewsbyage.setVisible(true);
            agencesfr.setVisible(false);


        }else if(event.getSource() == back){


            reviewsbyage.setVisible(false);
            agencesfr.setVisible(true);


        }


    }*/

}
