package Controllers;

import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Entities.Police;
import Entities.Sinistre;
import Services.PoliceService;
import Services.SinistreService;

import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.util.Date;
import java.util.stream.Collectors;
import javafx.scene.control.TextField;

public class AffichagefrontPolice {
    @FXML
    private VBox cardsContainer; // This is the main container in FXML
    @FXML
    private TextField searchField; // TextField for entering search terms
    private PoliceService policeService = new PoliceService();
    private SinistreService sinistreService = new SinistreService();
    @FXML
    private HBox sinistreFilterBox;

    @FXML
    public void initialize() {
        populateSinistreFilters();
        loadPolice();
        TextField searchField = new TextField();
        searchField.setPromptText("Search Policies...");
        setupSearchField();
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-padding: 10;");
        borderPane.setLeft(searchField);
        BorderPane.setAlignment(searchField, Pos.CENTER_LEFT);
    }
    private void setupSearchField() {
        searchField.setStyle("-fx-background-color: transparent; " +
                "-fx-border-width: 0 0 2 0; " +
                "-fx-border-color: #051b46; " +
                "-fx-padding: 15 0; " +
                "-fx-font-size: 18px; " +
                "-fx-text-fill: #051b46;");
        searchField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                searchField.setStyle(searchField.getStyle() + "-fx-border-color: #0c0c0c;");
            } else {
                searchField.setStyle(searchField.getStyle() + "-fx-border-color: #051b46;");
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterPoliciesBySearch(newValue);
            if (!newValue.trim().isEmpty()) {
                searchField.setStyle(searchField.getStyle() + "-fx-border-color: #051b46;");
            } else {
                searchField.setStyle(searchField.getStyle() + "-fx-border-color: #0c0c0c;");
            }
        });

    }
    private void filterPoliciesBySearch(String inputSearchTerm) {
        // Declare a final variable for use within the lambda to avoid issues
        final String searchTerm = inputSearchTerm.toLowerCase().trim();

        // Execute the search in a background thread if it might be long-running
        new Thread(() -> {
            if (searchTerm.isEmpty()) {
                Platform.runLater(this::loadPolice); // Use method reference for better readability
            } else {
                // Fetch all policies only once to avoid multiple database calls
                List<Police> allPolicies = policeService.getAll();
                List<Police> filteredPolicies = allPolicies.stream()
                        .filter(police -> police.getPoliceName().toLowerCase().contains(searchTerm) ||
                                police.getDescriptionPolice().toLowerCase().contains(searchTerm))
                        .collect(Collectors.toList());

                // Update UI in the JavaFX Application Thread
                Platform.runLater(() -> createAndDisplayPoliceCards(filteredPolicies));
            }
        }).start();
    }


    private void populateSinistreFilters() {
        // Create an "All" button to display all policies
        sinistreFilterBox.setStyle(TABS_STYLE);
        Button allButton = new Button("All");
        applyButtonStyle(allButton);
        allButton.setOnAction(event -> loadPolice());
        sinistreFilterBox.getChildren().add(allButton);

        // Populate buttons for each sinistre
        List<Sinistre> sinistres = sinistreService.getAll(); // Assume this fetches all Sinistres
        for (Sinistre sinistre : sinistres) {
            Button sinistreButton = new Button(sinistre.getSin_name());
            applyButtonStyle(sinistreButton);
            int sinistreId = sinistre.getId(); // Assuming getId() is a method that returns the sinistre's ID
            sinistreButton.setOnAction(event -> {
                System.out.println("Filtering policies for sinistre ID: " + sinistreId);
                filterPoliciesBySinistre(sinistreId);
            });
            sinistreFilterBox.getChildren().add(sinistreButton);
        }
    }
    private void applyButtonStyle(Button button) {
        button.setStyle(TAB_STYLE);
        button.setOnMouseEntered(e -> button.setStyle(TAB_HOVER_STYLE));
        button.setOnMouseExited(e -> button.setStyle(TAB_STYLE));
    }

    private void loadPolice() {
        cardsContainer.getChildren().clear();
        List<Police> polices = policeService.getAll(); // Assume this method fetches all Police entries
        createAndDisplayPoliceCards(polices);
    }
    private void createAndDisplayPoliceCards(List<Police> polices) {
        Platform.runLater(() -> {
            cardsContainer.getChildren().clear(); // Effacer le conteneur sur le thread JavaFX

            if (polices.isEmpty()) {
                Label noResults = new Label("Aucune police correspondante trouvée.");
                cardsContainer.getChildren().add(noResults);
            } else {
                for (int i = 0; i < polices.size(); i++) {
                    Police police = polices.get(i);
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
        Button pdfButton = new Button("Generate PDF");
        pdfButton.getStyleClass().add("btn");
        pdfButton.setOnAction(event -> generatePDF(police));
        HBox buttonContainer = new HBox(10); // Create an HBox to hold buttons
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(btn, pdfButton);
        card.getChildren().addAll(titleLabel, descriptionLabel, buttonContainer); // Add title, description, and button to the card

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
                sinistreTitle.setStyle("-fx-text-fill: #0c0c0c; -fx-background-color: #ffffff; -fx-padding: 5px; -fx-background-radius: 5px;");
                sinistreDescription.setStyle("-fx-text-fill: #0c0c0c; -fx-background-color: #ffffff; -fx-padding: 5px; -fx-background-radius: 5px;");

                card.getChildren().addAll(sinistreTitle, sinistreDescription);
                btn.setText("Hide Details");
            }
        } else {
            // Remove sinistre details from the card
            card.getChildren().removeIf(node -> node.getId() != null && (node.getId().equals("sinistreTitle") || node.getId().equals("sinistreDescription")));
            btn.setText("More Details");
        }
    }
    private void filterPoliciesBySinistre(int sinistreId) {
        // Clear the current display
        cardsContainer.getChildren().clear();

        // Fetch filtered policies
        List<Police> filteredPolicies = policeService.getPoliciesBySinistre(sinistreId);
        System.out.println("Found " + filteredPolicies.size() + " policies for sinistre ID: " + sinistreId);

        // Use the createAndDisplayPoliceCards method to add filtered policies to the UI
        createAndDisplayPoliceCards(filteredPolicies);
    }
    private void generatePDF(Police police) {
        Document document = new Document(PageSize.A4);
        try {
            String fileName = police.getPoliceName() + "Details.pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            FooterPageEvent footer = new FooterPageEvent();
            writer.setPageEvent(footer);
            document.open();

            // Ajouter le logo


            // Ajouter un titre
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
            titleFont.setColor(new BaseColor(0, 14, 41));
            Paragraph title = new Paragraph("Police Details", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Ajouter un espace
            document.add(new Paragraph(" \n\n"));

            Font sectionTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);

            Font sectionTitleFont1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            // Ajouter le nom de la police et la description

            document.add(new Paragraph("Police Name:", sectionTitleFont));
            document.add(new Paragraph(police.getPoliceName()));
            document.add(new Paragraph("Description:", sectionTitleFont));
            document.add(new Paragraph(police.getDescriptionPolice()));

            document.add(new Paragraph(" \n\n\n"));
            // Add the text "Associated Disaster" instead of a horizontal line
            document.add(new Paragraph("Associated Disaster:", sectionTitleFont1));
            document.add(new Paragraph(" \n\n"));
            // Ajouter les détails du sinistre associé
            Sinistre sinistre = police.getSinistre();
            if (sinistre != null) {
                document.add(new Paragraph("Disaster Name:", sectionTitleFont));
                document.add(new Paragraph(sinistre.getSin_name()));
                document.add(new Paragraph("Disaster Description:", sectionTitleFont));
                document.add(new Paragraph(sinistre.getDescription_sin()));
            }

            // Add a table to push the signature to the bottom of the page
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            table.setSpacingBefore(220);
            PdfPCell cell = new PdfPCell(new Paragraph("Signature:____________________________", sectionTitleFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setVerticalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            document.add(table);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class FooterPageEvent extends PdfPageEventHelper {
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Phrase footer = new Phrase("Généré le : " + new Date().toString(), footerFont);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
        }
    }
    private static final String TABS_STYLE = "-fx-background-color: #fff; " +
            "-fx-padding: 12px; " + // Converted from 0.75rem assuming 16px = 1rem
            "-fx-background-radius: 50px; " + // 99px is visually similar to fully rounded corners
            "-fx-effect: dropshadow(three-pass-box, rgba(24, 94, 224, 0.15), 6, 0.1, 0, 6);";

    private static final String TAB_STYLE = "-fx-background-color: transparent; " +
            "-fx-font-size: 0.8rem; " +
            "-fx-text-fill: black; " +
            "-fx-font-weight: bold; " +
            "-fx-cursor: hand; " +
            "-fx-background-radius: 50px; " +
            "-fx-padding: 5 10;";

    private static final String TAB_HOVER_STYLE = "-fx-background-color: #e6eef9;";


}