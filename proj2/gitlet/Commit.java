package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.LinkedList;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    /** The time of this Commit. */
    private Date timestamp;

    private Commit parent;
    /** to collect the files in the commit */
    private File snapshot;

    /**
     * TODO: frame a construction that stores all the files.This should be a instance variable since
     * every commit's files are different and that's also what you have to checkout.
     */


    /* TODO: fill in the rest of this class. */

    /** set the commit message */
    public void setMessage(String message) {
        this.message = message;
    }

    /** initialize the commit */
    public Commit() {
        this.message = "initial commit";
        this.timestamp = new Date(0);
        snapshot.mkdir();

    }

    /** this is to create a new commit but not the initialized one */
    public Commit(String message, Date timestamp, Commit parent) {
        this.message = message;
        this.timestamp = timestamp;
        this.parent = parent;
    }


    /** this method will return the copy of the commit instead of changing the origin commit */
    public static Commit copy(Commit b) {
        Commit returnCommit = new Commit();
        returnCommit.timestamp = b.timestamp;
        returnCommit.snapshot = b.snapshot;
        return returnCommit;
    }

    /** set the original commit time */


    /**
     * Saves a snapshot of tracked files in the current commit and staging area
     * so they can be restored at a later time, creating a new commit.
     * The commit is said to be tracking the saved files. By default,
     * each commit’s snapshot of files will be exactly the same as its parent commit’s snapshot of files;
     * it will keep versions of files exactly as they are, and not update them. A commit will only update
     * the contents of files it is tracking that have been staged for addition at the time of commit,
     * in which case the commit will now include the version of the file that was staged instead of the
     * version it got from its parent. A commit will save and start tracking any files that were staged
     * for addition but weren’t tracked by its parent. Finally, files tracked in the current commit may be
     * untracked in the new commit as a result being staged for removal by the rm command (below).
     *
     * The bottom line: By default a commit has the same file contents as its parent.
     * Files staged for addition and removal are the updates to the commit. Of course,
     * the date (and likely the message) will also different from the parent.
     */
}
