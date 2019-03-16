import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    public void StartButtonPressed(ActionEvent actionEvent) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        Scene game= new Scene(root);

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        stage.setScene(game);
        stage.show();

    }

    public void QuitButtonPressed() {
        System.exit(0);
    }

}
