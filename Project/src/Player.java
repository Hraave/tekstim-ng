public class Player extends Character {

    public enum Class {
        Druid,
        Hunter,
        Mage,
        Paladin,
        Priest,
        Rogue,
        Shaman,
        Warlock,
        Warrior
    }

    public Class playerClass;

    public int xp;
    public int level = 1;

    public int gold;

    public Inventory inventory;

    public Weapon equippedWeapon;
    public Weapon GetEquippedWeapon() {
        if (equippedWeapon == null) {
            return new Weapon("Fists", 1, 1);
        }
        return equippedWeapon;
    }

    public static Player instance;

    public Player(String name, int health, Class playerClass) {
        super(name, health);
        this.playerClass = playerClass;

        instance = this;
    }

    public void GainXP(int amount) {

        xp += amount;
        System.out.println("Gained " + amount + " xp!");
        if (xp >= level * 10) {
            xp -= level * 10;
            level++;
            System.out.println("LEVEL UP!\nLevel: " + level);
        }

    }

    @Override
    public void Die() {

        System.out.println("You died.");

    }

}
