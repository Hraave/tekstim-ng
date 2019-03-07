import java.util.concurrent.ThreadLocalRandom;

public class RNG {

    public static int RandomInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
