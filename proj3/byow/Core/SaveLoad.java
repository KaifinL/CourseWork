package byow.Core;

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
    public static final File AVATARS = Utils.join(GAME, "avatars");

    /**
     * this function is basically initialize the whole world
     * @throws IOException no use!
     */
    public static void initialize() {
        // actually if we want to have different versions we will need a directory
        GAME.mkdir();
        AVATARS.mkdir();
        File initialAvatar = join(AVATARS, "newest avatar");
        try {
            initialAvatar.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(Avatar avatar) {
        makedir(GAME);
        makedir(AVATARS);
        File targetAvatar = Utils.join(AVATARS, "NewestAvatar.txt");
        try {
            targetAvatar.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeObject(targetAvatar, avatar);
    }

    /**
     * Load a previous saved world.
     * I think we can modify this so we can choose which one to load.
     * If no previous save, simply quit and the UI interface should close with no errors produced
     */

    public static Avatar loadAvatar() {
        Avatar target;
        File targetAvatar = join(AVATARS, "NewestAvatar.txt");
        if (!targetAvatar.exists()) {
            return null;
        }
        target = readObject(targetAvatar, Avatar.class);
        return target;
    }

    private static void makedir(File dire) {
        if (!dire.exists()) {
            dire.mkdir();
        }
    }

}

/**
 * kefin's thinking:
 * 1. maybe we should also record the avatar? since the avatar is also very important
 * 2. what do you mean by choose one to load? if there are multiple choices, we will need
 * to provide those information in the menu.
 * 3. it seems like we lack the path since the statement above will definitely fail.
 */

