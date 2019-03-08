public class Monster extends Character {

    public String name;
    public int health;
    public int damage;

    public Monster() {
        Setup();
    }

    public void Setup() {
        name = "monster";
        health = RNG.RandomInRange(1, 100);
        damage = RNG.RandomInRange(1, 100);
    }

}
