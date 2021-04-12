package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class RandomWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 40;

    private static final long SEED = 28723;
    static Queue<Position> exitsQueue = new LinkedList<>();
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
        if (randomNum < 0.8) {
            newObject = generateRoom(seed);
        }else {
            newObject = generateHallway(seed);
        }
        newObject.setFocus(focus);
        if (newObject.checkIndexError(world) || newObject.checkOverlap(world)) {
            newObject = randomlyGeneration(RANDOM.nextInt((int) seed), world, focus);
        }
        // add all the exits given by exits to the queue.
        newObject.generate(world);
        for (Position exit : newObject.getExits()) {
            exitsQueue.offer(exit);
        }
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
        RoomUnit r = randomlyGeneration(seed, world, focus);
        long newSeed = RANDOM.nextInt((int) seed);
        while (complexity < 0.75) {
            Position currExit = exitsQueue.poll();
            newSeed = RANDOM.nextInt((int) newSeed);
            RoomUnit newRoom = randomlyGeneration(newSeed, world, newFocus(currExit));
            complexity += newRoom.getSize() / 4000;
        }
    }

    /**
     * this function is to return the focus of a new room
     * @param exit the previous room's exit
     * @return the generated new focus's position
     */
    private static Position newFocus(Position exit) {
        int m;
        int n;
        int direction = exit.getDirection();
        switch (direction) {
            case '0': m = 0;
                      n = 1;
            case '1': m = 0;
                      n = -1;
            case '2': m = -1;
                      n = 0;
            default: m = 1;
                      n = 0;
        }
        Position returnFocus = new Position(exit.getX() + m, exit.getY() + n);
        return returnFocus;
    }

    /**
     * this function is to make a exit and focus visible
     * @param target the
     * @param world
     */
    private static void chisel(Position target, TETile[][] world) {
        world[target.getX()][target.getY()] = Tileset.FLOOR;
    }

    /**
     * This is used for debugging.
     */
    public static void main(String[] args) {
        worldGenerator();
    }

}
