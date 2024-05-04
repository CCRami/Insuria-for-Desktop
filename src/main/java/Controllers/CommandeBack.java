package Controllers;

import Entities.Commande;
import Services.CommandeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CommandeBack implements Initializable {
    @FXML
    private ListView<Commande> tabcom;

    @FXML
    private VBox contentArea;


    CommandeService commandeService = new CommandeService();


    private void initializeListView() {
        tabcom.setCellFactory(param -> new ListCell<Commande>() {
            @Override
            protected void updateItem(Commande item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {


                    // Création d'un HBox pour contenir les labels et les boutons
                    HBox hbox = new HBox(2); // Espace de 10px entre les éléments
                    hbox.setAlignment(Pos.CENTER);

                    Label comID = new Label(Integer.toString(item.getId()));
                    comID.setMinWidth(120.0);
                    comID.setMaxWidth(130.0);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Define your desired date format
                    Label dateeff = new Label(dateFormat.format(item.getDate_effet())); // Convert Date to String
                    dateeff.setMinWidth(120.0);
                    dateeff.setMaxWidth(130.0);

                    Label dateexp = new Label(dateFormat.format(item.getDate_exp())); // Convert Date to String
                    dateexp.setMinWidth(120.0);
                    dateexp.setMaxWidth(130.0);

                    Label descriptionLabel = new Label(Float.toString(item.getMontant())); // Convert float to String
                    descriptionLabel.setMinWidth(120.0);
                    descriptionLabel.setMaxWidth(130.0);
                    descriptionLabel.setWrapText(true); // Enable automatic wrapping
                    descriptionLabel.setStyle("-fx-padding: 5px;");


                    Label insurancelabel = new Label(item.getDoa_com_id().getName_ins());
                    insurancelabel.setMinWidth(120.0);
                    insurancelabel.setMaxWidth(130.0);


                    Label policylabel = new Label(item.getUser_id() != null ? Integer.toString(item.getUser_id().getId()) : "");
                    policylabel.setMinWidth(120.0);
                    policylabel.setMaxWidth(130.0);

                    ArrayList<String> doaList = item.getDoa_full(); // Retrieve the list of DOA from the Insurance item
                    String firstDoa = ""; // Initialize with an empty string
                    if (doaList != null && !doaList.isEmpty()) {
                        firstDoa = doaList.get(0); // Get the first DOA from the list if it's not empty
                    }
                    Label DOAlabel = new Label(firstDoa);
                    DOAlabel.setMinWidth(120.0);
                    DOAlabel.setMaxWidth(130.0);












                    // Ajout des composants au HBox
                    hbox.getChildren().addAll(comID,dateeff,dateexp, descriptionLabel,DOAlabel,insurancelabel,policylabel);
                    setGraphic(hbox); // Utiliser le HBox comme graphique pour la cellule
                    setText(null);
                }
            }
        });
    }



   /* private void deleteSinistre(int id) {
        commandeService.de(id); // Ici, sinistreService doit être l'instance de ton service qui contient la méthode supprimer
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Commande> data = FXCollections.observableArrayList(commandeService.getAllCommandes());
        tabcom.setItems(data);
        initializeListView(); // Configurez le ListView avec un CellFactory personnalisé
        //tabins.setMaxWidth(500);
    }








    public void handleSearch(MouseEvent mouseEvent) {
    }
}
