package Controller;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import Entity.Offre;
import Service.OffreService;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    public void setData(Offre offre) {
        // Set other offer details
        Advantage.setText(offre.getAdvantage());
        Condition.setText(offre.getConditions());
        Discount.setText(String.valueOf(offre.getDiscount()));
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
            offerJson.put("advantage", offre.getAdvantage());
            offerJson.put("conditions", offre.getConditions());
            offerJson.put("discount", offre.getDiscount());
            offerJson.put("duration", offre.getDuration());

            // Encode JSON object as a string
            String offerJsonString = offerJson.toString();

            // Generate QR code using the encoded JSON string
            int width = 125;
            int height = 125;

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



}
