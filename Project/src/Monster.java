public class Monster extends Character {

    public String name;
    public int health;
    public int damage;

    public Monster() {
        this.name = GenerateName();
        this.health = RandomNumberGenerator.RandomInRange(1, 100);
        this.damage = RandomNumberGenerator.RandomInRange(1, 100);
    }

    private String GenerateName() {

        // failist monsters.txt võtta suvaline nimi, nii et igal real on üks nimi?

        return "monstername";

    }

}
