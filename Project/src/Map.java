import java.util.ArrayList;
import java.util.List;

public class Map {

    private List<Dungeon> dungeons = new ArrayList<>();

    public void AddLocation(Dungeon dungeon) {

        dungeons.add(dungeon);

    }

}
