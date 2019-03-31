public class Armor extends Item {

    public enum Type {
        Helmet,
        Chestplate,
        Boots
    }

    public Type type;
    public int protection;

    public Armor(String name, Type type, int protection) {
        super(name);
        this.type = type;
        this.protection = protection;
    }

    public void Use() {
        super.Use();

        if (type == Type.Helmet) {
            Player.instance.helmet = this;
        } else if (type == Type.Chestplate) {
            Player.instance.chestplate = this;
        } else if (type == Type.Boots) {
            Player.instance.boots = this;
        }

        Controller.instance.SetSlotImage(this);
    }

}
