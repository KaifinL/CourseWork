package gitlet;

// TODO: any imports you need here


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;

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
    /** to collect the files in the commit */
    public HashMap<String, Blob> snapshot;
    public String parentId;
    public String parent2Id;
    public String id;

    /* TODO: fill in the rest of this class. */


    /** initialize the commit */
    public Commit() {
        this.message = "initial commit";
        this.timestamp = new Date(0);
        this.snapshot = null;
        this.parentId = null;
        this.parent2Id = null;
        this.id = Utils.sha1(this);
    }

    /**
    public Commit(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
        this.id = Utils.sha1(this);
        this.snapshot = null;
        this.parent2Id = null;
    }

    */

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

    public String getParentId() {
        return this.parentId;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public String getParent2Id() {
        return this.parent2Id;
    }

    public boolean parent2Exist() {
        return parent2Id != null;
    }

    /** a method that can change a commit's file
     *  and I think this method works correctly!
     *  */
    /** TODO: we haven't done anything with removal yet so this still needed to be revised*/
    public void makeChange(String message, Date date) {
        for (String x : snapshot.keySet()) {
            if (StagingArea.containsName(x)) {
                snapshot.remove(x);
            }
        }
        for (String y : StagingArea.addition.keySet()) {
            snapshot.put(y, StagingArea.addition.get(y));
        }
        this.message = message;
        this.timestamp = date;
        StagingArea.addition.clear();
        StagingArea.removal.clear();
    }

    // return the parent of the current commit
    public Commit getParent() {
        File parent =Utils.join(Repository.Commits, getParentId()) ;
        Commit parentCommit = Utils.readObject(parent, Commit.class);
        return parentCommit;
    }
}
