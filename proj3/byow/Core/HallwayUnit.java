package byow.Core;

/**
 * Unit of the Hallway.
 * Abstractly speaking, hallway is a kind of room.
 */
public class HallwayUnit extends RoomUnit {

    public HallwayUnit() {
        this(randomWidth(), randomLength(), randomDirection());
    }

    public HallwayUnit(int width, int length, int direction) {
        super(width, length, direction);
    }



    /**
     * Pick random width between [1, 2].
     */
    public static int randomWidth() {
        return 1 + RANDOM.nextInt(2);
    }

    /**
     * Pick random length between [2, 10].
     */
    public static int randomLength() {
        return 2 + RANDOM.nextInt(9);
    }

}

