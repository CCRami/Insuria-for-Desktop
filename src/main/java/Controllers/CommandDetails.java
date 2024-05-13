package Controllers;

import Entities.Commande;
import Services.CommandeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CommandDetails {
    @FXML
    private Label montantLabel;

    @FXML
    private Label dateEffetLabel;

    @FXML
    private Label dateExpLabel;

    @FXML
    private VBox doaDetailsContainer;

    @FXML
    private Label insname;

    @FXML
    private AnchorPane contentArea;

    private Commande currentCommande;
    private Scene scene;

    public void initialize() {
        // Initialize your controller
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private void reloadScene() {
        if (scene != null) {
            Stage stage = (Stage) scene.getWindow();
            stage.setScene(scene);
        } else {
            System.out.println("Error: Scene is null.");
        }
    }

    @FXML
    private void openModifyPopup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditCommande.fxml"));
            Parent root = loader.load();

            System.out.println("Opening modify popup for Commande: " + currentCommande);

            EditCommande controller = loader.getController();
            controller.setCommandeData(currentCommande);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modify Commande");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // After the popup is closed, reload the updated data
            CommandeService commandeService = new CommandeService();
            Commande updatedCommande = commandeService.getCommandeById(currentCommande.getId());
            initData(updatedCommande);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initData(Commande commande) {
        this.currentCommande = commande;
        montantLabel.setText(String.valueOf(commande.getMontant()));
        dateEffetLabel.setText(commande.getDate_effet() != null ? commande.getDate_effet().toString() : "N/A");
        dateExpLabel.setText(commande.getDate_exp() != null ? commande.getDate_exp().toString() : "N/A");
        insname.setText(commande.getDoa_com_id().getName_ins());

        doaDetailsContainer.getChildren().clear();

        String doaJson = commande.getDoa_full().toString();
        if (doaJson != null && !doaJson.isEmpty()) {
            String[] doaDetails = doaJson.replace("[", "").replace("]", "").split(",");
            for (String detail : doaDetails) {
                String[] keyValue = detail.split(":");
                String key = keyValue[0].trim().replaceAll("\"", "");
                String value = keyValue[1].trim().replaceAll("\"", "");
                Label label = new Label(key + ": " + value);
                label.setTextFill(Color.color(0, 0, 0));
                label.setFont(new Font("System", 20));
                doaDetailsContainer.getChildren().add(label);
            }
        } else {
            System.out.println("No DOA details available.");
        }
    }

    @FXML
    private void viewSelectedInsurances(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommandeBasket.fxml"));
            Node eventFXML = loader.load();
            if (contentArea != null) {
                contentArea.getChildren().clear();
                contentArea.getChildren().add(eventFXML);
            } else {
                System.out.println("Error: contentArea is null, check your FXML file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}