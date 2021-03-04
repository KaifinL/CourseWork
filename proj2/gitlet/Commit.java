package gitlet;

// TODO: any imports you need here


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
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
    public HashMap<String, String> snapshot;

    public String id;

    public static LinkedList<Commit> CommitCollection;

    /**
     * TODO: frame a construction that stores all the files.This should be a instance variable since
     * every commit's files are different and that's also what you have to checkout.
     */


    /* TODO: fill in the rest of this class. */


    /** initialize the commit */
    public Commit() {
        this.message = "initial commit";
        this.timestamp = new Date(0);
        this.snapshot = null;
        this.id = Utils.sha1(this);
        this.parent = null;
    }

    /** this is to create a new commit but not the initialized one */
    public Commit(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
        this.parent = CommitCollection.getLast();
        CommitCollection.addLast(this);
    }

    /**
     * save each commit in a file just something like 'dog' in lab6
     */
    public void saveCommit() {
        File newCommit = Utils.join(Repository.Commits, this.id + ".txt");
        try {
            newCommit.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils.writeObject(newCommit, this);
    }

    /** get the commit's message */
    public String getMessage() {
        return this.message;
    }

    /** get the commit's id */
    public String getId() {
        return this.id;
    }

    public Commit getParent() {
        return this.parent;
    }
    public Date getTimestamp() {
        return timestamp;
    }

    /** a method that can change a commit's file */
    /** TODO: we haven't done anything with removal yet so this still needed to be revised*/
    public void makeChange(String message) {
        for (String x : snapshot.keySet()) {
            if (StagingArea.containsName(x)) {
                snapshot.remove(x);
            }
        }
        for (String y : StagingArea.addition.keySet()) {
            snapshot.put(y, StagingArea.addition.get(y));
        }
        this.message = message;

        /** by doing this the way we represent the time would be a little bit complex:
         * System.out.println(formatter.format(timestamp));
         * this may cause a time zone error (i don't know whether if it may)
         */
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        this.timestamp = new Date(System.currentTimeMillis());
        StagingArea.addition.clear();
        StagingArea.removal.clear();
        Repository.Head = this;
    }

    /** set the original commit time */


    /**
     * Some additional points about commit:
     * The commit command never adds, changes, or removes files in the working directory
     *   (other than those in the .gitlet directory). The rm command will remove such files,
     *   as well as staging them for removal, so that they will be untracked after a commit.
     *
     * After the commit command, the new commit is added as a new node in the commit tree.
     *
     * The commit just made becomes the “current commit”, and the head pointer now points to it.
     * The previous head commit is this commit’s parent commit.
     *
     * Each commit should contain the date and time it was made.
     *
     * Each commit has a log message associated with it that describes the changes to the files
     * in the commit. This is specified by the user. The entire message should take up only one
     * entry in the array args that is passed to main. To include multiword messages, you’ll have
     * to surround them in quotes.
     *
     * Each commit is identified by its SHA-1 id, which must include the file (blob) references
     * of its files, parent reference, log message, and commit time.
     */
}
