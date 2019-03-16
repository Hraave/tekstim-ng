public class CombatManager {

    private static Monster monster;
    private static Room room;

    public static void StartBattle(Monster m) {
        monster = m;
        BattleLoop();
    }

    public static void StartBattle(Monster m, Room r) {
        monster = m;
        room = r;
        BattleLoop();
    }

    private static void BattleLoop() {

        Choice root = new Choice("Battle: " + monster.name + "\nHealth: " + monster.health + "/" + monster.maxHealth);
        Choice attack = root.AddChoice("Attack");

        attack.SetAction(() -> {

            AttackMonster();

            if (!monster.isAlive) {
                if (room != null) {
                    room.hasBeenVisited = true;
                    room.Enter();
                } else {
                    Game.instance.NewEncounter();
                }
                return;
            }

            AttackPlayer();
            BattleLoop();

        });

        root.Display();

    }

    private static void AttackMonster() {

        Weapon weapon = Player.instance.GetEquippedWeapon();

        int damage = weapon.damage;

        if (RNG.PercentageChance(weapon.critChance)) {
            System.out.println("Critical hit! Double damage.");
            damage *= 2;
        }

        monster.TakeDamage(damage);
        System.out.println(monster.name + " took " + damage + " damage");

        if (!monster.isAlive) {
            System.out.println("Monster is dead");
            //Controller.instance.EndBattle();
            Player.instance.GainXP(monster.health * damage / 100);
        }

    }

    private static void AttackPlayer() {

        int damage = monster.damage;

        if (RNG.PercentageChance(2)) {
            System.out.println("Critical hit! Double damage.");
            damage *= 2;
        }

        Player.instance.TakeDamage(damage);

        System.out.println(monster.name + " hit you for " + monster.damage + " damage");

    }

    /*
    private static void TryEscape(Monster monster) {

        if (monster.health * monster.damage > Player.instance.level * Player.instance.health) {

            System.out.println("You ran away!");

        } else {

            System.out.println("Failed to run away - the enemy is too strong!");

        }

    }
    */

}
