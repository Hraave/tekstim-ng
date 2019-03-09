import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Menu();

    }

    private static void Menu() {

        System.out.println("1. Start Game\n2. Exit");
        int input = scanner.nextInt();

        if (input == 1) {

            Game game = new Game();
            game.Start();

        } else if (input == 2) {

            System.exit(0);

        }

    }

}
