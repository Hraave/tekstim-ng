import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MonsterFactory {

    private static List<Monster> monsters;
    private static List<Monster> bosses;
    private static List<Monster> scriptedMonsters;

    public static void Setup() {

        monsters = TextFileToMonsterList("monsters.txt");
        bosses = TextFileToMonsterList("monsters_bosses.txt");
        scriptedMonsters = TextFileToMonsterList("monsters_scripted.txt");

    }

    public static Monster GetMonster(String name) {

        for (Monster monster : scriptedMonsters) {
            if (monster.name.equals(name)) {
                return new Monster(monster.name, monster.damage, monster.health, monster.ability);
            }
        }

        return null;

    }

    public static Monster GetRandomMonster() {

        Random random = new Random();
        Monster monster = monsters.get(random.nextInt(monsters.size()));
        return new Monster(monster.name, monster.damage, monster.health, monster.ability);

    }

    public static Monster GetRandomBoss() {

        Random random = new Random();
        Monster monster = bosses.get(random.nextInt(bosses.size()));
        return new Monster(monster.name, monster.damage, monster.health, monster.ability);

    }

    private static List<Monster> TextFileToMonsterList(String path) {

        List<Monster> list = new ArrayList<>();

        File file = new File(path);
        try {

            Scanner s = new Scanner(file);

            String line;
            while (s.hasNextLine()) {
                line = s.nextLine();
                list.add(TextFileLineToMonster(line));
            }

            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("Monster file is missing");
            e.printStackTrace();
        }
        return list;

    }

    private static Monster TextFileLineToMonster(String line) {

        String name;
        int damage;
        int health;
        Monster.Ability ability = null;

        String[] values = line.split(" ");
        name = values[0].replaceAll("_", " ");
        damage = Integer.valueOf(values[1]);
        health = Integer.valueOf(values[2]);
        if (values.length > 3) {
            ability = Monster.Ability.valueOf(values[3]);
        }

        return new Monster(name, damage, health, ability);

    }

}
