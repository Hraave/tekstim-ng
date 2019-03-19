public class Monster extends Character {

    public enum Ability {
        Charge,
        Deathrattle,
        Lifesteal,
        Regeneration
    }

    public int damage;
    public Ability ability;

    public Monster(String name, int damage, int health, Ability ability) {
        super(name, health);
        this.damage = damage;
        this.ability = ability;
    }

}
