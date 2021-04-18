package byow.Core;

import byow.TileEngine.TETile;

import java.io.File;
import java.io.IOException;

import static byow.Core.Utils.*;
import static byow.Core.Utils.readObject;
import static byow.Core.Utils.writeObject;

public class SaveLoad {
    /**
     * Save the current world, essentially TETile[][]
     * and quit.
     */
    private static final File CWD = new File(System.getProperty("user.dir"));

    public static void initialize() {}

    public static void save(TETile[][] world) {
        File outFile = new File("newest world");//We can change the pathname(maybe time) to show different saves.
        writeObject(outFile, world);//I think this world may not be the final object.
    }

    /**
     * Load a previous saved world.
     * I think we can modify this so we can choose which one to load.
     * If no previous save, simply quit and the UI interface should close with no errors produced
     */
    public static TETile[][] load() {
        TETile[][] world;
        File inFile = new File("newest world");
        world = readObject(inFile, TETile[][].class);
        return world;
    }

    private static void createFile(File fileName) throws IOException {
        if (fileName.exists()) {
            return;
        }
        fileName.createNewFile();
    }

}

/**
 * kefin's thinking:
 * 1. maybe we should also record the avatar? since the avatar is also very important
 * 2. what do you mean by choose one to load? if there are multiple choices, we will need
 * to provide those information in the menu.
 * 3. it seems like we lack the path since the statement above will definitely fail.
 */

