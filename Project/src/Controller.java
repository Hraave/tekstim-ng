import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
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
    HBox bottomBox;

    @FXML
    GridPane pane2;

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

        screenPane.setBackground(GetBackground("road.png"));
        bottomBox.setBackground(GetBackground("background2.png"));


        Pane pane = new Pane();
        pane.setBackground(GetBackground("background2.png"));
        screenPane.getChildren().add(pane);
        pane.setMinHeight(540);
        pane.setPrefHeight(540);
        pane.setMaxHeight(540);
        pane.setMinWidth(400);
        pane.setPrefWidth(400);
        pane.setMaxWidth(400);
        pane.setLayoutX(Screen.getPrimary().getBounds().getWidth() / 2 - 200);
        pane.setLayoutY(Screen.getPrimary().getBounds().getHeight() - 540);
        pane.toBack();



        for (int i = 0; i < Inventory.columns; i++) {
            ColumnConstraints column = new ColumnConstraints(50);
            inventoryPane.getColumnConstraints().add(column);
            pane2.getColumnConstraints().add(column);
        }
        for (int i = 0; i < Inventory.rows; i++) {
            RowConstraints row = new RowConstraints(50);
            inventoryPane.getRowConstraints().add(row);
            pane2.getRowConstraints().add(row);
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

    public void ToggleInventory() {
        if (inventoryPane.isVisible()) {
            inventoryPane.setVisible(false);
            return;
        }
        inventoryPane.getChildren().clear();

        Player player = Player.instance;

        inventoryPane.setVisible(true);

        int column = 0;
        int row = 0;
        for (Item item : player.inventory.items) {

            ImageView image = new ImageView();
            image.setFitWidth(50);
            image.setFitHeight(50);

            String path = "";
            if (item instanceof Weapon) {
                path = "weapons/";
            } else if (item instanceof Shield) {
                path = "shields/";
            } else if (item instanceof Armor) {
                if (((Armor) item).type == Armor.Type.Helmet) {
                    path = "helmets/";
                } else if (((Armor) item).type == Armor.Type.Chestplate) {
                    path = "chestplates/";
                } else if (((Armor) item).type == Armor.Type.Boots) {
                    path = "boots/";
                }
            } else if (item instanceof Potion) {
                path = "potion/";
            }
            SetImage(image, "inventory/" + path + item.name.replaceAll(" ", "_").toLowerCase() + ".png");

            CreateItemTooltip(image, item);

            ContextMenu contextMenu = new ContextMenu();

            MenuItem use = new MenuItem("Use");
            MenuItem delete = new MenuItem("Delete");
            use.setOnAction(action -> {
                if (item instanceof Weapon) {
                    if (!player.GetEquippedWeapon().name.equals("Fists")) {
                        player.inventory.Add(player.GetEquippedWeapon());
                    }
                } else if (item instanceof Shield) {
                    if (player.shield != null) {
                        player.inventory.Add(player.shield);
                    }
                } else if (item instanceof Armor) {
                    if (((Armor) item).type == Armor.Type.Helmet) {
                        if (player.helmet != null) {
                            player.inventory.Add(player.helmet);
                        }
                    } else if (((Armor) item).type == Armor.Type.Chestplate) {
                        if (player.chestplate != null) {
                            player.inventory.Add(player.chestplate);
                        }
                    } else if (((Armor) item).type == Armor.Type.Boots) {
                        if (player.boots != null) {
                            player.inventory.Add(player.boots);
                        }
                    }
                }

                item.Use();
            });
            delete.setOnAction(action -> {
                Player.instance.inventory.Remove(item);
            });

            if (item instanceof Weapon || item instanceof Shield || item instanceof Armor || item instanceof Potion) {
                contextMenu.getItems().add(use);
            }
            if (!(item instanceof Key)) {
                contextMenu.getItems().add(delete);
            }


            image.setOnContextMenuRequested(action -> {
                contextMenu.show(image, action.getScreenX(), action.getScreenY());
            });

            inventoryPane.add(image, column, row);

            column++;

            if (column == Inventory.rows) {
                row++;
                column = 0;
            }

        }

    }

    public void SetSlotImage(Item item) {
        String name = item.name.replaceAll(" ", "_").toLowerCase() + ".png";

        Sound.PlaySound("equip");

        if (item instanceof Weapon) {

            SetImage(rightHandImage, "inventory/weapons/" + name);
            CreateItemTooltip(rightHandImage, item);

            MenuItem unequipButton = AddUnequipButton(rightHandImage);
            unequipButton.setOnAction(action -> {
                Player.instance.SetEquippedWeapon(null);
                Player.instance.inventory.Add(item);
                rightHandImage.setImage(null);
                rightHandImage.setOnMouseMoved(null);
                rightHandImage.setOnMouseExited(null);
                rightHandImage.setOnContextMenuRequested(null);
                SetImage(rightHandImage, "slot.png");
            });

        } else if (item instanceof Armor) {
            if (((Armor) item).type == Armor.Type.Helmet) {

                SetImage(helmetImage, "inventory/helmets/" + name);
                CreateItemTooltip(helmetImage, item);

                MenuItem unequipButton = AddUnequipButton(helmetImage);
                unequipButton.setOnAction(action -> {
                    Player.instance.helmet = null;
                    Player.instance.inventory.Add(item);
                    helmetImage.setImage(null);
                    helmetImage.setOnMouseMoved(null);
                    helmetImage.setOnMouseExited(null);
                    helmetImage.setOnContextMenuRequested(null);
                    SetImage(helmetImage, "slot.png");
                });

            } else if (((Armor) item).type == Armor.Type.Chestplate) {

                SetImage(armorImage, "inventory/chestplates/" + name);
                CreateItemTooltip(armorImage, item);

                MenuItem unequipButton = AddUnequipButton(armorImage);
                unequipButton.setOnAction(action -> {
                    Player.instance.chestplate = null;
                    Player.instance.inventory.Add(item);
                    armorImage.setImage(null);
                    armorImage.setOnMouseMoved(null);
                    armorImage.setOnMouseExited(null);
                    armorImage.setOnContextMenuRequested(null);
                    SetImage(armorImage, "slot.png");
                });

            } else if (((Armor) item).type == Armor.Type.Boots) {

                SetImage(bootsImage, "inventory/boots/" + name);
                CreateItemTooltip(bootsImage, item);

                MenuItem unequipButton = AddUnequipButton(bootsImage);
                unequipButton.setOnAction(action -> {
                    Player.instance.boots = null;
                    Player.instance.inventory.Add(item);
                    bootsImage.setImage(null);
                    bootsImage.setOnMouseMoved(null);
                    bootsImage.setOnMouseExited(null);
                    bootsImage.setOnContextMenuRequested(null);
                    SetImage(bootsImage, "slot.png");
                });

            }
        } else if (item instanceof Shield) {

            SetImage(leftHandImage, "inventory/shields/" + name);
            CreateItemTooltip(leftHandImage, item);

            MenuItem unequipButton = AddUnequipButton(leftHandImage);
            unequipButton.setOnAction(action -> {
                Player.instance.shield = null;
                Player.instance.inventory.Add(item);
                leftHandImage.setImage(null);
                leftHandImage.setOnMouseMoved(null);
                leftHandImage.setOnMouseExited(null);
                leftHandImage.setOnContextMenuRequested(null);
                SetImage(leftHandImage, "slot.png");
            });

        }

    }

    private void CreateItemTooltip(Node node, Item item) {

        String name = item.name;
        if (item instanceof Weapon) {
            name = item.name + "\nDamage: " + ((Weapon) item).damage + "\nCrit Chance: " + ((Weapon) item).critChance;
        } else if (item instanceof Shield) {
            name = item.name + "\nBlock Chance: " + ((Shield) item).blockChance;
        } else if (item instanceof Armor) {
            name = item.name + "\nProtection: " + ((Armor) item).protection;
        } else if (item instanceof Potion) {
            name = item.name + "\nHeals 5 HP";
        }

        Tooltip tooltip = new Tooltip(name);

        node.setOnMouseMoved(action -> {
            tooltip.show(node, action.getSceneX() + 30, action.getSceneY() + 30);
        });
        node.setOnMouseExited(action -> {
            tooltip.hide();
        });
    }

    private MenuItem AddUnequipButton(Node node) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem unequip = new MenuItem("Unequip");

        contextMenu.getItems().add(unequip);

        node.setOnContextMenuRequested(action -> {
            contextMenu.show(node, action.getScreenX(), action.getScreenY());
        });
        return unequip;
    }

    public void PlayerDeath() {

        Sound.PlaySound("hero_portrait_crack");

        PauseTransition pauseTransition = Pause(1);
        pauseTransition.setOnFinished(a -> {
            Sound.PlaySound("hero_portrait_explode");

            screenPane.getChildren().clear();
            Sound.PlaySound("You Died");
            screenPane.setBackground(GetBackground("death.png"));
            PauseTransition pauseTransition2 = Pause(5);
            pauseTransition2.setOnFinished(e -> {
                System.exit(0);
            });

        });
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
                Sound.PlaySound("choice_click");
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

    public void DisplayBackground(String path) {
        screenPane.setBackground(GetBackground(path));
    }

    public void HideImage() {
        imagePane.setVisible(false);
    }

    ///////////////////////////////////////////////////// Dungeon /////////////////////////////////////////////////////

    public void EnterRoom() {

        screenPane.setBackground(GetBackground("background.png"));

        imagePane.setVisible(true);
        SetImage(image, "dungeon/room.png");

        SetImage(doorRight, "dungeon/door_right.png");
        SetImage(doorLeft, "dungeon/door_left.png");
        SetImage(doorForward, "dungeon/door_forward.png");

    }

    public void DisplayDoor(Dungeon.Direction direction, boolean isBossDoor) {

        if (direction == Dungeon.Direction.RIGHT) {
            doorRight.setVisible(true);
            if (isBossDoor) {
                SetImage(doorRight, "dungeon/bossdoor_right.png");
            }
        } else if (direction == Dungeon.Direction.LEFT) {
            doorLeft.setVisible(true);
            if (isBossDoor) {
                SetImage(doorLeft, "dungeon/bossdoor_left.png");
            }
        } else if (direction == Dungeon.Direction.UP) {
            doorForward.setVisible(true);
            if (isBossDoor) {
                SetImage(doorForward, "dungeon/bossdoor_forward.png");
            }
        }

    }

    public void ExitRoom() {

        screenPane.setBackground(GetBackground("road.png"));

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

    private TranslateTransition PlayAnimation(Node node, int x, int y, float duration) {

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(duration));

        transition.setToX(x);
        transition.setToY(y);
        transition.setNode(node);
        transition.play();

        return transition;

    }

    private PauseTransition Pause(float duration) {

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(duration));

        pauseTransition.play();

        return pauseTransition;

    }

    public void PlayerAttackAnimation() {

        DisableChoiceButtons(true);

        TranslateTransition transition = PlayAnimation(playerPane, 0, -300, 0.1f);
        transition.setOnFinished(e -> {
            TranslateTransition transition2 = PlayAnimation(playerPane, 0, 0, 0.5f);
            transition2.setOnFinished(a -> {
                if (CombatManager.monster.isAlive) {
                    PauseTransition pauseTransition = Pause(0.3f);
                    pauseTransition.setOnFinished(o -> {
                        //CombatManager.AttackPlayer();
                        CombatManager.BattleLoop();
                        MonsterAttackAnimation();
                    });
                }
            });
        });

    }

    public void MonsterAppearAnimation() {

        DisableChoiceButtons(true);

        monsterPane.setTranslateY(monsterPane.getTranslateY()-300);

        TranslateTransition transition = PlayAnimation(monsterPane, 0, 0, 1f);
        transition.setOnFinished(a -> {
            CombatManager.BattleLoop();
            DisableChoiceButtons(false);
        });

    }

    public void MonsterAttackAnimation() {

        DisableChoiceButtons(true);

        Sound.PlaySound("monsters/" + CombatManager.monster.name + "/attack");
        PauseTransition pauseTransition = Pause(0.5f);
        pauseTransition.setOnFinished(a -> {

            TranslateTransition transition = PlayAnimation(monsterPane, 0, 300, 0.1f);
            transition.setOnFinished(e -> {
                TranslateTransition transition2 = PlayAnimation(monsterPane, 0, 0, 0.5f);
                CombatManager.AttackPlayer();
                transition2.setOnFinished(o -> {
                    if (Player.instance.isAlive) {
                        DisableChoiceButtons(false);
                    }
                });
            });

        });

    }

    private void DisableChoiceButtons(boolean a) {
        for (Node node : choiceBox.getChildren()) {
            node.setDisable(a);
            node.setVisible(!a);
        }
    }

    public void DisplayMessage(String text, String sound) {
        Sound.PlaySound(sound);

        Label label = new Label(text);
        label.setTextFill(Color.rgb(250, 0, 0));
        label.setFont(new Font(25));
        label.setStyle("-fx-font-weight: bold");
        playerPane.getChildren().add(label);
        label.setLayoutX(playerPane.getWidth() / 2);
        label.setLayoutX(playerPane.getHeight() / 2);

        PauseTransition pauseTransition = Pause(2);
        pauseTransition.setOnFinished(a -> {
            playerPane.getChildren().remove(label);
        });

    }

    public void ShieldBlock() {
        DisplayMessage("Blocked", "block");

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

    private Background GetBackground(String filePath) {
        try {
            FileInputStream inputStream = new FileInputStream("src/resources/sprites/" + filePath);
            Image image = new Image(inputStream);
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
            BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
            return new Background(background);
        } catch (FileNotFoundException e) {
            System.out.println("Background image is missing");
            e.printStackTrace();
        }
        return null;
    }

}
