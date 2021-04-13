package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class RandomWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 40;

    private static final long SEED = 287223;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Return a random location on the map.
     */

    private static Position randomFocus(long seed) {
        Random r = new Random(seed);
        return new Position(r.nextInt(HEIGHT), r.nextInt(WIDTH), 1);
    }

    /**
     * This is just skeleton code.
     */
    private static void worldGenerator() {
        PriorityQueue<Position> exitsQueue = new PriorityQueue<>();
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
        RoomUnit r = randomlyGeneration(pseudoSeed, world, randomFocus(pseudoSeed),
                1, exitsQueue);
        int counter = 0;
        while (counter < 10){
            Position exit = exitsQueue.poll();
            chisel(realExit(exit), world);
            RoomUnit child = randomlyGeneration(RANDOM.nextInt((int) r.SEED),
                    world, newFocus(exit), 1, exitsQueue);
            if (child != null) {
                child.generate(world);
            }
            counter += 1;
        }
        //generateWorld(pseudoSeed, world, randomFocus());
        // draws the world to the screen
        ter.renderFrame(world);

    }

    private static RoomUnit generateRoom(long seed, Position focus) {
        return new RoomUnit(focus.getDirection(), seed);
    }

    private static HallwayUnit generateHallway(long seed, Position focus) {
        return new HallwayUnit(focus.getDirection(), seed);
    }

    /**
     * this method simply return a room or a hallway randomly
     * @param seed nothing special here
     * @param world the whole board this should not be changed
     * @param focus this should be changed as you might use recursion.You should pass the exit position to it.
     * @return generally return a random room or hallway
     * however, it should return null if attempt more than 3 times.
     */
    private static RoomUnit randomlyGeneration(long seed, TETile[][] world, Position focus, int tries,
                                               PriorityQueue exitsQueue) {
        int randomNum = (int) (seed % 3);
        RoomUnit newObject;
        if (randomNum < 2) {
            newObject = generateRoom(seed, focus);
        }else {
            newObject = generateHallway(seed, focus);
        }
        newObject.setFocus(focus);
        if (newObject.checkIndexError(world) || newObject.checkOverlap(world)) {
            if (tries > 3) {
                return null;
            }
            newObject = randomlyGeneration(RANDOM.nextInt((int) seed), world, focus, tries + 1, exitsQueue);
        } else {
            newObject.generate(world);
            for (Position exit : newObject.getExits()) {
                exitsQueue.add(exit);
            }
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

    private static void generateWorld(long seed, TETile[][] world, Position focus) {
        double complexity = 0;
        RoomUnit r = randomlyGeneration(seed, world, focus, 1);
        long newSeed = RANDOM.nextInt((int) seed);
        while (complexity < 0.75) {
            Position currExit = exitsQueue.poll();
            newSeed = RANDOM.nextInt((int) newSeed);
            RoomUnit newRoom = randomlyGeneration(newSeed, world, realExit(currExit), 1);
            complexity += (double) newRoom.getSize() / 50;
        }
    }
    */

    /**
     * this function is to return the focus of a new room
     * @param exit the previous room's exit
     * @return the generated new focus's position
     */
    private static Position realExit(Position exit) {
        int m;
        int n;
        int direction = exit.getDirection();
        switch (direction) {
            case 0: m = 0;
                n = 1;
                break;
            case 1: m = 0;
                n = -1;
                break;
            case 2: m = -1;
                n = 0;
                break;
            default: m = 1;
                n = 0;
        }
        Position returnFocus = new Position(exit.getX() + m, exit.getY() + n, exit.getDirection());
        return returnFocus;
    }

    /**
     * this function is to make a exit and focus visible
     * @param target
     * @param world
     */
    private static void chisel(Position target, TETile[][] world) {
        world[target.getX()][target.getY()] = Tileset.FLOWER;
    }

    /**
     *
     * @param exit the original exit which is in the matrix
     * @return the new focus
     */
    private static Position newFocus(Position exit) {
        return realExit(realExit(realExit(exit)));
    }

    /**
     * This is used for debugging.
     */
    public static void main(String[] args) {
        worldGenerator();
    }

}
