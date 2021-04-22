package byow.Core;

//this is actually a focus.

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

/**
 * A position (x, y) on the map.
 */
public class Position implements Comparable, Serializable {
    private int x;
    private int y;
    private int direction;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void changePos(int x0, int y0) {
        this.x += x0;
        this.y += y0;
    }

    public void changeAvatarPos(int m, int n, TETile[][] world, TETile floor, TETile tileset) {
        world[x][y] = floor;
        this.x += m;
        this.y += n;
        world[x][y] = tileset;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }


    @Override
    public int compareTo(Object o) {
        return this.x;
    }

    public void setDirection() {
        this.direction = 0;
    }

    public void changeDirection() {
        this.direction += 1;
    }
}
