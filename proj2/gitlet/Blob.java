package gitlet;

import java.io.File;
import java.util.HashMap;

/**
 * the Blob class can somehow represent the content of a file while simply use a file can't.
 * I attempt to use it instead of direct file in the Commit 's property 'snapshot'
 */
public class Blob {

    public byte[] BlobContent;
    public String blobId;
    public static HashMap<String, byte[]> BlobCollection = new HashMap<>();
    /**
     * TODO: some variables like blob itself should be created
     * TODO: how to represent a file's content by an blob object?I suppose 'readObject' will be useful.
     */

    public Blob() {
        this.BlobContent = null;
        this.blobId = null;
    }
    // create a blob by the pass-in file
    public Blob(File tobeRead) {
        this.BlobContent = Utils.readContents(tobeRead);
        this.blobId = Utils.sha1(this.toString());
        this.BlobCollection.put(blobId, BlobContent);
    }


    // return the content of a Blob
    public byte[] getBlob() {
        return this.BlobContent;
    }

    public String getBlobId() {
        return this.blobId;
    }

    public static byte[] getBlobContent(String id) {
        return BlobCollection.get(id);
    }
}
