import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuController {

    @FXML
    HBox backgroundHBox;
    @FXML
    Button playButton;
    @FXML
    Button quitButton;

    @FXML
    VBox menuBox;
    @FXML
    VBox classSelectionBox;
    @FXML
    TextField nameField;

    //// Stats ////
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

    @FXML
    Label pointsLabel;


    int points = 5;

    int strength = 5;
    int perception = 5;
    int endurance = 5;
    int charisma = 5;
    int intelligence = 5;
    int agility = 5;
    int luck = 5;

    public void initialize() {
        menuBox.managedProperty().bind(menuBox.visibleProperty());
        classSelectionBox.managedProperty().bind(classSelectionBox.visibleProperty());
        ShowClassSelectionMenu(false);
        RefreshLabels();



        backgroundHBox.setBackground(new Background(GetBackground("background.png")));

        classSelectionBox.setBackground(new Background(GetBackground("background2.png")));

        //playButton.setBackground(new Background(GetBackground("button.png")));
    }

    private BackgroundImage GetBackground(String path) {
        try {
            FileInputStream inputStream = new FileInputStream("src/resources/sprites/" + path);
            Image image = new Image(inputStream);
            return new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void PlayButtonPressed() {
        Sound.PlaySound("click", false);
        ShowClassSelectionMenu(true);
    }

    public void PlayButtonPressed2(ActionEvent actionEvent) throws Exception {
        Sound.PlaySound("click", false);

        if (points != 0) {
            System.out.println("You have not used up all your points");
            return;
        }

        Player player = new Player(nameField.getText(), strength, perception, endurance, charisma, intelligence, agility, luck);

        //////////// Switch to game scene ////////////
        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        Scene game = new Scene(root);

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        stage.setScene(game);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreen(true);
        stage.show();

    }

    private void ShowClassSelectionMenu(boolean b) {
        menuBox.setVisible(!b);
        classSelectionBox.setVisible(b);
    }

    public void QuitButtonPressed() {
        Sound.PlaySound("click", false);
        System.exit(0);
    }

    public void IncreaseStrength() {
        IncreaseAttribute("strength");
    }
    public void IncreasePerception() {
        IncreaseAttribute("perception");
    }
    public void IncreaseEndurance() {
        IncreaseAttribute("endurance");
    }
    public void IncreaseCharisma() {
        IncreaseAttribute("charisma");
    }
    public void IncreaseIntelligence() {
        IncreaseAttribute("intelligence");
    }
    public void IncreaseAgility() {
        IncreaseAttribute("agility");
    }
    public void IncreaseLuck() {
        IncreaseAttribute("luck");
    }

    public void DecreaseStrength() {
        DecreaseAttribute("strength");
    }
    public void DecreasePerception() {
        DecreaseAttribute("perception");
    }
    public void DecreaseEndurance() {
        DecreaseAttribute("endurance");
    }
    public void DecreaseCharisma() {
        DecreaseAttribute("charisma");
    }
    public void DecreaseIntelligence() {
        DecreaseAttribute("intelligence");
    }
    public void DecreaseAgility() {
        DecreaseAttribute("agility");
    }
    public void DecreaseLuck() {
        DecreaseAttribute("luck");
    }

    private void IncreaseAttribute(String attribute) {

        if (points == 0) {
            return;
        }

        if (attribute.equals("strength")) {
            if (strength != 10) {
                strength++;
                points--;
            }
        } else if (attribute.equals("perception")) {
            if (perception != 10) {
                perception++;
                points--;
            }
        } else if (attribute.equals("endurance")) {
            if (endurance != 10) {
                endurance++;
                points--;
            }
        } else if (attribute.equals("charisma")) {
            if (charisma != 10) {
                charisma++;
                points--;
            }
        } else if (attribute.equals("intelligence")) {
            if (intelligence != 10) {
                intelligence++;
                points--;
            }
        } else if (attribute.equals("agility")) {
            if (agility != 10) {
                agility++;
                points--;
            }
        } else if (attribute.equals("luck")) {
            if (luck != 10) {
                luck++;
                points--;
            }
        }
        RefreshLabels();
    }

    private void DecreaseAttribute(String attribute) {
        if (attribute.equals("strength")) {
            if (strength != 1) {
                strength--;
                points++;
            }
        } else if (attribute.equals("perception")) {
            if (perception != 1) {
                perception--;
                points++;
            }
        } else if (attribute.equals("endurance")) {
            if (endurance != 1) {
                endurance--;
                points++;
            }
        } else if (attribute.equals("charisma")) {
            if (charisma != 1) {
                charisma--;
                points++;
            }
        } else if (attribute.equals("intelligence")) {
            if (intelligence != 1) {
                intelligence--;
                points++;
            }
        } else if (attribute.equals("agility")) {
            if (agility != 1) {
                agility--;
                points++;
            }
        } else if (attribute.equals("luck")) {
            if (luck != 1) {
                luck--;
                points++;
            }
        }
        RefreshLabels();
    }

    private void RefreshLabels() {
        strengthLabel.setText(String.valueOf(strength));
        perceptionLabel.setText(String.valueOf(perception));
        enduranceLabel.setText(String.valueOf(endurance));
        charismaLabel.setText(String.valueOf(charisma));
        intelligenceLabel.setText(String.valueOf(intelligence));
        agilityLabel.setText(String.valueOf(agility));
        luckLabel.setText(String.valueOf(luck));

        pointsLabel.setText(String.valueOf(points));
    }

}
