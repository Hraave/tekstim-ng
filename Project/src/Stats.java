import java.util.Random;

public class Stats {

    public static Monster GenerateMonsterStats(Monster monster) {
        int level = Player.instance.level;

        monster.damage = monster.damage + level * 2;
        monster.health = monster.maxHealth = monster.maxHealth + level * 2;

        return monster;
    }

    public static Weapon GenerateRandomWeapon() {
        int level = Player.instance.level;

        String name = ItemFactory.GetRandomWeaponName();
        int damage = RNG.RandomInRange(2, 5);
        int critChance = RNG.RandomInRange(1, 30);

        damage = damage + level * 2;

        return new Weapon(name, damage, critChance);
    }

    public static Shield GenerateRandomShield() {
        int level = Player.instance.level;

        String name = ItemFactory.GetRandomShieldName();
        int blockChance = RNG.RandomInRange(1, 30);

        return new Shield(name, blockChance);
    }

    public static Armor GenerateRandomArmor() {
        int level = Player.instance.level;

        Random random = new Random();
        Armor.Type type = Armor.Type.values()[random.nextInt(Armor.Type.values().length)];
        String name = "";
        if (type == Armor.Type.Helmet) {
            name = ItemFactory.GetRandomHelmetName();
        } else if (type == Armor.Type.Chestplate) {
            name = ItemFactory.GetRandomChestplateName();
        } else if (type == Armor.Type.Boots) {
            name = ItemFactory.GetRandomBootsName();
        }
        int protection = RNG.RandomInRange(1, 10);

        return new Armor(name, type, protection);
    }

}
