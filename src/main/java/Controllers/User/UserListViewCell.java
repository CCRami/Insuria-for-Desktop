package Controllers.User;

import Entities.User;
import Services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    @FXML
    private Button deletebtn;
    @FXML
    private Button updatebtn;
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
            label7.setText(user.isVerified() == 1 ? "Verified" : "Not Verified");
            label8.setText(user.isBlocked() == 1 ? "Blocked" : "Not Blocked");
            label9.setText(user.getRole().equals("[\"ROLE_ADMIN\"]") ? "Admin" : "User");

            updatebtn.setOnAction(event -> {
                try {
                    showedituser(user);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            deletebtn.setOnAction(event -> {
                UserService userService = new UserService();
                userService.delete(user);
                ListView<User> listView = (ListView<User>) getListView();
                listView.getItems().remove(user);
                listView.refresh();
            });
            setText(null);
            setGraphic(gridPane);
        }
    }
    public void showedituser(User user) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditUser.fxml"));
            Parent root = loader.load();
            EditUserController controller = loader.getController();
            controller.initData(user);
            //controller.setUpdateCallback(UserListController::updateUserlistview);  // Utilisez la bonne méthode de callback

            Stage stage = new Stage();
            stage.setTitle("Edit User");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            ListView<User> listView = (ListView<User>) getListView();
            listView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
