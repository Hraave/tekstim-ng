public class Monster extends Character {

    public int damage;

    public void GenerateStats() {

        name = "monster";
        maxHealth = RNG.RandomInRange(1, Player.instance.level * 5);
        health = maxHealth;
        damage = RNG.RandomInRange(1, Player.instance.level * 5);

    }

}
