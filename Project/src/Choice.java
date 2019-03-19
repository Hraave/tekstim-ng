import java.util.ArrayList;
import java.util.List;

public class Choice {

    public String text;
    public List<Choice> choices = new ArrayList<>();

    private Runnable action;

    public Choice(String text) {
        this.text = text;
    }

    public Choice AddChoice(String text) {
        Choice choice = new Choice(text);
        choices.add(choice);
        return choice;
    }

    public void AddChoice(Choice choice) {
        choices.add(choice);
    }

    public void SetAction(Runnable action) {
        this.action = action;
    }

    public void SetImage(String path) {
        Controller.instance.DisplayImage(path);
    }

    public void Display() {
        Controller.instance.DisplayChoices(this);
    }

    public void MakeSelection(Choice choice) {
        Controller.instance.HideImage();
        if (choice.action != null) {
            choice.action.run();
        } else {
            Game.instance.NewEncounter();
        }
    }

}
