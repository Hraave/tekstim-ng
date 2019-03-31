public class Item {

    public String name;

    public Item(String name) {
        this.name = name;
    }

    public void Use() {
        Player.instance.inventory.Remove(this);
    }

}
