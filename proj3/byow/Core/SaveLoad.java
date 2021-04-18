package byow.Core;

import byow.TileEngine.TETile;

import java.io.File;

import static byow.Core.Utils.*;
import static byow.Core.Utils.readObject;
import static byow.Core.Utils.writeObject;

public class SaveLoad {
    /**
     * Save the current world, essentially TETile[][]
     * and quit.
     */
    public static void save(TETile[][] world) {
        File outFile = new File("newest world");//We can change the pathname(maybe time) to show different saves.
        writeObject(outFile, world);//I think this world may not be the final object.
    }

    /**
     * Load a previous saved world.
     * I think we can modified this so we can choose which one to load.
     * If no previous save, simply quit and the UI interface should close with no errors produced
     */
    public static TETile[][] load() {
        TETile[][] world;
        File inFile = new File("newest world");
        world = readObject(inFile, TETile[][].class);
        return world;
    }
}
