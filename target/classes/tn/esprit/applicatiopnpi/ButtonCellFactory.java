package tn.esprit.applicatiopnpi;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.services.SinistreService;
import javafx.util.Callback;

public class ButtonCellFactory implements Callback<TableColumn<Sinistre, Void>, TableCell<Sinistre, Void>> {
    @Override
    public TableCell<Sinistre, Void> call(final TableColumn<Sinistre, Void> param) {
        return new TableCell<Sinistre, Void>() {
            private final ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/tn/esprit/applicatiopnpi/edit_icon.png")));
            private final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/tn/esprit/applicatiopnpi/delet_icon.png")));
            private final Button editBtn = new Button("", editIcon);
            private final Button deleteBtn = new Button("", deleteIcon);
            private final HBox pane = new HBox(editBtn, deleteBtn);

            {
                pane.setSpacing(10);
                editBtn.setOnAction(event -> {
                    Sinistre sinistre = getTableView().getItems().get(getIndex());
                    // Handler for edit action
                });
                deleteBtn.setOnAction(event -> {
                    Sinistre sinistre = getTableView().getItems().get(getIndex());
                    // Handler for delete action
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        };
    }
}
