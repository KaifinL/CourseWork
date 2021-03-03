package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class

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


    /* TODO: fill in the rest of this class. */

    /** set the commit message */
    public void setMessage(String message) {
        this.message = message;
    }

    /** initialize the commit */
    public Commit() {
        this.message = "initial commit";
        this.timestamp = new Date(0);
    }


    /** this method will return the copy of the commit instead of changing the origin commit */
    public static Commit copy(Commit b) {
        Commit returnCommit = new Commit();
        returnCommit.timestamp = b.timestamp;
        return returnCommit;
    }

    /** set the original commit time */

}
