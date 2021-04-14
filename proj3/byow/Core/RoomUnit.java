package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Unit of the Room.
 *
 * Notes by kefin:
 * 1. this class has 4 methods
 *      a.create a room by simply call 'new RoomUnit' and then 3 types can be chosen.
 *      b.generate -- draw the real room into the board
 *      c.check load and index but the parameter is still a problem.
 *      d.create exits actually there are two methods for this.
 */
public class RoomUnit {
    private int width;
    private int length;
    private int direction;
    private long SEED = 2873123;

    private Position focus;
    private Position[] exits;
    private int size = 0;
    public static final int SCOPE = 5;

    public void setFocus(Position focus) {
        this.focus = focus;
    }

    public int getSize() {
        return this.size;
    }

    public void changeLenWid() {
        int t = this.length;
        this.length = this.width;
        this.width = t;
    }

    /**
     * Pick random width between [2, 6] or length between [2, 7].
     */
    public static int randomEdge(long seed) {
        Random r = new Random(seed);
        return 2 + r.nextInt(SCOPE);
    }

    /**
     * Return a random direction, 0-4 correspond to up, down, left, right.
     */
    public static int randomDirection(long seed) {
        Random r = new Random(seed);
        return r.nextInt(4);
    }

    /**
     * Constructors
     */
    public RoomUnit(long seed) {
        this(randomEdge(seed), randomEdge(seed + 1), randomDirection(seed), seed);
    }

    public RoomUnit(int direction, long seed) {
        this(randomEdge(seed), randomEdge(seed + 1), direction, seed);
    }

    public RoomUnit(int width, int length, int direction, long seed) {
        this.width = width;
        this.length = length;
        this.direction = direction;
        this.SEED = seed;
        this.size = (width + 2) * (length + 2);
    }


    /**
     * Check if creating the room will cause out of bound error.
     * This method will also shift focus or exchange width and length
     * ro get the Down version.
     * question here:
     * 1. still the parameter problem it is really hard to pass the created
     * Room to this method I will need to firstly convert the room into a 2D world.
     * 2. the checkIn series should be set private?(style problem not a big deal)
     */
    public boolean checkIndexError(TETile[][] world) {
        switch (this.direction) {
            case 0: return checkIndexErrorUp(world);
            case 1: return checkIndexErrorDown(world);
            case 2: return checkIndexErrorLeft(world);
            default: return checkIndexErrorRight(world);
        }
    }



    public boolean checkIndexErrorDown(TETile[][] world) {
        int xLow = this.focus.x - 1;
        int xHigh = this.focus.x + this.width;
        int yLow = this.focus.y - this.length;
        int yHigh = this.focus.y + 1;
        if (xLow < 0 || xHigh >= world.length
                || yLow < 0 || yHigh >= world[0].length) {
            return true;
        }
        return false;
    }

    public boolean checkIndexErrorUp(TETile[][] world) {
        this.focus.changePos(0, this.length - 1);
        return checkIndexErrorDown(world);
    }

    public boolean checkIndexErrorLeft(TETile[][] world) {
        this.focus.changePos(1 - this.length, 0);
        this.changeLenWid();
        return checkIndexErrorDown(world);
    }

    public boolean checkIndexErrorRight(TETile[][] world) {
        this.changeLenWid();
        return checkIndexErrorDown(world);
    }

    /**
     * Check if the location is already occupied.
     * Warning: Check IndexError first, since it will change focus and width/length
     * according the direction.
     */
    public boolean checkOverlap(TETile[][] world) {
        for (int i = 0; i < this.width + 2; i += 1) {
            for (int j = 0; j < this.length + 2; j += 1) {
                if (!world[this.focus.x - 1 + i][this.focus.y + 1 - j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Generate this random room unit.
     */
    public void generate(TETile[][] world) {
        for (int i = 0; i < this.width; i += 1) {
            for (int j = 0; j < this.length; j += 1) {
                world[this.focus.x + i][this.focus.y - j] = Tileset.FLOOR;
            }
        }
        for (int i = 0; i < this.width + 2; i += 1) {
            world[this.focus.x - 1 + i][this.focus.y + 1] = Tileset.WALL;
            world[this.focus.x - 1 + i][this.focus.y - length] = Tileset.WALL;
        }
        for (int j = 0; j < this.length; j += 1) {
            world[this.focus.x - 1][this.focus.y - j] = Tileset.WALL;
            world[this.focus.x + this.width][this.focus.y - j] = Tileset.WALL;
        }
        creatExits(this.SEED);
    }

    /**
     * Create exits.
     * spell error here(createExits rather than creatExits),should be fixed later
     * question here:
     */
    public void creatExits(long seed) {
        Random r = new Random(seed);
        int direction1 = r.nextInt(4);
        int direction2 = r.nextInt(8) % 4;
        if (direction1 == direction2) {
            //create one exit.
            this.exits = new Position[1];
            this.exits[0] = creatOneExit(direction1, seed);
        } else {
            // create two exits
            this.exits = new Position[2];
            this.exits[0] = creatOneExit(direction1, seed);
            this.exits[1] = creatOneExit(direction2, seed);
        }
    }

    /**
     * Create one exit.
     * @param direc Indicate which side the exit lies on.
     * @param seed
     */
    public Position creatOneExit(int direc, long seed) {
        Random r = new Random(seed);
        switch (direction) {
            case 0:
                return new Position(this.focus.x + r.nextInt(this.width),
                        this.focus.y, direc);
            case 1:
                return new Position(this.focus.x + r.nextInt(this.width),
                        this.focus.y - this.length + 1, direc);
            case 2:
                return new Position(this.focus.x,
                        this.focus.y - r.nextInt(this.length), direc);
            default:
                return new Position(this.focus.x + this.width - 1,
                        this.focus.y - r.nextInt(this.length), direc);
        }
    }

    public Position[] getExits() {
        return exits;
    }

    public long getSEED() {
        return SEED;
    }

    /**
     * This is used for debugging.
     */
    public static void main(String[] args) {
        Random r = new Random(7);
        System.out.println(r.nextInt(134));
        /**
         System.out.println(randomEdge());
         System.out.println(randomDirection());
         RoomUnit r = new RoomUnit();
         RoomUnit r2 = new RoomUnit();
         r2.focus = new Position(28, 17);
         r.focus = new Position(30,15);
         int WIDTH = 100;
         int HEIGHT = 40;
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
         if (r2.checkOverlap(world)) {
         System.out.println("overlap");
         }
         r2.generate(world);
         ter.renderFrame(world);*/
    }
}
