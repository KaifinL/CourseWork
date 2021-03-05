package gitlet;

import java.io.File;
import java.text.SimpleDateFormat;
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

    public static void makeCommit(String message) {
        /**
         * TODO: set the commit message, date, parent(the last commit)
         */
        Date dateObj = new Date();
        Commit newCommit = new Commit(message, dateObj);
        File parentFile = Utils.join(Commits, newCommit.getParentId());
        newCommit = readObject(parentFile, Commit.class);
        newCommit.parentId = newCommit.id;
        newCommit.id = Utils.sha1(newCommit);
        newCommit.makeChange(message);
        newCommit.saveCommit();
        Head = newCommit;
    }

    public static boolean removeFile(String Filename) {
        File tobeRemoved = Utils.join(GITLET_DIR, Filename);
        String FileContent = readContentsAsString(tobeRemoved);
        Commit currentCommit = Head;
        boolean changed = false;
        if (!tobeRemoved.exists()) {
            changed = Utils.restrictedDelete(Filename);
        }else if(StagingArea.addition.containsKey(Filename)) {
            StagingArea.addition.remove(Filename);
            changed = true;
        }else if(currentCommit.snapshot.containsKey(Filename)) {
            StagingArea.removal.put(Filename, FileContent);
            tobeRemoved.delete();
            changed = true;
        }
        return changed;
    }

    /** haven't done with merge log information yet */
    public static void log() {
        Commit curr = Head;
        while(curr != null) {
            System.out.println("===");
            System.out.println("commit " + curr.id);
            SimpleDateFormat formatter= new SimpleDateFormat("d, EEE MMM HH:mm:ss yyyy Z");
            System.out.println("Date: " + formatter.format(curr.getTimestamp()));
            System.out.println(curr.getMessage());
            System.out.println();
            curr = curr.getParent();
        }
    }

}
