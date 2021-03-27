package gitlet;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Kaifeng Lin
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");

    public static final File Commits = Utils.join(GITLET_DIR, "commits");

    /* TODO: fill in the rest of this class. */

    // named by the blobId
    public static final File Blobs = Utils.join(GITLET_DIR, "Blobs");

    public static final File BranchCollection = Utils.join(GITLET_DIR, "Branch");

    public static final File HEAD = Utils.join(BranchCollection, "Head");

    public static void setupPersistence() {
        GITLET_DIR.mkdir();
        Commits.mkdir();
        StagingArea.StagingArea.mkdir();
        StagingArea.addition.mkdir();
        StagingArea.removal.mkdir();
        Blobs.mkdir();
        BranchCollection.mkdir();
        Date initDate = new Date(0);
        Commit initialCommit = new Commit("initial commit", initDate);
        initialCommit.saveCommit();
        createFile(HEAD);
        Branch master = new Branch("master", initialCommit);
        File masterBranch = Utils.join(BranchCollection, "master");
        createFile(masterBranch);
        Utils.writeObject(masterBranch, master);
        Utils.writeObject(HEAD, master);
    }

    // all this method need to do is to simply move the file to the staging area.
    public static void add(String[] args) {
        if (args.length == 0) {

        }
        String fileName = args[1];
        File tobeAdded = Utils.join(CWD, fileName);
        if (!tobeAdded.exists()) { // to make sure that the specified file is in the CWD
            Utils.exitWithError("File does not exist.");
        }else {
            Blob tobeAdd = new Blob(tobeAdded); // create a blob based on the specified file
            File targetFile = Utils.join(StagingArea.addition, fileName); // to create the file
            Branch Head = Utils.readObject(HEAD, Branch.class);
            String Id = Head.getCurrentCommit().snapshot.get(fileName);
            // update the file if already exists in the staging area.
            if (Id == null || !Id.equals(tobeAdd.getBlobId())) {    // the content of the blob is different from head one
                createFile(targetFile);
                writeObject(targetFile, tobeAdd);
            }else {
                if (targetFile.exists()) {
                    Utils.restrictedDelete(targetFile);
                }
            }
            File targetFile2 = Utils.join(StagingArea.removal, fileName); // to remove the file
            if (targetFile2.exists()) {
                targetFile2.delete();
            }
        }
    }

    private static void makeCommit(String message) {
        /**
         * TODO: set the commit message, date, parent(the last commit)
         */
        Date dateObj = new Date();
        Commit newCommit = readObject(HEAD, Branch.class).getCurrentCommit();
        newCommit.parentId = newCommit.id;
        newCommit.makeChange(message, dateObj);
        newCommit.saveCommit();
        Branch Head = Utils.readObject(HEAD, Branch.class);
        Head.setCurrentCommit(newCommit);
        writeObject(HEAD, Head);
        Branch HeadBranch = Utils.readObject(HEAD, Branch.class);
        File currentBranch = Utils.join(BranchCollection, HeadBranch.getName());
        writeObject(currentBranch, Head);
    }

    public static void finalCommit(String[] args) {
        if (args.length == 1 || args[1].equals("")) {
            Utils.exitWithError("Please enter a commit message.");
        }
        String message = args[1];
        if (commitFailure()) {
            Utils.exitWithError("No changes added to the commit.");
        }else {
            Repository.makeCommit(message);
        }
    }

    public static void log() {
        Commit curr = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
        while(curr != null && curr.id != curr.getParentId()) {
            if (!curr.parent2Exist()) {
                logHelper(curr);
                curr = curr.getParent();
            }else if (curr.parent2Exist()) {
                logMerge(curr, curr.getMessage());
                curr = curr.getParent();
            }
        }
    }

    /** create the target file if it doesn't exist
     */
    private static void createFile(File targetFile) {
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void globalLog() {
        if (Utils.plainFilenamesIn(Commits) != null) {
            for (String name : Utils.plainFilenamesIn(Commits)) {
                File currFile = Utils.join(Commits, name);
                Commit curr = Utils.readObject(currFile, Commit.class);
                logHelper(curr);
            }
        }
    }

    public static void checkout(String[] args) {
        if (args[0].equals("checkout") && args.length == 3) {//java gitlet.Main checkout -- [file name]
            if (!args[1].equals("--")) {
                Utils.exitWithError("Incorrect operands.");
            }
            Commit nHead = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
            if (!nHead.snapshot.containsKey(args[2])){
                exitWithError("File does not exist in that commit.");
            }else {
                File targetFile = Utils.join(CWD, args[2]);
                createFile(targetFile);
                String targetId = nHead.snapshot.get(args[2]);
                File targetBlob = Utils.join(Blobs, targetId);
                byte[] content = readContents(targetBlob);
                Utils.writeContents(targetFile, content);
            }
        }else if (args[0].equals("checkout") && args.length == 4) { //checkout [commit id] -- [file name]
            if (!args[2].equals("--")) {
                Utils.exitWithError("Incorrect operands.");
            }
            File targetFile = Utils.join(CWD, args[3]);
            createFile(targetFile);
            File commitFile = Utils.join(Commits, args[1]);;
            if (args[1].length() == 6) {
                for (String commitId : Utils.plainFilenamesIn(Commits)) {
                    if (shortenId(commitId).equals(args[1])) {
                        commitFile = Utils.join(Commits, commitId);
                    }
                }
            }
            if (!commitFile.exists()) {
                Utils.exitWithError("No commit with that id exists.");
            }
            Commit targetCommit = Utils.readObject(commitFile, Commit.class);
            String targetId = targetCommit.snapshot.get(args[3]);
            if (targetId == null) {
                exitWithError("File does not exist in that commit.");
            }
            File targetBlob = Utils.join(Blobs, targetId);
            byte[] content = readContents(targetBlob);
            Utils.writeContents(targetFile, content);
        }else {
            String targetName = args[1];
            File targetBranch = Utils.join(BranchCollection, targetName);
            if (!targetBranch.exists()) {
                Utils.exitWithError("No such branch exists.");
            }
            Commit Head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
            Branch givenBranch = Utils.readObject(targetBranch, Branch.class);
            Commit givenCommit = givenBranch.getCurrentCommit();
            checkoutFailure(givenCommit);
            Branch head = Utils.readObject(HEAD, Branch.class);
            if (givenBranch.getName().equals(head.getName())) {  //if givenBranch is the same as the currentBranch
                Utils.exitWithError("No need to checkout the current branch.");
            }else {
                Commit.helpDelete(StagingArea.addition);
                Commit.helpDelete(StagingArea.removal);
            }

            for (String FileName : givenCommit.snapshot.keySet()){ //put all the files to the CWD and overwrite them if necessary.
                String BlobId = givenCommit.snapshot.get(FileName);
                File targetBlob = Utils.join(Blobs, BlobId);
                byte[] content = Utils.readContents(targetBlob);
                File targetFile = Utils.join(CWD, FileName);
                createFile(targetFile);
                writeContents(targetFile, content);
            }
            for (String FileName : Utils.plainFilenamesIn(CWD)) { // remove files that are not present in the
                                                                // given commit but present in the current commit
                if (!givenCommit.snapshot.containsKey(FileName) && Head.snapshot.containsKey(FileName)) {
                    File target = Utils.join(CWD, FileName);
                    target.delete();
                }
            }
            Utils.writeObject(HEAD, givenBranch);
        }
    }

    public static void find(String message) {
        boolean exist = false;
        if (Utils.plainFilenamesIn(Commits) != null) {
            for (String name : Utils.plainFilenamesIn(Commits)) {
                File currFIle = Utils.join(Commits, name);
                Commit curr = Utils.readObject(currFIle, Commit.class);
                if (curr.getMessage().equals(message)) {
                    System.out.println(curr.getId());
                    exist = true;
                }
            }
        }
        if (!exist) {
            System.out.println("Found no commit with that message.");
        }
    }

    public static void branchFunc(String[] args) {
        if (args.length == 2) {
            String branchName = args[1];
            for (String BranchName : Utils.plainFilenamesIn(BranchCollection)) {
                if (BranchName.equals(branchName)) {
                    Utils.exitWithError("A branch with that name already exists.");
                }
            }
            Commit Head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
            Branch newBranch = new Branch(branchName, Head);
            File Branch1 = Utils.join(Repository.BranchCollection, branchName);
            createFile(Branch1);
            Utils.writeObject(Branch1, newBranch);
        }
    }

    public static void remove(String fileName) {
        File InAddition = Utils.join(StagingArea.addition, fileName);
        if (!InAddition.exists() && untracked(fileName)) {
            Utils.exitWithError("No reason to remove the file.");
        }else if (InAddition.exists() && !InAddition.isDirectory()) {
            InAddition.delete();
        }else if (!untracked(fileName)) {
            Commit Head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
            String blobId = Head.snapshot.get(fileName);
            if (blobId != null) {
                File toRemoval = Utils.join(StagingArea.removal, fileName);
                createFile(toRemoval);
                File targetBlob = Utils.join(Blobs, blobId);
                byte[] content = Utils.readContents(targetBlob);
                writeContents(toRemoval, content);
                File InCwd = Utils.join(CWD, fileName);
                if (InCwd.exists() && !InCwd.isDirectory()) {
                    Utils.restrictedDelete(InCwd);
                }
            }
        }
    }

    public static void status() {
        // phase 1
        System.out.println("=== Branches ===");
        Branch head = Utils.readObject(HEAD, Branch.class);
        System.out.println("*" + head.getName());
        for (String branchName : Utils.plainFilenamesIn(BranchCollection)) {
            if (!branchName.equals(head.getName()) && !branchName.equals("Head")) {
                System.out.println(branchName);
            }
        }
        System.out.println();
        // phase 2
        System.out.println("=== Staged Files ===");
        for (String FileName : Utils.plainFilenamesIn(StagingArea.addition)) {
            System.out.println(FileName);
        }
        System.out.println();
        // phase 3
        System.out.println("=== Removed Files ===");
        for (String FileName : Utils.plainFilenamesIn(StagingArea.removal)) {
            System.out.println(FileName);
        }
        System.out.println();
        //phase 4
        System.out.println("=== Modifications Not Staged For Commit ===");
        HashMap<String, String> modifiedFiles = ModifiedButNStag();
        for (String FileName : modifiedFiles.keySet()) {
            System.out.println(FileName + "(" + modifiedFiles.get(FileName) + ")");
        }
        System.out.println();
        //phase 5
        System.out.println("=== Untracked Files ===");
        LinkedList<String> untrackedFiles = untrackedFiles();
        for (String FileName : untrackedFiles) {
            System.out.println(FileName);
        }
        System.out.println();
    }

    public static void rmBranch(String targetBranch) {
        Branch currentBranch = Utils.readObject(HEAD, Branch.class);
        if (currentBranch.getName().equals(targetBranch)) {
            Utils.exitWithError("Cannot remove the current branch.");
        }
        boolean error = true;
        for (String BranchName : Utils.plainFilenamesIn(BranchCollection)) {
            if (BranchName.equals(targetBranch)) {
                error = false;
            }
        }
        if (error) {
            Utils.exitWithError("A branch with that name does not exist.");
        }
        for (String branchName : Utils.plainFilenamesIn(BranchCollection)) {
            File targetFile = Utils.join(BranchCollection, branchName);
            targetFile.delete();
        }
    }

    private static void checkoutFailure(Commit givenCommit) {
        for (String FileName : Utils.plainFilenamesIn(CWD)) {
            File untracked = Utils.join(CWD, FileName);
            if (untracked.isDirectory()) {
                continue;
            }
            if (untracked(FileName) && beOverwritten(FileName, givenCommit)) {
                // to make sure that this file is untracked in the current branch
                Utils.exitWithError("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }
    }

    /**
     * @param FileName
     * @return return true if the file is tracked by the head of the current branch,return false otherwise.
     */
    // return true if the file is untracked by the Head commit.
    private static boolean untracked(String FileName) {
        Commit head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
        return !head.snapshot.containsKey(FileName);
    }

    /**
     * when will the file be overwritten?
     * The file in the current branch has the different content as the given Commit
     * @param FileName
     * @return
     */
    private static boolean beOverwritten(String FileName, Commit givenCommit) {
        File inCWD = Utils.join(CWD, FileName);
        String BlobId = givenCommit.snapshot.get(FileName);
        File targetBlob = Utils.join(Blobs, BlobId);
        String content1 = readContentsAsString(inCWD);
        String content2 = readContentsAsString(targetBlob);
        if (!content1.equals(content2)) {
            return true;
        }
        return false;
    }

    public static void reset(String givenId) {
        Commit head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
        File givenCommitFile = Utils.join(Commits, givenId);
        if (!givenCommitFile.exists()) {
            Utils.exitWithError("No commit with that id exists.");
        }
        Commit givenCommit = Utils.readObject(givenCommitFile, Commit.class);
        checkoutFailure(givenCommit);
        for (String fileName : givenCommit.snapshot.keySet()) {
            String[] args = {"checkout", givenId, "--", fileName};
            checkout(args);
        }
        for (String FileName : Utils.plainFilenamesIn(CWD)) { // remove files that are not present in the
            // given commit but present in the current commit
            File target = Utils.join(CWD, FileName);
            if (!givenCommit.snapshot.containsKey(FileName) && !target.isDirectory() && head.snapshot.containsKey(FileName)) {
                target.delete();
            }
        }
        Branch currentBranch = Utils.readObject(HEAD, Branch.class);
        currentBranch.setCurrentCommit(givenCommit);
        Utils.writeObject(HEAD, currentBranch);
    }

    public static void merge(String givenBranch1) {
        // step 1: catch splitPoint;
        File givenBranchName = Utils.join(BranchCollection, givenBranch1);
        Branch givenBranch = Utils.readObject(givenBranchName, Branch.class);
        Commit givenCommit = givenBranch.getCurrentCommit();
        Branch currentBranch = Utils.readObject(HEAD, Branch.class);
        checkoutFailure(givenCommit);
        mergeFailures2(givenBranch1);
        Commit splitPoint = null;
        for (String commitId : Utils.plainFilenamesIn(Commits)) {
            for (String commitId2 : Utils.plainFilenamesIn(Commits)) {
                if (!commitId.equals(commitId2)) {
                    File commit1File = Utils.join(Commits, commitId);
                    File commit2File = Utils.join(Commits, commitId2);
                    Commit commit1 = Utils.readObject(commit1File, Commit.class);
                    Commit commit2 = Utils.readObject(commit2File, Commit.class);
                    if (commit1.parentId != null && commit2.parentId != null
                            && commit1.getParentId().equals(commit2.getParentId())) {
                        String splitPointId = commit1.getParentId();
                        File splitFile = Utils.join(Commits, splitPointId);
                        splitPoint = Utils.readObject(splitFile, Commit.class);
                        break;
                    }
                }
            }
            if (splitPoint != null) {
                break;
            }
        }
        // step 2: 2 failure cases
        if (splitPoint.equals(givenBranch.getCurrentCommit())) {
            Utils.exitWithError("Given branch is an ancestor of the current branch.");
        }else if (splitPoint.equals(currentBranch.getCurrentCommit())) {
            Utils.exitWithError("Current branch fast-forwarded.");
        }

        // step 3: create the new mergeCommit
        Commit mergeCommit = Utils.readObject(HEAD, Commit.class);

        //step 4: follow up steps
        Commit givenBranchCurrCommit = givenBranch.getCurrentCommit();
        mergeCommit.parent2Id = givenBranchCurrCommit.id;
        merHelper1(splitPoint, givenBranchCurrCommit, mergeCommit);
        merHelper2(splitPoint, givenBranchCurrCommit, mergeCommit);
        merHelper3(splitPoint, givenBranchCurrCommit, mergeCommit);
        if (merHelper4(givenBranchCurrCommit, mergeCommit) || merHelper5(splitPoint, givenBranchCurrCommit, mergeCommit)
        || merHelper5(splitPoint, mergeCommit, givenBranchCurrCommit)) {
            System.out.println("Encountered a merge conflict.");
        }
        mergeCommit.setMessage("Merged " + givenBranch1 + " " + currentBranch.getName());
    }

    private static void merHelper1(Commit splitPoint, Commit givenBranchCurrCommit, Commit mergeCommit) {
        for (String Filename : splitPoint.snapshot.keySet()) {
            if (givenBranchCurrCommit.snapshot.containsKey(Filename) && mergeCommit.snapshot.containsKey(Filename)) {
                String BlobId = splitPoint.snapshot.get(Filename);
                String compared = givenBranchCurrCommit.snapshot.get(Filename);
                String curr = mergeCommit.snapshot.get(Filename);
                if (BlobId.equals(curr) && !BlobId.equals(compared)) {
                    mergeCommit.snapshot.put(Filename, compared);
                }
            }
        }
    }

    private static void merHelper2(Commit splitPoint, Commit givenBranchCurrCommit, Commit mergeCommit) {
        for (String FileName : splitPoint.snapshot.keySet()) {
            if (mergeCommit.snapshot.containsKey(FileName) && !givenBranchCurrCommit.snapshot.containsKey(FileName)) {
                String BlobId = splitPoint.snapshot.get(FileName);
                if (BlobId.equals(mergeCommit)) {
                    mergeCommit.snapshot.remove(FileName);
                }
            }
        }
    }

    private static void merHelper3(Commit splitPoint, Commit givenBranchCurrentCommit, Commit mergeCommit) {
        for (String FileName : givenBranchCurrentCommit.snapshot.keySet()) {
            if (!splitPoint.snapshot.containsKey(FileName) && !mergeCommit.snapshot.containsKey(FileName)) {
                String[] args = {givenBranchCurrentCommit.getId(), "--", FileName};
                checkout(args);
                mergeCommit.snapshot.put(FileName, givenBranchCurrentCommit.snapshot.get(FileName));
            }
        }
    }

    private static boolean merHelper4(Commit givenBranchCurrentCommit, Commit mergeCommit) {
        boolean conflict = false;
        for (String FileName : mergeCommit.snapshot.keySet()) {
            if (givenBranchCurrentCommit.snapshot.containsKey(FileName)) {
                String BlobId1 = mergeCommit.snapshot.get(FileName);
                String BlobId2 = givenBranchCurrentCommit.snapshot.get(FileName);
                if (!BlobId1.equals(BlobId2)) {
                    conflict = true;
                    showConflict(BlobId1, BlobId2);
                }
            }
        }
        return conflict;
    }

    //we can change the position of the last two parameters to show the different cases.
    private static boolean merHelper5(Commit splitPoint, Commit givenBranchCurrentCommit, Commit mergeCommit) {
        boolean conflict = false;
        for (String FileName : givenBranchCurrentCommit.snapshot.keySet()) {
            if (splitPoint.snapshot.containsKey(FileName) && !mergeCommit.snapshot.containsKey(FileName)) {
                String blobId1 = splitPoint.snapshot.get(FileName);
                String blobId2 = splitPoint.snapshot.get(FileName);
                if (!blobId1.equals(blobId2)) {
                    conflict = true;
                    showConflict("null", blobId2);
                }
            }
        }
        return conflict;
    }



    private static void showConflict(String blobId1, String blobId2) {
        System.out.println("<<<<<<< HEAD");
        File Blob1 = Utils.join(Blobs, blobId1);
        if (blobId1.equals("null")) {
            System.out.println();
        }else {
            String content = Utils.readContentsAsString(Blob1);
            System.out.println(content);
        }
        System.out.println("=======");
        if (blobId2.equals("null")) {
            System.out.println();
        }else {
            File Blob2 = Utils.join(Blobs, blobId2);
            String content2 = Utils.readContentsAsString(Blob2);
            System.out.println(content2);
        }
        System.out.println(">>>>>>>");
    }

    private static void mergeFailures2(String givenBranch) {
        Branch currentBranch = Utils.readObject(HEAD, Branch.class);
        // case 1
        for (String FileName : Utils.plainFilenamesIn(StagingArea.addition)) {
            Utils.exitWithError("You have uncommitted changes.");
        }
        for (String FileName : Utils.plainFilenamesIn(StagingArea.removal)) {
            Utils.exitWithError("You have uncommitted changes.");
        }
        //case 2
        File targetBranch = Utils.join(BranchCollection, givenBranch);
        if (!targetBranch.exists()) {
            Utils.exitWithError("A branch with that name does not exist.");
        }
        //case 3
        if (givenBranch.equals(currentBranch.getName())) {
            Utils.exitWithError("Cannot merge a branch with itself.");
        }
    }


    private Commit shortId(String ShortId) {
        for (String id : Utils.plainFilenamesIn(Commits)) {
            String subId = id.substring(0, 5);
            if (subId.equals(ShortId)) {
                File target = Utils.join(Commits, id);
                Commit targetCommit = Utils.readObject(target, Commit.class);
                return targetCommit;
            }
        }
        return null;
    }

    private static String shortenId(String id) {
        return id.substring(0, 6);
    }

    private static void logHelper(Commit cur) {
        System.out.println("===");
        System.out.println("commit " + cur.id);
        SimpleDateFormat formatter= new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
        System.out.println("Date: " + formatter.format(cur.getTimestamp()));
        System.out.println(cur.getMessage());
        if (cur.parent2Exist()) {
            System.out.println("Merged " + cur.parentId);
        }
        System.out.println();
    }
    // this declares that the given commit is the merged commit
    private static void logMerge(Commit target, String message) {
        System.out.println("===");
        System.out.println("commit " + target.id);
        System.out.println("Merge: " + shortenId(target.getParentId()) + " " + shortenId(target.getParent2Id()));
        SimpleDateFormat formatter= new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
        System.out.println("Date: " + formatter.format(target.getTimestamp()));
        System.out.println(message);//Merged development into master.
    }


    /**
     * TODO:Tracked in the current commit, changed in the working directory, but not staged; or
     * Staged for addition, but with different contents than in the working directory; or
     * Staged for addition, but deleted in the working directory; or
     * TODO: Not staged for removal, but tracked in the current commit and deleted from the working directory
     * @return
     */
    private static HashMap<String, String> ModifiedButNStag() {
        HashMap returnList = new HashMap();
        Commit head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
        for (String FileName : Utils.plainFilenamesIn(CWD)) {
            File file = Utils.join(CWD, FileName);
            File inAddition = Utils.join(StagingArea.addition, FileName);
            if (!file.isDirectory() && !untracked(FileName) && different(FileName) && !inAddition.exists()) {
                returnList.put(FileName, "modified");
            }
        }
        for (String FileName : Utils.plainFilenamesIn(StagingArea.addition)) {
            if (different2(FileName)) {
                returnList.put(FileName, "modified");
            }
        }
        for (String FileName : head.snapshot.keySet()) {
            File targetFile = Utils.join(CWD, FileName);
            File inRemoval = Utils.join(StagingArea.removal, FileName);
            if (!targetFile.exists() && !inRemoval.exists()) {
                returnList.put(FileName, "deleted");
            }
        }
        for (String FileName : Utils.plainFilenamesIn(StagingArea.addition)) {
            File file = Utils.join(CWD, FileName);
            if (!file.exists()) {
                returnList.put(FileName, "deleted");
            }
        }
        return returnList;
    }

    private static LinkedList<String> untrackedFiles() {
        LinkedList<String> returnList = new LinkedList<>();
        for (String FileName : Utils.plainFilenamesIn(CWD)) {
            File file = Utils.join(CWD, FileName);
            if (!file.isDirectory() && untracked(FileName)) {
                returnList.addLast(FileName);
            }
        }
        return returnList;
    }

    // this means that the file in the working directory has different content with the addition dir.

    /**
     *
     * @param fileName the file you want to check if it is unStaged
     * @return false if it has the same content as in the addition area return true otherwise
     */
    private static boolean unStaged(String fileName) {
        File target = Utils.join(CWD, fileName);
        File InAddition = Utils.join(StagingArea.addition, fileName);
        if (target.exists() && InAddition.exists()) {
            String content1 = Utils.readContentsAsString(target);
            String content2 = Utils.readContentsAsString(InAddition);
            if (content1.equals(content2)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param fileName the file you want to check if it is different from what it is in the head commit.
     * @return true if it is different from the head commit return false otherwise
     */
    private static boolean different(String fileName) {
        File target = Utils.join(CWD, fileName);
        Commit head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
        String blobId = head.snapshot.get(fileName);
        if (blobId != null && target.exists()) {
            File targetBlob = Utils.join(Blobs, blobId);
            String content1 = Utils.readContentsAsString(targetBlob);
            String content2 = Utils.readContentsAsString(target);
            if (!content1.equals(content2)) {
                return true;
            }
        }
        return false;
    }

    // basically the same as the last one but this method compare the files in staging are and CWD
    private static boolean different2(String fileName) {
        File target = Utils.join(CWD, fileName);
        File inAddition = Utils.join(StagingArea.addition, fileName);
        String content1 = Utils.readContentsAsString(target);
        String content2 = Utils.readContentsAsString(inAddition);
        if (!content1.equals(content2)) {
            return false;
        }
        return true;
    }

    // return false if there is file in the addition area
    private static boolean commitFailure() {
        for (String FileName : Utils.plainFilenamesIn(StagingArea.removal)) {
            return false;
        }
        for (String FileName : Utils.plainFilenamesIn(StagingArea.addition)) {
            return false;
        }
        return true;
    }



}
