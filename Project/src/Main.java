import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static boolean running = false;

    public static void main(String[] args) {

        ////// Programm küsib, kas mängija soovib mängu alustada. //////

        System.out.println("Kas soovite mängu alustada?");
        String input = scanner.nextLine().toLowerCase();
        if (input.equals("jah")) {
            StartGame();
        }

    }

    private static void StartGame() {

        System.out.println("Mis on teie nimi?");
        Player.name = scanner.nextLine();

        System.out.println("Tere tulemast, " + Player.name);

        running = true;
        while (running) {
            System.out.println("| " + Player.name + "\n| Health: " + Player.health + "\n| Armor: " + Player.armor + "\n----------------------------");

            String input = scanner.nextLine();

        }

    }
}
