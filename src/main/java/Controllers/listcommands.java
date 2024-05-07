package Controllers;

import Entities.Commande;
import Entities.Police;
import Entities.UserSession;
import Services.CommandeService;
import Services.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class listcommands {

    @FXML
    private VBox cardsContainer;

    @FXML
    private VBox mainContainer;

    CommandeService cs= new CommandeService();

    @FXML
    public void initialize() {
        loadCom();
    }

    private void loadCom() {
        cardsContainer.getChildren().clear();
        List<Commande> comd = cs.getcheckedCommandesByUserId(Integer.parseInt(UserSession.id)); // Assume this method fetches all Police entries
        createAndDisplaycomCards(comd);
    }
    private void createAndDisplaycomCards(List<Commande> polices) {
        Platform.runLater(() -> {
            cardsContainer.getChildren().clear(); // Effacer le conteneur sur le thread JavaFX

            if (polices.isEmpty()) {
                Label noResults = new Label("Aucune commande correspondante trouvée.");
                cardsContainer.getChildren().add(noResults);
            } else {
                for (int i = 0; i < polices.size(); i++) {
                    Commande police = polices.get(i);
                    VBox policeCard = createPoliceCard(police);
                    if (i % 3 == 0) {
                        HBox currentHBox = new HBox(20);
                        currentHBox.setAlignment(Pos.CENTER);
                        cardsContainer.getChildren().add(currentHBox);
                    }
                    ((HBox)cardsContainer.getChildren().get(cardsContainer.getChildren().size() - 1)).getChildren().add(policeCard);
                }
            }
        });
    }

    private VBox createPoliceCard(Commande police) {
        VBox card = new VBox();
        card.getStyleClass().add("card");
        card.setSpacing(10); // Spacing between elements in the card

        Label titleLabel = new Label(police.getDoa_com_id().getName_ins());
        titleLabel.getStyleClass().add("card__title");

        Label descriptionLabel = new Label(String.valueOf(police.getMontant()));
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("card__title");
        descriptionLabel.setPrefWidth(200); // Ensuring it's wide enough to display content
        descriptionLabel.setMinHeight(Label.USE_PREF_SIZE);

        Button btn = new Button("Add claim");
        btn.getStyleClass().add("btn");
        btn.setOnAction(event -> showaddclaim(police));
        HBox buttonContainer = new HBox(10); // Create an HBox to hold buttons
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(btn);
        card.getChildren().addAll(titleLabel, descriptionLabel, buttonContainer); // Add title, description, and button to the card

        return card;
    }

    private void showaddclaim(Commande police) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addReclamation.fxml"));
            Parent root = loader.load();
            addReclamation controller = loader.getController();
            controller.initData(police);
            mainContainer.getChildren().clear();
            mainContainer.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
