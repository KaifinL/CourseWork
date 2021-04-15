package byow.Core;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import byow.TileEngine.TETile;
import org.junit.Assert;
import org.junit.Test;

public class tests {
    @Test
    public void testTwoSameInput() {
        TETile[][] world1 = Engine.interactWithInputString("n5197880843569031643s");
        TETile[][] world2 = Engine.interactWithInputString("n5197880843569031643s");
        assertArrayEquals(world1, world2);
    }
}