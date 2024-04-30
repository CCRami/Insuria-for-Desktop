package tn.esprit.applicatiopnpi.controllers;

import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.applicatiopnpi.models.Police;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.PoliceService;
import tn.esprit.applicatiopnpi.services.SinistreService;

import java.io.*;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.util.Date;

public class AffichagefrontPolice {
    @FXML
    private VBox cardsContainer; // This is the main container in FXML

    private PoliceService policeService = new PoliceService();
    private SinistreService sinistreService = new SinistreService();
    @FXML
    private HBox sinistreFilterBox;

    @FXML
    public void initialize() {
        populateSinistreFilters();
        loadPolice();
    }
    private void populateSinistreFilters() {
        // Create an "All" button to display all policies
        Button allButton = new Button("All");
        allButton.setOnAction(event -> loadPolice());
        sinistreFilterBox.getChildren().add(allButton);

        // Populate buttons for each sinistre
        List<Sinistre> sinistres = sinistreService.getAll(); // Assume this fetches all Sinistres
        for (Sinistre sinistre : sinistres) {
            Button sinistreButton = new Button(sinistre.getSin_name());
            int sinistreId = sinistre.getId(); // Assuming getId() is a method that returns the sinistre's ID
            sinistreButton.setOnAction(event -> {
                System.out.println("Filtering policies for sinistre ID: " + sinistreId);
                filterPoliciesBySinistre(sinistreId);
            });
            sinistreFilterBox.getChildren().add(sinistreButton);
        }
    }

    private void loadPolice() {
        cardsContainer.getChildren().clear();
        List<Police> polices = policeService.getAll(); // Assume this method fetches all Police entries
        createAndDisplayPoliceCards(polices);
    }
    private void createAndDisplayPoliceCards(List<Police> polices) {
        HBox currentHBox = null;
        for (int i = 0; i < polices.size(); i++) {
            if (i % 3 == 0) { // Every three police records, start a new HBox
                currentHBox = new HBox(20); // 20 is the spacing between each card
                currentHBox.setAlignment(Pos.CENTER); // Ensure alignment is set if needed
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
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("PoliceDetails.pdf"));
            FooterPageEvent footer = new FooterPageEvent();
            writer.setPageEvent(footer);
            document.open();

            // Ajouter le logo


            // Ajouter un titre
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Détails de la Police", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Ajouter un espace
            document.add(new Paragraph(" "));

            // Ajouter le nom de la police et la description
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Nom de la Police: " + police.getPoliceName(), bodyFont));
            document.add(new Paragraph("Description: " + police.getDescriptionPolice(), bodyFont));

            // Ajouter les détails du sinistre associé
            Sinistre sinistre = police.getSinistre();
            if (sinistre != null) {
                document.add(new Paragraph("Nom du Sinistre: " + sinistre.getSin_name(), bodyFont));
                document.add(new Paragraph("Description du Sinistre: " + sinistre.getDescription_sin(), bodyFont));
            }

            // Ajouter un espace pour la signature
            document.add(new Paragraph("Signature:____________________________", bodyFont));
            document.add(new Paragraph("Date: ", bodyFont));

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

}