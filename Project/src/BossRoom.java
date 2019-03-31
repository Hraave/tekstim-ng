public class BossRoom extends Room {

    private Monster boss;

    public BossRoom(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        isBossRoom = true;
    }

    @Override
    public void Generate() {
        boss = MonsterFactory.GetRandomBoss();
    }

    @Override
    public void Enter() {

        Controller.instance.EnterRoom();
        Controller.instance.imagePane.setVisible(false);

        if (!hasBeenVisited) {
            CombatManager.StartBattle(boss, this);
        }

        if (hasBeenVisited) {
            Controller.instance.ExitRoom();
            Game.instance.NewEncounter();
        }

    }

}
