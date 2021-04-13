package byow.Core;

//this is actually a focus.

import java.util.Iterator;

/**
 * A position (x, y) on the map.
 */
public class Position implements Comparable {
    public int x;
    public int y;
    public int direction;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void changePos(int x, int y) {
        this.x += x;
        this.y += y;
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

}
