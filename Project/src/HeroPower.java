public class HeroPower extends Item {

    public enum Type {
        Damage,
        Heal
    }

    public int manaCost;

    public HeroPower(String name, int manaCost) {
        super(name);
        this.manaCost = manaCost;
    }

    public void Use() {
        Player.instance.heroPower = this;
    }

    public void Activate(Player player) {
        player.GainHealth(10);
    }

}
