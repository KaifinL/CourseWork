package byow.Core;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import byow.TileEngine.TETile;
import org.junit.Test;

import java.io.IOException;

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
        Engine.stringManipulation("LDS:Q");
    }

    /**
     * some thinkings about the extra credits:
     * 1. add a new door,which is fake.If you enter a fake door, you will need
     * to enter another completely new world.We won't tell you which one is true
     * unless you have eaten all the possible points.
     * (To complete this, we can create a door class which has a distribution of
     * type Boolean)
     *
     * 2. whenever you choose to pass a hallway, you may create a door which will
     * no longer allow you to in again.By 'may' I mean this can be random or by some
     * specific manipulation, this depends on how we design it.
     *
     * 3. to get more points you want we give you a chance to chisel a wall, which
     * allows you to nearly generate a small world randomly,but it is comprehensively
     * connect with the previous world.(by doing this , we can design a 'skill' like
     * pressing 'c' and than we will make a wall into a 'floor' and again generate another
     * world.)
     *
     *
     */

}
