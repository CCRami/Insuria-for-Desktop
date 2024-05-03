package Controller;

import Entity.OfferCategory;
import Entity.Offre;
import Service.OffreCatService;
import Service.OffreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
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
import javafx.util.Callback;
import Controller.Mail;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Addoffer {

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

    @FXML
    void addoffer(ActionEvent event) {
        if (!isInputValid()) {
            return; // Exit the method if input is not valid
        }

        String advantageof = advantage.getText();
        String conditionof = condition.getText();
        String discountof = discount.getText();
        double discountValue = Double.parseDouble(discountof);
        String durationof = duration.getText();

        Image image = offerimg.getImage();
        String ofImageUrl = image != null ? image.getUrl() : null;
        OfferCategory selectedCategory = categoryfComboBox.getSelectionModel().getSelectedItem();
        Offre off = new Offre(advantageof, conditionof, durationof, discountValue, ofImageUrl, selectedCategory);

        OffreService service = new OffreService();
        service.AddOff(off);
        String emailValue = "minoubhs@gmail.com";
        Mail.sendConfirmationEmail(emailValue,off);

        advantage.clear();
        condition.clear();
        discount.clear();
        duration.clear();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Offer added successfully");
        alert.showAndWait();
        SMS sms = new SMS();
        sms.sendSMSExternally();

    }


    private boolean isInputValid() {
        boolean isValid = true;

        // Validate and display error messages
        if (advantage.getText().isEmpty()) {
            // If advantage input is empty
            erroradvantage.setText("Category offer name is required");
            isValid = false; // Flag indicating input is invalid
        } else if (!advantage.getText().matches("^[a-zA-Z ]+$")) {
            // If advantage input contains non-alphabetic characters
            erroradvantage.setText("Category offer name should not contain numbers");
            isValid = false; // Flag indicating input is invalid
        } else {
            erroradvantage.setText(""); // Clear error message if input is valid
        }


        if (condition.getText().isEmpty()) {
            // If condition input is empty
            errorcondition.setText("Description is required");
            isValid = false; // Flag indicating input is invalid
        } else if (!condition.getText().matches("^[a-zA-Z ]+$")) {
            // If condition input contains non-alphabetic characters
            errorcondition.setText("Description should not contain numbers");
            isValid = false; // Flag indicating input is invalid
        } else {
            errorcondition.setText(""); // Clear error message if input is valid
        }


        if (discount.getText().isEmpty()) {
            // If discount input is empty
            errordiscount.setText("Discount is required");
            isValid = false; // Flag indicating input is invalid
        } else if (!discount.getText().matches("^(?:100|[1-9][0-9]?)$")) {
            // If discount input is not between 1 and 100
            errordiscount.setText("Discount should be between 1 and 100");
            isValid = false; // Flag indicating input is invalid
        } else {
            errordiscount.setText(""); // Clear error message if input is valid
        }


        if (duration.getText().isEmpty()) {
            // If duration input is empty
            errorduration.setText("Duration is required");
            isValid = false; // Flag indicating input is invalid
        } else if (!duration.getText().matches("^(?:365|[1-9][0-9]?)$")) {
            // If duration input contains non-numeric characters
            errorduration.setText("Duration should be between 1 and 365");
            isValid = false; // Flag indicating input is invalid
        } else {
            errorduration.setText(""); // Clear error message if input is valid
        }


        return isValid;

    }

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
            offerimg.setFitWidth(300); // Set the desired width
            offerimg.setFitHeight(200); // Set the desired height

            // Set the image to the ImageView
            offerimg.setImage(image);

            // Clip the ImageView to the rounded rectangle shape
            Rectangle clipRect = new Rectangle(300, 200); // Adjust size as needed
            clipRect.setArcWidth(40); // Set arc width for rounded corners
            clipRect.setArcHeight(40); // Set arc height for rounded corners
            offerimg.setClip(clipRect);

            // Create a StackPane and add the image view to it
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(offerimg);
            StackPane.setAlignment(offerimg, Pos.CENTER); // Center the image within the StackPane

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
    private void initialize() {
        populateCategoryfComboBox();
    }

    private void populateCategoryfComboBox() {
        // Assuming OffreCatService is a class
        OffreCatService offreCatService = new OffreCatService();
        List<OfferCategory> categories = offreCatService.getAll();

        // Clear existing items before adding new ones
        categoryfComboBox.getItems().clear();

        // Add the OfferCategory objects to the ComboBox
        categoryfComboBox.getItems().addAll(categories);

        // Set the cell factory to display only the Categorie_name
        categoryfComboBox.setCellFactory(new Callback<ListView<OfferCategory>, ListCell<OfferCategory>>() {
            @Override
            public ListCell<OfferCategory> call(ListView<OfferCategory> param) {
                return new ListCell<OfferCategory>() {
                    @Override
                    protected void updateItem(OfferCategory item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getCategorie_name());
                        }
                    }
                };
            }
        });

        // Set the cell factory to display only the Categorie_name when the ComboBox is showing
        categoryfComboBox.setButtonCell(new ListCell<OfferCategory>() {
            @Override
            protected void updateItem(OfferCategory item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getCategorie_name());
                }
            }
        });
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

