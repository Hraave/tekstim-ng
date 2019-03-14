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

    @FXML
    HBox choiceBox;

    @FXML
    ImageView playerImage;
    @FXML
    Label playerHealthLabel;
    @FXML
    Label playerDamageLabel;
    @FXML
    Label playerGoldLabel;

    @FXML
    Pane monsterPane;
    @FXML
    ImageView monsterImage;
    @FXML
    Label monsterDamage;
    @FXML
    Label monsterHealth;

    public void initialize() {
        instance = this;

        DisplayStats();

        DisplayMonster(new Monster("Voidwalker", 1, 3));

    }

    public void DisplayStats() {

        SetImage(playerImage, "sprites/heroes/mage.png");

    }

    public void DisplayMonster(Monster monster) {

        monsterPane.setVisible(true);

        SetImage(monsterImage, "sprites/monsters/" +  monster.name.replaceAll(" ", "_").toLowerCase() + ".png");
        monsterDamage.setText(String.valueOf(monster.damage));
        monsterHealth.setText(String.valueOf(monster.health));

    }

    public void DisplayChoices(Choice root) {

        choiceBox.getChildren().removeAll();

        for (Choice choice : root.choices) {

            Button choiceButton = new Button(choice.text);
            choiceBox.getChildren().add(choiceButton);

        }

    }

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
