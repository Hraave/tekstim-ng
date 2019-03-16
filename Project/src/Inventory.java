import java.util.ArrayList;
import java.util.List;

public class Inventory {

    public List<Item> items = new ArrayList<>();

    public void Add(Item item) {
        items.add(item);
    }

    public void Remove(Item item) {
        items.remove(item);
    }

    public void Open() {

        List<Item> items = Player.instance.inventory.items;
        List<Weapon> weapons = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {

            Item item = items.get(i);

            if (item instanceof Weapon) {
                weapons.add((Weapon) item);
                System.out.println(i + ". " + item.name);
            }

            //int input = Main.scanner.nextInt();

            //Player.instance.equippedWeapon = weapons.get(input);

            //System.out.println("Equipped " + weapons.get(input));

        }

    }

}
