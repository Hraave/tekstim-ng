import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemFactory {

    private static List<String> weaponNames;
    private static List<String> shieldNames;
    private static List<String> armorNames;

    public static void Setup() {

        /*
        weaponNames = GetAllItemsInPath("weapons");
        shieldNames = GetAllItemsInPath("shields");
        armorNames = GetAllItemsInPath("armor");
        */

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

    public static String GetRandomArmorName() {

        Random random = new Random();
        String name = armorNames.get(random.nextInt(armorNames.size()));
        return name;

    }

    private static List<String> GetAllItemsInPath(String path) {

        List<String> names = new ArrayList<>();

        File folder = new File("src/resources/sprites/inventory/" + path);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String name = listOfFiles[i].getName();
                int index = name.indexOf("_");
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                name = name.substring(0, index - 1) + name.substring(index - 1, index).toUpperCase() + name.substring(index + 1);
                names.add(name.replaceAll("_", " "));
            }
        }
        return names;

    }

}
