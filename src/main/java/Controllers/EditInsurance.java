package Controllers;

import Entities.Insurance;
import Entities.InsuranceCategory;
import Entities.police;
import Services.InsuranceCatService;
import Services.InsuranceService;
import Services.policeService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;




public class EditInsurance {

    @FXML
    private TextField insNameField;
    @FXML private TextField montantField;
    @FXML private TextField sinImagePathField;
    @FXML private TextField DOAField;
    @FXML private ComboBox<InsuranceCategory> categoryComboBox;
    @FXML private ComboBox<police> policyComboBox;

    @FXML
    private Label errorName;
    @FXML
    private Label errorDescription;
    @FXML
    private Label errorImagePath;

    private InsuranceService insservice = new InsuranceService();
    private Insurance currentInsurance;
    private Consumer<Insurance> updateCallback;
    @FXML private ImageView imageView;
    private InsuranceCatService insuranceCatService;
    private InsuranceService insuranceService;
    private Services.policeService policeService;
    @FXML private ListView<TextField> doaListView;

    private ArrayList<String> doaItems;
    @FXML
    private void addDOAField() {
        TextField textField = new TextField();
        doaListView.getItems().add(textField);
    }

    @FXML
    private void removeDOAField() {
        int selectedIndex = doaListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            doaListView.getItems().remove(selectedIndex);
        }
    }
    public void initialize() {
        insuranceCatService = new InsuranceCatService();
        policeService = new policeService();
        doaItems = new ArrayList<>();

        // Optionally, add an initial DOA field
        addDOAField();
        // Populate categoryComboBox with InsuranceCategory objects
        List<InsuranceCategory> categories = insuranceCatService.getAll();
        categoryComboBox.getItems().addAll(categories);

        // Populate policyComboBox with police objects
        List<police> policies = policeService.getAll();
        policyComboBox.getItems().addAll(policies);

        doaItems = new ArrayList<>();
    }

    private List<String> parseJsonStringToList(String jsonString) {
        List<String> list = new ArrayList<>();
        // Remove brackets and quotes from the JSON string
        String cleanedString = jsonString.replaceAll("[\\[\\]\"]", "");
        // Split the string by comma to get individual DOA values
        String[] values = cleanedString.split(",");
        for (String value : values) {
            list.add(value.trim()); // Trim to remove extra spaces
        }
        return list;
    }
    private String listToJsonString(ArrayList<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append("\"").append(list.get(i)).append("\"");
            if (i < list.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }



    public void initData(Insurance insurance) {
        currentInsurance = insurance;
        insNameField.setText(insurance.getName_ins());
        montantField.setText(Float.toString(insurance.getMontant()));

        // Clear previous doaItems before populating with new values
        // Serialize the list of strings to JSON

        ArrayList<String> doaList = insurance.getDoa();
        if (doaList != null) {
            String doaJsonString = listToJsonString(doaList);

            if (doaJsonString != null && !doaJsonString.isEmpty()) {
                // Parse the JSON string into a list of strings
                List<String> parsedDoaList = parseJsonStringToList(doaJsonString);

                // Clear existing items in the ListView
                doaListView.getItems().clear();

                // Populate ListView with the parsed DOA values
                for (String doa : parsedDoaList) {
                    TextField textField = new TextField(doa);
                    doaListView.getItems().add(textField);
                }
            }
        }




        // Assuming categoryComboBox is your ComboBox instance
        int categoryId = insurance.getCatins_id().getId();
        for (InsuranceCategory category : categoryComboBox.getItems()) {
            if (category.getId() == categoryId) {
                categoryComboBox.setValue(category);
                break;
            }
        }

        int policyId = insurance.getPol_id().getId();
        for (police policy : policyComboBox.getItems()) {
            if (policy.getId() == policyId) {
                policyComboBox.setValue(policy);
                break;
            }
        }

        sinImagePathField.setText(insurance.getIns_image());

        // Load and display the image if the path is not empty
        if (insurance.getIns_image() != null && !insurance.getIns_image().isEmpty()) {
            File imageFile = new File(insurance.getIns_image());
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
            } else {
                imageView.setImage(null);
                System.out.println("Image file not found: " + insurance.getIns_image());
            }
        } else {
            imageView.setImage(null);
        }
    }

    @FXML
    private void handleSave() {
        if (validateInput()) {  // Assurez-vous que la validation retourne true avant de continuer
            currentInsurance.setName_ins(insNameField.getText().trim());
            // Convert text to float for montant
            String montantText = montantField.getText().trim();
            float montantValue = Float.parseFloat(montantText);
            currentInsurance.setMontant(montantValue);

// Convert text to String for DOA
            String doaText = DOAField.getText().trim();
            ArrayList<String> doaList = new ArrayList<>();
            doaList.add(doaText);
            currentInsurance.setDoa(doaList);
            doaItems.clear();
            for (TextField textField : doaListView.getItems()) {
                doaItems.add(textField.getText());
            }

            // Update currentInsurance with the edited doa values
            currentInsurance.setDoa(doaItems);

// Convert text to int for catins_id
            // Get the selected item from the categoryComboBox
            InsuranceCategory catinsCategory = categoryComboBox.getValue();

// Get the selected item from the policyComboBox
            police polObject = policyComboBox.getValue();

// Set the selected items to the currentInsurance
            currentInsurance.setCatins_id(catinsCategory);
            currentInsurance.setPol_id(polObject);

            doaItems.clear();
            for (TextField textField : doaListView.getItems()) {
                doaItems.add(textField.getText());
            }
            // Update currentInsurance with the edited doa values
            currentInsurance.setDoa(doaItems);

            currentInsurance.setIns_image(sinImagePathField.getText().trim());

            try {
                insservice.updateInsurance(currentInsurance);
                if (updateCallback != null) {
                    updateCallback.accept(currentInsurance);
                }
                closeStage();  // Fermer la fenêtre uniquement si la mise à jour est réussie
            } catch (Exception e) {
                // Gérer l'exception, par exemple afficher un message d'erreur
                showError("Failed to update Insurance: " + e.getMessage());
            }
        }
    }
    private boolean validateInput() {
        /*boolean isValid = true;
        String name = insNameField.getText().trim();
        String description = sinDescriptionField.getText().trim();
        String imagePath = sinImagePathField.getText().trim();

        errorName.setText("");
        errorDescription.setText("");
        errorImagePath.setText("");

        if (name.isEmpty()) {
            errorName.setText("Name cannot be empty!");
            isValid = false;
        } else if (!Character.isUpperCase(name.charAt(0))) {
            errorName.setText("Name must start with an uppercase letter!");
            isValid = false;
        } else if (name.length() > 30) {
            errorName.setText("Name cannot exceed 30 characters!");
            isValid = false;
        } else if (!isNameUnique(name)) {
            errorName.setText("Name must be unique!");
            isValid = false;
        }

        if (description.isEmpty()) {
            errorDescription.setText("Description cannot be empty!");
            isValid = false;
        }

        if (imagePath.isEmpty()) {
            errorImagePath.setText("Image path cannot be empty!");
            isValid = false;
        }*/

        return true;
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
        insNameField.getScene().getWindow().hide();
    }
    @FXML
    private void handleBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        Stage stage = (Stage) sinImagePathField.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            sinImagePathField.setText(file.getAbsolutePath());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void setUpdateCallback(Consumer<Insurance> callback) {
        this.updateCallback = callback;
    }

    public void saveChanges() {
        // Enregistre les modifications dans la base de données
        insservice.updateInsurance(currentInsurance);
        // Appelle le callback pour mettre à jour l'affichage
        if (updateCallback != null) {
            updateCallback.accept(currentInsurance);
        }

    }
    /**
     * Checks if the given name is unique in the system.
     * @param name The name to check.
     * @return true if the name is unique, false otherwise.
     */



}
