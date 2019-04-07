import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Dungeon {

    public enum Direction { UP, DOWN, RIGHT, LEFT }

    public List<Room> rooms = new ArrayList<>();
    private Room currentRoom;
    private Direction playerFacingDirection = Direction.UP;

    private static final int minNumberOfRooms = 3;
    private static final int maxNumberOfRooms = 5;

    public static boolean playerIsInDungeon;

    public void Generate() {

        ////////////////// Generate entrance room //////////////////

        Room mainRoom = new Room(0, 0, this);
        mainRoom.Generate();
        rooms.add(mainRoom);

        ////////////////// Generate rooms for the dungeon //////////////////

        int numberOfRooms = RNG.RandomInRange(minNumberOfRooms - 1, maxNumberOfRooms - 1);

        for (int i = 0; i < numberOfRooms; i++) {

            int x = rooms.get(i).x;
            int y = rooms.get(i).y;

            // Get random direction //
            Random random = new Random();
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

            //////////////////////
            if (x < 0 || y < 0) {
                i--;
                continue;
            }
            //////////////////////

            Room room = new Room(x, y, this);

            if (i == numberOfRooms - 1) {
                room = new BossRoom(x, y, this);
            }

            room.Generate();
            rooms.add(room);

        }

        ////////////////// Add boss room key to a random room //////////////////

        List<Room> notBossOrFirstRooms = rooms.stream()
                .filter(room -> !room.isBossRoom)
                .filter(room -> room != rooms.get(0))
                .collect(Collectors.toList());

        Random random = new Random();
        Room randomRoom = notBossOrFirstRooms.get(random.nextInt(notBossOrFirstRooms.size()));
        randomRoom.containsBossKey = true;

    }

    public void Enter() {

        playerIsInDungeon = true;

        Sound.PlayMusic("Conquer_song");
        EnterRoom(rooms.get(0));

    }

    public void PromptToMove(Choice choice) {

        /*
        for (Room room : rooms) {
            System.out.println(room.x + ", " + room.y);
        }
        */

        Choice root = new Choice(" ");

        Choice left = new Choice("Go left");
        Choice forward = new Choice("Go forward");
        Choice back = new Choice("Go back");
        Choice right = new Choice("Go right");

        Controller.instance.EnterRoom();

        for (Room room : rooms) {
            if (room.x == currentRoom.x - 1 && room.y == currentRoom.y) {
                /*
                root.AddChoice(left);
                Controller.instance.DisplayDoor(Direction.LEFT, room.isBossRoom);
                */
                AddDoor(root, right, left, forward, back, Direction.LEFT, room);
            }
            if (room.x == currentRoom.x && room.y == currentRoom.y + 1) {
                /*
                root.AddChoice(forward);
                Controller.instance.DisplayDoor(Direction.UP, room.isBossRoom);
                */
                AddDoor(root, right, left, forward, back, Direction.UP, room);
            }
            if (room.x == currentRoom.x && room.y == currentRoom.y - 1) {
                /*
                root.AddChoice(back);
                Controller.instance.DisplayDoor(Direction.DOWN, room.isBossRoom);
                */
                AddDoor(root, right, left, forward, back, Direction.DOWN, room);
            }
            if (room.x == currentRoom.x + 1 && room.y == currentRoom.y) {
                /*
                root.AddChoice(right);
                Controller.instance.DisplayDoor(Direction.RIGHT, room.isBossRoom);
                */
                AddDoor(root, right, left, forward, back, Direction.RIGHT, room);
            }
        }

        right.SetAction(() -> Move(Direction.RIGHT));
        left.SetAction(() -> Move(Direction.LEFT));
        forward.SetAction(() -> Move(Direction.UP));
        back.SetAction(() -> Move(Direction.DOWN));


        if (choice != null) {
            root.AddChoice(choice);
        }


        root.Display();

    }

    private void AddDoor(Choice root, Choice right, Choice left, Choice forward, Choice back, Direction direction, Room room) {

        Direction doorDirection = Direction.UP;

        if (playerFacingDirection == Direction.RIGHT) {
            if (direction == Direction.RIGHT) {
                root.AddChoice(forward);
                doorDirection = Direction.UP;
            } else if (direction == Direction.LEFT) {
                root.AddChoice(back);
                doorDirection = Direction.DOWN;
            } else if (direction == Direction.UP) {
                root.AddChoice(left);
                doorDirection = Direction.LEFT;
            } else if (direction == Direction.DOWN) {
                root.AddChoice(right);
                doorDirection = Direction.RIGHT;
            }
        } else if (playerFacingDirection == Direction.LEFT) {
            if (direction == Direction.RIGHT) {
                root.AddChoice(back);
                doorDirection = Direction.DOWN;
            } else if (direction == Direction.LEFT) {
                root.AddChoice(forward);
                doorDirection = Direction.UP;
            } else if (direction == Direction.UP) {
                root.AddChoice(right);
                doorDirection = Direction.RIGHT;
            } else if (direction == Direction.DOWN) {
                root.AddChoice(left);
                doorDirection = Direction.LEFT;
            }
        } else if (playerFacingDirection == Direction.UP) {
            if (direction == Direction.RIGHT) {
                root.AddChoice(right);
                doorDirection = Direction.RIGHT;
            } else if (direction == Direction.LEFT) {
                root.AddChoice(left);
                doorDirection = Direction.LEFT;
            } else if (direction == Direction.UP) {
                root.AddChoice(forward);
                doorDirection = Direction.UP;
            } else if (direction == Direction.DOWN) {
                root.AddChoice(back);
                doorDirection = Direction.DOWN;
            }
        } else if (playerFacingDirection == Direction.DOWN) {
            if (direction == Direction.RIGHT) {
                root.AddChoice(left);
                doorDirection = Direction.LEFT;
            } else if (direction == Direction.LEFT) {
                root.AddChoice(right);
                doorDirection = Direction.RIGHT;
            } else if (direction == Direction.UP) {
                root.AddChoice(back);
                doorDirection = Direction.DOWN;
            } else if (direction == Direction.DOWN) {
                root.AddChoice(forward);
                doorDirection = Direction.UP;
            }
        }

        Controller.instance.DisplayDoor(doorDirection, room.isBossRoom);
    }

    private Direction AdjustToPlayerFacingDirection(Direction direction) {

        Direction newDirection = direction;

        if (playerFacingDirection == Direction.RIGHT) {
            if (direction == Direction.RIGHT) {
                newDirection = Direction.DOWN;
            } else if (direction == Direction.LEFT) {
                newDirection = Direction.UP;
            } else if (direction == Direction.UP) {
                newDirection = Direction.RIGHT;
            } else if (direction == Direction.DOWN) {
                newDirection = Direction.LEFT;
            }
        } else if (playerFacingDirection == Direction.LEFT) {
            if (direction == Direction.RIGHT) {
                newDirection = Direction.UP;
            } else if (direction == Direction.LEFT) {
                newDirection = Direction.DOWN;
            } else if (direction == Direction.UP) {
                newDirection = Direction.LEFT;
            } else if (direction == Direction.DOWN) {
                newDirection = Direction.RIGHT;
            }
        } else if (playerFacingDirection == Direction.DOWN) {
            if (direction == Direction.RIGHT) {
                newDirection = Direction.LEFT;
            } else if (direction == Direction.LEFT) {
                newDirection = Direction.RIGHT;
            } else if (direction == Direction.UP) {
                newDirection = Direction.DOWN;
            } else if (direction == Direction.DOWN) {
                newDirection = Direction.UP;
            }
        }

        return newDirection;

    }

    private void Move(Direction direction) {

        Controller.instance.ExitRoom();

        int x = currentRoom.x;
        int y = currentRoom.y;

        direction = AdjustToPlayerFacingDirection(direction);

        if (direction == Direction.RIGHT) {
            x++;
        } else if (direction == Direction.LEFT) {
            x--;
        } else if (direction == Direction.UP) {
            y++;
        } else if (direction == Direction.DOWN) {
            y--;
        }

        //playerFacingDirection = direction;

        for (Room room : rooms) {
            if (room.x == x && room.y == y) {

                // Checks for key if trying to enter boss room
                if (room.isBossRoom) {
                    for (Item item : Player.instance.inventory.items) {
                        if (item.name.equals("Boss Key")) {
                            Player.instance.inventory.Remove(item);
                            playerFacingDirection = direction;
                            EnterRoom(room);
                            return;
                        }
                    }
                    Controller.instance.DisplayMessage("You need the\nboss key", "block");
                    PromptToMove(null);
                    return;
                } else {
                    playerFacingDirection = direction;
                    EnterRoom(room);
                }

            }
        }
    }

    private void EnterRoom(Room room) {
        currentRoom = room;
        room.Enter();
    }
}
