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
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");

    public static final File Commits = Utils.join(GITLET_DIR, "commits");

    public static final File HEAD = Utils.join(Commits, "Head");
    /* TODO: fill in the rest of this class. */

    public static Commit master;

    public static void setupPersistence() {
        GITLET_DIR.mkdir();
        Commits.mkdir();
        Date initDate = new Date(0);
        Commit initialCommit = new Commit("initial commit", initDate);
        initialCommit.saveCommit();
        try {
            HEAD.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeObject(HEAD, initialCommit);
    }

    public static void add(String fileName) {
        File tobeAdded = Utils.join(CWD, fileName);
        if (!tobeAdded.exists()) {
            Utils.exitWithError("File does not exist.");
        }else {
            Blob tobeAdd = new Blob(tobeAdded);
            if (StagingArea.containsName(StagingArea.addition, fileName)) {
                Commit nHead = Utils.readObject(HEAD, Commit.class);
                String donknow = nHead.snapshot.get(fileName);
                StagingArea.addition.remove(fileName);
                if (!tobeAdd.getBlobId().equals(donknow)){
                    StagingArea.addition.put(fileName, new Blob(tobeAdded).getBlobId());
                }
            }else if (StagingArea.containsName(StagingArea.removal, fileName)) {
                StagingArea.removal.remove(fileName);
            }else {
                StagingArea.addition.put(fileName, tobeAdd.getBlobId());
            }
        }
    }

    public static void makeCommit(String message) {
        /**
         * TODO: set the commit message, date, parent(the last commit)
         */
        Date dateObj = new Date();
        Commit newCommit = readObject(HEAD, Commit.class);
        newCommit.parentId = newCommit.id;
        newCommit.parent2Id = null;
        newCommit.makeChange(message, dateObj);
        newCommit.id = Utils.sha1(newCommit.toString());
        newCommit.saveCommit();
        writeObject(HEAD, newCommit);
    }

    public static void finalCommit(String[] args) {
        if (args.length == 1) {
            Utils.exitWithError("Please enter a commit message.");
        }
        String message = args[1];
        if (StagingArea.addition == null) {
            Utils.exitWithError("No changes added to the commit.");
        }else {
            Repository.makeCommit(message);
        }
    }

    public static boolean removeFile(String Filename) {
        File tobeRemoved = Utils.join(GITLET_DIR, Filename);
        Commit nHead = Utils.readObject(HEAD, Commit.class);
        Commit currentCommit = nHead;
        boolean changed = false;
        if (!tobeRemoved.exists()) {
            changed = Utils.restrictedDelete(Filename);
        }else if(StagingArea.addition.containsKey(Filename)) {
            StagingArea.addition.remove(Filename);
            changed = true;
        }else if(currentCommit.snapshot.containsKey(Filename)) {
            StagingArea.removal.put(Filename, new Blob(tobeRemoved).getBlobId());
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
        if (cur.parent2Exist()) {
            System.out.println("Merged development into master.");
        }
        System.out.println();
    }

    /** haven't done with merge log information yet */
    public static void log() {
        Commit nHead = Utils.readObject(HEAD, Commit.class);
        Commit curr = nHead;
        while(curr != null) {
            logHelper(curr);
            curr = curr.getParent();
        }
    }

    /** create the target file if it doesn't exist
     */
    public static void createFile(File targetFile) {
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public static void checkout(String[] args) {
        if (args[1].equals("--")) {
            File targetFile = Utils.join(CWD, args[2]);
            createFile(targetFile);
            Commit nHead = Utils.readObject(HEAD, Commit.class);
            String targetContent = nHead.snapshot.get(args[2]);
            Utils.writeContents(targetFile, targetContent);
        }else if (args[2].equals("--")) {
            File targetFile = Utils.join(CWD, args[3]);
            createFile(targetFile);
            File commitFile = Utils.join(Commits, args[1]);
            Commit targetCommit = Utils.readObject(commitFile, Commit.class);
            String targetContent = targetCommit.snapshot.get(args[3]);
            Utils.writeObject(targetFile, targetContent);
        }else {
            /** TODO: haven't done anything with this situation yet!
             *
             */
            String targetName = args[1];
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

    public static void branchFunc(String branchName) {
        Commit nHead = Utils.readObject(HEAD, Commit.class);
        Commit curr = nHead;
        master = curr;
    }

}
