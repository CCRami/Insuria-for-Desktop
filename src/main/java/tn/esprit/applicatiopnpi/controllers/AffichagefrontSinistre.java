package tn.esprit.applicatiopnpi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.SinistreService;

import java.util.List;

public class AffichagefrontSinistre {
    @FXML
    private VBox mainContainer; // This is the main container in FXML

    private SinistreService sinistreService = new SinistreService();

    @FXML
    public void initialize() {
        loadSinistres();
    }

    private void loadSinistres() {
        List<Sinistre> sinistres = sinistreService.getAll();
        HBox currentHBox = null;
        for (int i = 0; i < sinistres.size(); i++) {
            if (i % 3 == 0) {  // Every three sinistres, start a new HBox
                currentHBox = new HBox(20); // 20 is the spacing between each VBox
                mainContainer.getChildren().add(currentHBox);
            }
            VBox sinistreBox = createSinistreCard(sinistres.get(i));
            if (currentHBox != null) {
                currentHBox.getChildren().add(sinistreBox);
            }
        }
    }

    private VBox createSinistreCard(Sinistre sinistre) {
        VBox card = new VBox(10); // Added spacing between elements
        card.getStyleClass().add("card");

        ImageView imageView = new ImageView(new Image(sinistre.getImage_path()));
        imageView.setFitWidth(280);
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label(sinistre.getSin_name());
        titleLabel.getStyleClass().add("card__title");

        Label descriptionLabel = new Label(sinistre.getDescription_sin());
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("card__description");
        descriptionLabel.setPrefWidth(280); // Ensuring it's wide enough to display content
        descriptionLabel.setMinHeight(Label.USE_PREF_SIZE); // Ensure label expands to fit content

        VBox contentBox = new VBox(titleLabel, descriptionLabel);
        contentBox.getStyleClass().add("card__content");
        contentBox.setSpacing(5); // Adjust spacing between title and description
        contentBox.setVisible(false); // Initially hidden

        card.getChildren().addAll(imageView, contentBox);
        card.setOnMouseEntered(e -> contentBox.setVisible(true));
        card.setOnMouseExited(e -> contentBox.setVisible(false));

        return card;
    }



}
