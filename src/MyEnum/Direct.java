package MyEnum;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Direct {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    private static final List<Direct> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Direct randomDirect() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
