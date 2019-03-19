import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Controller {

    public static Controller instance;

    @FXML
    AnchorPane screenPane;

    //// Choices ////
    @FXML
    Label promptLabel;
    @FXML
    HBox choiceBox;

    //// Player ////
    @FXML
    ImageView playerImage;
    @FXML
    Label playerHealthLabel;
    @FXML
    Label playerDamageLabel;
    @FXML
    Label playerGoldLabel;

    //// Image ////
    @FXML
    Pane imagePane;
    @FXML
    ImageView image;

    //// Monster ////
    @FXML
    Pane monsterPane;
    @FXML
    ImageView monsterImage;
    @FXML
    Label monsterName;
    @FXML
    Label monsterDamage;
    @FXML
    Label monsterHealth;

    //// Dungeon ////
    @FXML
    ImageView doorForward;
    @FXML
    ImageView doorLeft;
    @FXML
    ImageView doorRight;

    public void initialize() {
        instance = this;

        Game game = new Game();
        game.Start();

        DisplayStats();

        imagePane.managedProperty().bind(imagePane.visibleProperty());
        monsterPane.managedProperty().bind(monsterPane.visibleProperty());

        imagePane.setVisible(false);
        monsterPane.setVisible(false);

        doorRight.setVisible(false);
        doorLeft.setVisible(false);
        doorForward.setVisible(false);





        /*
        try {

            FileInputStream inputStream = new FileInputStream("src/resources/sprites/dungeon/room.png");
            Image image = new Image(inputStream);
            BackgroundImage myBI = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            screenPane.setBackground(new Background(myBI));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */

    }

    public void DisplayStats() {

        String playerClass = Player.instance.playerClass.toString();
        SetImage(playerImage, "heroes/" + playerClass.toLowerCase() + ".png");

        String playerClass = Player.instance.Class.toString();
        SetImage(playerImage, "sprites/heroes/" + playerClass.toLowerCase() + ".png");
        playerHealthLabel.setText(Player.instance.health);
        playerDamageLabel.setText(Player.instance.GetEquippedWeapon.damage();
        playerGoldLabel.setText(Player.instance.gold);

    }

    public void DisplayChoices(Choice root) {

        choiceBox.getChildren().clear();

        promptLabel.setText(root.text);
        for (Choice choice : root.choices) {

            Button choiceButton = new Button(choice.text);
            choiceButton.setOnAction(action -> root.MakeSelection(choice));
            choiceBox.getChildren().add(choiceButton);

        }

    }

    public void QuitButtonPressed() {
        System.exit(0);
    }

    public void DisplayImage(String path) {

        imagePane.setVisible(true);
        SetImage(image, path);

    }

    ///////////////////////////////////////////////////// Dungeon /////////////////////////////////////////////////////

    public void EnterRoom() {

        imagePane.setVisible(true);
        SetImage(image, "dungeon/room.png");

        SetImage(doorRight, "dungeon/door_forward.png");
        SetImage(doorLeft, "dungeon/door_forward.png");
        SetImage(doorForward, "dungeon/door_forward.png");

    }

    public void DisplayDoor(Dungeon.Direction direction, boolean isBossDoor) {

        if (direction == Dungeon.Direction.RIGHT) {
            doorRight.setVisible(true);
            if (isBossDoor) {
                SetImage(doorRight, "dungeon/bossdoor_forward.png");
            }
        } else if (direction == Dungeon.Direction.LEFT) {
            doorLeft.setVisible(true);
            if (isBossDoor) {
                SetImage(doorLeft, "dungeon/bossdoor_forward.png");
            }
        } else if (direction == Dungeon.Direction.UP) {
            doorForward.setVisible(true);
            if (isBossDoor) {
                SetImage(doorForward, "dungeon/bossdoor_forward.png");
            }
        }

    }

    public void ExitRoom() {

        imagePane.setVisible(false);

        doorRight.setVisible(false);
        doorLeft.setVisible(false);
        doorForward.setVisible(false);

    }

    ////////////////////////////////////////////////// Monster Battle //////////////////////////////////////////////////

    public void StartBattle(Monster monster) {

        monsterPane.setVisible(true);

        SetImage(monsterImage, "monsters/" +  monster.name.replaceAll(" ", "_").toLowerCase() + ".png");
        monsterName.setText(monster.name);
        UpdateMonsterStats(monster);

    }

    public void UpdateMonsterStats(Monster monster) {

        monsterDamage.setText(String.valueOf(monster.damage));
        monsterHealth.setText(String.valueOf(monster.health));

    }

    public void EndBattle() {

        monsterPane.setVisible(false);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void SetImage(ImageView imageView, String filePath) {
        try {

            FileInputStream inputStream = new FileInputStream("src/resources/sprites/" + filePath);
            Image image = new Image(inputStream);
            imageView.setImage(image);

        } catch (FileNotFoundException e) {
            System.out.println("An image is missing");
            e.printStackTrace();
        }
    }

}
