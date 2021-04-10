package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Hexagon {
    // variable here
    int sideLength;
    int height;
    int width;
    TETile[][] realHexagon;
    TETile pattern;


    public Hexagon(int sideLength, TETile pattern) {
        this.sideLength = sideLength;
        this.height = 2 * sideLength;
        this.width = 3 * sideLength - 2;
        this.pattern = pattern;
        realHexagon = new TETile[width][height];
        fillHexagon();
    }

    public void drawIt() {
        int maximum = 3 * sideLength - 2;
        // draw the top half hexagon
        for (int i = sideLength; i < maximum + 1; i += 2) {
            printLine(i, maximum, pattern);
        }
        // draw the bottom half hexagon
        for (int i = 3 * sideLength - 2; i > maximum + 1; i -= 2) {
            printLine(i, maximum, pattern);
        }
    }

    /**
     *
     * @param p the specified pattern number
     * @param q the maximum number of patterns in one row of the hexagon
     * @param pattern the parameter that is set when creating this hexagon
     */
    private static void printLine(int p, int q, TETile pattern) {
        int spaceNumber = (p - q) / 2;
        for (int i = 0; i < spaceNumber; i++) {
            System.out.print(" ");
        }
        for (int i = 0; i < p; i++) {
            System.out.print(pattern);
        }
        System.out.println();
    }

    private void fillLine(int row, int tileNumber) {
        int begin = (width / 2) - 1;
        for (int i = 0; i < tileNumber; i++) {
            realHexagon[begin + i][row] = this.pattern;
        }
    }

    private void fillHexagon() {
        int tileNumber = sideLength;
        for (int row = 0; row < sideLength; row++, tileNumber += 2) {
            fillLine(row, tileNumber);
        }
        for (int row = sideLength; row < height; row++, tileNumber -= 2) {
            fillLine(row, tileNumber);
        }
    }

    public TETile[][] getRealHexagon() {
        return realHexagon;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(50, 50);
        Hexagon test = new Hexagon(4, Tileset.FLOWER);
        ter.renderFrame(test.getRealHexagon());
        
    }

}
