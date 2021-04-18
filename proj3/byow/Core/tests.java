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
        String numberOnly= str.replaceAll("[^0-9]", "");
        System.out.println(Long.parseLong(numberOnly));
    }
}
