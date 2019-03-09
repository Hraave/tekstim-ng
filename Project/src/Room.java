import java.util.ArrayList;
import java.util.List;

public class Room {

    public int x;
    public int y;

    private List<Monster> monsters = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public Room(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void Generate() {

        ////////////////// Generate monsters //////////////////
        int numberOfMonsters = RNG.RandomInRange(0, 5);

        for (int i = 0; i < numberOfMonsters; i++) {

            Monster monster = new Monster();
            monster.GenerateStats();
            monsters.add(monster);

        }

        ////////////////// Generate items //////////////////

        if (RNG.PercentageChance(10)) {



        }

    }

    public void Enter() {

        ////////////////// Description //////////////////

        System.out.println("You enter the room.");

        ////////////////// Monsters //////////////////

        System.out.println("There are " + monsters.size() + " monsters in this room");

        for (int i = 0; i < monsters.size(); i++) {
            CombatManager.Battle(monsters.get(i));
        }

        //////////////////////////////////////////////////////

        RoomEncounters.GetRandom();

    }

}
