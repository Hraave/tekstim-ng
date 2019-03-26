public class Weapon extends Item {

    public int damage;
    public int critChance;

    public Weapon(String name, int damage, int critChance) {
        super(name);
        this.damage = damage;
        this.critChance = critChance;
    }

    public void Use() {
        Player.instance.SetEquippedWeapon(this);
        Controller.instance.SetSlotImage(this);
    }

}
