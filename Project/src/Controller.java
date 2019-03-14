import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    public static Controller INSTANCE;

    @FXML
    Label playerHealthLabel;
    @FXML
    Label playerDamageLabel;
    @FXML
    Label playerGoldLabel;

    public Controller() {
        INSTANCE = this;
    }
}

