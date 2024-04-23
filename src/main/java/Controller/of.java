package Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import Entity.Offre;
import Service.OffreService;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;

import javafx.scene.layout.VBox;
public class of {
    @FXML
    private Button Rateus;

    @FXML
    private Label Advantage;


    @FXML
    private Label Condition;

    @FXML
    private Label Discount;

    @FXML
    private Label Duration;
    @FXML
    private ImageView offreimg;


    @FXML
    private Button reviews;

    public void setData(Offre offre) {
        Advantage.setText(offre.getAdvantage());
        Condition.setText(offre.getConditions());
        Discount.setText(String.valueOf(offre.getDiscount()));
        Duration.setText(offre.getDuration());

        String imageUrl = offre.getOffreimg();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = new Image(imageUrl);
                offreimg.setImage(image);
            } catch (Exception e) {
                // Handle image loading failure, e.g., display a placeholder image
                System.err.println("Failed to load image: " + e.getMessage());
                offreimg.setImage(null); // Set a placeholder image or leave it empty
            }
        } else {
            // Handle empty or null image URL, e.g., display a placeholder image
            offreimg.setImage(null); // Set a placeholder image or leave it empty
        }
    }


}
