import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dungeon {

    public enum Direction { UP, DOWN, RIGHT, LEFT }

    public Monster.Type monsterType;

    private List<Room> rooms = new ArrayList<>();
    private Room currentRoom;

    public void Generate() {

        Random random;

        ////////////////// Generate dungeon type //////////////////

        random = new Random();
        monsterType = Monster.Type.values()[random.nextInt(Monster.Type.values().length)];

        ////////////////// Generate entrance room //////////////////

        Room mainRoom = new Room(0, 0, this);
        mainRoom.Generate();
        rooms.add(mainRoom);

        ////////////////// Generate rooms for the dungeon //////////////////

        int numberOfRooms = RNG.RandomInRange(1, 10);

        for (int i = 0; i < numberOfRooms; i++) {

            int x = rooms.get(i).x;
            int y = rooms.get(i).y;

            // Get random direction //
            random = new Random();
            Direction direction = Direction.values()[random.nextInt(Direction.values().length)];

            if (direction == Direction.RIGHT) {
                x++;
            } else if (direction == Direction.LEFT) {
                x--;
            } else if (direction == Direction.UP) {
                y++;
            } else if (direction == Direction.DOWN) {
                y--;
            }

            boolean overlappingRooms = false;
            for (Room room : rooms) {
                if (room.x == x && room.y == y) {
                    overlappingRooms = true;
                }
            }

            if (overlappingRooms) {
                i--;
                continue;
            }

            Room room = new Room(x, y, this);
            room.Generate();
            rooms.add(room);

        }

    }

    public void Enter() {

        System.out.println("You enter the dungeon");
        currentRoom = rooms.get(0);
        rooms.get(0).Enter();

    }

    public void PromptToMove() {

        System.out.println("-----------------");
        System.out.println("Displaying coordinates of each room");
        for (Room room : rooms) {
            System.out.println("-----------------");
            System.out.println(room.x + ", " + room.y);
        }
        System.out.println("-----------------");


        Choice root = new Choice(" ");

        Choice right = new Choice("Go right");
        Choice left = new Choice("Go left");
        Choice forward = new Choice("Go forward");
        Choice back = new Choice("Go back");

        Controller.instance.EnterRoom();

        for (Room room : rooms) {
            if (room.x == currentRoom.x + 1 && room.y == currentRoom.y) {
                // Room is on the right
                System.out.println("There is a door on the right");
                root.AddChoice(right);
                Controller.instance.DisplayDoor(Direction.RIGHT);
            }
            if (room.x == currentRoom.x - 1 && room.y == currentRoom.y) {
                // Room is on the left
                System.out.println("There is a door on the left");
                root.AddChoice(left);
                Controller.instance.DisplayDoor(Direction.LEFT);
            }
            if (room.x == currentRoom.x && room.y == currentRoom.y + 1) {
                // Room is above
                System.out.println("There is a door forward");
                root.AddChoice(forward);
                Controller.instance.DisplayDoor(Direction.UP);
            }
            if (room.x == currentRoom.x && room.y == currentRoom.y - 1) {
                // Room is below
                System.out.println("There is a door behind you");
                root.AddChoice(back);
                Controller.instance.DisplayDoor(Direction.DOWN);
            }
        }

        right.SetAction(() -> Move(Direction.RIGHT));
        left.SetAction(() -> Move(Direction.LEFT));
        forward.SetAction(() -> Move(Direction.UP));
        back.SetAction(() -> Move(Direction.DOWN));

        root.Display();

    }

    private void Move(Direction direction) {

        Controller.instance.ExitRoom();

        int x = currentRoom.x;
        int y = currentRoom.y;

        if (direction == Direction.RIGHT) {
            x++;
        } else if (direction == Direction.LEFT) {
            x--;
        } else if (direction == Direction.UP) {
            y++;
        } else if (direction == Direction.DOWN) {
            y--;
        }

        for (Room room : rooms) {
            if (room.x == x && room.y == y) {
                currentRoom = room;
                room.Enter();
            }
        }

    }

}
