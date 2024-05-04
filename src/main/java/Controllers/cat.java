package Controllers;

import Entities.OfferCategory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;

import java.io.IOException;

public class cat {
    @FXML
    private Button Rateus;

    @FXML
    private Label CategoryName;


    @FXML
    private Label Description;


    @FXML
    private Button reviews;

    @FXML
    private ImageView piccatoff;

    public int idoff;


    public void setData(OfferCategory offerCategory){
        CategoryName.setText(offerCategory.getCategorie_name());
        Description.setText(offerCategory.getDescription_cat());
        idoff=offerCategory.getId();

        String imageUrl = offerCategory.getCatimg();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = new Image(imageUrl);
                piccatoff.setImage(image);
            } catch (Exception e) {
                // Handle image loading failure, e.g., display a placeholder image
                System.err.println("Failed to load image: " + e.getMessage());
                piccatoff.setImage(null); // Set a placeholder image or leave it empty
            }
        } else {
            // Handle empty or null image URL, e.g., display a placeholder image
            piccatoff.setImage(null); // Set a placeholder image or leave it empty
        }

    }

    @FXML
    void viewoffers(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
        Parent root=loader.load();
        dashboardFront controller = loader.getController();

        System.out.println("filcat"+idoff);
        controller.idoff=idoff;
        controller.Showoff(event);
        piccatoff.getScene().setRoot(root);

    }




}

