package byow.lab12;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Hexagon {
    // variable here
    int sideLength;
    TETile[][] realHexagon;
    TETile pattern;


    public Hexagon(int sideLength, TETile pattern) {
        this.sideLength = sideLength;
        this.pattern = pattern;

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

    public static void main(String[] args) {
        Hexagon test = new Hexagon(4, Tileset.FLOWER);
        test.drawIt();
    }

}
