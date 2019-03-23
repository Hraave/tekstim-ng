import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

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
    Pane playerPane;
    @FXML
    ImageView playerImage;
    @FXML
    Label playerHealthLabel;
    @FXML
    Label playerDamageLabel;
    @FXML
    Label playerGoldLabel;
    @FXML
    ProgressBar levelProgressBar;
    @FXML
    Label xpLabel;
    @FXML
    Label levelLabel;
    @FXML
    ProgressBar manaBar;
    @FXML
    Label manaLabel;

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

    }

    public void DisplayStats() {

        playerHealthLabel.setText(String.valueOf(Player.instance.health));
        playerDamageLabel.setText(String.valueOf(Player.instance.GetEquippedWeapon().damage));
        playerGoldLabel.setText(String.valueOf(Player.instance.gold));
        xpLabel.setText(Player.instance.xp + "/" + Player.instance.requiredXP);
        levelLabel.setText("Level: " + Player.instance.level);
        levelProgressBar.setProgress((Player.instance.xp * 100f / Player.instance.requiredXP) / 100f);
        manaLabel.setText(Player.instance.mana + "/" + Player.instance.maxMana);
        manaBar.setProgress((Player.instance.mana * 100f / Player.instance.maxMana) / 100f);

    }

    public void HeroPowerButton() {

        Player.instance.UseHeroPower();

    }

    public void OpenInventory() {

        for (Item item : Player.instance.inventory.items) {
            Button invButton = new Button(item.name);
            invButton.setOnAction(action -> item.Use());
            imagePane.getChildren().add(invButton);
        }

    }

    public void PlayerDeath() {
        screenPane.getChildren().clear();
        SetBackground("death.png");
    }

    public void DisplayChoices(Choice root) {

        choiceBox.getChildren().clear();

        promptLabel.setText(root.text);
        for (Choice choice : root.choices) {

            Button choiceButton = new Button(choice.text);
            choiceButton.setPrefWidth(100);
            choiceButton.setMinWidth(100);
            choiceButton.setPrefHeight(100);
            choiceButton.setMinHeight(100);
            choiceButton.setOnAction(action -> {
                PlayAnimation(choiceButton, 0, 10, 0.1f);
                Sound.PlaySound("click", false);
                root.MakeSelection(choice);
            });
            choiceButton.setOnMouseEntered(action -> {
                PlayAnimation(choiceButton, 0, -10, 0.1f);
            });
            choiceButton.setOnMouseExited(action -> {
                PlayAnimation(choiceButton, 0, 0, 0.1f);
            });
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

    public void HideImage() {
        imagePane.setVisible(false);
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

        MonsterAppearAnimation();

    }

    public void UpdateMonsterStats(Monster monster) {

        monsterDamage.setText(String.valueOf(monster.damage));
        monsterHealth.setText(String.valueOf(monster.health));

    }

    public void EndBattle() {

        monsterPane.setVisible(false);

    }

    //////// Animations ////////

    private TranslateTransition transition;

    private void PlayAnimation(Node node, int x, int y, float duration) {

        transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(duration));

        transition.setToX(x);
        transition.setToY(y);
        transition.setNode(node);
        transition.play();

    }

    public void PlayerAttackAnimation() {

        PlayAnimation(playerPane, 0, -300, 0.1f);
        transition.setOnFinished(e -> {
            PlayAnimation(playerPane, 0, 0, 0.5f);
        });




        //pause here





    }

    public void MonsterAppearAnimation() {

        monsterPane.setTranslateY(monsterPane.getTranslateY()-300);

        PlayAnimation(monsterPane, 0, 0, 1f);

    }

    public void MonsterAttackAnimation() {

        PlayAnimation(monsterPane, 0, 300, 0.1f);
        transition.setOnFinished(e -> {
            PlayAnimation(monsterPane, 0, 0, 0.5f);
        });

    }

    public void MonsterRegenAnimation() {

    }

    public void DeathrattleAnimation() {

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

    private void SetBackground(String filePath) {
        try {

            FileInputStream inputStream = new FileInputStream("src/resources/sprites/" + filePath);
            Image image = new Image(inputStream);
            BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            screenPane.setBackground(new Background(background));

        } catch (FileNotFoundException e) {
            System.out.println("Background image is missing");
            e.printStackTrace();
        }
    }

}
