package Controllers;

import Entities.*;
import Services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddInsurance {

    @FXML
    private TextField nameins; // Field for insurance name

    @FXML
    private TextField amount; // Field for insurance amount

    @FXML
    private ImageView insuranceImageView; // Field for insurance image path

    @FXML
    private Button save; // Save button

    @FXML
    private VBox fieldsContainer; // Reference to the VBox containing dynamic fields

    @FXML
    private ComboBox<InsuranceCategory> categoryComboBox;

    @FXML
    private ComboBox<police> policeComboBox;

    private ArrayList<String> dynamicFieldValues = new ArrayList<>();

    private InsuranceCatService insuranceCatService;
    private InsuranceService insuranceService;
    private policeService policeService;
    Insurance insurance = new Insurance();
    private String path;

    private static final Logger logger = Logger.getLogger(AddInsurance.class.getName());
    private UserService userService = new UserService();
    public AddInsurance() {
        this.insuranceCatService = new InsuranceCatService();
        this.insuranceService = new InsuranceService();
        this.policeService = new policeService();
    }

    @FXML
    private void addDynamicField() {
        // Create a new TextField for the dynamic field
        TextField newTextField = new TextField();
        newTextField.setPrefHeight(34.0);
        newTextField.setPrefWidth(229.0);
        newTextField.setPromptText("Enter another field");

        // Apply the same style classes as the existing fields
        newTextField.getStyleClass().addAll("search", "shadow");

        // Create a new HBox to hold the dynamic field
        HBox newHBox = new HBox();
        newHBox.setSpacing(10.0);
        newHBox.getChildren().add(newTextField);

        // Add the new HBox with the dynamic field to the fieldsContainer VBox
        fieldsContainer.getChildren().add(newHBox);

        // Add event listener to dynamically added TextField
        newTextField.setOnAction(event -> {
            // When Enter key is pressed in the TextField, add its value to the ArrayList
            String value = newTextField.getText();
            dynamicFieldValues.add(value);
            // Optionally, you can perform any additional actions here
        });
    }

    @FXML
    private void initialize() {

        populateCategoryComboBox();
        populatePoliceComboBox();
    }

    private void populateCategoryComboBox() {
        try {
            List<InsuranceCategory> categories = insuranceCatService.getAll();
            categoryComboBox.getItems().addAll(categories);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while populating category combo box", e);
        }
    }

    private void populatePoliceComboBox() {
        try {
            List<police> policeList = policeService.getAll();
            policeComboBox.getItems().addAll(policeList);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while populating police combo box", e);
        }
    }
    private ArrayList<String> getDynamicFieldValues() {

        ArrayList<String> dynamicFields = new ArrayList<>();
        for (Node node : fieldsContainer.getChildren()) {
            if (node instanceof HBox) {
                HBox hBox = (HBox) node;
                for (Node childNode : hBox.getChildren()) {
                    if (childNode instanceof TextField) {
                        TextField textField = (TextField) childNode;
                        String fieldValue = textField.getText().trim();
                        dynamicFields.add(fieldValue);
                    }
                }
            }
        }
        return dynamicFields;
    }
    @FXML
    void addInsuranceAction(ActionEvent event) {
        try {
            if (isInputValid()) {
                String insuranceName = nameins.getText();
                float insuranceAmount = Float.parseFloat(amount.getText());
                String insuranceImageUrl = path;
                InsuranceCategory selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();
                police selectedPolice = policeComboBox.getSelectionModel().getSelectedItem();
                ArrayList<String> dynamicFields = getDynamicFieldValues();
                insurance = new Insurance(insuranceName, insuranceAmount, insuranceImageUrl, dynamicFields, selectedCategory, selectedPolice);
                InsuranceService is= new InsuranceService();
                is.addInsurance(insurance);


                CommandeService cs=new CommandeService();
                List<Commande> allCommandes = cs.getAllCommandes();
                Set<String> userEmails = new HashSet<>();

                // Map to store users and their commandes
                Map<String, List<Commande>> userCommandesMap = new HashMap<>();

                for (Commande com : allCommandes) {
                    String userEmail = userService.getUserEmailById(com.getUser_id().getId());
                    userEmails.add(userEmail);
                    userCommandesMap.computeIfAbsent(userEmail, k -> new ArrayList<>()).add(com);
                }

                if (!userEmails.isEmpty()) {
                    ExecutorService executor = Executors.newFixedThreadPool(10); // Adjust thread pool size as needed
                    for (String email : userEmails) {
                        executor.submit(() -> {
                            List<Commande> userSpecificCommandes = userCommandesMap.get(email);
                            MailServiceIns.sendConfirmationEmail(email, insurance, userSpecificCommandes);
                        });
                    }
                    executor.shutdown();
                } else {
                    showAlert("Warning", "No users found for the insurance.");
                }


                // Get user IDs associated with the insurance

            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while adding insurance", e);
            showAlert("Error", "An error occurred while adding insurance");
        }
    }






    @FXML
    public void chooseImageAction(ActionEvent actionEvent) {
        try {
            // Create a FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );

            // Show the file chooser dialog
            Stage stage = (Stage) save.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            // Check if a file was selected
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                path = selectedFile.getAbsolutePath();
                insuranceImageView.setImage(image);
                insuranceImageView.setFitWidth(50);
                insuranceImageView.setFitHeight(50);
                insuranceImageView.setPreserveRatio(true);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while choosing image", e);
            showAlert("Error", "An error occurred while choosing image: " + e.getMessage());
        }
    }

    private boolean isInputValid() {
        try {
            String insuranceName = nameins.getText().trim();
            String amountText = amount.getText().trim();

            // Check if insurance name is empty
            if (insuranceName.isEmpty()) {
                showAlert("Error", "Please enter the insurance name.");
                return false;
            }

            // Check if amount is empty
            if (amountText.isEmpty()) {
                showAlert("Error", "Please enter the insurance amount.");
                return false;
            }

            // Check if amount is a valid number
            float insuranceAmount = Float.parseFloat(amountText);
            if (insuranceAmount <= 0) {
                showAlert("Error", "Please enter a valid insurance amount greater than zero.");
                return false;
            }

            // Check if an insurance category is selected
            if (categoryComboBox.getSelectionModel().isEmpty()) {
                showAlert("Error", "Please select an insurance category.");
                return false;
            }

            // Check if a police is selected
            if (policeComboBox.getSelectionModel().isEmpty()) {
                showAlert("Error", "Please select a police.");
                return false;
            }

            for (Node node : fieldsContainer.getChildren()) {
                if (node instanceof HBox) {
                    HBox hBox = (HBox) node;
                    for (Node childNode : hBox.getChildren()) {
                        if (childNode instanceof TextField) {
                            TextField textField = (TextField) childNode;
                            String fieldValue = textField.getText().trim();
                            if (fieldValue.isEmpty()) {
                                showAlert("Error", "Please enter a value for all dynamic fields.");
                                return false;
                            }
                        }
                    }
                }
            }

            return true;
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for the insurance amount.");
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
