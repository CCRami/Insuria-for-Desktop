package Controllers.User;

import Entities.User;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserListController implements Initializable {

    @FXML
    public ListView<User> listView;

    private ObservableList<User> allUsers;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;
    private final int itemsPerPage = 7;
    private int currentPageIndex = 0;

    public UserListController() {
        UserService userService = new UserService();
        List<User> users = userService.displayAll();
        allUsers = FXCollections.observableArrayList(users);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPage(0);
    }

    @FXML
    private void previousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            loadPage(currentPageIndex);
            updatePaginationButtons();
        }
    }

    @FXML
    private void nextPage() {
        int lastPageIndex = getLastPageIndex();
        if (currentPageIndex < lastPageIndex) {
            currentPageIndex++;
            loadPage(currentPageIndex);
            updatePaginationButtons();
        }
    }
    public void loadPage(int pageIndex) {
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, allUsers.size());
        ObservableList<User> pageUsers = FXCollections.observableArrayList(allUsers.subList(fromIndex, toIndex));
        listView.setItems(pageUsers);
        listView.setCellFactory(userListView -> new UserListViewCell(pageIndex, itemsPerPage));
    }

    private int getLastPageIndex() {
        return (int) Math.ceil((double) allUsers.size() / itemsPerPage) - 1;
    }

    private void updatePaginationButtons() {
        previousButton.setDisable(currentPageIndex == 0);
        nextButton.setDisable(currentPageIndex == getLastPageIndex());
    }
    public void updateUserlistview(User updatedUser) {
        // Mettre à jour un sinistre spécifique dans la ListView
        ObservableList<User> Users = listView.getItems();
        int index = Users.indexOf(updatedUser);
        if (index != -1) {
            Users.set(index, updatedUser);
            listView.refresh();
        }
    }

}
