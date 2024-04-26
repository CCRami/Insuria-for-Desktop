package controller;
import controller.CreationListCell;
import entity.Reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ReclamationService;

import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class listeReclamationBack implements Initializable {

    @FXML
    private VBox contentArea;

    @FXML
    private ListView<Reclamation> recList;

    @FXML
    private TextField searchField;

    private ReclamationService service = new ReclamationService();

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
        recList.setCellFactory(param -> new ListCell<Reclamation>() {
            @Override
            protected void updateItem(Reclamation rec, boolean empty) {
                super.updateItem(rec, empty);
                if (empty || rec == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(0);
                    hbox.setAlignment(Pos.BASELINE_LEFT);
                    Label libelleLabel = new Label(rec.getLibelle());
                    libelleLabel.setMinWidth(185.0);
                    libelleLabel.setMaxWidth(185.0);
                    Label datReclamationLabel = new Label(rec.getDateReclamation().toString());
                    datReclamationLabel.setMinWidth(173.0);
                    datReclamationLabel.setMaxWidth(173.0);
                    datReclamationLabel.setStyle("-fx-padding: 2px;");
                    Label dateSinistreLabel = new Label(rec.getDateSinitre().toString());
                    dateSinistreLabel.setMinWidth(173.0);
                    dateSinistreLabel.setMaxWidth(173.0);
                    dateSinistreLabel.setStyle("-fx-padding: 2px;");
                    Label reponseLabel = new Label(rec.getReponse());
                    reponseLabel.setMinWidth(185.0);
                    reponseLabel.setMaxWidth(185.0);

                    Button button;
                    if (rec.getReponse().equals("refused") || rec.getReponse().equals("accepted")) {
                        button = new Button("Button 1");
                    } else {
                        button = new Button("Button 2");
                    }
                    button.setOnAction(event -> {
                        if (button.getText().equals("Button 1")) {


                        } else if (button.getText().equals("Button 2")) {
                            // Action pour Button 2
                            System.out.println("Button 2 clicked!");
                            // Autres actions pour Button 2
                        }
                    });
                    hbox.getChildren().addAll(libelleLabel, datReclamationLabel, dateSinistreLabel, reponseLabel, button);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void loadRecData() throws SQLException {
        ObservableList<Reclamation> data = FXCollections.observableArrayList(service.afficherReclamations());
        recList.setItems(data);
    }

    public void refreshList() {
    }
}