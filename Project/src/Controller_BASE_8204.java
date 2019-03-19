import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Controller {

    public static Controller instance;

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

    //// Monster ////
    @FXML
    Pane monsterPane;
    @FXML
    ImageView monsterImage;
    @FXML
    Label monsterDamage;
    @FXML
    Label monsterHealth;

    //// Dungeon ////
    @FXML
    Pane dungeonPane;
    @FXML
    ImageView dungeonRoom;
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

        //StartBattle(new Monster("Voidwalker", 1, 3));

        SetImage(dungeonRoom, "sprites/dungeon/room.png");
        SetImage(doorForward, "sprites/dungeon/door_forward.png");
        SetImage(doorLeft, "sprites/dungeon/door_forward.png");
        SetImage(doorRight, "sprites/dungeon/door_forward.png");

        dungeonPane.setVisible(false);
        doorRight.setVisible(false);
        doorLeft.setVisible(false);
        doorForward.setVisible(false);

    }

    public void DisplayStats() {

        String playerClass = Player.instance.Class.toString();
        SetImage(playerImage, "sprites/heroes/" + playerClass.toLowerCase() + ".png");




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

    ///////////////////////////////////////////////////// Dungeon /////////////////////////////////////////////////////

    public void EnterRoom() {

        dungeonPane.setVisible(true);

    }

    public void DisplayDoor(Dungeon.Direction direction) {

        if (direction == Dungeon.Direction.RIGHT) {
            doorRight.setVisible(true);
        } else if (direction == Dungeon.Direction.LEFT) {
            doorLeft.setVisible(true);
        } else if (direction == Dungeon.Direction.UP) {
            doorForward.setVisible(true);
        }

    }

    public void ExitRoom() {

        dungeonPane.setVisible(false);

        doorRight.setVisible(false);
        doorLeft.setVisible(false);
        doorForward.setVisible(false);

    }

    ////////////////////////////////////////////////// Monster Battle //////////////////////////////////////////////////

    public void StartBattle(Monster monster) {

        monsterPane.setVisible(true);

        SetImage(monsterImage, "sprites/monsters/" +  monster.name.replaceAll(" ", "_").toLowerCase() + ".png");
        monsterDamage.setText(String.valueOf(monster.damage));
        monsterHealth.setText(String.valueOf(monster.health));

    }

    public void EndBattle() {

        monsterPane.setVisible(false);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void SetImage(ImageView imageView, String filePath) {
        try {

            FileInputStream inputStream = new FileInputStream(filePath);
            Image image = new Image(inputStream);
            imageView.setImage(image);

        } catch (FileNotFoundException e) {
            System.out.println("An image is missing");
            e.printStackTrace();
        }
    }

}
