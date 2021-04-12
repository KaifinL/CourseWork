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
        /**
         * it does work! but there is a bug in it. I fail to connect the two rooms
        RoomUnit r = randomlyGeneration(pseudoSeed, world, randomFocus());
        randomlyGeneration(RANDOM.nextInt((int) pseudoSeed), world,(r.getExits()[0]));
         */
        generateWorld(pseudoSeed, world, randomFocus());
        // draws the world to the screen
        ter.renderFrame(world);

    }

    private static RoomUnit generateRoom(long seed) {
        return new RoomUnit(seed);
    }

    private static HallwayUnit generateHallway(long seed) {
        return new HallwayUnit(seed);
    }

    /**
     * this method simply return a room or a hallway randomly
     * @param seed nothing special here
     * @param world the whole board this should not be changed
     * @param focus this should be changed as you might use recursion.You should pass the exit position to it.
     * @return just for a better use of recursion in case there is an IndexError or overloadError.
     */
    private static RoomUnit randomlyGeneration(long seed, TETile[][] world, Position focus) {
        double randomNum = Math.random();
        RoomUnit newObject;
        if (randomNum < 0.3) {
            newObject = generateRoom(seed);
        }else {
            newObject = generateHallway(seed);
        }
        newObject.setFocus(focus);
        if (newObject.checkIndexError(world) || newObject.checkOverlap(world)) {
            newObject = randomlyGeneration(RANDOM.nextInt((int) seed), world, focus);
        }
        newObject.generate(world);
        Counter.changeSize(newObject.getSize());
        return newObject;
    }

    /**
     * the reason why I create this method is to avoid writing to many codes in one method.
     * Therefore, the parameters and the meaning are all the same as in the previous method.
     * @param seed
     * @param world
     * @param focus
     * here I want to use a recursion since I think it will be more convenient to get the focus;
     */
    private static void generateWorld(long seed, TETile[][] world, Position focus) {
        double complexity = 0;
        while (complexity < 0.75) {
            RoomUnit r = randomlyGeneration(seed, world, randomFocus());
            randomlyGeneration(seed, world, (r.getExits()[0]));
            complexity += 0.1;
        }
    }

    /**
     * This is used for debugging.
     */
    public static void main(String[] args) {
        worldGenerator();
    }

}
