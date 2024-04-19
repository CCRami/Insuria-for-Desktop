/*package Controller;

import Entity.OfferCategory;
import Entity.Offre;
import Service.OffreCatService;
import Service.OffreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Updateoffer {
    @FXML
    private TextField advantage;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField condition;

    @FXML
    private TextField discount;

    @FXML
    private TextField duration;

    @FXML
    private Text erroradvantage;

    @FXML
    private Text errorcondition;

    @FXML
    private Text errordiscount;

    @FXML
    private Text errorduration;

    @FXML
    private Text errorofferimg;

    @FXML
    private ComboBox<OfferCategory> categoryfComboBox;

    @FXML
    private ImageView offerimg;

    @FXML
    private Button save;

    @FXML
    private VBox vboxdash;

}
@FXML
private void updateOffer(ActionEvent event) {
    String advantageof = updatedAdvantage.getText();
    String conditionof = condition.getText();
    String discountof = discount.getText();
    double discountValue = Double.parseDouble(discountof);
    String durationof = duration.getText();

    Image image = offerimg.getImage();
    String ofImageUrl = image != null ? image.getUrl() : null;
    OfferCategory selectedCategory = categoryfComboBox.getSelectionModel().getSelectedItem();
    Offre off = new Offre(advantageof, conditionof, durationof, discountValue, ofImageUrl,selectedCategory);

    OffreService service = new OffreService();
    service.AddOff(off);


    updatedAdvantage.clear();
    condition.clear();
    discount.clear();
    duration.clear();

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText("Offer added successfully");
    alert.showAndWait();


}
    if (isInputValid()) {
        // Get the selected offer to update
        Offre selectedOffer = tableOfOffers.getSelectionModel().getSelectedItem();
        if (selectedOffer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select an offer to update.");
            alert.showAndWait();
            return;
        }

        // Get updated data from the input fields
        String updatedAdvantage = advantage.getText();
        String updatedCondition = condition.getText();
        String updatedDiscount = discount.getText();
        double updatedDiscountValue = Double.parseDouble(updatedDiscount);
        String updatedDuration = duration.getText();

        Image updatedImage = offerimg.getImage();
        String updatedImageUrl = updatedImage != null ? updatedImage.getUrl() : null;

        OfferCategory updatedCategory = categoryfComboBox.getSelectionModel().getSelectedItem();

        // Update the selected offer with the new data
        selectedOffer.setAdvantage(updatedAdvantage);
        selectedOffer.setCondition(updatedCondition);
        selectedOffer.setDiscountValue(updatedDiscountValue);
        selectedOffer.setDuration(updatedDuration);
        selectedOffer.setImageUrl(updatedImageUrl);
        selectedOffer.setCategory(updatedCategory);

        // Call the service to update the offer in the database
        OffreService service = new OffreService();
        service.updateOff(selectedOffer);

        // Show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Offer updated successfully");
        alert.showAndWait();

        // Clear input fields
        clearFields();
    }
}

private void clearFields() {
    advantage.clear();
    condition.clear();
    discount.clear();
    duration.clear();
    // Clear image and reset ImageView
    offerimg.setImage(null);
    offerimg.setClip(null);
    offerimg.setFitWidth(0);
    offerimg.setFitHeight(0);
    vboxdash.getChildren().clear(); // Clear any previous image display
}

@FXML
private void initialize() {
    populateCategoryComboBox();
    // Assuming tableOfOffers is a TableView for displaying existing offers
    // Configure the table to allow selection of single rows
    tableOfOffers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    // Set listener for selection change to populate fields with selected offer's data
    tableOfOffers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            populateFields(newSelection);
        }
    });
}

private void populateFields(Offre offer) {
    advantage.setText(offer.getAdvantage());
    condition.setText(offer.getCondition());
    discount.setText(String.valueOf(offer.getDiscountValue()));
    duration.setText(offer.getDuration());

    // Set the category in the ComboBox
    categoryfComboBox.getSelectionModel().select(offer.getCategory());

    // Load and display the image
    String imageUrl = offer.getImageUrl();
    if (imageUrl != null && !imageUrl.isEmpty()) {
        Image image = new Image(imageUrl);
        offerimg.setImage(image);
        // Rest of the image display logic...
    }
}

private void populateCategoryComboBox() {
    OffreCatService offreCatService = new OffreCatService();
    List<OfferCategory> categories = offreCatService.getAll();
    categoryfComboBox.getItems().addAll(categories);
}
*/