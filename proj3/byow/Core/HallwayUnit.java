package byow.Core;

import java.util.Random;

/**
 * Unit of the Hallway.
 * Abstractly speaking, hallway is a kind of room.
 */
public class HallwayUnit extends RoomUnit {
    public static final int SCOPE1 = 12;
    public static final int SCOPE2 = 12;


    /**
     * Constructors.
     */
    public HallwayUnit(long seed) {
        this(randomWidth(seed), randomLength(seed), randomDirection(seed), seed);
    }

    public HallwayUnit(int direction, long seed) {
        this(randomWidth(seed), randomLength(seed), direction, seed);
    }

    public HallwayUnit(int width, int length, int direction, long seed) {
        super(width, length, direction, seed);
    }



    /**
     * Pick random width between [1, 2].
     */
    public static int randomWidth(long seed) {
        Random r = new Random(seed);
        return 1 + r.nextInt(SCOPE1);
    }

    /**
     * Pick random length between [2, 10].
     */
    public static int randomLength(long seed) {
        Random r = new Random(seed);
        return 2 + r.nextInt(SCOPE2);
    }

}
