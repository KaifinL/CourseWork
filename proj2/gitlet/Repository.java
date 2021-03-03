package gitlet;

import jh61b.junit.In;

import javax.swing.*;
import java.io.File;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Kaifeng Lin
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /* TODO: fill in the rest of this class. */
    /** The files haven't been staged will be added to this directory. */
    public static File stagingArea = join(GITLET_DIR, ".stagingArea");

    public static void setupPersistence() {
        GITLET_DIR.mkdir();
        Commit initialCommit = new Commit();
        File InitialCommitFile = join(GITLET_DIR, "InitialCommit");
        writeObject(InitialCommitFile, initialCommit);
        Commit master = initialCommit;
        Commit Head = initialCommit;
    }

}
