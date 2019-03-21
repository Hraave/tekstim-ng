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

}
