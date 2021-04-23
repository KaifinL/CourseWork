package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.PriorityQueue;

/**
 * this class is about achieving a function that is if the avatar is near a wall,
 * it can press 'c' to make a new world and further explore it!
 */
public class Skill {
    private Avatar avatar;
    // we need to pass in the previous world so that it can copy the tiles
    private TETile[][] world;
    int width;
    int height;

    private void initialize(Avatar avatar, TETile[][] world, int width, int height) {
        this.avatar = avatar;
        this.world = world;
        this.width = width;
        this.height = height;
    }


    public void chisel(Avatar avatar, TETile[][] world, int width, int height) {
        initialize(avatar, world, width, height);
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

    private void explore(int direction, Position focus) {
        focus.setDirection(direction);
        Position realFocus = RandomWorld.realExit(focus);
        world[focus.getX()][focus.getY()] = Tileset.FLOOR;
        PriorityQueue<Position> exitsQueue = new PriorityQueue<>();
        TERenderer ter = new TERenderer();
        ter.initialize(width, height);

    }

}
