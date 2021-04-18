package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        Avatar avatar = new Avatar();
        avatar.drawStart();
        String seed = "";
        String typed;
        int  flag = 0;
        for (int i = 0; true; i += 1) {
            if (StdDraw.hasNextKeyTyped()) {
                typed = Character.toString(StdDraw.nextKeyTyped());
                typed.toUpperCase();
                if (flag == 1) {
                    if (typed.equals("S")) {
                        //input seed to create the world.
                        break;
                    }
                    seed += typed;
                    continue;
                }
                switch (typed) {
                    case "N":
                        //Start to Create NewWorld
                        flag = 1;
                        break;
                    case "Q":
                        //nothing happend, end the game, close the UI
                        break;
                    case "L":
                        //load
                        break;
                    default:
                        //maybe we can add some other keyboard control selection.
                }
            }
        }
        avatar.playerInput();
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        
        String realSeed = (input.substring(1, input.length() - 1));
        RandomWorld newRandomWorld = new RandomWorld(Long.parseLong(realSeed));
        Avatar avatar = new Avatar();
        TETile[][] finalWorldFrame = newRandomWorld.worldGenerator();
        avatar.systemInput("WASD");
        return finalWorldFrame;
    }

    public static void main(String[] args) {
        interactWithInputString("5197880843569031643");
    }
}
