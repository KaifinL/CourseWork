package gitlet;

import java.io.File;

/**
 * the Blob class can somehow represent the content of a file while simply use a file can't.
 * I attempt to use it instead of direct file in the Commit 's property 'snapshot'
 */
public class Blob {

    private byte[] BlobContent;
    private String blobId;
    /**
     * TODO: some variables like blob itself should be created
     * TODO: how to represent a file's content by an blob object?I suppose 'readObject' will be useful.
     */

    // create a blob by the pass-in file
    public Blob(File tobeRead) {
        this.BlobContent = Utils.readContents(tobeRead);
        this.blobId = Utils.sha1(this);
    }


    // return the content of a Blob
    public byte[] getBlob() {
        return this.BlobContent;
    }

    public String getBlobId() {
        return this.blobId;
    }
}
