import java.util.ArrayList;
import java.util.List;

public class Room {

    public int x;
    public int y;
    public Encounter encounter;
    public boolean hasBeenVisited;
    public boolean isBossRoom;

    private List<Monster> monsters = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    protected Dungeon dungeon;

    public Room(int x, int y, Dungeon dungeon) {
        this.x = x;
        this.y = y;
        this.dungeon = dungeon;
    }

    public void Generate() {

        ////////////////// Generate monsters //////////////////

        //Icecrown citadel first room encounter
        if (dungeon.type == Dungeon.Type.Icecrown_Citadel && x == 0 && y == 0) {
            Monster monster = MonsterFactory.GetMonster("Lord Marrowgar");
            monsters.add(monster);
            return;
        }
        ///////////////////////////////////////

        int numberOfMonsters = RNG.RandomInRange(0, 0);

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
                System.out.println("Room startbattle called");
                CombatManager.StartBattle(monsters.get(i), this);
            }

            if (monsters.size() == 0) {
                hasBeenVisited = true;
                Enter();
            }

        }

        if (hasBeenVisited) {
            encounter.Call();
            dungeon.PromptToMove();
        }

    }

}
