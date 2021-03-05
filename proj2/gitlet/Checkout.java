package gitlet;

import java.io.File;

public class Checkout {

    public static void checkout1(String a) {
        /**
         * Takes the version of the file as it exists in the head commit and puts it
         * in the working directory, overwriting the version of the file that’s already there
         * if there is one. The new version of the file is not staged.
         */
        File Head = Utils.join(Repository.Commits, Repository.Head.getId());
    }
}
