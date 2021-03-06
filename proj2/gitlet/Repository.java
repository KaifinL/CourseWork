package gitlet;


import java.io.File;
import java.io.IOException;
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

    public static void add(String fileName) {
        File tobeAdded = Utils.join(GITLET_DIR, fileName);
        if (!tobeAdded.exists()) {
            Utils.exitWithError("File does not exist.");
        }else {
            if (StagingArea.containsName(fileName)) {
                StagingArea.addition.remove(fileName);
            }
            StagingArea.addition.put(fileName, new Blob(tobeAdded));
        }
    }

    public static void makeCommit(String message) {
        /**
         * TODO: set the commit message, date, parent(the last commit)
         */
        Date dateObj = new Date();
        File parentFile = Utils.join(Commits, Head.getId());
        Commit newCommit = readObject(parentFile, Commit.class);
        newCommit.parentId = newCommit.id;
        newCommit.parent2Id = null;
        newCommit.makeChange(message, dateObj);
        newCommit.id = Utils.sha1(newCommit);
        newCommit.saveCommit();
        Head = newCommit;
    }

    public static boolean removeFile(String Filename) {
        File tobeRemoved = Utils.join(GITLET_DIR, Filename);
        Commit currentCommit = Head;
        boolean changed = false;
        if (!tobeRemoved.exists()) {
            changed = Utils.restrictedDelete(Filename);
        }else if(StagingArea.addition.containsKey(Filename)) {
            StagingArea.addition.remove(Filename);
            changed = true;
        }else if(currentCommit.snapshot.containsKey(Filename)) {
            StagingArea.removal.put(Filename, new Blob(tobeRemoved));
            tobeRemoved.delete();
            changed = true;
        }
        return changed;
    }

    public static void logHelper(Commit cur) {
        System.out.println("===");
        System.out.println("commit " + cur.id);
        SimpleDateFormat formatter= new SimpleDateFormat("d, EEE MMM HH:mm:ss yyyy Z");
        System.out.println("Date: " + formatter.format(cur.getTimestamp()));
        System.out.println(cur.getMessage());
        System.out.println();
        if (cur.parent2Exist()) {
            System.out.println("Merged development into master.");
        }
    }

    /** haven't done with merge log information yet */
    public static void log() {
        Commit curr = Head;
        while(curr != null) {
            logHelper(curr);
            curr = curr.getParent();
        }
    }

    public static void checkout(File targetFile, Blob target) {

    }
    public static void checkout2(String target, File targetFile) {

    }
    public static void globalLog() {
        if (Utils.plainFilenamesIn(Commits) != null) {
            for (String name : Utils.plainFilenamesIn(Commits)) {
                File currFile = Utils.join(Commits, name);
                Commit curr = Utils.readObject(currFile, Commit.class);
                logHelper(curr);
            }
        }
    }

    public static void find(String message) {
        boolean exist = false;
        if (Utils.plainFilenamesIn(Commits) != null) {
            for (String name : Utils.plainFilenamesIn(Commits)) {
                File currFIle = Utils.join(Commits, name);
                Commit curr = Utils.readObject(currFIle, Commit.class);
                if (curr.getMessage().equals(message)) {
                    System.out.println(curr.getId());
                    exist = true;
                }
            }
        }
        if (exist == false) {
            System.out.println("Found no commit with that message.");
        }
    }

}
