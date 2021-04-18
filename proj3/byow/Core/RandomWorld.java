package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.PriorityQueue;
import java.util.Random;

public class RandomWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private int roomNum;
    private long seed;
    private Random random;

    /**
     * Return a random location on the map.
     */

    public RandomWorld(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }

    private static Position randomFocus(long seed) {
        Random r = new Random(seed);
        return new Position(r.nextInt(HEIGHT), r.nextInt(WIDTH), 1);
    }

    /**
     * This is just skeleton code.
     */
    public TETile[][] worldGenerator() {
        long seed2 = turnPositive(this.seed);
        PriorityQueue<Position> exitsQueue = new PriorityQueue<>();
        long pseudoSeed = seed2;
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
        Position initialFocus = randomFocus(pseudoSeed);
        initialFocus.setDirection();
        RoomUnit r = initialization(world, pseudoSeed, exitsQueue);
        int counter = 0;
        // generate the rooms by exits
        while (counter < 50) {
            Position exit = exitsQueue.poll();
            if (exit == null) {
                break;
            }
            r.reviseSeed();
            RoomUnit child = randomlyGeneration(this.random.nextInt((int) r.getSEED()),
                    world, newFocus(exit), 1, exitsQueue);
            if (child != null) {
                chisel(realExit(exit), world);
                chisel(realExit(realExit(exit)), world);
            }
            counter += 1;
        }
        //generateWorld(pseudoSeed, world, randomFocus());
        // draws the world to the screen
        ter.renderFrame(world);
        return world;
    }

    private static RoomUnit generateRoom(long seed, Position focus) {
        return new RoomUnit(focus.getDirection(), seed);
    }

    private static HallwayUnit generateHallway(long seed, Position focus) {
        return new HallwayUnit(focus.getDirection(), seed);
    }

    /**
     * this method simply return a room or a hallway randomly
     * @param world the whole board this should not be changed
     * @return generally return a random room or hallway
     * however, it should return null if attempt more than 3 times.
     */
    private RoomUnit randomlyGeneration(long seed2, TETile[][] world, Position focus,
                                        int tries, PriorityQueue exitsQueue) {
        long originSeed = turnPositive(seed2);
        Position getOrigin = new Position(focus.getX(), focus.getY(), focus.getDirection());
        int randomNum = (int) (originSeed % 3);
        RoomUnit newObject;
        if (randomNum < 1 && tries < 2) {
            newObject = generateRoom(originSeed, focus);
        } else {
            newObject = generateHallway(originSeed, focus);
        }
        newObject.setFocus(focus);
        if (newObject.checkIndexError(world) || newObject.checkOverlap(world)) {
            if (tries > 6) {
                return null;
            }
            newObject = randomlyGeneration(this.random.nextInt((int) originSeed), world,
                    getOrigin, tries + 1, exitsQueue);
        } else {
            newObject.generate(world);
            makeFlower(focus, world);
            roomNum += 1;
            makeDoor(focus, world);
            for (Position exit : newObject.getExits()) {
                exitsQueue.add(exit);
            }
        }
        return newObject;
    }

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
        world[target.getX()][target.getY()] = Tileset.FLOOR;
    }

    /**
     *
     * @param exit the original exit which is in the matrix
     * @return the new focus
     */
    private static Position newFocus(Position exit) {
        return realExit(realExit(realExit(exit)));
    }

    private static RoomUnit initialization(TETile[][] world, long seed, PriorityQueue exitsQueue) {
        Position focus = new Position(50, 20, 0);
        RoomUnit newObject = generateRoom(seed, focus);
        newObject.setFocus(focus);
        newObject.generate(world);
        for (Position exit : newObject.getExits()) {
            exitsQueue.add(exit);
        }
        return newObject;
    }

    private static long turnPositive(long seed) {
        if (seed < 0) {
            return -seed;
        }
        if (seed == 0) {
            return 28739;
        }
        return seed;
    }

    /**
     * create the flower in the exit to help the user where to go next
     * @param focus the exit's focus
     * @param world the 2D world we initially created
     */
    private static void makeFlower(Position focus, TETile[][] world) {
        world[focus.getX()][focus.getY()] = Tileset.FLOWER;
    }


    /**
     * this function will judge if we should create the door according to the roomNum
     * @return true if we need to create a door false otherwise
     */
    private boolean createDoor() {
        if (this.roomNum == this.seed % 5) {
            return true;
        }
        return false;
    }

    /**
     * this fuction will automatically judge if we will need to create the door.
     * @param focus the beginning focus we need to pass in
     * @param world the 2D world we initialized at the beginning of the proj.
     */
    private void makeDoor(Position focus, TETile[][] world) {
        if (createDoor()) {
            Position door = realExit(focus);
            world[door.getX()][door.getY()] = Tileset.LOCKED_DOOR;
        }
    }

    /**
     * This is used for debugging.
     */
    //public static void main(String[] args) {
    //worldGenerator();
    //}

}
