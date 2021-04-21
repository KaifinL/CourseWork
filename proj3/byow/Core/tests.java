package byow.Core;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import byow.TileEngine.TETile;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class tests {
    private static final String testInput = "n5197880843569031643s";

    @Test
    public void testTwoSameInput() throws IOException {
        TETile[][] world1 = Engine.interactWithInputString(testInput);
        TETile[][] world2 = Engine.interactWithInputString(testInput);
        assertArrayEquals(world1, world2);
    }

    @Test
    public void testMethod() {
        String str="N543SWWWWAA";
        long realSeed= Long.parseLong(str.replaceAll("[^0-9]", ""));
        int seedNum = String.valueOf(realSeed).length();
        String manipulation = str.substring(seedNum + 1);
        System.out.println(manipulation);
    }


    @Test
    public void paradigm() {
        RandomWorld newWorld = new RandomWorld(292382);
        TETile[][] finalWorldFrame = newWorld.worldGenerator();
        Position door = newWorld.getDoor();
        Position start = newWorld.getStartPos();
        System.out.println(door.getX() + "   " + door.getY() + "    " + door.getDirection());
        System.out.println(start.getX() + "    " + start.getY() + "     " + start.getDirection());

    }

    @Test
    public void testDigitFunc() {
        String str = "N23WESJKENS";
        assertTrue(str.matches(".*\\d.*"));
        System.out.println(str.substring(1));
        //System.out.println(Engine.getQIndex(str));
    }

    @Test
    public void setTestInput() throws IOException {
        Engine.stringManipulation("L999SDDDWWWDDD:Q");
    }

}
