package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

// this class is basically to build the hallway by the given parameter
public class Hallway {
    // variable here
    int length;
    int width;
    TETile pattern;
    TETile[][] realHallway;

    // methods here
    public Hallway(int length, int width, TETile pattern) {
        this.length = length;
        this.width = width;
        this.pattern = pattern;
    }

    public void drawHallway() {

    }

    private void initialize() {
        TERenderer ter = new TERenderer();
        ter.initialize(50, 50);
        TETile[][] world = new TETile[50][50];
        for (int x = 0; x < 50; x += 1) {
            for (int y = 0; y < 50; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

}
