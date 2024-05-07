package Controllers;

import Entities.Indemnissation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import Services.IndemnisationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class indemnisationsBack implements Initializable {

    @FXML
    private ListView<Indemnissation> ComList;

    @FXML
    private HBox compensation;

    @FXML
    private HBox reclamations;

    @FXML
    private TextField searchField;

    @FXML
    private AnchorPane anchor;

    IndemnisationService indemnisationService=new IndemnisationService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListView();
        try {
            loadRecData();

        } catch (SQLException e) {
            e.printStackTrace();
        }
      }
    private void initializeListView() {
        ComList.setCellFactory(param -> new ListCell<Indemnissation>() {
            @Override
            protected void updateItem(Indemnissation rec, boolean empty) {
                super.updateItem(rec, empty);
                if (empty || rec == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(0);
                    hbox.setAlignment(Pos.BASELINE_LEFT);
                    Label AmountLabel = new Label();

                    AmountLabel.setText(String.format("%.2f", rec.getMontant()));

                    AmountLabel.setMinWidth(170.0);
                    AmountLabel.setMaxWidth(170.0);
                    Label dateLabel = new Label(rec.getDate());
                    dateLabel.setMinWidth(160.0);
                    dateLabel.setMaxWidth(160.0);
                    dateLabel.setStyle("-fx-padding: 2px;");
                    Label noteLabel = new Label(rec.getBeneficitaire());
                    noteLabel.setMinWidth(200.0);
                    noteLabel.setMaxWidth(200.0);
                    noteLabel.setStyle("-fx-padding: 2px;");


                    Button button = new Button("Show ");

                    button.setMinWidth(150.0);
                    button.setMaxWidth(150.0);
                    button.setOnAction(event -> {


                            Indemnissation ind = getItem();


                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/showComBack.fxml"));
                                Node root = loader.load();
                                showCompensation controller = loader.getController();
                                controller.initData(ind);
                                anchor.getChildren().clear();
                                anchor.getChildren().add(root);
                            } catch (IOException e) {
                                e.printStackTrace();
                                // Gérer l'erreur si nécessaire
                            }

                        }
                    );
                    hbox.getChildren().addAll(AmountLabel, dateLabel, noteLabel, button);
                    setGraphic(hbox);
                }
            }
        });
    }
    private void loadRecData() throws SQLException {
        ObservableList<Indemnissation> data = FXCollections.observableArrayList(indemnisationService.afficherIndemnisation());
        ComList.setItems(data);
    }

    @FXML
    void showCompensations(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/indemnisationsBack.fxml"));
            Parent root = loader.load();

            // Utilisez votre objet Stage pour afficher la nouvelle interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermez la fenêtre actuelle
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showReclamations(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listReclamationBack.fxml"));
            Parent root = loader.load();

            // Utilisez votre objet Stage pour afficher la nouvelle interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermez la fenêtre actuelle
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
