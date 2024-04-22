package Controllers.User;

import Entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.IOException;

public class UserListViewCell extends ListCell<User> {

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label6;

    @FXML
    private Label label7;

    @FXML
    private Label label8;

    @FXML
    private Label label9;

    @FXML
    private ImageView avatar;

    @FXML
    private GridPane gridPane;

    private int pageIndex;
    private int itemsPerPage;

    public UserListViewCell(int pageIndex, int itemsPerPage) {
        this.pageIndex = pageIndex;
        this.itemsPerPage = itemsPerPage;
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UserCell.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        if (empty || user == null) {
            setText(null);
            setGraphic(null);
        } else {
            label1.setText(String.valueOf(user.getFirst_name()));
            label2.setText(user.getLast_name());
            label3.setText(user.getEmail());
            label4.setText(String.valueOf(user.getPhone_number()));
            if (user.getAvatar() != null) {
                Image image = new Image(user.getAvatar());
                avatar.setImage(image);
                avatar.setFitWidth(100);
            } else {
                Image image = new Image("https://i.imgur.com/x5co7s8.png");
                avatar.setImage(image);
                avatar.setFitWidth(100);
            }
            label6.setText(user.getBirth_date());
            label7.setText(String.valueOf(user.isVerified()));
            label8.setText(String.valueOf(user.isBlocked()));
            label9.setText(user.getRole());
            setText(null);
            setGraphic(gridPane);
        }
    }
}
