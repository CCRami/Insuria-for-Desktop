package Controllers;
import Entities.OfferCategory;
import Entities.Offre;
import Services.OffreCatService;
import Services.OffreService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.util.List;
import java.util.function.Consumer;
public class Updateoff {
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
    private TextField offerimgPathField;


    @FXML
    private ComboBox<OfferCategory> categoryfComboBox;


    @FXML
    private Button save;

    @FXML
    private VBox vboxdash;
    private OffreService service = new OffreService();
    private Offre currentOffre;
    private Consumer<Offre> updateCallback;
    @FXML
    private ImageView imageView;
    @FXML
    private OffreCatService OffreCatService;


    public void initialize() {
        OffreCatService = new OffreCatService();


        // Populate categoryComboBox with InsuranceCategory objects
        List<OfferCategory> offerCategories = OffreCatService.getAll();
        categoryfComboBox.getItems().addAll(offerCategories);

    }



    public void initData(Offre offre) {
        currentOffre = offre;
        advantage.setText(offre.getAdvantage());
        condition.setText(offre.getConditions());
        discount.setText(String.valueOf(offre.getDiscount()));
        duration.setText(offre.getDuration());


        int categoryId = offre.getId_catoff().getId();
        for (OfferCategory category : categoryfComboBox.getItems()) {
            if (category.getId() == categoryId) {
                categoryfComboBox.setValue(category);
                break;
            }
        }
        String imagePath = offre.getOffreimg();
        Image image = new Image(imagePath);
        imageView.setImage(image);

        // Charger et afficher l'image si le chemin n'est pas vide
        if (offre.getOffreimg() != null && !offre.getOffreimg().isEmpty()) {
            File imageFile = new File(offre.getOffreimg());
            if (imageFile.exists()) { // Vérifiez que le fichier existe avant de tenter de le charger
                Image image1 = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
            } else {
                imageView.setImage(null); // Aucune image ou chemin invalide
                System.out.println("Image file not found: " + offre.getOffreimg());
            }
        } else {
            imageView.setImage(null); // Aucun chemin d'image fourni
        }
    }


    @FXML
    private void handleSave() {

            currentOffre.setAdvantage(advantage.getText().trim());
            currentOffre.setConditions(condition.getText().trim());
            currentOffre.setDuration(duration.getText().trim());
            currentOffre.setDiscount(Double.parseDouble(discount.getText().trim()));
            currentOffre.setOffreimg(offerimgPathField.getText().trim());

            try {
                service.updateOffre(currentOffre);
                if (updateCallback != null) {
                    updateCallback.accept(currentOffre);
                }
                closeStage();
            } catch (Exception e) {

                showError("Failed to update Offre: " + e.getMessage());
            }

    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    private void handleCancel() {
        closeStage();
    }

    private void closeStage() {
        advantage.getScene().getWindow().hide();
    }
    @FXML
    private void handleBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        Stage stage = (Stage) offerimgPathField.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            offerimgPathField.setText(file.toURI().toString());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void setUpdateCallback(Consumer<Offre> callback) {
        this.updateCallback = callback;
    }

    public void saveChanges() {
        // Enregistre les modifications dans la base de données
        service.updateOffre(currentOffre);
        // Appelle le callback pour mettre à jour l'affichage
        if (updateCallback != null) {
            updateCallback.accept(currentOffre);
        }

    }
    /**
     * Checks if the given name is unique in the system.
     * @param advantage The name to check.
     * @return true if the name is unique, false otherwise.
     */

    /*
    private boolean isNameUnique(String advantage) {
        List<Offre> allAdvantages = service.getAll();
        for (String existingAdvantage : allAdvantages) {
            if (existingAdvantage.equals(advantage)) {
                return false; // Not unique
            }
        }
        return true; // Unique
    }
*/



}
