import java.util.ArrayList;
import java.util.List;

public class Encounter {

    Player player;
    int input;

    public Encounter(Player player) {

        player = player;

        int random = RNG.RandomInRange(1, 1);
        switch (random) {
            case 1: MonsterEncounter();
                    break;
            case 2: FindingWeapon();
                    break;
        }

    }

    public void MonsterEncounter() {

        Monster monster = new Monster();

        System.out.println(monster.name + " attacks!");
        System.out.println("Weapon: " + player.equippedWeapon.name + "\n1. Attack\n2. Run\n3. Switch weapon");

        input = Main.scanner.nextInt();

        if (input == 1) {

            monster.TakeDamage(player.equippedWeapon.damage);
            System.out.println(monster.name + " took " + player.equippedWeapon.damage + " damage");

        } else if (input == 2) {



        } else if (input == 3) {

            List<Item> items = player.inventory.items;
            List<Weapon> weapons = new ArrayList<>();

            for (int i = 0; i < items.size(); i++) {

                Item item = items.get(i);

                if (item instanceof Weapon) {
                    weapons.add((Weapon)item);
                    System.out.println(i + ". " + item.name);
                }

                if (Main.scanner.hasNextInt()) {
                    input = Main.scanner.nextInt();

                    player.equippedWeapon = weapons.get(input);

                    System.out.println("Equipped " + weapons.get(input));

                }

                System.out.println();

            }

        }

    }

    public void FindingWeapon() {

        Weapon weapon = new Weapon("weapon", 1);

        System.out.println("You find a " + weapon.name + "\nDamage: " + weapon.damage + "\n1. Take it\n2. Leave it");
        input = Main.scanner.nextInt();
        if (input == 1) {
            player.inventory.Add(weapon);
        } else if (input == 2) {

        }

    }

}
