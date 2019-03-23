public class Player extends Character {

    public HeroPower heroPower;
    public int mana = 10;
    public int maxMana = 10;

    public int xp;
    public int level = 1;
    public int requiredXP = 10;

    public int gold;

    public Inventory inventory = new Inventory();

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

    public Player(String name, int health) {
        super(name, health);

        instance = this;
    }

    public void GainXP(int amount) {
        xp += amount;

        if (xp >= requiredXP) {
            xp -= requiredXP;
            level++;
            System.out.println("level up!");
            requiredXP = level * 10;
        }
        Controller.instance.DisplayStats();

        System.out.println("xp: " + xp + "\n requiredXP" + requiredXP + "\nlevel: " + level);
    }

    public void RefillMana() {
        mana = maxMana;
    }

    public void UseHeroPower() {
        if (heroPower != null && mana >= heroPower.manaCost) {
            mana -= heroPower.manaCost;
            heroPower.Activate(this);
            Controller.instance.DisplayStats();
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
