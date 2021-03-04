package gitlet;

import java.io.File;
import java.util.Date;
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

    public static final File Commits = join(GITLET_DIR, "commits");
    /* TODO: fill in the rest of this class. */

    public static Commit Head;
    public static Commit Master;



    public static void setupPersistence() {
        GITLET_DIR.mkdir();
        Commits.mkdir();
        Commit initialCommit = new Commit();
        File InitialCommitFile = join(Commits, "InitialCommit");
        writeObject(InitialCommitFile, initialCommit);
        Master = initialCommit;
        Head = initialCommit;
    }

    public static void makeCommit(String message, Commit parent) {
        /**
         * TODO: make a new commit
         * TODO: inherit the snapshot from its parent
         * TODO: update the files from staging area
         * TODO: set the commit message, date, parent(the last commit)
         */
        File parentFile = Utils.join(Commits, parent.getId());
        Commit newCommit = readObject(parentFile, Commit.class);
        newCommit.makeChange(message);
        newCommit.saveCommit();
    }

}
