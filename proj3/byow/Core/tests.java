package byow.Core;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import byow.TileEngine.TETile;
import org.junit.Assert;
import org.junit.Test;

public class tests {
    private static final String testInput = "n5197880843569031643s";

    @Test
    public void testTwoSameInput() {
        TETile[][] world1 = Engine.interactWithInputString(testInput);
        TETile[][] world2 = Engine.interactWithInputString(testInput);
        assertArrayEquals(world1, world2);
    }

    @Test
    public void testMethod() {
        String str="N543SWWWWAA";
        long realSeed= Long.parseLong(str.replaceAll("[^0-9]", ""));
        int seedNum = String.valueOf(realSeed).length();
        String manipulation = str.substring(seedNum + 1, str.length() - 1);
        System.out.println(manipulation);
    }
}
