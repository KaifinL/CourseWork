package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * the Blob class can somehow represent the content of a file while simply use a file can't.
 * I attempt to use it instead of direct file in the Commit 's property 'snapshot'
 */
public class Blob implements Serializable {

    private byte[] BlobContent;
    private String blobId;
    public File blobs = Repository.Blobs;
    public static HashMap<String, byte[]> BlobCollection = new HashMap<>();
    /**
     * TODO: some variables like blob itself should be created
     * TODO: how to represent a file's content by an blob object?I suppose 'readObject' will be useful.
     */
    // create a blob by the pass-in file
    public Blob(File tobeRead) {
        this.BlobContent = Utils.readContents(tobeRead);
        this.blobId = Utils.sha1(this.toString());
        File aBlob = Utils.join(blobs, this.blobId);
        try {
            aBlob.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils.writeContents(aBlob, this.BlobContent);
        this.BlobCollection.put(blobId, BlobContent);
    }


    // return the content of a Blob
    public byte[] getBlobContent() {
        return this.BlobContent;
    }

    public String getBlobId() {
        return this.blobId;
    }

    public static byte[] getBlobContent(String id) {
        return BlobCollection.get(id);
    }


}
