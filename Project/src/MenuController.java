import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    VBox menuBox;
    @FXML
    VBox classSelectionBox;
    @FXML
    TextField nameField;

    public void initialize() {
        menuBox.managedProperty().bind(menuBox.visibleProperty());
        classSelectionBox.managedProperty().bind(classSelectionBox.visibleProperty());
        ShowClassSelectionMenu(false);
    }

    public void PlayButtonPressed() {
        ShowClassSelectionMenu(true);
    }

    public void PlayButtonPressed2(ActionEvent actionEvent) throws Exception {

        Player player = new Player(nameField.getText(), 30, Player.Class.Mage);

        //////////// Switch to game scene ////////////
        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        Scene game = new Scene(root);

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        stage.setScene(game);
        //stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        //stage.setFullScreen(true);
        stage.show();

    }

    private void ShowClassSelectionMenu(boolean b) {
        menuBox.setVisible(!b);
        //menuBox.setManaged(!b);
        classSelectionBox.setVisible(b);
        //classSelectionBox.setManaged(b);
    }

    public void QuitButtonPressed() {
        System.exit(0);
    }

}
