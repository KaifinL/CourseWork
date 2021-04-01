package gitlet;

import java.io.File;
import java.io.Serializable;

public class Remote implements Serializable {
    // variables here
    private String name;
    private String nameOfDir;
    public static final File REMOTES = Utils.join(Repository.GITLET_DIR, "Remotes");

    public Remote(String name, String nameOfDir) {
        this.name = name;
        this.nameOfDir = nameOfDir;
        File newRemote = Utils.join(REMOTES, this.name);
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
        File target = Utils.join(REMOTES, name);
        target.delete();
    }

    public static void push(String[] args) {
        // phase 1 : check if the gitlet repo exists
        String name = args[1];
        String branchName = args[2];
        File targetRemoteF = Utils.join(REMOTES, name);
        Remote targetRemote = Utils.readObject(targetRemoteF, Remote.class);
        String address = targetRemote.getNameOfDir();
        File gitLetSystem = new File(address);
        if (!gitLetSystem.exists()) {
            Utils.exitWithError("Remote directory not found.");
        }
        // phase 2 : check if the head commit exists
        File headF = Utils.join(address, "Branch",  "HEAD");
        Commit remoteHead = Utils.readObject(headF, Branch.class).getCurrentCommit();
        Commit head = Utils.readObject(Repository.HEAD, Branch.class).getCurrentCommit();
        if (!Repository.isAncestor(remoteHead, head)) {
            Utils.exitWithError("Please pull down remote changes before pushing.");
        }
        // pull everything from local to remote
        File targetBranch = Utils.join(address, "Branch", branchName);
        if (!targetBranch.exists()) {
            Repository.createFile(targetBranch);
            Branch remoteBranch = Utils.readObject(headF, Branch.class);
            Utils.writeContents(targetBranch, remoteBranch);
        }
        pushHelper(remoteHead, head, address);
        // phase 3 : reset the head commit to the new commit
        Branch remoteBranch = Utils.readObject(headF, Branch.class);
        remoteBranch.setCurrentCommit(head);
        Utils.writeObject(headF, remoteBranch);
        File addition = Utils.join(address, "StagingArea", "addition");
        Commit.helpDelete(addition);
        // phase 4 : removes files in CWD that are present in the original head commit
        // but not present in the local commit
        for (String fileName : Utils.plainFilenamesIn(Repository.CWD)) {
            File target = Utils.join(targetRemoteF, fileName);
            if (!target.isDirectory() && remoteHead.getSnapshot().containsKey(fileName)
                    && head.getSnapshot().containsKey(fileName)) {
                target.delete();
            }
        }
    }

    public static void fetch(String[] args) {
        // phase 1 simply check if specified git let repository did exist;
        String name = args[1];
        String branchName = args[2];
        File targetRemoteF = Utils.join(REMOTES, name);
        Remote targetRemote = Utils.readObject(targetRemoteF, Remote.class);
        String address = targetRemote.getNameOfDir();
        File gitLetSystem = new File(address);
        if (!gitLetSystem.exists()) {
            Utils.exitWithError("Remote directory not found.");
        }
        // phase 2 : check if the given branch exists
        File givenBranchF = Utils.join(address, "Branch", branchName);
        if (!givenBranchF.exists()) {
            Utils.exitWithError("That remote does not have that branch.");
        }
        // phase 3 : copy commits and blobs
        Branch remoteBranch = Utils.readObject(givenBranchF, Branch.class);
        Commit remoteCommit = remoteBranch.getCurrentCommit();
        File remoteCommits = Utils.join(address, "commits");
        File localCommit = Utils.join(Repository.COMMITS, remoteCommit.getId());
        File remoteBlobs = Utils.join(address, "Blobs");
        while (!localCommit.exists()) {
            Repository.createFile(localCommit);
            Utils.writeObject(localCommit, remoteCommit);
            copyBlobs(remoteCommit, remoteBlobs);
            remoteCommit = remoteCommit.getRemoteParent(remoteCommit, remoteCommits);
            localCommit = Utils.join(Repository.COMMITS, remoteCommit.getId());
        }
        String branchName2 = args[1] + "." + args[2];
        remoteBranch.setName(branchName2);
        String[] args2 = {"branch", branchName2};
        Repository.branchFunc(args2);
        File localBranch = Utils.join(Repository.BRANCHCOLLECTION, branchName2);
        Utils.writeObject(localBranch, remoteBranch);
    }

    public static void pull(String[] args) {
        fetch(args);
        String branchName2 = args[1] + "." + args[2];
        Repository.merge(branchName2);
    }

    /**
     * this is a helper function
     * @param name the given string you want to check
     * @return true if the given name exists in the remotes directory;
     */
    private static boolean checkRepetition(String name) {
        for (String fileName : Utils.plainFilenamesIn(REMOTES)) {
            if (fileName.equals(name)) {
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
            File findCommit = Utils.join(address, "commits", commit1.getId());
            Repository.createFile(findCommit);
            commit2 = commit2.getParent();
        }
    }

    /**
     * this helper function is designed to check if 'gitlet' directory did exist;
     * @param remoteName this is simply the remote name;
     */
    private static void checkGitLet(String remoteName) {
        File targetRemoteF = Utils.join(REMOTES, remoteName);
        Remote targetRemote = Utils.readObject(targetRemoteF, Remote.class);
        String address = targetRemote.getNameOfDir();
        File gitLetSystem = new File(address);
        if (!gitLetSystem.exists()) {
            Utils.exitWithError("Remote directory not found.");
        }
    }

    private static void copyBlobs(Commit remoteCommit, File remoteBlobs) {
        for (String fileName : remoteCommit.getSnapshot().keySet()) {
            String blobId = remoteCommit.getSnapshot().get(fileName);
            File blob = Utils.join(remoteBlobs, blobId);
            String blobContent = Utils.readContentsAsString(blob);
            File localBlob = Utils.join(Repository.BLOBS, blobId);
            Repository.createFile(localBlob);
            Utils.writeContents(localBlob, blobContent);
        }
    }

    public String getName() {
        return name;
    }

    public String getNameOfDir() {
        return nameOfDir;
    }
}
