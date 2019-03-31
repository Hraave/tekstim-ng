public class Weapon extends Item {

    public int damage;
    public int critChance;

    public Weapon(String name, int damage, int critChance) {
        super(name);
        this.damage = damage;
        this.critChance = critChance;
    }

    public void Use() {
        super.Use();
        Player.instance.SetEquippedWeapon(this);
    }

    public void GenerateRandomStats() {
        this.name = "Silver Sword";
        this.damage = RNG.RandomInRange(1, 3);
        this.critChance = RNG.RandomInRange(1, 30);
    }

}
