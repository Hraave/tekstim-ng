import java.util.concurrent.ThreadLocalRandom;

public class RNG {

    public static int RandomInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static boolean PercentageChance(int percentage) {

        double d = Math.random();
        double p = percentage / 100.0;

        if (d < p) {
            return true;
        } else {
            return false;
        }

    }

}
