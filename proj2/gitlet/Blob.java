package gitlet;

import java.io.File;
import java.util.HashSet;

/**
 * this class is to represent the changed contents of files and to make things easier
 * also by this class we are able to keep track of what files have been changed so we are able to store
 * them in commits.
 * This is basically work for 'commits'
 */
public class Blob {

    private byte[] BlobContent;
    private String BlobsName;
    private HashSet BlobCollection;
    /**
     * TODO: some variables like blob itself should be created
     * TODO: how to represent a file's content by an blob object?I suppose 'readObject' will be useful.
     */
    public Blob(File tobeRead) {
        this.BlobContent = Utils.readContents(tobeRead);
    }
}
