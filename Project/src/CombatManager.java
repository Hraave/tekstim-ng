public class CombatManager {

    public static void Battle(Monster monster) {

        System.out.println(monster.name + " appears!");

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

        int damage;
        if (Player.instance.equippedWeapon != null) {
            damage = Player.instance.equippedWeapon.damage;
        } else {
            damage = 1;
        }
        monster.TakeDamage(damage);
        System.out.println(monster.name + " took " + damage + " damage");

        if (!monster.isAlive) {
            System.out.println("Enemy has died");
            Player.instance.GainXP(monster.health * monster.damage / 100);
        }

    }

    private static void AttackPlayer(Monster monster) {

        Player.instance.TakeDamage(monster.damage);

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
