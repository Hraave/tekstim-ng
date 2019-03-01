public class Encounter {

    public Encounter() {

        int random = RandomNumberGenerator.RandomInRange(1, 1);
        switch (random) {
            case 1: MonsterEncounter();
                    break;
        }

    }

    public void MonsterEncounter() {

        Monster monster = new Monster();

        System.out.println(monster.name + " attacks!");
        System.out.println("1. Fight\n2. Run");

        int input = Main.scanner.nextInt();

        if (input == 1) {



        } else if (input == 2) {



        }

    }

}
