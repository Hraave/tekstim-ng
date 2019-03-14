public class CombatManager {

    public static void Battle(Monster monster) {

        System.out.println(monster.name + " appears!");

        Controller.instance.DisplayMonster(monster);

        while (monster.isAlive) {

            Choice root = new Choice("Battle: " + monster.name + " |||||| Health: " + monster.health + "/" + monster.maxHealth);
            Choice attack = root.AddChoice("Attack");
            Choice run = root.AddChoice("Run");
            Choice selection = root.GetSelection();

            if (selection == attack) {

                AttackMonster(monster);

            } else if (selection == run) {

                TryEscape(monster);

            }

            if (!monster.isAlive) {
                break;
            }

            AttackPlayer(monster);

        }

    }

    private static void AttackMonster(Monster monster) {

        Weapon weapon = Player.instance.equippedWeapon;
        int damage = 1;
        if (weapon != null) {
            damage = weapon.damage;

            if (RNG.PercentageChance(weapon.critChance)) {
                System.out.println("Critical hit! Double damage.");
                damage *= 2;
            }
        }

        monster.TakeDamage(damage);
        System.out.println(monster.name + " took " + damage + " damage");

        if (!monster.isAlive) {
            System.out.println("Monster is dead");
            Player.instance.GainXP(monster.health * damage / 100);
        }

    }

    private static void AttackPlayer(Monster monster) {

        int damage = monster.damage;

        if (RNG.PercentageChance(2)) {
            System.out.println("Critical hit! Double damage.");
            damage *= 2;
        }

        Player.instance.TakeDamage(damage);

        System.out.println(monster.name + " hit you for " + monster.damage + " damage");

    }

    private static void TryEscape(Monster monster) {

        if (monster.health * monster.damage > Player.instance.level * Player.instance.health) {

            System.out.println("You ran away!");

        } else {

            System.out.println("Failed to run away - the enemy is too strong!");

        }

    }

}
