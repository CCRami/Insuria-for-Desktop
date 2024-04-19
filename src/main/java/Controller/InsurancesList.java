package Controller;

import Entity.Insurance;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.VBox;
public class InsurancesList {



    @FXML
    private Label insName;

    @FXML
    private VBox agevbox;

    @FXML
    private ImageView insImage;

    @FXML
    private Label insAmount;



    @FXML
    private Button reviews;

    public void setData(Insurance insurance){
        insName.setText(insurance.getName_ins());

        String imageUrl = insurance.getIns_image();
        try {
            Image image = new Image(imageUrl);
            insImage.setImage(image);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace for debugging
            // Optionally, provide a fallback image or handle the error gracefully
        }

        insAmount.setText(String.valueOf(insurance.getMontant()));
    }

}
