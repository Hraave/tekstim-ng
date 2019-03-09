import java.util.ArrayList;
import java.util.List;

public class Choice {

    public String text;
    public List<Choice> choices = new ArrayList<>();

    public Choice(String text) {
        this.text = text;
    }

    public Choice AddChoice(String text) {
        Choice choice = new Choice(text);
        choices.add(choice);
        return choice;
    }

    public Choice GetSelection() {

        System.out.println(text);

        for (int i = 0; i < choices.size(); i++) {
            System.out.println(i + 1 + ". " + choices.get(i).text);
        }

        /*
        if (canGoBack) {
            System.out.println(choices.size() + 1 + ". Back");
        }
        */

        int input = Main.scanner.nextInt() - 1;

        Choice choice = choices.get(input);
        //choice.GetSelection();

        System.out.println("////////////////////////////////////" + choice.text);

        return choice;

    }

}
