public class Encounter {

    public enum Type { PATH, DUNGEON }

    public void Init(Type type) {

        if (type == Type.PATH) {

            int random = RNG.RandomInRange(1, 3);
            switch (random) {
                case 1: MonsterEncounter();
                    break;
                case 2: DungeonEncounter();
                    break;
                case 3: FindingWeapon();
                    break;
            }

        } else if (type == Type.DUNGEON) {

            int random = RNG.RandomInRange(1, 3);
            switch (random) {
                case 1: Empty();
                    break;
                case 2: Chest();
                    break;
                case 3: Chest();
                    break;
            }

        }

    }

    ///////////////////////////////////////////////////// PATH ENCOUNTERS /////////////////////////////////////////////////////

    private void MonsterEncounter() {

        Monster monster = new Monster();
        monster.GenerateStats();
        CombatManager.Battle(monster);

    }

    private void DungeonEncounter() {

        Choice root = new Choice("You come across a dungeon entrance");
        Choice enter = root.AddChoice("Enter");
        Choice leave = root.AddChoice("Leave");

        Choice selection = root.GetSelection();

        if (selection == enter) {
            Dungeon dungeon = new Dungeon();
            dungeon.Generate();
            dungeon.Enter();
        }

    }

    private void FindingWeapon() {

        Weapon weapon = new Weapon("weapon", 1, 30);

        Choice root = new Choice("You find a " + weapon.name + "\nDamage: " + weapon.damage);
        Choice take = root.AddChoice("Take it");
        Choice leave = root.AddChoice("Leave it");

        Choice selection = root.GetSelection();

        if (selection == take) {
            //player.inventory.Add(weapon);
            System.out.println("You picked up the " + weapon.name);
        } else if (selection == leave) {
            System.out.println("You left it");
        }

    }

    ///////////////////////////////////////////////////// DUNGEON ENCOUNTERS /////////////////////////////////////////////////////

    private void Empty() {

        Choice root = new Choice("The room is empty");
        Choice proceed = root.AddChoice("Proceed");

    }

    private void Chest() {

        Choice root = new Choice("There is a chest in the middle of the room");
        Choice open = root.AddChoice("Open it");
        Choice leave = root.AddChoice("Leave it");

        Choice selection = root.GetSelection();

        if (selection == open) {

            if (RNG.PercentageChance(50)) {

                System.out.println("A monster comes out of it!");
                Monster monster = new Monster();
                monster.GenerateStats();
                CombatManager.Battle(monster);

            } else {
                System.out.println("It's empty");
            }

        }

    }

}
