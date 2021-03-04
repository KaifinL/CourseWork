package gitlet;

import java.io.File;

/**
 * this class is to represent the changed contents of files and to make things easier
 * also by this class we are able to keep track of what files have been changed so we are able to store
 * them in commits.
 * This is basically work for 'commits'
 */
public class Blobs {

    private String BlobContent;
    private String BlobsName;
    private 
    /**
     * TODO: some variables like blob itself should be created
     * TODO: how to represent a file's content by an blob object?I suppose 'readObject' will be useful.
     */
    public Blobs(File tobeRead) {
        this.BlobContent = Utils.readContentsAsString(tobeRead);
    }
}
