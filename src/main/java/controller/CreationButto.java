package controller;
import entity.Indemnissation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class CreationButto extends javafx.scene.control.TableCell<Indemnissation, Void> {
    private final Button firstButton = new Button("show");

    public CreationButto() {
        firstButton.setStyle("-fx-background-color: #051B46; -fx-text-fill: white;");
        firstButton.setOnAction(event -> {
            Indemnissation ind = getTableView().getItems().get(getIndex());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/showCompensationBack.fxml"));
                Parent root = loader.load();
                showCompensation controller = loader.getController(); // Assurez-vous que le contrôleur est correctement nommé
                controller.iniData(ind); // Transférez l'indemnisation à l'interface d'affichage
                // Utilisez votre objet Stage pour afficher la nouvelle interface
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                Stage currentStage = (Stage) getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(firstButton);
        }
    }
}
