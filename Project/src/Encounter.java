public class Encounter {

    public void Init() {

        int random = RNG.RandomInRange(2, 2);
        switch (random) {
            case 1: MonsterEncounter();
                break;
            case 2: DungeonEncounter();
                break;
            case 3: FindingWeapon();
                break;
        }

    }

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

}
