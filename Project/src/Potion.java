public class Potion extends Item {

    public Potion(String name) {
        super(name);
    }

    public void Use() {
        super.Use();
        Player.instance.GainHealth(5);
        Sound.PlaySound("health_potion", false);
    }

}
