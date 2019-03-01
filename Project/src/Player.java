public class Player extends Character {

    public int level = 1;
    public int armor = 0;

    public Player(String name) {
        this.name = name;
    }

    public void Die() {

        Main.gameIsRunning = false;
        System.out.println("You died.");

    }

}
