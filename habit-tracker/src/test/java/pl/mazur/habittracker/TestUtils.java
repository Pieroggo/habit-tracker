package pl.mazur.habittracker;

import java.util.concurrent.ThreadLocalRandom;

public class TestUtils {
    public static Long randomId() {
        return ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
    }
}
