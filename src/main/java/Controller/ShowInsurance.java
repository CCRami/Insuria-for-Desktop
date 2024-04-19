package Controller;

import Entity.Insurance;
import Entity.InsuranceCategory;
import Entity.police;
import Service.InsuranceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShowInsurance {

    @FXML
    private ListView<Insurance> insuranceListView;

    @FXML
    private Button addInsuranceButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private InsuranceService ins = new InsuranceService();

    public void initialize() {
        // Set cell factory for the ListView to display Insurance objects
        insuranceListView.setCellFactory(param -> new ListCell<Insurance>() {
            @Override
            protected void updateItem(Insurance item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getName_ins() == null) {
                    setText(null);
                } else {
                    // Concatenate all the information into a single string
                    String displayText = item.getName_ins() + " - " + item.getMontant() + " - " + item.getIns_image() + " - ";

                    // Handle ArrayList doa
                    ArrayList<String> doaList = item.getDoa();
                    for (String doa : doaList) {
                        displayText += doa + ", ";
                    }

                    // Handle InsuranceCategory and police
                    displayText += item.getCatins_id() + " - " + item.getPol_id();

                    // Set the text to display in the ListView
                    setText(displayText);
                }
            }
        });

        // Populate ListView data
        try {
            loadListViewData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Handle ListView selection
        insuranceListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Insurance selectedInsurance = insuranceListView.getSelectionModel().getSelectedItem();
                // Here you can access the selected insurance attributes and perform any actions
            }
        });
    }

    private void loadListViewData() throws SQLException {
        ObservableList<Insurance> data = FXCollections.observableArrayList(ins.getAllInsurances());
        insuranceListView.setItems(data);
    }

    @FXML
    private void addInsuranceButtonClicked() {
        // Handle add insurance button action here
    }

    @FXML
    private void editButtonClicked() {
        // Handle edit button action here
    }

    @FXML
    private void deleteButtonClicked() {
        // Handle delete button action here
    }
}
