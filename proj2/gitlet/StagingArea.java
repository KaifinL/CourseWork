package gitlet;

import java.io.File;

/** this class is basically for adding and removing things, just to make things easier. */
public class StagingArea {

    /**
     * some useful variables are listed here
     */
    public static final File STAGINGAREA = Utils.join(Repository.GITLET_DIR, "StagingArea");
    public static final File ADDITION = Utils.join(STAGINGAREA, "addition");
    public static final File REMOVAL = Utils.join(STAGINGAREA, "removal");


    /**
     * We should now list the methods which will be helpful storing information of files.
     * the contents needed to be stored are file's name,file's content
     */
}
