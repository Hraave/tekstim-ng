public class Game {

    public Map map;
    public boolean isRunning;

    public static Game instance;

    public Game() {
        instance = this;
    }

    public void DisplayStats(Player player) {
        System.out.println("| " + player.name + "\n| Health: " + player.health + "/" + player.maxHealth + "\n| Armor: " + player.armor + "\n----------------------------");
    }

    public void Start() {

        System.out.println("Enter your name:");
        Player player = new Player(Main.scanner.nextLine());
        System.out.println("Welcome, " + player.name + ".");

        map = new Map();

        isRunning = true;
        while (isRunning) {

            DisplayStats(player);
            System.out.println("1. Follow the road");
            int input = Main.scanner.nextInt();

            DisplayStats(player);
            Encounter encounter = new Encounter();
            encounter.Init();

        }

    }

}
