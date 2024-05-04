package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Entities.Sinistre;
import Services.SinistreService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AffichagefrontSinistre {
    @FXML
    private VBox mainContainer; // Main container in FXML
    @FXML
    private Pagination pagination;

    private SinistreService sinistreService = new SinistreService();
    private List<Sinistre> allSinistres = new ArrayList<>();
    private static final int ITEMS_PER_PAGE = 3;
    @FXML
    private TextField searchField;

    @FXML
    public void initialize() {
        loadAllSinistres();
    }

    private void loadAllSinistres() {
        allSinistres = sinistreService.getAll();
        updatePagination(allSinistres);
    }
    private void updatePagination(List<Sinistre> sinistres) {
        int pageCount = (int) Math.ceil(sinistres.size() / (double) ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setPageFactory(pageIndex -> createPage(pageIndex, sinistres));
    }


    private VBox createPage(int pageIndex, List<Sinistre> sinistres) {
        int startIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, sinistres.size());
        VBox box = new VBox(10);
        HBox currentHBox = null;
        for (int i = startIndex; i < endIndex; i++) {
            if (i % 3 == 0 || currentHBox == null) {
                currentHBox = new HBox(20);
                box.getChildren().add(currentHBox);
            }
            VBox sinistreBox = createSinistreCard(sinistres.get(i));
            currentHBox.getChildren().add(sinistreBox);
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

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchText = searchField.getText().trim();
        if (!searchText.isEmpty()) {
            List<Sinistre> filteredSinistres = allSinistres.stream()
                    .filter(sinistre -> sinistre.getSin_name().toLowerCase().contains(searchText.toLowerCase())
                            || sinistre.getDescription_sin().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());
            if (filteredSinistres.isEmpty()) {
                logUnfoundSearch(searchText);
            }
            updatePagination(filteredSinistres);
        } else {
            updatePagination(allSinistres);
        }
    }
    private void logUnfoundSearch(String searchQuery) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("unfoundSearches.txt", true))) {
            writer.write(LocalDateTime.now() + ": " + searchQuery + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
