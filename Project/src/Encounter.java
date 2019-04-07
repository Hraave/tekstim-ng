import java.util.Random;

public class Encounter {

    public enum Type { PATH, DUNGEON }

    private static final int pathEncounterAmount = 5;
    private static final int dungeonEncounterAmount = 1;

    private int encounterNumber;

    public void GenerateRandom(Type type) {

        int random = 0;
        if (type == Type.PATH) {
            random = RNG.RandomInRange(1, pathEncounterAmount);
        } else if (type == Type.DUNGEON) {
            random = RNG.RandomInRange(1, dungeonEncounterAmount);
        }
        encounterNumber = random;

    }

    public void PathEncounter() {

        switch (encounterNumber) {
            case 1:
                Monster();
                break;
            case 2:
                Dungeon();
                break;
            case 3:
                FindingItem();
                break;
            case 4:
                BearCave();
                break;
            case 5:
                Bonfire();
                break;
            case 6:
                Thief();
                break;
        }

    }

    public Choice DungeonEncounter() {

        switch (encounterNumber) {
            case 1: return Chest();
        }

        return null;
    }

    ///////////////////////////////////////////////////// PATH ENCOUNTERS /////////////////////////////////////////////////////

    private void Monster() {

        Monster monster = MonsterFactory.GetRandomMonster();
        CombatManager.StartBattle(monster);

    }

    private void Dungeon() {

        Dungeon dungeon = new Dungeon();
        dungeon.Generate();

        //Choice root = new Choice("You come across " + dungeon.type.toString().replaceAll("_", " "));
        Choice root = new Choice("You come across a dungeon");
        Choice enter = root.AddChoice("Enter");
        Choice leave = root.AddChoice("Leave");

        enter.SetAction(() -> {
            dungeon.Enter();
        });

        //root.SetImage("dungeon/" + dungeon.type.toString().toLowerCase() + ".png");
        //root.SetImage("dungeon/dungeon.png");
        root.SetBackground("dungeon/dungeon.png");
        root.Display();

    }

    public void FindingItem() {

        Weapon weapon = Stats.GenerateRandomWeapon();
        Shield shield = Stats.GenerateRandomShield();
        Armor armor = Stats.GenerateRandomArmor();
        Potion potion = new Potion("Health Potion");

        Item[] itemList = new Item[] {weapon, shield, armor, potion};

        Random random = new Random();
        Item item = itemList[random.nextInt(itemList.length)];

        Choice root = new Choice("You find a " + item.name);
        Choice take = root.AddChoice("Take");
        Choice leave = root.AddChoice("Leave");

        take.SetAction(() -> {
            System.out.println("You picked up the " + item.name);
            Player.instance.inventory.Add(item);
            Game.instance.NewEncounter();
        });

        String path = "";
        if (item instanceof Weapon) {
            path = "weapons/";
        } else if (item instanceof Shield) {
            path = "shields/";
        } else if (item instanceof Armor) {
            if (((Armor) item).type == Armor.Type.Helmet) {
                path = "helmets/";
            } else if (((Armor) item).type == Armor.Type.Chestplate) {
                path = "chestplates/";
            } else if (((Armor) item).type == Armor.Type.Boots) {
                path = "boots/";
            }
        } else if (item instanceof Potion) {
            path = "potion/";
        }
        root.SetImage("inventory/" + path + item.name.replaceAll(" ", "_").toLowerCase() + ".png");
        root.Display();

    }

    private void BearCave() {

        Choice root = new Choice("You come across a cave");
        Choice enter = root.AddChoice("Enter");
        Choice leave = root.AddChoice("Leave");

        enter.SetAction(() -> {
            if (RNG.PercentageChance(50)) {
                System.out.println("A bear comes out to attack you!");
                Monster monster = MonsterFactory.GetMonster("Ironfur Grizzly");
                CombatManager.StartBattle(monster);
            } else {
                FindingItem();
            }
        });

        root.SetBackground("cave.png");
        root.Display();

    }

    private void Thief() {

        Choice root = new Choice("You see a thieve's guild on the road");
        Choice enter = root.AddChoice("Enter");
        Choice leave = root.AddChoice("Leave");

        enter.SetAction(() -> {
            if (RNG.PercentageChance(50)) {
                System.out.println("A thief comes to attack you!");
                Monster monster = MonsterFactory.GetMonster("Thief");
                CombatManager.StartBattle(monster);
            } else {
                FindingItem();
            }
        });

        root.SetBackground("thief_house.png");
        root.Display();

    }

    private void Bonfire() {

        Choice root = new Choice("You find a bonfire");
        Choice health = root.AddChoice("Rest");
        Choice proceed = health.AddChoice("Continue");
        Choice leave = root.AddChoice("Leave");

        Choice root2 = new Choice("Healed to max health");
        root2.AddChoice("Continue");

        health.SetAction(() -> {
            Player.instance.GainHealth(100);
            Sound.PlaySound("health_potion");

            root2.Display();
        });

        root.SetImage("bonfire.png");
        root.Display();

    }

    ///////////////////////////////////////////////////// DUNGEON ENCOUNTERS /////////////////////////////////////////////////////

    private Choice Chest() {

        Choice openChest = new Choice("Open the chest");
        Choice take = new Choice("Take item");

        openChest.AddChoice(take);

        openChest.SetAction(() -> {
            System.out.println("It's empty");
        });

        return openChest;
    }

}
