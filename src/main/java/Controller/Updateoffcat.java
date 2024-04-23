package Controller;

import Entity.OfferCategory;
import Entity.Offre;
import Service.OffreCatService;
import Service.OffreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;


public class Updateoffcat {
    @FXML
    private TextField catoffname;

    @FXML
    private TextField descatoff;


    @FXML
    private ImageView piccatoff;

    @FXML
    private Button save;

    private OfferCategory offerCategoryToUpdate;

    private OffreCatService service = new OffreCatService();
    private OfferCategory currentOffrecategory;
    private Consumer<OfferCategory> updateCallback;
    @FXML
    private ImageView imageView;

    @FXML
    private TextField catimgPathField;










    public void initData(OfferCategory offerCategory) {
        currentOffrecategory = offerCategory;
        catoffname.setText(offerCategory.getCategorie_name());
        descatoff.setText(offerCategory.getDescription_cat());

    String imagePath = offerCategory.getCatimg();
    Image image = new Image(imagePath);
        imageView.setImage(image);

    // Charger et afficher l'image si le chemin n'est pas vide
        if (offerCategory.getCatimg() != null && !offerCategory.getCatimg().isEmpty()) {
        File imageFile = new File(offerCategory.getCatimg());
        if (imageFile.exists()) { // Vérifiez que le fichier existe avant de tenter de le charger
            Image image1 = new Image(imageFile.toURI().toString());
            imageView.setImage(image);
        } else {
            imageView.setImage(null); // Aucune image ou chemin invalide
            System.out.println("Image file not found: " + offerCategory.getCatimg());
        }
    } else {
        imageView.setImage(null); // Aucun chemin d'image fourni
    }
}


    @FXML
    private void handleSave() {
       // Assurez-vous que la validation retourne true avant de continuer
            currentOffrecategory.setCategorie_name(catoffname.getText().trim());
            currentOffrecategory.setDescription_cat(descatoff.getText().trim());
            currentOffrecategory.setCatimg(catimgPathField.getText().trim());



            try {
                service.UpdateCatOff(currentOffrecategory);
                if (updateCallback != null) {
                    updateCallback.accept(currentOffrecategory);
                }
                closeStage();  // Fermer la fenêtre uniquement si la mise à jour est réussie
            } catch (Exception e) {
                // Gérer l'exception, par exemple afficher un message d'erreur


        }
    }




    @FXML
    private void handleCancel() {
        closeStage();
    }


    private void closeStage() {
        catoffname.getScene().getWindow().hide();
    }
    @FXML
    private void handleBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        Stage stage = (Stage) catimgPathField.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            catimgPathField.setText(file.toURI().toString());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }

    }

    public void setUpdateCallback(Consumer<OfferCategory> callback) {
        this.updateCallback = callback;
    }

    public void saveChanges() {
        // Enregistre les modifications dans la base de données
        service.UpdateCatOff(currentOffrecategory);
        // Appelle le callback pour mettre à jour l'affichage
        if (updateCallback != null) {
            updateCallback.accept(currentOffrecategory);
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
