public class CombatManager {

    private static Monster monster;
    private static Room room;

    public static void StartBattle(Monster m) {
        monster = m;
        Controller.instance.StartBattle(m);
        BattleLoop();
    }

    public static void StartBattle(Monster m, Room r) {
        monster = m;
        room = r;
        Controller.instance.StartBattle(m);
        BattleLoop();
    }

    private static void BattleLoop() {

        Choice root = new Choice("Battle: " + monster.name + "\nHealth: " + monster.health + "/" + monster.maxHealth);
        Choice attack = root.AddChoice("Attack");

        attack.SetAction(() -> {

            AttackMonster();

            if (!monster.isAlive) {
                MonsterDeath();
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

        Controller.instance.UpdateMonsterStats(monster);

        if (!monster.isAlive) {
            System.out.println("Monster is dead");
            Controller.instance.EndBattle();
            Player.instance.GainXP(monster.health * damage / 100);
        }

    }

    private static void AttackPlayer() {

        if (monster.ability == Monster.Ability.Regeneration) {
            monster.GainHealth(monster.maxHealth / 2);
            Controller.instance.MonsterRegenAnimation();
        }

        Controller.instance.MonsterAttackAnimation();

        int damage = monster.damage;

        if (RNG.PercentageChance(2)) {
            System.out.println("Critical hit! Double damage.");
            damage *= 2;
        }

        Player.instance.TakeDamage(damage);

        if (monster.ability == Monster.Ability.Lifesteal) {
            monster.GainHealth(damage);
        }

        System.out.println(monster.name + " hit you for " + monster.damage + " damage");

    }

    private static void MonsterDeath() {

        if (monster.ability == Monster.Ability.Deathrattle) {
            Monster newMonster = new Monster(" ", 1, 1, null);
            if (monster.name.equals("Splitting Festeroot")) {
                newMonster = MonsterFactory.GetMonster("Splitting Sapling");
            } else if (monster.name.equals("Splitting Sapling")) {
                newMonster = MonsterFactory.GetMonster("Woodchip");
            }
            CombatManager.StartBattle(newMonster);
            Controller.instance.DeathrattleAnimation();
        } else if (room != null) {
            room.hasBeenVisited = true;
            room.Enter();
        } else {
            Game.instance.NewEncounter();
        }

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
