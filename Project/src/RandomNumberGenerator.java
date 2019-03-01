import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {

    public static int RandomInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
