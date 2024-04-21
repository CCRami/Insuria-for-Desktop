package Controller;

import Entity.OfferCategory;
import Service.OffreCatService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class Updateoffcat {
    @FXML
    private TextField catoffname;

    @FXML
    private TextField descatoff;

    @FXML
    private Text errorcattoffname;

    @FXML
    private Text errordescatoff;

    @FXML
    private ImageView piccatoff;

    @FXML
    private Button save;

    private OfferCategory offerCategoryToUpdate;

    public void initData(OfferCategory offerCategory) {
        this.offerCategoryToUpdate = offerCategory;

        // Populate fields with existing offer category details
        catoffname.setText(offerCategory.getCategorie_name());
        descatoff.setText(offerCategory.getDescription_cat());

        // Load existing image, if available
        String imageUrl = offerCategory.getCatimg();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            piccatoff.setImage(new Image(imageUrl));
        }
    }

    @FXML
    void updateOfferCat(ActionEvent event) {
        if (!isInputValid()) {
            return; // Exit the method if input is not valid
        }

        // Update offer category details
        offerCategoryToUpdate.setCategorie_name(catoffname.getText());
        offerCategoryToUpdate.setDescription_cat(descatoff.getText());
        Image image = piccatoff.getImage();
        String catofImageUrl = image != null ? image.getUrl() : null;
        offerCategoryToUpdate.setCatimg(catofImageUrl);

        OffreCatService service = new OffreCatService();
        service.UpdateCatOff(offerCategoryToUpdate);

        // Display success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Offer Category updated successfully");
        alert.showAndWait();
    }

    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (catoffname.getText().isEmpty()) {
            // If catoffname input is empty
            errorcattoffname.setText("Category offer name is required");
            isValid = false; // Flag indicating input is invalid
        } else if (!catoffname.getText().matches("^[a-zA-Z]+$")) {
            // If catoffname input contains non-alphabetic characters
            errorcattoffname.setText("Category offer name should only contain letters");
            isValid = false; // Flag indicating input is invalid
        } else {
            errorcattoffname.setText(""); // Clear error message if input is valid
        }

        if (descatoff.getText().isEmpty()) {
            // If descatoff input is empty
            errordescatoff.setText("Description is required");
            isValid = false; // Flag indicating input is invalid
        } else if (!descatoff.getText().matches("^[a-zA-Z]+$")) {
            // If descatoff input contains non-alphabetic characters
            errordescatoff.setText("Description should only contain letters");
            isValid = false; // Flag indicating input is invalid
        } else {
            errordescatoff.setText(""); // Clear error message if input is valid
        }

        return isValid;
    }


    @FXML
    public void chooseImageAction(ActionEvent actionEvent) {
        // Implement image choosing logic similar to the add page
    }
}


