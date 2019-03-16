import java.util.ArrayList;
import java.util.List;

public class Room {

    public int x;
    public int y;
    public Encounter encounter;
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
        int numberOfMonsters = RNG.RandomInRange(1, 2);

        for (int i = 0; i < numberOfMonsters; i++) {
            Monster monster = new Monster();
            monster.GenerateStats(dungeon.monsterType);
            monsters.add(monster);

        }

        ////////////////// Generate random encounter //////////////////

        Encounter encounter = new Encounter();
        encounter.GenerateRandom(Encounter.Type.DUNGEON);
        this.encounter = encounter;

    }

    public void Enter() {

        ////////////////// Monsters //////////////////

        if (!hasBeenVisited) {

            System.out.println("There are " + monsters.size() + " monsters in this room");

            for (int i = 0; i < monsters.size(); i++) {
                System.out.println("Room startbattle called");
                CombatManager.StartBattle(monsters.get(i), this);
            }

        }

        if (hasBeenVisited) {
            encounter.Call();
            dungeon.PromptToMove();
        }

    }

}
