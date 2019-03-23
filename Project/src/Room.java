import java.util.ArrayList;
import java.util.List;

public class Room {

    private static final int maxNumberOfMonsters = 0;

    public int x;
    public int y;
    public Encounter encounter;
    public boolean hasBeenVisited;
    public boolean isBossRoom;
    public boolean containsBossKey;

    private List<Monster> monsters = new ArrayList<>();

    protected Dungeon dungeon;

    public Room(int x, int y, Dungeon dungeon) {
        this.x = x;
        this.y = y;
        this.dungeon = dungeon;
    }

    public void Generate() {

        ////////////////// Generate monsters //////////////////

        int numberOfMonsters = RNG.RandomInRange(0, maxNumberOfMonsters);

        for (int i = 0; i < numberOfMonsters; i++) {

            Monster monster = MonsterFactory.GetRandomMonster();
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
                CombatManager.StartBattle(monsters.get(i), this);
            }

            if (monsters.size() == 0) {
                hasBeenVisited = true;
                Enter();
            }

        }
        /////////////////////////////////////////////

        if (hasBeenVisited) {

            if (containsBossKey) {
                System.out.println("You found the boss key!");
                Player.instance.inventory.Add(new Key("Boss Key"));
            }

            encounter.Call();
            dungeon.PromptToMove();
        }

    }

}
