package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Entities.Police;
import Services.PoliceService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AffichagePolice implements Initializable {
    @FXML
    private ListView<Police> policeList;
    @FXML
    private VBox contentArea;
    @FXML
    private TextField searchField;
    private int currentPage = 0;
    private int itemsPerPage = 3;
    // Adjust this based on your preference
    @FXML
    private Button prevPageBtn; // Pagination previous page button
    @FXML
    private Button nextPageBtn;
    @FXML
    private HBox paginationContainer; // Container for pagination controls
    private int totalItems;




    private PoliceService policeService = new PoliceService(); // Assuming you have a service class to handle database operations
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        totalItems = policeService.getAll().size();
        initializeListView();
        loadPoliceData();
        setupPaginationControls();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                rechercherPol();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    private void setupPaginationControls() {
        paginationContainer.getChildren().clear(); // Clear existing controls

        // Add the Previous button
        prevPageBtn.setDisable(currentPage == 0);
        paginationContainer.getChildren().add(prevPageBtn);

        // Calculate the number of pages
        int pageCount = (int) Math.ceil((double) totalItems / itemsPerPage);

        // Add page number buttons dynamically
        for (int i = 0; i < pageCount; i++) {
            Button pageBtn = new Button(String.valueOf(i + 1));
            int finalI = i;
            pageBtn.setOnAction(e -> {
                currentPage = finalI;
                setupPaginationControls(); // Refresh pagination controls on click
                updateListView();
            });
            pageBtn.setDisable(currentPage == i); // Disable the button of the current page
            paginationContainer.getChildren().add(pageBtn);
        }

        // Add the Next button
        nextPageBtn.setDisable(currentPage >= pageCount - 1);
        paginationContainer.getChildren().add(nextPageBtn);
    }


    private void updateListView() {
        int start = currentPage * itemsPerPage;
        int end = Math.min(start + itemsPerPage, totalItems);
        List<Police> sublist = policeService.getAll().subList(start, end); // Consider caching the full list
        policeList.setItems(FXCollections.observableArrayList(sublist));
    }
    @FXML
    private void handleNextPage() {
        if (currentPage < (totalItems / itemsPerPage)) {
            currentPage++;
            setupPaginationControls();
            updateListView();
        }
    }

    @FXML
    private void handlePrevPage() {
        if (currentPage > 0) {
            currentPage--;
            setupPaginationControls();
            updateListView();
        }
    }

    @FXML
    private void rechercherPol() throws SQLException {
        String searchTerm = searchField.getText().trim().toLowerCase();
        List<Police> filteredList = policeService.getAll().stream()
                .filter(police -> police.getPoliceName().toLowerCase().contains(searchTerm)
                        || police.getDescriptionPolice().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
        totalItems = filteredList.size();
        currentPage = 0; // Reset to the first page for new search
        updateFilteredListView(filteredList);
    }
    private void updateFilteredListView(List<Police> filteredList) {
        int start = currentPage * itemsPerPage;
        int end = Math.min(start + itemsPerPage, filteredList.size());
        policeList.setItems(FXCollections.observableArrayList(filteredList.subList(start, end)));

        prevPageBtn.setDisable(currentPage <= 0);
        nextPageBtn.setDisable(end >= filteredList.size());
    }

    private void initializeListView() {
        policeList.setCellFactory(param -> new ListCell<Police>() {
            @Override
            protected void updateItem(Police police, boolean empty) {
                super.updateItem(police, empty);
                if (empty || police == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10);
                    hbox.setAlignment(Pos.CENTER);
                    Label nameLabel = new Label(police.getPoliceName());
                    nameLabel.setMinWidth(185.0);
                    nameLabel.setMaxWidth(185.0);
                    Label descriptionLabel = new Label(police.getDescriptionPolice());
                    descriptionLabel.setMinWidth(173.0);
                    descriptionLabel.setMaxWidth(173.0);
                    descriptionLabel.setWrapText(true); // Activer le retour à la ligne automatique
                    descriptionLabel.setStyle("-fx-padding: 5px;");
                    Label sinistreNameLabel = new Label(police.getSinistre() != null ? police.getSinistre().getSin_name() : "No Sinistre");
                    sinistreNameLabel.setMinWidth(167.0);
                    sinistreNameLabel.setMaxWidth(167.0);

                    // Configuration des boutons avec icônes et texte
                    ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/tn/esprit/applicatiopnpi/edit_icon.png")));
                    editIcon.setFitHeight(20); // Taille de l'icône
                    editIcon.setFitWidth(20);
                    Button editButton = new Button("Edit", editIcon);
                    editButton.getStyleClass().add("button2");

                    ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/tn/esprit/applicatiopnpi/delet_icon.png")));
                    deleteIcon.setFitHeight(20); // Taille de l'icône
                    deleteIcon.setFitWidth(20);
                    Button deleteButton = new Button("Delete", deleteIcon);
                    deleteButton.getStyleClass().add("button2");

                    HBox actionBox = new HBox(editButton, deleteButton);
                    actionBox.setSpacing(10);
                    actionBox.setMinWidth(150.0);
                    actionBox.setMaxWidth(150.0);

                    editButton.setOnAction(e -> openDialog(police));
                    deleteButton.setOnAction(e -> {
                        policeService.supprimer(police.getId());
                        policeList.getItems().remove(police);
                    });

                    hbox.getChildren().addAll(nameLabel, descriptionLabel, sinistreNameLabel,actionBox);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void loadPoliceData() {
        ObservableList<Police> data = FXCollections.observableArrayList(policeService.getAll());
        policeList.setItems(data);
    }

    private void openDialog(Police police) {
        // Open a dialog or another stage to edit Police
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/EditPolice.fxml"));
            Parent root = loader.load();
            EditPolice controller = loader.getController();
            controller.intData(police);
            controller.setUpdateCallback(this::updatePoliceInListView);  // Utilisez la bonne méthode de callback

            Stage stage = new Stage();
            stage.setTitle("Edit Police");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updatePoliceInListView(Police updatedPolice) {
        // Mettre à jour un sinistre spécifique dans la ListView
        ObservableList<Police> polices = policeList.getItems();
        int index = polices.indexOf(updatedPolice);
        if (index != -1) {
            polices.set(index, updatedPolice);
            policeList.refresh();
        }
    }




    @FXML
    private void handleAddNew(MouseEvent event) {
        try {
            // Charger le fichier FXML pour ajouter un sinistre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicatiopnpi/addPolice.fxml"));
            Node eventFXML = loader.load();

            if (contentArea != null) {
                contentArea.getChildren().clear(); // Nettoyer le contenu existant
                contentArea.getChildren().add(eventFXML); // Ajouter le contenu chargé
            } else {
                System.out.println("Erreur : contentArea est null, vérifiez votre fichier FXML.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
