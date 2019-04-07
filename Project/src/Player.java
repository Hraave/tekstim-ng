public class Player extends Character {

    public int strength;
    public int perception;
    public int endurance;
    public int charisma;
    public int intelligence;
    public int agility;
    public int luck;

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
            return new Weapon("Fists", strength / 2, luck);
        }
        return equippedWeapon;
    }
    public void SetEquippedWeapon(Weapon weapon) {
        equippedWeapon = weapon;
        Controller.instance.DisplayStats();
    }

    public Shield shield;
    public Armor helmet;
    public Armor chestplate;
    public Armor boots;

    public static Player instance;

    public Player(String name, int strength, int perception, int endurance, int charisma, int intelligence, int agility, int luck) {
        super(name, endurance * 5);
        this.strength = strength;
        this.perception = perception;
        this.endurance = endurance;
        this.charisma = charisma;
        this.intelligence = intelligence;
        this.agility = agility;
        this.luck = luck;

        instance = this;
    }

    public void GainXP(int amount) {
        xp += amount;

        if (xp >= requiredXP) {
            xp -= requiredXP;
            level++;
            requiredXP = level * 10;
            Sound.PlaySound("levelup");
        }
        Controller.instance.DisplayStats();
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
        super.Die();
        Controller.instance.PlayerDeath();
    }

}
