package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Unit of the Room.
 */
public class RoomUnit {
    public int width;
    public int length;
    public int direction;
    public Position focus;
    public Position[] exits;

    public static final long SEED = 287312;
    public static final Random RANDOM = new Random(SEED);

    public void setFocus(Position focus) {
        this.focus = focus;
    }

    public void changeLenWid() {
        int t = this.length;
        this.length = this.width;
        this.width = t;
    }

    /**
     * Pick random width or length between [1, 6].
     */
    public static int randomEdge() {
        return 2 + RANDOM.nextInt(5);
    }

    /**
     * Return a random direction, 0-4 correspond to up, down, left, right.
     */
    public static int randomDirection() {
        return RANDOM.nextInt(4);
    }

    /**
     * Constructors
     */
    public RoomUnit() {
        this(randomEdge(), randomEdge(), randomDirection());
    }

    public RoomUnit(int width, int length, int direction) {
        this.width = width;
        this.length = length;
        this.direction = direction;
    }


    /**
     * Check if creating the room will cause out of bound error.
     */
    public boolean checkIndexError(){
        return false;
    }

    /**
     * Check if the location is already occupied.
     * Warning: Check IndexError first.
     */
    public boolean checkOverlap(TETile[][] world) {
        if (checkOverlapDown(world) || checkOverlapUp(world) ||
                checkOverlapLeft(world) || checkOverlapRight(world)) {
            return true;
        }
        return false;
    }

    public boolean checkOverlapDown(TETile[][] world) {
        for (int i = 0; i < this.width + 2; i += 1) {
            for (int j = 0; j < this.length + 2; j += 1) {
                if (!world[this.focus.x - 1 + i][this.focus.y + 1 - j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkOverlapUp(TETile[][] world) {
        this.focus.changePos(0,this.length - 1);
        return checkOverlapDown(world);
    }

    public boolean checkOverlapLeft(TETile[][] world) {
        this.focus.changePos(1 - this.length,0);
        this.changeLenWid();
        return checkOverlapDown(world);
    }

    public boolean checkOverlapRight(TETile[][] world) {
        this.changeLenWid();
        return checkOverlapDown(world);
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
    }


    /**
     * This is used for debugging.
     */
    public static void main(String[] args) {
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
        ter.renderFrame(world);
    }
}

