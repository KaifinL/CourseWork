package byow.Core;


import static byow.Core.Utils.*;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import java.io.File;
import java.io.IOException;


public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */

    public void interactWithKeyboard() {
        boolean run = true;
        Avatar avatar = new Avatar();
        avatar.drawStart();
        String seed = "";
        String typed;
        int  flag = 0;
        for (int i = 0; run; i += 1) {
            if (StdDraw.hasNextKeyTyped()) {
                typed = Character.toString(StdDraw.nextKeyTyped());
                typed = typed.toUpperCase();
                if (flag == 1) {
                    if (typed.equals("S")) {
                        break;
                    }
                    seed += typed;
                    avatar.drawFrame(seed);
                    continue;
                }
                switch (typed) {
                    case "N":
                        avatar.drawStartTwo();
                        flag = 1;
                        break;
                    case "Q":
                        System.exit(0);
                        return;
                    case "L":
                        flag = 2;
                        avatar = SaveLoad.loadAvatar();
                        if (avatar == null) {
                            System.exit(0);
                            return;
                        }
                        avatar.setBackupworld();
                        avatar.setOriginstate();
                        avatar.setOriginpoints();
                        avatar.setOrigindark();
                        avatar.setOriginpos();
                        break;
                    case "C":
                        avatar.setAppearance();
                        avatar.drawStart();
                        break;
                    case "R":
                        flag = 2;
                        avatar = SaveLoad.loadAvatar();
                        avatar.replay();
                        break;
                    default:
                        break;
                }
                if (flag == 2) {
                    break;
                }
            }
        }
        if (run) {
            if (flag == 2) {
                avatar.playerInput();
            } else {
                long realSeed = Long.parseLong(seed);
                RandomWorld newRandomWorld = new RandomWorld(realSeed);
                TETile[][] finalWorldFrame = newRandomWorld.worldGenerator();
                Position door = newRandomWorld.getDoor();
                Position startPos = newRandomWorld.getStartPos();
                Position pos = new Position(startPos.getX(),
                        startPos.getY(), startPos.getDirection());
                avatar.setStartpos(startPos);
                avatar.setPos(pos);
                avatar.setDoor(door);
                avatar.setSeedNum(realSeed);
                avatar.setWorld(finalWorldFrame);
                avatar.playerInput();
            }
        }
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
        // from online :Extract digits from string - StringUtils Java @stack overflow
        TETile[][] finalWorldFrame;
        Avatar avatar = new Avatar();
        String manipulation = input.substring(1);
        if (input.contains("L") || input.contains("l")) {
            avatar = SaveLoad.loadAvatar();
            avatar.setBackupworld();
            avatar.setOriginstate();
            avatar.setOriginpoints();
            finalWorldFrame = avatar.getWorld();
            avatar.systemInput(manipulation);
        } else {
            long realSeed;
            realSeed = Long.parseLong(input.replaceAll("[^0-9]", ""));
            int seedNum = String.valueOf(realSeed).length();
            manipulation = input.substring(seedNum + 1);
            if (!digitExist(input)) {
                manipulation = input;
            }
            RandomWorld newRandomWorld = new RandomWorld(realSeed);
            finalWorldFrame = newRandomWorld.worldGenerator();
            Position door = newRandomWorld.getDoor();
            Position startPos = newRandomWorld.getStartPos();
            Position pos = new Position(startPos.getX(), startPos.getY(), startPos.getDirection());
            avatar.setStartpos(startPos);
            avatar.setPos(pos);
            avatar.setDoor(door);
            avatar.setWorld(finalWorldFrame);
            avatar.systemInput(manipulation);
        }
        return finalWorldFrame;
    }

    public static void stringManipulation(String target) throws IOException {
        String firstLetter = target.substring(0, 1);
        switch (firstLetter) {
            case "N":
                interactWithInputString(target);
                break;
            default:
                File newestAvatar = join(SaveLoad.CWD, "NewestAvatar.txt");
                if (!newestAvatar.exists()) {
                    Utils.exitWithError("No previous file exists, please create one first");
                }
                interactInLoading(target);
        }
    }

    /**
     * this function is help to judge if a target string contains a digit or not
     * @param target the target string you want to judge
     * @return true if there is a digit in the string false otherwise
     */
    private static boolean digitExist(String target) {
        return target.matches(".*\\d.*");
    }

    private static String excludeTermination(String target) {
        if (target.contains(":Q")) {
            int index = target.indexOf(":Q");
            return target.substring(0, index);
        }
        return target;
    }

    private static int getQIndex(String target) {
        if (target.contains(":Q")) {
            return target.indexOf(":Q");
        }
        return target.length() - 1;
    }

    private static void save(Avatar avatar, String target) throws IOException {
        if (target.contains(":Q")) {
            SaveLoad.save(avatar);
        }
    }

    private static void interactInLoading(String input) throws IOException {
        File previousAvatarFile = join(SaveLoad.CWD, "NewestAvatar.txt");
        Avatar previousAvatar = readObject(previousAvatarFile, Avatar.class);
        String excludeQ = excludeTermination(input);
        String manipulation = "";
        if (excludeQ.length() > 3) {
            manipulation += excludeQ.substring(1);
        }
        previousAvatar.systemInput(manipulation);
        save(previousAvatar, input);
    }

    public static void main(String[] args) throws IOException {
        interactWithInputString("lwsd");
    }

}
