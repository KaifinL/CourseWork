package gitlet;

import java.io.File;

/** this class is basically for adding and removing things, just to make things easier. */
public class StagingArea {

    /**
     * some useful variables are listed here
     */

    public static final File StagingArea = Utils.join(Repository.GITLET_DIR, "StagingArea");
    public static final File addition = Utils.join(StagingArea, "addition");
    public static final File removal = Utils.join(StagingArea, "removal");


    /**
     * We should now list the methods which will be helpful storing information of files.
     * the contents needed to be stored are file's name,file's content
     */
}
