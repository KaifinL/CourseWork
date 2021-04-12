package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class RandomWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 40;

    private static final long SEED = 28723;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Return a random location on the map.
     */
    private static Position randomFocus() {
        return new Position(RANDOM.nextInt(HEIGHT), RANDOM.nextInt(WIDTH));
    }

    /**
     * This is just skeleton code.
     */
    private static void worldGenerator() {
        long pseudoSeed = SEED;
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        //Index?
        //Overlap?
        // draw the room/hallway to the world
        RoomUnit r = randomlyGeneration(pseudoSeed);
        r.generate(world);
        // draws the world to the screen
        ter.renderFrame(world);

    }

    private static RoomUnit generateRoom(long seed) {
        return new RoomUnit(seed);
    }

    private static HallwayUnit generateHallway(long seed) {
        return new HallwayUnit(seed);
    }

    private static RoomUnit randomlyGeneration(long seed) {
        double randomNum = Math.random();
        RoomUnit newObject;
        if (randomNum < 0.3) {
            newObject = generateRoom(seed);
        }else {
            newObject = generateHallway(seed);
        }
        newObject.setFocus(randomFocus());
        return newObject;
    }

    /**
     * This is used for debugging.
     */
    public static void main(String[] args) {
        worldGenerator();
    }

}
