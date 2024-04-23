package Controller;

import Entity.OfferCategory;
import Service.OffreCatService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;


public class Addoffercat {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField catoffname;

    @FXML
    private TextField descatoff;

    @FXML
    private Text errorcattoffname;

    @FXML
    private Text errordescatoff;

    @FXML
    private Text errorpiccatoff;

    @FXML
    private ImageView piccatoff;

    @FXML
    private Button save;

    @FXML
    private VBox vboxdash;

    @FXML
    void addoffercat(ActionEvent event) {
        if (!isInputValid()) {
            return; // Exit the method if input is not valid
        }
        String nomcatof = catoffname.getText();
        String catoffdes = descatoff.getText();
        Image image = piccatoff.getImage();
        String catofImageUrl = image != null ? image.getUrl() : null;

        OfferCategory offcat = new OfferCategory(nomcatof, catoffdes, catofImageUrl );

        OffreCatService service = new OffreCatService();
        service.AddCatOff(offcat);


        catoffname.clear();
        descatoff.clear();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Offer Category added successfully");
        alert.showAndWait();


    }

    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (catoffname.getText().isEmpty()) {
            // If catoffname input is empty
            errorcattoffname.setText("Category offer name is required");
            isValid = false; // Flag indicating input is invalid
        } else if (!catoffname.getText().matches("^[a-zA-Z ]+$")) {
            // If catoffname input contains non-alphabetic characters
            errorcattoffname.setText("Category offer name should not contain numbers");
            isValid = false; // Flag indicating input is invalid
        } else {
            errorcattoffname.setText(""); // Clear error message if input is valid
        }


        if (descatoff.getText().isEmpty()) {
            // If descatoff input is empty
            errordescatoff.setText("Description is required");
            isValid = false; // Flag indicating input is invalid
        } else if (!descatoff.getText().matches("^[a-zA-Z ]+$")) {
            // If descatoff input contains non-alphabetic characters
            errordescatoff.setText("Description should not contain numbers");
            isValid = false; // Flag indicating input is invalid
        } else {
            errordescatoff.setText(""); // Clear error message if input is valid
        }




        return isValid;

    }
    @FXML
    public void chooseImageAction(ActionEvent actionEvent) {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image"); // Set the title of the file chooser dialog
        fileChooser.setInitialDirectory(new File("C:\\Insuria Java\\Insuria-for-Desktop\\src\\main\\resources\\images"));



        // Show the file chooser dialog
        Stage stage = (Stage) save.getScene().getWindow(); // Assuming save button is in the same stage
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Check if a file was selected
        if (selectedFile != null) {
            // Create an Image object from the selected file
            Image image = new Image(selectedFile.toURI().toString());

            // Set the fitWidth and fitHeight of the ImageView
            piccatoff.setFitWidth(300); // Set the desired width
            piccatoff.setFitHeight(200); // Set the desired height

            // Set the image to the ImageView
            piccatoff.setImage(image);

            // Clip the ImageView to the rounded rectangle shape
            Rectangle clipRect = new Rectangle(300, 200); // Adjust size as needed
            clipRect.setArcWidth(40); // Set arc width for rounded corners
            clipRect.setArcHeight(40); // Set arc height for rounded corners
            piccatoff.setClip(clipRect);

            // Create a StackPane and add the image view to it
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(piccatoff);
            StackPane.setAlignment(piccatoff, Pos.CENTER); // Center the image within the StackPane

            // Create a rounded rectangle with a black border
            Rectangle border = new Rectangle(300, 200); // Adjust size as needed
            border.setArcWidth(40); // Set arc width for rounded corners
            border.setArcHeight(40); // Set arc height for rounded corners
            border.setFill(Color.TRANSPARENT); // Set fill color to transparent
            border.setStroke(Color.BLACK); // Set border color
            border.setStrokeWidth(6); // Set border width
            stackPane.getChildren().add(border);

            // Set the StackPane as the content of your layout
            // For example, if your layout is a VBox:
            vboxdash.getChildren().add(stackPane);
        }
    }

    @FXML
    void showOffre(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Showoffer.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }


    @FXML
    void showCatOffre(MouseEvent event) {

        try {
            // Load user.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Showoffcat.fxml"));
            Node eventFXML = loader.load();

            // Clear existing content from FieldHolder
            vboxdash.getChildren().clear();

            // Add the loaded userFXML to FieldHolder
            vboxdash.getChildren().add(eventFXML);
        } catch (IOException e) {
            // Handle exception (e.g., file not found or invalid FXML)
            e.printStackTrace();
        }
    }

}