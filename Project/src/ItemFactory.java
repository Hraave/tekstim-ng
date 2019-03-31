import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemFactory {

    private static List<String> weaponNames;
    private static List<String> shieldNames;
    private static List<String> helmetNames;
    private static List<String> chestplateNames;
    private static List<String> bootsNames;

    public static void Setup() {

        weaponNames = GetAllItemsInPath("weapons");
        shieldNames = GetAllItemsInPath("shields");
        helmetNames = GetAllItemsInPath("helmets");
        chestplateNames = GetAllItemsInPath("chestplates");
        bootsNames = GetAllItemsInPath("boots");

    }

    public static String GetRandomWeaponName() {

        Random random = new Random();
        String name = weaponNames.get(random.nextInt(weaponNames.size()));
        return name;

    }

    public static String GetRandomShieldName() {

        Random random = new Random();
        String name = shieldNames.get(random.nextInt(shieldNames.size()));
        return name;

    }

    /*
    public static String GetRandomArmorName() {

        Random random = new Random();
        String name = armorNames.get(random.nextInt(armorNames.size()));
        return name;

    }
    */

    public static String GetRandomHelmetName() {

        Random random = new Random();
        String name = helmetNames.get(random.nextInt(helmetNames.size()));
        return name;

    }

    public static String GetRandomChestplateName() {

        Random random = new Random();
        String name = chestplateNames.get(random.nextInt(chestplateNames.size()));
        return name;

    }

    public static String GetRandomBootsName() {

        Random random = new Random();
        String name = bootsNames.get(random.nextInt(bootsNames.size()));
        return name;

    }

    private static List<String> GetAllItemsInPath(String path) {

        List<String> names = new ArrayList<>();

        File folder = new File("src/resources/sprites/inventory/" + path);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && !listOfFiles[i].getName().equals("Thumbs.db")) {
                String name = listOfFiles[i].getName();
                name = name.substring(0, name.length() - 4); //Remove .png from the end of the name.
                String[] words = name.split("_");
                for (int u = 0; u < words.length; u++) {
                    words[u] = words[u].substring(0, 1).toUpperCase() + words[u].substring(1); //Make each word start with an uppercase letter.
                }
                name = String.join(" ", words);
                names.add(name);
            }
        }
        return names;

    }

}
