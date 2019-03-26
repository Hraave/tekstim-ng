public class Potion extends Item {

    public void Use() {
        Player.instance.GainHealth(5);
    }

}
