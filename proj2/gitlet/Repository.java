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

    public static final File HEAD = Utils.join(Commits, "Head");
    /* TODO: fill in the rest of this class. */

    // named by the blobId
    public static final File Blobs = Utils.join(GITLET_DIR, "Blobs");

    public static final File BranchCollection = Utils.join(GITLET_DIR, "Branch");

    public static Branch currentBranch = new Branch(null, null);

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
        try {
            HEAD.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeObject(HEAD, initialCommit);
        Commit Head = Utils.readObject(HEAD, Commit.class);
        Branch master = new Branch("master", Head);
        currentBranch = master;
    }

    // all this method need to do is to simply move the file to the staging area.
    public static void add(String fileName) {
        File tobeAdded = Utils.join(CWD, fileName);
        if (!tobeAdded.exists()) { // to make sure that the specified file is in the CWD
            Utils.exitWithError("File does not exist.");
        }else {
            Blob tobeAdd = new Blob(tobeAdded); // create a blob based on the specified file
            File targetFile = Utils.join(StagingArea.addition, fileName); // to create the file
            Commit Head = Utils.readObject(HEAD, Commit.class);
            String Id = Head.snapshot.get(fileName);
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
                Utils.restrictedDelete(targetFile2);
            }
        }
    }

    private static void makeCommit(String message) {
        /**
         * TODO: set the commit message, date, parent(the last commit)
         */
        Date dateObj = new Date();
        Commit newCommit = readObject(HEAD, Commit.class);
        newCommit.parentId = newCommit.id;
        newCommit.makeChange(message, dateObj);
        newCommit.saveCommit();
        writeObject(HEAD, newCommit);
        Commit Head = Utils.readObject(HEAD, Commit.class);
        currentBranch.setCurrentCommit(Head);
    }

    public static void finalCommit(String[] args) {
        if (args.length == 1) {
            Utils.exitWithError("Please enter a commit message.");
        }
        String message = args[1];
        if (StagingArea.addition.length() == 0) {
            Utils.exitWithError("No changes added to the commit.");
        }else {
            Repository.makeCommit(message);
        }
    }

    public static void log() {
        Commit curr = Utils.readObject(HEAD, Commit.class);
        while(curr != null && curr.id != curr.getParentId()) {
            if (curr.getParent2Id() != null) {
                logHelper(curr);
                curr = curr.getParent();
            }else if (curr.parent2Exist()) {
                logMerge(curr, curr.getMessage());
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
        if (args[0].equals("checkout") && args[1].equals("--")) {
            Commit nHead = Utils.readObject(HEAD, Commit.class);
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
        }else if (args[0].equals("checkout") && args[2].equals("--")) { //checkout [commit id] -- [file name]
            File targetFile = Utils.join(CWD, args[3]);
            createFile(targetFile);
            File commitFile = Utils.join(Commits, args[1]);
            if (!commitFile.exists()) {
                Utils.exitWithError("No commit with that id exists.");
            }
            Commit targetCommit = Utils.readObject(commitFile, Commit.class);
            String targetId = targetCommit.snapshot.get(args[3]);
            File targetBlob = Utils.join(Blobs, targetId);
            byte[] content = readContents(targetBlob);
            Utils.writeContents(targetFile, content);
        }else {
            String targetName = args[1];
            File targetBranch = Utils.join(BranchCollection, targetName);
            Commit Head = currentBranch.getCurrentCommit();
            if (!targetBranch.exists()) {
                Utils.exitWithError("No such branch exists.");
            }
            Branch givenBranch = Utils.readObject(targetBranch, Branch.class);
            Commit givenCommit = givenBranch.getCurrentCommit();
            checkoutFailure(givenCommit);
            if (givenBranch.equals(currentBranch)) {  //if givenBranch is the same as the currentBranch
                Utils.exitWithError("No need to checkout the current branch.");
            }else {
                Commit.helpDelete(StagingArea.addition);
                Commit.helpDelete(StagingArea.removal);
            }

            for (String FileName : givenCommit.snapshot.keySet()){ //put all the files to the CWD and overwrite them if necessary.
                String BlobId = givenCommit.snapshot.get(FileName);
                File targetBlob = Utils.join(Blobs, BlobId);
                Blob tobeCopied = Utils.readObject(targetBlob, Blob.class);
                byte[] content = tobeCopied.getBlobContent();
                File targetFile = Utils.join(CWD, FileName);
                createFile(targetFile);
                writeContents(targetFile, content);
            }
            for (String FileName : Head.snapshot.keySet()) { // remove files that are not present in the
                                                                // given commit but present in the current commit
                if (!givenCommit.snapshot.containsKey(FileName)) {
                    Head.snapshot.remove(FileName);
                }
            }
            currentBranch = givenBranch;
            Utils.writeObject(HEAD, Head);

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
        if (exist == false) {
            System.out.println("Found no commit with that message.");
        }
    }

    public static void branchFunc(String branchName) {
        if (Branch.branches.containsKey(branchName)) {
            Utils.exitWithError("A branch with that name already exists.");
        }
        Commit Head = Utils.readObject(HEAD, Commit.class);
        Branch newBranch = new Branch(branchName, Head);
        File Branch1 = Utils.join(Repository.BranchCollection, branchName);
        Repository.createFile(Branch1);
        Utils.writeObject(Branch1, newBranch);
    }

    public static void remove(String[] args) {
        int error = 0;
        File InAddition = Utils.join(StagingArea.addition, args[1]);
        if (InAddition.exists()) {
            Utils.restrictedDelete(InAddition);
            error += 1;
        }
        Commit Head = Utils.readObject(HEAD, Commit.class);
        String blobId = Head.snapshot.get(args[1]);
        if (blobId != null) {
            File toRemoval = Utils.join(StagingArea.removal, args[1]);
            createFile(toRemoval);
            writeContents(toRemoval, blobId);
            File InCwd = Utils.join(CWD, args[1]);
            if (InCwd.exists()) {
                Utils.restrictedDelete(InCwd);
            }
            error += 1;
        }
        if (error == 0) {
            Utils.exitWithError("No reason to remove the file");
        }
    }

    public static void status() {
        // phase 1
        System.out.println("=== Branches ===");
        System.out.println("*" + currentBranch.getName());
        for (String branchName : Utils.plainFilenamesIn(BranchCollection)) {
            if (!branchName.equals(currentBranch.getName())) {
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
        if (currentBranch.getName().equals(targetBranch)) {
            Utils.exitWithError("Cannot remove the current branch.");
        }
        boolean error = true;
        for (String branchName : Utils.plainFilenamesIn(BranchCollection)) {
            File targetFile = Utils.join(BranchCollection, branchName);
            Utils.restrictedDelete(targetFile);
            error = false;
        }
        if (error) {
            Utils.exitWithError("A branch with that name does not exist.");
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
        Commit currentCommit = currentBranch.getCurrentCommit();
        return currentCommit.snapshot.containsKey(FileName);
    }

    /**
     * when will the file be overwritten?
     * The file in the current branch has the different content as the given Commit
     * @param FileName
     * @return
     */
    private static boolean beOverwritten(String FileName, Commit givenCommit) {
        Commit head = Utils.readObject(HEAD, Commit.class);
        if (head.snapshot.containsKey(FileName) && givenCommit.snapshot.containsKey(FileName)) {
            String blobId1 = head.snapshot.get(FileName);
            String blobId2 = givenCommit.snapshot.get(FileName);
            if (!blobId1.equals(blobId2)) {
                return true;
            }
        }
        return false;
    }

    public static void reset(String givenId) {
        Commit head = Utils.readObject(HEAD, Commit.class);
        File givenCommitFile = Utils.join(Commits, givenId);
        if (!givenCommitFile.exists()) {
            Utils.exitWithError("No commit with that id exists.");
        }
        Commit givenCommit = Utils.readObject(givenCommitFile, Commit.class);
        checkoutFailure(givenCommit);
        for (String fileName : givenCommit.snapshot.keySet()) {
            String[] args = {givenId, "--", fileName};
            checkout(args);
        }
        for (String FileName : head.snapshot.keySet()) { // remove files that are not present in the
            // given commit but present in the current commit
            if (!givenCommit.snapshot.containsKey(FileName)) {
                head.snapshot.remove(FileName);
            }
        }
        currentBranch.setCurrentCommit(givenCommit);
    }

    public static void merge(String givenBranch1) {
        // step 1: catch splitPoint;
        File givenBranchName = Utils.join(BranchCollection, givenBranch1);
        Branch givenBranch = Utils.readObject(givenBranchName, Branch.class);
        Commit givenCommit = givenBranch.getCurrentCommit();
        checkoutFailure(givenCommit);
        mergeFailures2(givenBranch1);
        Commit splitPoint = null;
        for (String commitId : givenBranch.getBranches().keySet()) {
            for (String commitId2 : currentBranch.getBranches().keySet()) {
                File commit1File = Utils.join(Commits, commitId);
                File commit2File = Utils.join(Commits, commitId2);
                Commit commit1 = Utils.readObject(commit1File, Commit.class);
                Commit commit2 = Utils.readObject(commit2File, Commit.class);
                if (!commit1.equals(commit2) && commit1.getParentId().equals(commit2.getParentId())) {
                    String splitPointId = commit1.getParentId();
                    File splitFile = Utils.join(Commits, splitPointId);
                    splitPoint = Utils.readObject(splitFile, Commit.class);
                }
            }
        }
        // step 2: 2 failure cases
        if (splitPoint.equals(givenBranch.getCurrentCommit())) {
            Utils.exitWithError("Given branch is an ancestor of the current branch.");
        }else if (splitPoint.equals(currentBranch.getCurrentCommit())) {
            Utils.exitWithError("Current branch fast-forwarded.");
        }

        // step 4: create the new mergeCommit
        Commit mergeCommit = Utils.readObject(HEAD, Commit.class);

        //step 3: follow up steps
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

    private static HashMap<String, String> ModifiedButNStag() {
        HashMap returnList = new HashMap();
        Commit head = Utils.readObject(HEAD, Commit.class);
        for (String FileName : head.snapshot.keySet()) {
            File targetFile = Utils.join(CWD, FileName);
            if (!targetFile.exists()) {
                returnList.put(FileName, "deleted");
            }
            if (!targetFile.isDirectory() && targetFile.exists()) {
                byte[] content = Utils.readContents(targetFile);
                String BlobId = head.snapshot.get(FileName);
                byte[] compared = Utils.readContents(Utils.join(Blobs, BlobId));
                if (!content.equals(compared)) {
                    returnList.put(FileName, "modified");
                }
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



}
