package byow.Core;

//this is actually a focus.

/**
 * A position (x, y) on the map.
 */
public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void changePos(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
