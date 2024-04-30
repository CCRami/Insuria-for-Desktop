package tn.esprit.applicatiopnpi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.SinistreService;

import java.util.List;

public class AffichagefrontSinistre {
    @FXML
    private VBox mainContainer; // Main container in FXML
    @FXML
    private Pagination pagination;

    private SinistreService sinistreService = new SinistreService();
    private static final int ITEMS_PER_PAGE = 3;

    @FXML
    public void initialize() {
        setupPagination();
    }

    private void setupPagination() {
        List<Sinistre> sinistres = sinistreService.getAll();
        int pageCount = (int) Math.ceil(sinistres.size() / (double) ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
    }

    private VBox createPage(int pageIndex) {
        List<Sinistre> sinistres = sinistreService.getAll();
        int startIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, sinistres.size());
        VBox box = new VBox(10);
        HBox currentHBox = null;
        for (int i = startIndex; i < endIndex; i++) {
            if ((i % 3) == 0) {
                currentHBox = new HBox(20); // 20 is the spacing between each VBox
                box.getChildren().add(currentHBox);
            }
            VBox sinistreBox = createSinistreCard(sinistres.get(i));
            if (currentHBox != null) {
                currentHBox.getChildren().add(sinistreBox);
            }
        }
        return box;
    }

    private VBox createSinistreCard(Sinistre sinistre) {
        VBox card = new VBox(10);
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
        descriptionLabel.setPrefWidth(280);
        descriptionLabel.setMinHeight(Label.USE_PREF_SIZE);

        VBox contentBox = new VBox(titleLabel, descriptionLabel);
        contentBox.getStyleClass().add("card__content");
        contentBox.setSpacing(5);
        contentBox.setVisible(false);

        card.getChildren().addAll(imageView, contentBox);
        card.setOnMouseEntered(e -> contentBox.setVisible(true));
        card.setOnMouseExited(e -> contentBox.setVisible(false));

        return card;
    }
}
