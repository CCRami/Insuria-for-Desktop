package tn.esprit.applicatiopnpi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.applicatiopnpi.models.Police;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.PoliceService;

import java.util.List;

public class AffichagefrontPolice {
    @FXML
    private VBox cardsContainer; // This is the main container in FXML

    private PoliceService policeService = new PoliceService();

    @FXML
    public void initialize() {
        loadPolice();
    }

    private void loadPolice() {
        List<Police> polices = policeService.getAll(); // Assume this method fetches all Police entries
        HBox currentHBox = null;
        for (int i = 0; i < polices.size(); i++) {
            if (i % 3 == 0) { // Every three police records, start a new HBox
                currentHBox = new HBox(20); // 20 is the spacing between each card
                cardsContainer.getChildren().add(currentHBox);
            }
            VBox policeBox = createPoliceCard(polices.get(i));
            if (currentHBox != null) {
                currentHBox.getChildren().add(policeBox);
            }
        }
    }

    private VBox createPoliceCard(Police police) {
        VBox card = new VBox();
        card.getStyleClass().add("card");
        card.setSpacing(10); // Spacing between elements in the card

        Label titleLabel = new Label(police.getPoliceName());
        titleLabel.getStyleClass().add("card__title");

        Label descriptionLabel = new Label(police.getDescriptionPolice());
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("card__description");
        descriptionLabel.setPrefWidth(280); // Ensuring it's wide enough to display content
        descriptionLabel.setMinHeight(Label.USE_PREF_SIZE);

        Button btn = new Button("More Details");
        btn.getStyleClass().add("btn");
        btn.setOnAction(event -> showPoliceDetails(police, card, btn));

        card.getChildren().addAll(titleLabel, descriptionLabel, btn); // Add title, description, and button to the card

        return card;
    }

    private void showPoliceDetails(Police police, VBox card, Button btn) {
        boolean isShowingDetails = btn.getText().equals("Hide Details");
        if (!isShowingDetails) {
            Sinistre sinistre = police.getSinistre();
            if (sinistre != null) {
                String sinName = sinistre.getSin_name();
                String description = sinistre.getDescription_sin();

                // Check if the description is null and handle accordingly
                if (description == null || description.isEmpty()) {
                    description = "No description available.";
                }

                Label sinistreTitle = new Label("Sinistre Name: " + sinName);
                Label sinistreDescription = new Label("Description: " + description);
                sinistreDescription.setWrapText(true);
                sinistreDescription.setPrefWidth(280); // Ensuring it's wide enough to display content
                sinistreDescription.setMinHeight(Label.USE_PREF_SIZE);
                sinistreTitle.setId("sinistreTitle"); // Set an ID for easy access
                sinistreDescription.setId("sinistreDescription");

// Change text color to white
                sinistreTitle.setStyle("-fx-text-fill: white;");
                sinistreDescription.setStyle("-fx-text-fill: white;");


                card.getChildren().addAll(sinistreTitle, sinistreDescription);
                btn.setText("Hide Details");
            }
        } else {
            // Remove sinistre details from the card
            card.getChildren().removeIf(node -> node.getId() != null && (node.getId().equals("sinistreTitle") || node.getId().equals("sinistreDescription")));
            btn.setText("More Details");
        }
    }
    }

