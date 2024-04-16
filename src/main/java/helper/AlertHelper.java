package helper;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
public class AlertHelper {

    public static boolean result = false;

    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        if (alertType == Alert.AlertType.INFORMATION) {
            Notifications.create()
                    .darkStyle()
                    .title(title)
                    .text(message)
                    .hideAfter(Duration.seconds(10))
                    .showInformation();
        } else if (alertType == Alert.AlertType.ERROR) {
            Notifications.create()
                    .darkStyle()
                    .title(title)
                    .text(message)
                    .hideAfter(Duration.seconds(10))
                    .showError();
        }
    }
}