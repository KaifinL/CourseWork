package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * the Blob class can somehow represent the content of a file while simply use a file can't.
 * I attempt to use it instead of direct file in the Commit 's property 'snapshot'
 */
public class Blob implements Serializable {

    private byte[] BlobContent;
    private String blobId;
    private String fileName;
    public File blobs = Repository.Blobs;
    // create a blob by the pass-in file
    public Blob(File tobeRead, String fileName) {
        this.BlobContent = Utils.readContents(tobeRead);
        this.fileName = fileName;
        byte[] idPara = Utils.serialize(this);
        this.blobId = Utils.sha1(idPara);
        File aBlob = Utils.join(blobs, this.blobId);
        try {
            aBlob.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils.writeContents(aBlob, this.BlobContent);
    }


    // return the content of a Blob
    public byte[] getBlobContent() {
        return this.BlobContent;
    }

    public String getBlobId() {
        return this.blobId;
    }


}
