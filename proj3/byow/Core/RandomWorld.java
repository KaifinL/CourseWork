package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class RandomWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 40;

    private static final long SEED = 287123;
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
        /**
         * in this method 'world' is only a variable name represents a matrix
         * the initial world has been generated filled with 'nothing'
         */

        // generate a room
        RoomUnit r = new RoomUnit();
        r.setFocus(randomFocus()); // set the location

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
        r.generate(world);
        // draws the world to the screen
        ter.renderFrame(world);

    }
    /**
     * This is used for debugging.
     */
    public static void main(String[] args) {
        worldGenerator();
    }

}
