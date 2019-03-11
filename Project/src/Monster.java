import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Monster extends Character {

    private enum Ability {
        Regeneration
    }

    public int damage;
    public Ability ability;

    public void GenerateStats() {

        /*
        name = "monster";
        maxHealth = RNG.RandomInRange(1, Player.instance.level * 5);
        health = maxHealth;
        damage = RNG.RandomInRange(1, Player.instance.level * 5);
        */

        GetRandomEnemyFromFile();

    }

    private void GetRandomEnemyFromFile() {

        File file = new File("monsters.txt");
        try {

            Scanner s = new Scanner(file);

            String name = "";
            int health = 0;
            int damage = 0;

            int i = 0;
            while (s.hasNextLine()) {

                String line = s.nextLine();











                i++;

            }

            this.name = name;
            this.maxHealth = health;
            this.damage = damage;


        } catch (FileNotFoundException e) {
            System.out.println("File monsters.txt has been deleted!");
            e.printStackTrace();
        }

    }

}
