import java.util.ArrayList;
import java.util.List;

public class Room {

    private static final int maxNumberOfMonsters = 1;

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

        /*
        Encounter encounter = new Encounter();
        encounter.GenerateRandom(Encounter.Type.DUNGEON);
        this.encounter = encounter;
        */

    }

    public void Enter() {

        Controller.instance.EnterRoom();
        Controller.instance.imagePane.setVisible(false);

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
                Choice root = new Choice("You found the boss key");
                Choice take = root.AddChoice("Take it");
                take.SetAction(() -> {
                    Player.instance.inventory.Add(new Key("Boss Key"));
                    containsBossKey = false;
                    Enter();
                });
                root.SetImage("inventory/boss_key.png");
                root.Display();
                return;
            }

            //Choice choice = encounter.DungeonEncounter();
            //dungeon.PromptToMove(choice);
            dungeon.PromptToMove(null);
        }

    }

}
