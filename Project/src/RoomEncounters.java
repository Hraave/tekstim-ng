public class RoomEncounters {

    public static void GetRandom() {

        int random = RNG.RandomInRange(1, 1);
        switch (random) {
            case 1: Chest();
                    break;
        }

    }

    public static void Chest() {
        Choice root = new Choice("There is a chest in the middle of the room");
        Choice open = root.AddChoice("Open it");
        Choice leave = root.AddChoice("Leave it");

        Choice selection = root.GetSelection();

        if (selection == open) {

            if (RNG.PercentageChance(30)) {

                System.out.println("A monster comes out of it!");
                Monster monster = new Monster();
                monster.GenerateStats();
                CombatManager.Battle(monster);

            } else {
                System.out.println("It's empty");
            }

        }
    }



}
