public class Shield extends Item {

    public int blockChance;

    public Shield(String name, int blockChance) {
        super(name);
        this.blockChance = blockChance;
    }

    public void Use() {
        super.Use();
        Player.instance.shield = this;
        Controller.instance.SetSlotImage(this);
    }

}
