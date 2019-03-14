public class Player extends Character {

    private enum Class {
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

    public Class Class;

    public int xp;
    public int level = 1;

    public int gold;

    public Inventory inventory;
    public Weapon equippedWeapon;

    public static Player instance;

    public Player(String name) {
        instance = this;

        this.name = name;
        maxHealth = 30;
        health = maxHealth;
        Class = Class.Mage;
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

        Game.instance.isRunning = false;
        System.out.println("You died.");

    }

}
