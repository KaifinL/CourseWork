package gitlet;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/** Represents a gitlet commit object.
 *  does at a high level.
 *
 *  @author Kaifeng
 */
public class Commit implements Serializable {
    /**
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    /** The time of this Commit. */
    private Date timestamp;
    /** to collect the files in the commit
     *
     * @param String the fileName of a file.
     * @param String2 the id of the blob.
     * */
    private HashMap<String, String> snapshot;
    private String parentId;
    private String parent2Id;
    private String id;



    /** initialize the commit */
    public Commit() {
        this.message = "initial commit";
        this.timestamp = new Date(0);
        this.snapshot = null;
        this.parentId = null;
        this.parent2Id = null;
        this.id = Utils.sha1(this.toString());
    }

    public Commit(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
        this.snapshot = new HashMap<>();
        this.parent2Id = null;
        this.id = Utils.sha1(this.toString());
    }


    /**
     * save each commit in a file just something like 'dog' in lab6
     */
    public void saveCommit() {
        File newCommit = Utils.join(Repository.Commits, this.id);
        Utils.writeObject(newCommit, this);
        try {
            newCommit.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** get the commit's message */
    public String getMessage() {
        return this.message;
    }

    /** get the commit's id */
    public Date getTimestamp() {
        return timestamp;
    }
    public boolean parent2Exist() {
        return parent2Id != null;
    }

    /** a method that can change a commit's file
     *  and I think this method works correctly!
     *  */
    public void makeChange(String Message, Date date) {
        // to add those files in the staging area to the commit 's snapshot
        for (String fileName : Utils.plainFilenamesIn(StagingArea.ADDITION)) {
            File targetFile = Utils.join(StagingArea.ADDITION, fileName);
            Blob newBlob = Utils.readObject(targetFile, Blob.class);
            String blobId = newBlob.getBlobId();
            snapshot.put(fileName, blobId);
        }
        for (String FILENAME : Utils.plainFilenamesIn(StagingArea.REMOVAL)) {
            if (snapshot.containsKey(FILENAME)) {
                snapshot.remove(FILENAME);
            }
        }
        byte[] idPara = Utils.serialize(this);
        this.id = Utils.sha1(idPara);
        helpDelete(StagingArea.ADDITION); // clean all the files in the staging area.
        helpDelete(StagingArea.REMOVAL);
        this.message = MESSAGE;
        this.timestamp = date;
    }

    // return the parent of the current commit
    public Commit getParent() {
        if (parentId == null) {
            return null;
        }
        File parent = Utils.join(Repository.Commits, this.parentId);
        Commit parentCommit = Utils.readObject(parent, Commit.class);
        return parentCommit;
    }

    public Commit getParent2() {
        if (!parent2Exist()) {
            return null;
        }
        File parent2 = Utils.join(Repository.Commits, this.parent2Id);
        Commit parentCommit = Utils.readObject(parent2, Commit.class);
        return parentCommit;
    }

    public static void helpDelete(File dir) {
        for (File file: dir.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
    }

    public void setMessage(String givenMessage) {
        this.message = givenMessage;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParent2Id() {
        return parent2Id;
    }

    public void setParent2Id(String parent2Id) {
        this.parent2Id = parent2Id;
    }

    public void setSnapshot(HashMap<String, String> snapshot) {
        this.snapshot = snapshot;
    }

    public HashMap<String, String> getSnapshot() {
        return snapshot;
    }
}
