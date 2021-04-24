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
    private int roomNum;


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
        //Position avatarPos = avatar.getPos();
        Position avatarPos = new Position(39, 9, 2);
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
        //Position avatarPos = avatar.getPos();
        Position avatarPos = new Position(39, 9, 2);
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
        int roomNum = 0;
        // main function achievement
        Position entrance = worldEntrance();
        world[entrance.getX()][entrance.getY()] = Tileset.TREE;
        Position realEntrance = realExit(entrance);
        RoomUnit initial = initialization(realEntrance, world, exitsQueue, 0);
        if (initial == null) {
            return world;
        }
        while (roomNum < 10 && !exitsQueue.isEmpty()) {
            Position previousExit = exitsQueue.poll();
            Position newFocus = realExit(realExit(previousExit));
            RoomUnit newRoom = roomGeneration(this.seed, newFocus, 0, exitsQueue);
            roomNum += 1;
        }
        world[realEntrance.getX()][realEntrance.getY()] = Tileset.TREE;
        TERenderer teRenderer = new TERenderer();
        teRenderer.renderFrame(world);
        return this.world;
    }


    private RoomUnit roomGeneration(long seed, Position realFocus,
                                    int tries, PriorityQueue exits2) {
        Position getOrigin = new Position(realFocus.getX(), realFocus.getY(), realFocus.getDirection());
        int randomNum = (int) (seed % 3);
        long originSeed = RandomWorld.turnPositive(seed);
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
            newObject = roomGeneration(this.random.nextInt((int) originSeed),
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


    /**
    private RoomUnit initialHallway(int direction, Position focus, PriorityQueue exits, int length) {

        Position originFocus = new Position(focus.getX(), focus.getY(), focus.getDirection());
        RoomUnit returnHallway = new RoomUnit(2, length, direction, this.seed);
        returnHallway.setFocus(focus);
        if ((returnHallway.checkIndexError(world) || returnHallway.checkOverlap(world))) {
            if (length < 4) {
                return null;
            }
            returnHallway = initialHallway(direction, originFocus, exits, length - 1);
        } else {
            returnHallway.generate(world);
            for (Position exit : returnHallway.getExits()) {
                exits.add(exit);
            }
            return returnHallway;
        }
        return returnHallway;
    }
     */


    private RoomUnit initialization(Position realEntrance, TETile[][] world,
                                     PriorityQueue<Position> exitsQueue, int tries) {
        Position realFocus = new Position(realEntrance.getX(), realEntrance.getY(), realEntrance.getDirection());
        RoomUnit newObject = generateHallway(1, 7, realEntrance.getDirection());
        newObject.setFocus(realFocus);

        /**
        if (newObject.checkOverlap(world) || newObject.checkIndexError(world)) {
            if (tries > 5) {
                return null;
            }
            newObject = initialization(realEntrance, world, exitsQueue, tries + 1);
        }
        if (newObject == null) {
            return null;
        }
         */
        newObject.generate(world);
        Collections.addAll(exitsQueue, newObject.getExits());
        world[realFocus.getX()][realFocus.getY()] = Tileset.SAND;
        return newObject;
    }

    private RoomUnit generateHallway(int width, int length, int direction) {
        return new RoomUnit(width, length, direction, this.seed);
    }



    private static Position newFocus(Position exit) {
        return realExit(realExit(realExit(exit)));
    }


}
