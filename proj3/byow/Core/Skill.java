package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * this class is about achieving a function that is if the avatar is near a wall,
 * it can press 'c' to make a new world and further explore it!
 */
public class Skill {
    private final Avatar avatar;
    // we need to pass in the previous world so that it can copy the tiles
    private TETile[][] world;
    private final int width;
    private final int height;
    private final long seed;
    private final Random random = new Random();
    private int roomNum = 0;


    public Skill(Avatar avatar, TETile[][] world,
                 int width, int height, long seed) {
        this.avatar = avatar;
        this.world = world;
        this.width = width;
        this.height = height;
        this.seed = seed;
    }


    public void chiselNewWorld() {
        int direction = getDirection();
        if (direction == 4) {
            System.out.println("Please approach the wall");
        } else {
            worldGeneration();
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

    private Position worldEntrance() {
        Position avatarPos = avatar.getPos();
        if (world[avatarPos.getX() + 1][avatarPos.getY()].equals(Tileset.WALL)) {
            return new Position(avatarPos.getX() + 1, avatarPos.getY(), 3);
        }
        if (world[avatarPos.getX() - 1][avatarPos.getY()].equals(Tileset.WALL)) {
            return new Position(avatarPos.getX() - 1, avatarPos.getY(), 2);
        }
        if (world[avatarPos.getX()][avatarPos.getY() + 1].equals(Tileset.WALL)) {
            return new Position(avatarPos.getX(), avatarPos.getY() + 1, 0);
        }
        if (world[avatarPos.getX()][avatarPos.getY() - 1].equals(Tileset.WALL)) {
            return new Position(avatarPos.getX(), avatarPos.getY() - 1, 1);
        }
        return null;
    }

    private TETile[][] worldGeneration() {
        // variables
        PriorityQueue<Position> exitsQueue = new PriorityQueue();
        // main function achievement
        Position entrance = worldEntrance();
        Position realEntrance = realExit(entrance);
        RoomUnit initial = initialization(realEntrance, world, exitsQueue, 0, false);
        if (initial == null) {
            return world;
        }
        world[entrance.getX()][entrance.getY()] = Tileset.FLOOR;
        while (roomNum < 10 && !exitsQueue.isEmpty()) {
            Position previousExit = exitsQueue.poll();
            Position newFocus = realExit(realExit(previousExit));
            RoomUnit newRoom = roomGeneration(this.seed, newFocus, 0, exitsQueue);
            if (newRoom != null) {
                RandomWorld.makeFlower(realExit(previousExit), world, this.seed, this.random);
            }
            roomNum += 1;
        }
        world[realEntrance.getX()][realEntrance.getY()] = Tileset.FLOOR;
        TERenderer teRenderer = new TERenderer();
        teRenderer.renderFrame(world);
        return this.world;
    }


    private RoomUnit roomGeneration(long seedHere, Position realFocus,
                                    int tries, PriorityQueue exits2) {
        Position getOrigin = new Position(realFocus.getX(),
                realFocus.getY(), realFocus.getDirection());
        int randomNum = (int) (seedHere % 3);
        long originSeed = RandomWorld.turnPositive(seedHere);
        RoomUnit newObject;
        if (randomNum < 1 && tries < 2) {
            newObject = RandomWorld.generateRoom(random.nextInt((int) originSeed), realFocus);
        } else {
            newObject = RandomWorld.generateHallway(random.nextInt((int) originSeed), realFocus);
        }
        newObject.setFocus(realFocus);
        if (newObject.checkIndexError(world) || newObject.checkOverlap2(world)) {
            if (tries > 6) {
                return null;
            }
            newObject = roomGeneration(this.random.nextInt((int) originSeed),
                    getOrigin, tries + 1, exits2);
        } else {
            newObject.generate(world);
            RandomWorld.makeFlower(realFocus, world, this.seed, this.random);
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




    private RoomUnit initialization(Position realEntrance,
                                    TETile[][] worldHere, PriorityQueue<Position> exitsQueue, int tries, boolean reverse) {
        Position realFocus = new Position(realEntrance.getX(),
                realEntrance.getY(), realEntrance.getDirection());
        RoomUnit newObject;
        if (!reverse) {
            newObject = generateHallway(1, 5, realEntrance.getDirection());
        } else {
            newObject = generateHallway(5, 1, realEntrance.getDirection());
        }
        newObject.setFocus(realFocus);
        if (newObject.checkIndexError(worldHere) || newObject.checkOverlap2(worldHere)) {
            if (tries > 3) {
                return null;
            }
            newObject = initialization(realEntrance, worldHere, exitsQueue, tries + 1, true);
        }
        if (newObject == null) {
            return null;
        }

        newObject.generate(worldHere);
        Collections.addAll(exitsQueue, newObject.getExits());

        return newObject;
    }

    private RoomUnit generateHallway(int widthHere, int length, int direction) {
        return new RoomUnit(widthHere, length, direction, this.seed);
    }

    public TETile[][] getWorld() {
        return world;
    }
}
