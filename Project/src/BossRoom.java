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

        if (!hasBeenVisited) {
            CombatManager.StartBattle(boss);
        }

        if (hasBeenVisited) {
            Game.instance.NewEncounter();
        }

    }

}
