import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static boolean gameIsRunning = false;

    public static void main(String[] args) {

        Menu();

    }

    public static void DisplayStats(Player player) {
        System.out.println("| " + player.name + "\n| Health: " + player.health + "/" + player.maxHealth + "\n| Armor: " + player.armor + "\n----------------------------");
    }

    private static void Menu() {

        System.out.println("1. Start Game\n2. Exit");
        int input = scanner.nextInt();

        if (input == 1) {
            StartGame();
        } else if (input == 2) {
            System.exit(0);
        }

    }

    private static void StartGame() {

        System.out.println("Enter your name:");
        Player player = new Player(scanner.nextLine());

        System.out.println("Welcome, " + player.name + ".");

        gameIsRunning = true;
        while (gameIsRunning) {

            DisplayStats(player);
            Encounter encounter = new Encounter();

        }

    }
}
