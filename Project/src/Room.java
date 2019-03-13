import java.util.ArrayList;
import java.util.List;

public class Room {

    public int x;
    public int y;
    public boolean hasBeenVisited;

    private Dungeon dungeon;
    private List<Monster> monsters = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public Room(int x, int y, Dungeon dungeon) {
        this.x = x;
        this.y = y;
        this.dungeon = dungeon;
    }

    public void Generate() {

        ////////////////// Generate monsters //////////////////
        int numberOfMonsters = RNG.RandomInRange(0, 2);

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

        hasBeenVisited = true;

        ////////////////// Description //////////////////

        System.out.println("You enter the room.");

        ////////////////// Monsters //////////////////

        if (!hasBeenVisited) {

            System.out.println("There are " + monsters.size() + " monsters in this room");

            for (int i = 0; i < monsters.size(); i++) {
                CombatManager.Battle(monsters.get(i));
            }

        }

        //////////////////////////////////////////////////////

        if (!hasBeenVisited) {

            Encounter encounter = new Encounter();
            encounter.Init(Encounter.Type.DUNGEON);

        }

        //////////////////////////////////////////////////////

        dungeon.PromptToMove();

    }

}
