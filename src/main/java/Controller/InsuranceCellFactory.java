package Controller;

import Entity.Insurance;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.ArrayList;

public class InsuranceCellFactory implements Callback<javafx.scene.control.ListView<Insurance>, ListCell<Insurance>> {

    @Override
    public ListCell<Insurance> call(ListView<Insurance> listView) {
        return new ListCell<>() {
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
                    if (doaList != null) {
                        for (String doa : doaList) {
                            displayText += doa + ", ";
                        }
                    }

                    // Handle InsuranceCategory and police
                    displayText += item.getCatins_id() + " - " + item.getPol_id();

                    // Set the text to display in the ListView
                    setText(displayText);
                }
            }

        };


}}
