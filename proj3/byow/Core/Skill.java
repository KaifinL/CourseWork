package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * this class is about achieving a function that is if the avatar is near a wall,
 * it can press 'c' to make a new world and further explore it!
 */
public class Skill {
    private Avatar avatar;
    // we need to pass in the previous world so that it can copy the tiles
    private TETile[][] world;
    private int width;
    private int height;
    private long seed;
    private Random random = new Random();
    private int roomNum;

    private void initialize(Avatar avatar, TETile[][] world,
                            int width, int height, long seed) {
        this.avatar = avatar;
        this.world = world;
        this.width = width;
        this.height = height;
        this.seed = seed;
    }


    public void chiselNewWorld(Avatar avatar, TETile[][] world, int width, int height, long seed) {
        initialize(avatar, world, width, height, seed);
        int direction = getDirection();
        switch (direction) {
            case 4:
                System.out.println("Please approach the wall");
                break;
            case 3:

        }

    }

    private int getDirection() {
        Position avatarPos = avatar.getPos();
        if (world[avatarPos.getX() + 1][avatarPos.getY()].equals(Tileset.WALL)) {
            return 3;
        }
        if (world[avatarPos.getX() - 1][avatarPos.getY()].equals(Tileset.WALL)) {
            return 2;
        }
        if (world[avatarPos.getX()][avatarPos.getY() + 1].equals(Tileset.WALL)) {
            return 0;
        }
        if (world[avatarPos.getX() + 1][avatarPos.getY() - 1].equals(Tileset.WALL)) {
            return 1;
        }
        return 4;
    }


    private RoomUnit roomGeneration(int direction, Position realFocus,
                                    int tries, PriorityQueue exits2) {
        Position getOrigin = new Position(realFocus.getX(), realFocus.getY(), realFocus.getDirection());
        int randomNum = (int) (this.seed % 3);
        RoomUnit newObject;
        if (randomNum < 1 && tries < 2) {
            newObject = RandomWorld.generateRoom(seed, realFocus);
        } else {
            newObject = RandomWorld.generateHallway(seed, realFocus);
        }
        newObject.setFocus(realFocus);
        if (newObject.checkIndexError(world) || newObject.checkOverlap(world)) {
            if (tries > 6) {
                return null;
            }

            newObject = roomGeneration(this.random.nextInt((int) seed),
                    getOrigin, tries + 1, exits2);
        } else {
            newObject.generate(world);
            RandomWorld.makeFlower(realFocus, world);
            roomNum += 1;
            for (Position exit : newObject.getExits()) {
                exits2.add(exit);
            }
        }
        return newObject;
    }

    public static Position realExit(Position exit) {
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


    private static Position newFocus(Position exit) {
        return realExit(realExit(realExit(exit)));
    }


}
