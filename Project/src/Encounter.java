import java.util.Random;

public class Encounter {

    public enum Type { PATH, DUNGEON }

    public enum Biome {
        Grass,
        Forest,
        Snow
    }

    public static Biome currentBiome = Biome.Grass;

    private static final int pathEncounterAmount = 3;
    private static final int dungeonEncounterAmount = 1;

    private Type type;
    private int encounterNumber;

    public void GenerateRandom(Type type) {

        this.type = type;
        int random = 0;
        if (type == Type.PATH) {
            random = RNG.RandomInRange(1, pathEncounterAmount);
        } else if (type == Type.DUNGEON) {
            random = RNG.RandomInRange(1, dungeonEncounterAmount);
        }
        encounterNumber = random;

    }

    public void Call() {

        if (type == Type.PATH) {

            switch (encounterNumber) {
                case 1: MonsterEncounter();
                    break;
                case 2: DungeonEncounter();
                    break;
                case 3: FindingWeapon();
                    break;
            }

        } else if (type == Type.DUNGEON) {

            switch (encounterNumber) {
                case 1: FindingWeapon();
                    break;
            }

        }

    }

    ///////////////////////////////////////////////////// PATH ENCOUNTERS /////////////////////////////////////////////////////

    private void MonsterEncounter() {

        Monster monster = MonsterFactory.GetRandomMonster();
        CombatManager.StartBattle(monster);

    }

    private void DungeonEncounter() {

        Dungeon dungeon = new Dungeon();
        dungeon.Generate();

        Choice root = new Choice("You come across " + dungeon.type.toString().replaceAll("_", " "));
        Choice enter = root.AddChoice("Enter");
        Choice leave = root.AddChoice("Leave");

        enter.SetAction(() -> {
            System.out.println("Entered dungeon");
            dungeon.Enter();
        });

        root.SetImage("dungeon/" + dungeon.type.toString().toLowerCase() + ".png");
        root.Display();

    }

    private void FindingWeapon() {

        Weapon weapon = new Weapon("Silver Sword", 3, 30);
        //Weapon weapon = new Weapon();
        weapon.GenerateRandomStats();

        Choice root = new Choice("You find a " + weapon.name + "\nDamage: " + weapon.damage);
        Choice take = root.AddChoice("Take");
        Choice leave = root.AddChoice("Leave");

        take.SetAction(() -> {
            System.out.println("You picked up the " + weapon.name);
            Player.instance.inventory.Add(weapon);
            Game.instance.NewEncounter();
        });

        //root.SetImage("weapons/silver_sword.png");
        root.SetImage("weapons/" + weapon.name.replaceAll(" ", "_").toLowerCase() + ".png");
        root.Display();

    }

    private void DiscoverBiome() {

        Random random = new Random();
        Biome biome = Biome.values()[random.nextInt(Biome.values().length)];

        String biomeText = "";
        if (biome == Biome.Grass) {
            biomeText = "the grasslands";
        } else if (biome == Biome.Forest) {
            biomeText = "the dark forest";
        } else if (biome == Biome.Snow) {
            biomeText = "the snow area";
        }

        Choice root = new Choice("You find your way out of " + biomeText);
        Choice enter = root.AddChoice("Enter " + biomeText);
        Choice leave = root.AddChoice("Return");

        enter.SetAction(() -> {
            currentBiome = biome;
        });

        root.SetImage(biome.toString() + ".png");
        root.Display();

    }

    ///////////////////////////////////////////////////// DUNGEON ENCOUNTERS /////////////////////////////////////////////////////

    private void Chest() {

        Choice root = new Choice("There is a chest in the middle of the room");
        Choice open = root.AddChoice("Open");
        Choice leave = root.AddChoice("Leave");

        open.SetAction(() -> {
            if (RNG.PercentageChance(50)) {

                System.out.println("A monster comes out of it!");
                MonsterEncounter();

            } else {
                System.out.println("It's empty");
            }
        });

        root.Display();

    }

}
