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
    public int mana;
    public int maxMana;
    public void RefillMana() {
        mana = maxMana;
    }

    public int xp;
    public int level = 1;

    public int gold;

    public Inventory inventory;

    private Weapon equippedWeapon;
    public Weapon GetEquippedWeapon() {
        if (equippedWeapon == null) {
            return new Weapon("Fists", 1, 1);
        }
        return equippedWeapon;
    }
    public void SetEquippedWeapon(Weapon weapon) {
        equippedWeapon = weapon;
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
    public void GainHealth(int amount) {
        super.GainHealth(amount);
        Controller.instance.DisplayStats();
    }

    @Override
    public void TakeDamage(int amount) {
        super.TakeDamage(amount);
        Controller.instance.DisplayStats();
    }

    @Override
    public void Die() {
        Controller.instance.PlayerDeath();
        Sound.PlaySound("You Died", false);
    }

}
