package edu.esprit.Helper;

import java.awt.*;

import javafx.scene.control.Alert;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
public class AlertHelper {

    public static boolean result = false;

    public static void showAlert(Alert.AlertType alertType, SystemColor owner, String title, String message) {
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