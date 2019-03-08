public class Encounter {

    public Encounter() {

        int random = RNG.RandomInRange(1, 1);
        switch (random) {
            case 1: MonsterEncounter();
                    break;
            case 2: FindingWeapon();
                    break;
        }

    }

    private void MonsterEncounter() {

        Monster monster = new Monster();

        Choice root = new Choice(monster.name + "attacks!");
        Choice attack = root.AddChoice("Attack");
        Choice run = root.AddChoice("Run");

        Choice a1 = run.AddChoice("a1");
        Choice a2 = run.AddChoice("a2");

        Choice selection = root.GetSelection();

        if (selection == run) {
            Choice selection2 = run.GetSelection();
            System.out.println(selection2.text);
        }

        /*
        Choice selection = root.GetSelection();

        if (selection == attack) {
            if (Player.instance.equippedWeapon != null) {
            monster.TakeDamage(Player.instance.equippedWeapon.damage);
            } else {
                monster.TakeDamage(1);
            }
            System.out.println(monster.name + " took " + Player.instance.equippedWeapon.damage + " damage");
        } else if (selection == run) {

        }
        */

    }

    private void FindingWeapon() {

        Weapon weapon = new Weapon("weapon", 1);

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
