package gitlet;

import java.io.File;
import java.io.Serializable;

public class Remote implements Serializable {
    // variables here
    private String name;
    private String nameOfDir;
    public static final File Remotes = Utils.join(Repository.CWD, "Remotes");

    public Remote(String name, String nameOfDir) {
        this.name = name;
        this.nameOfDir = nameOfDir;
        File newRemote = Utils.join(Remotes, this.name);
        Repository.createFile(newRemote);
        Utils.writeObject(newRemote, this);
    }

    public static void addRemote(String name, String nameOfDir) {
        new Remote(name, nameOfDir);
    }

    public static void rmRemote(String name) {
        if (!checkRepetition(name)) {
            Utils.exitWithError("A remote with that name does not exists");
        }
        File target = Utils.join(Remotes, name);
        target.delete();
    }

    public static void push(String name, String branchName) {
        File targetRemoteF = Utils.join(Remotes, name);
        Remote targetRemote = Utils.readObject(targetRemoteF, Remote.class);
        String address = targetRemote.getNameOfDir();
        File gitLetSystem = new File(address);
        if (!gitLetSystem.exists()) {
            Utils.exitWithError("Remote directory not found.");
        }
        File HeadF = Utils.join(address,"Branches",  "HEAD");
        Commit remoteHead = Utils.readObject(HeadF, Branch.class).getCurrentCommit();
        Commit head = Utils.readObject(Repository.HEAD, Branch.class).getCurrentCommit();
        if (!Repository.isAncestor(remoteHead, head)) {
            Utils.exitWithError("Please pull down remote changes before pushing.");
        }
        File targetBranch = Utils.join(address, "BranchCollection", branchName);
        if (!targetBranch.exists()) {
            pushHelper();
        }
    }

    /**
     * this is a helper function
     * @param name the given string you want to check
     * @return true if the given name exists in the remotes directory;
     */
    private static boolean checkRepetition(String name) {
        for (String FileName : Utils.plainFilenamesIn(Remotes)) {
            if (FileName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * the order of this function matters
     * we should put the ancestor commit in the first parameter and the latter one in the second one
     * @param commit1 the commit in the given branch's head
     * @param commit2 the commit in the current working directory's head
     * @param address just for convenience it's the same as in the push command
     */

    private static void pushHelper(Commit commit1, Commit commit2, String address) {
        while (!commit2.getId().equals(commit1.getId())) {
            File findCommit = Utils.join(address, "Commits", commit1.getId());
            Repository.createFile(findCommit);
            commit2 = commit2.getParent();
        }
    }

    public String getName() {
        return name;
    }

    public String getNameOfDir() {
        return nameOfDir;
    }
}
