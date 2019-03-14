import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Monster extends Character {

    public enum Type {
        General,
        Beast,
        Demon,
        Pirate
    }

    private enum Ability {
        Lifesteal,
        Regeneration
    }

    public int damage;
    public Type type;
    public Ability ability;

    public Monster() {

    }

    public Monster(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public void GenerateStats() {

        name = "monster_name";
        maxHealth = RNG.RandomInRange(1, Player.instance.level * 5);
        health = maxHealth;
        damage = RNG.RandomInRange(1, Player.instance.level * 5);

    }

    public void GenerateStats(Type type) {

        this.type = type;
        //GetRandomEnemyFromFile();

    }

    private void GetRandomEnemyFromFile() {

        File file = new File("monsters.txt");
        try {

            Scanner s = new Scanner(file);

            List<Monster> monsters = new ArrayList<>();

            String name = "";
            int health = 0;
            int damage = 0;
            Type type = null;
            Ability ability = null;

            int i = 0;
            while (s.hasNextLine()) {

                String line = s.nextLine();

                String[] values = line.split(" ");

                name = values[0];
                damage = Integer.valueOf(values[1]);
                health = Integer.valueOf(values[2]);
                type = Type.valueOf(values[3]);
                if (values.length >= 5) {
                    ability = Ability.valueOf(values[4]);
                }

                //Monster monster = new Monster(name, health, damage);








                i++;

            }

            this.name = name;
            this.maxHealth = health;
            this.damage = damage;
            this.type = type;


        } catch (FileNotFoundException e) {
            System.out.println("File monsters.txt has been deleted!");
            e.printStackTrace();
        }

    }

}
