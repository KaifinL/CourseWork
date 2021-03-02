package gitlet;

// TODO: any imports you need here

import java.util.ArrayList;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.LinkedList;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit {
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
    private LinkedList timestamp;


    /* TODO: fill in the rest of this class. */

    /** set the original commit message */
    public void commitMessage(String message) {
        message = message;
        return;
    }

    /** set the original commit time */
    public void setTimestamp(int h, int min, int sec, String date, int day, String month, int year) {
        timestamp.addFirst(h);
        timestamp.addLast(min);
        timestamp.addLast(sec);
        timestamp.addLast(date);
        timestamp.addLast(day);
        timestamp.addLast(month);
        timestamp.addLast(year);

    }
}
