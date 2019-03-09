import java.util.ArrayList;
import java.util.List;

public class Dungeon {

    private List<Room> rooms = new ArrayList<>();
    private Room currentRoom;

    public void Generate() {

        ////////////////// Generate entrance room //////////////////

        Room mainRoom = new Room(0, 0);
        mainRoom.Generate();
        rooms.add(mainRoom);

        ////////////////// Generate rooms for the dungeon //////////////////

        int numberOfRooms = RNG.RandomInRange(1, 10);

        for (int i = 0; i < numberOfRooms; i++) {

            int x = rooms.get(i).x + i;
            int y = rooms.get(i).y + i;

            Room room = new Room(x, y);
            room.Generate();
            rooms.add(room);

        }

    }

    public void Enter() {

        System.out.println("You enter the dungeon");
        rooms.get(0).Enter();

    }

}
