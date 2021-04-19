package byow.Core;

import byow.TileEngine.TETile;

import java.io.File;
import java.io.IOException;

import static byow.Core.Utils.*;

public class SaveLoad {
    /**
     * Save the current world, essentially TETile[][]
     * and quit.
     */
    private static final File CWD = new File(System.getProperty("user.dir"));
    private static final File GAME = Utils.join(CWD, "game");
    private static final File WORLDS = Utils.join(GAME, "worlds");
    private static final File AVATARS = Utils.join(GAME, "avatars");

    public static void initialize() throws IOException {
        // actually if we want to have different versions we will need a directory
        GAME.mkdir();
        WORLDS.mkdir();
        AVATARS.mkdir();
    }

    public static void save(TETile[][] world, Avatar avatar) throws IOException {
        initialize();
        File outFile = Utils.join(WORLDS, "newest world");//We can change the pathname(maybe time) to show different saves.
        outFile.createNewFile();
        writeObject(outFile, world);//I think this world may not be the final object.
        File targetAvatar = Utils.join(AVATARS, "newest avatar");
        targetAvatar.createNewFile();
        writeObject(targetAvatar, avatar);

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

    public static Avatar loadAvatar() {
        Avatar target;
        File targetAvatar = join(AVATARS, "newest avatar");
        target = readObject(targetAvatar, Avatar.class);
        return target;
    }

}

/**
 * kefin's thinking:
 * 1. maybe we should also record the avatar? since the avatar is also very important
 * 2. what do you mean by choose one to load? if there are multiple choices, we will need
 * to provide those information in the menu.
 * 3. it seems like we lack the path since the statement above will definitely fail.
 */

