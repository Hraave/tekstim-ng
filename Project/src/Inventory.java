import java.util.ArrayList;
import java.util.List;

public class Inventory {

    public static final int columns = 5;
    public static final int rows = 5;

    public List<Item> items = new ArrayList<>();

    public void Add(Item item) {
        if (items.size() >= columns * rows) {
            System.out.println("Inventory is full!");
        } else {
            items.add(item);
            Controller.instance.ToggleInventory();
            Controller.instance.ToggleInventory();
        }
    }

    public void Remove(Item item) {
        items.remove(item);
        Controller.instance.ToggleInventory();
        Controller.instance.ToggleInventory();
    }

}
