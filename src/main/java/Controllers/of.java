package Controllers;
import com.google.zxing.BarcodeFormat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import Entities.Offre;
import javafx.scene.image.Image;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
    private ImageView qrCodeImageView;
    @FXML
    private Button reviews;
    Double discount=null;

    public void setData(Offre offre) {
        // Set other offer details
        Advantage.setText(offre.getAdvantage());
        Condition.setText(offre.getConditions());
        Discount.setText(String.valueOf(offre.getDiscount()));
        discount=offre.getDiscount();
        Duration.setText(offre.getDuration());

        // Load offer image
        String imageUrl = offre.getOffreimg();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = new Image(imageUrl);
                offreimg.setImage(image);
            } catch (Exception e) {
                System.err.println("Failed to load image: " + e.getMessage());
                offreimg.setImage(null); // Set a placeholder image or handle appropriately
            }
        } else {
            offreimg.setImage(null); // Set a placeholder image or handle appropriately
        }

        // Generate and display QR code for the offer
        generateAndDisplayQRCode(offre);

    }

    public void generateAndDisplayQRCode(Offre offre) {
        try {
            // Construct JSON object with offer details
            JSONObject offerJson = new JSONObject();
            offerJson.put("id", offre.getId_off());
            offerJson.put("advantage\n", offre.getAdvantage());
            offerJson.put("conditions\n", offre.getConditions());
            offerJson.put("discount\n", offre.getDiscount());
            offerJson.put("duration\n", offre.getDuration());

// Constructing a single-line representation of the JSON object
            StringBuilder jsonString = new StringBuilder("{");
            for (String key : offerJson.keySet()) {
                jsonString.append("\"").append(key).append("\":\"").append(offerJson.get(key)).append("\", ");
            }
// Remove the trailing comma and space if there are any entries
            if (jsonString.length() > 2) {
                jsonString.setLength(jsonString.length() - 2); // Remove the last comma and space
            }
            jsonString.append("}");

            String offerJsonString = offerJson.toString();
            System.out.println(offerJsonString);



            // Generate QR code using the encoded JSON string
            int width = 50;
            int height = 50;

            MultiFormatWriter writer = new MultiFormatWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix matrix = writer.encode(offerJsonString, BarcodeFormat.QR_CODE, width, height, hints);
            Image qrImage = matrixToImage(matrix);

            qrCodeImageView.setImage(qrImage); // Display the generated QR code Image

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private Image matrixToImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        javafx.scene.image.WritableImage image = new javafx.scene.image.WritableImage(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.getPixelWriter().setArgb(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF); // Black or white pixel
            }
        }

        return image;
    }

    @FXML
    void discountbasket(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
        Parent root= loader.load();
        Condition.getScene().setRoot(root);
        dashboardFront controller = loader.getController();
        controller.showbasket(discount);
    }

}
