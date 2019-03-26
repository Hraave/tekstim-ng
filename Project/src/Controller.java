import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
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
    GridPane inventoryPane;

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

    ////

    @FXML
    HBox hBox;
    @FXML
    Region region1;
    @FXML
    Region region2;

    @FXML
    ImageView leftHandImage;
    @FXML
    ImageView rightHandImage;
    @FXML
    ImageView helmetImage;
    @FXML
    ImageView armorImage;
    @FXML
    ImageView bootsImage;

    @FXML
    Label strengthLabel;
    @FXML
    Label perceptionLabel;
    @FXML
    Label enduranceLabel;
    @FXML
    Label charismaLabel;
    @FXML
    Label intelligenceLabel;
    @FXML
    Label agilityLabel;
    @FXML
    Label luckLabel;

    public void initialize() {
        instance = this;

        Game game = new Game();
        game.Start();

        DisplayStats();

        imagePane.managedProperty().bind(imagePane.visibleProperty());
        monsterPane.managedProperty().bind(monsterPane.visibleProperty());

        imagePane.setVisible(false);
        monsterPane.setVisible(false);
        inventoryPane.setVisible(false);

        doorRight.setVisible(false);
        doorLeft.setVisible(false);
        doorForward.setVisible(false);

        inventoryPane.setBackground(Background.EMPTY);
        String style = "-fx-background-color: rgba(255, 255, 255, 0.5);";
        inventoryPane.setStyle(style);

        //inventoryPane.setPrefSize(50 * Inventory.columns, 50 * Inventory.rows);
        //inventoryPane.setMinSize(50 * Inventory.columns, 50 * Inventory.rows);

        for (int i = 0; i < Inventory.columns; i++) {
            ColumnConstraints column = new ColumnConstraints(50);
            inventoryPane.getColumnConstraints().add(column);
        }
        for (int i = 0; i < Inventory.rows; i++) {
            RowConstraints row = new RowConstraints(50);
            inventoryPane.getRowConstraints().add(row);
        }

        hBox.setHgrow(region1, Priority.ALWAYS);
        hBox.setHgrow(region2, Priority.ALWAYS);

    }

    public void DisplayStats() {

        Player player = Player.instance;
        playerHealthLabel.setText(String.valueOf(player.health));
        playerDamageLabel.setText(String.valueOf(player.GetEquippedWeapon().damage));
        playerGoldLabel.setText(String.valueOf(player.gold));
        xpLabel.setText(player.xp + "/" + player.requiredXP);
        levelLabel.setText("Level: " + player.level);
        levelProgressBar.setProgress((player.xp * 100f / player.requiredXP) / 100f);
        //manaLabel.setText(Player.instance.mana + "/" + Player.instance.maxMana);
        //manaBar.setProgress((Player.instance.mana * 100f / Player.instance.maxMana) / 100f);
        strengthLabel.setText(String.valueOf(player.strength));
        perceptionLabel.setText(String.valueOf(player.perception));
        enduranceLabel.setText(String.valueOf(player.endurance));
        charismaLabel.setText(String.valueOf(player.charisma));
        intelligenceLabel.setText(String.valueOf(player.intelligence));
        agilityLabel.setText(String.valueOf(player.agility));
        luckLabel.setText(String.valueOf(player.luck));

    }

    public void HeroPowerButton() {

        Player.instance.UseHeroPower();

    }

    public void ToggleInventory() {
        inventoryPane.getChildren().clear();
        if (inventoryPane.isVisible()) {
            inventoryPane.setVisible(false);
            return;
        }

        inventoryPane.setVisible(true);

        int column = 0;
        int row = 0;
        for (Item item : Player.instance.inventory.items) {

            String name = item.name;
            if (item instanceof Weapon) {
                name = item.name + "\nDamage: " + ((Weapon) item).damage + "\nCrit chance: " + ((Weapon) item).critChance;
            }

            Button invButton = new Button();
            invButton.setPrefWidth(50);
            invButton.setPrefHeight(50);

            ImageView buttonImage = new ImageView();
            buttonImage.setFitWidth(50);
            buttonImage.setFitHeight(50);
            SetImage(buttonImage, "inventory/weapons/" + item.name.replaceAll(" ", "_").toLowerCase() + ".png");
            invButton.setGraphic(buttonImage);

            Tooltip tooltip = new Tooltip(name);

            invButton.setOnMouseMoved(action -> {
                tooltip.show(invButton, action.getSceneX() + 30, action.getSceneY() + 30);
            });
            invButton.setOnMouseExited(action -> {
                tooltip.hide();
            });

            invButton.setOnAction(action -> item.Use());

            ContextMenu contextMenu = new ContextMenu();

            MenuItem use = new MenuItem("Use");
            MenuItem delete = new MenuItem("Delete");
            use.setOnAction(action -> {
                item.Use();
            });
            delete.setOnAction(action -> {
                Player.instance.inventory.Remove(item);
            });

            if (item instanceof Weapon) {
                contextMenu.getItems().add(use);
            }
            if (!(item instanceof Key)) {
                contextMenu.getItems().add(delete);
            }

            invButton.setOnContextMenuRequested(action -> {
                contextMenu.show(invButton, action.getScreenX(), action.getScreenY());
            });

            inventoryPane.add(invButton, column, row);

            column++;

            if (column == Inventory.rows) {
                row++;
                column = 0;
            }

        }

    }

    public void SetSlotImage(Item item) {
        String name = item.name.replaceAll(" ", "_").toLowerCase() + ".png";

        if (item instanceof Weapon) {
            SetImage(rightHandImage, "inventory/weapons/" + name);
        } else if (item instanceof Armor) {
            if (((Armor) item).type == Armor.Type.Helmet) {
                SetImage(helmetImage, "inventory/armor/" + name);
            } else if (((Armor) item).type == Armor.Type.Chestplate) {
                SetImage(armorImage, "inventory/armor/" + name);
            } else if (((Armor) item).type == Armor.Type.Boots) {
                SetImage(bootsImage, "inventory/armor/" + name);
            }
        } else if (item instanceof Shield) {
            SetImage(leftHandImage, "inventory/shields/" + name);
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
        //System.exit(0);
        Player.instance.inventory.Add(new Weapon("Silver Sword", 1, 1));
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
