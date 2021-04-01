package gitlet;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static gitlet.Utils.*;


/** Represents a gitlet repository.
 *  does at a high level.
 *
 *  @author Kaifeng Lin
 */
public class Repository {
    /**
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");

    public static final File COMMITS = Utils.join(GITLET_DIR, "commits");


    // named by the blobId
    public static final File BLOBS = Utils.join(GITLET_DIR, "Blobs");

    public static final File BRANCHCOLLECTION = Utils.join(GITLET_DIR, "Branch");

    public static final File HEAD = Utils.join(BRANCHCOLLECTION, "Head");

    public static void setupPersistence() {
        if (GITLET_DIR.exists()) {
            Utils.exitWithError("A Gitlet version-control system "
                    + "already exists in the current directory.");
        }
        GITLET_DIR.mkdir();
        COMMITS.mkdir();
        StagingArea.STAGINGAREA.mkdir();
        StagingArea.ADDITION.mkdir();
        StagingArea.REMOVAL.mkdir();
        BLOBS.mkdir();
        BRANCHCOLLECTION.mkdir();
        Remote.REMOTES.mkdir();
        Date initDate = new Date(0);
        Commit initialCommit = new Commit("initial commit", initDate);
        initialCommit.saveCommit();
        createFile(HEAD);
        Branch master = new Branch("master", initialCommit);
        File masterBranch = Utils.join(BRANCHCOLLECTION, "master");
        createFile(masterBranch);
        Utils.writeObject(masterBranch, master);
        Utils.writeObject(HEAD, master);
    }

    // all this method need to do is to simply move the file to the staging area.
    public static void add(String[] args) {
        String fileName = args[1];
        File tobeAdded = Utils.join(CWD, fileName);
        if (!tobeAdded.exists()) { // to make sure that the specified file is in the CWD
            Utils.exitWithError("File does not exist.");
        } else {
            Blob tobeAdd = new Blob(tobeAdded, fileName);
            File targetFile = Utils.join(StagingArea.ADDITION, fileName); // to create the file
            Branch head = Utils.readObject(HEAD, Branch.class);
            String id = head.getCurrentCommit().getSnapshot().get(fileName);
            // update the file if already exists in the staging area.
            if (id == null || !id.equals(tobeAdd.getBlobId())) {
                createFile(targetFile);
                writeObject(targetFile, tobeAdd);
            } else {
                if (targetFile.exists()) {
                    Utils.restrictedDelete(targetFile);
                }
            }
            File targetFile2 = Utils.join(StagingArea.REMOVAL, fileName); // to remove the file
            if (targetFile2.exists()) {
                targetFile2.delete();
            }
        }
    }

    private static void makeCommit(String message) {
        Date dateObj = new Date();
        Commit newCommit = readObject(HEAD, Branch.class).getCurrentCommit();
        newCommit.setParentId(newCommit.getId());
        newCommit.makeChange(message, dateObj);
        newCommit.saveCommit();
        Branch head = Utils.readObject(HEAD, Branch.class);
        head.setCurrentCommit(newCommit);
        writeObject(HEAD, head);
        Branch headBranch = Utils.readObject(HEAD, Branch.class);
        File currentBranch = Utils.join(BRANCHCOLLECTION, headBranch.getName());
        writeObject(currentBranch, head);
    }

    public static void finalCommit(String[] args) {
        if (args.length == 1 || args[1].equals("")) {
            Utils.exitWithError("Please enter a commit message.");
        }
        String message = args[1];
        if (commitFailure()) {
            Utils.exitWithError("No changes added to the commit.");
        } else {
            Repository.makeCommit(message);
        }
    }

    public static void log() {
        Commit curr = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
        while (curr != null && curr.getId() != curr.getParentId()) {
            if (!curr.parent2Exist()) {
                logHelper(curr);
                curr = curr.getParent();
            } else if (curr.parent2Exist()) {
                logMerge(curr, curr.getMessage());
                curr = curr.getParent();
            }
        }
    }

    /** create the target file if it doesn't exist
     */
    public static void createFile(File targetFile) {
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void globalLog() {
        if (Utils.plainFilenamesIn(COMMITS) != null) {
            for (String name : Utils.plainFilenamesIn(COMMITS)) {
                File currFile = Utils.join(COMMITS, name);
                Commit curr = Utils.readObject(currFile, Commit.class);
                logHelper(curr);
            }
        }
    }

    public static void checkout(String[] args) {
        if (args[0].equals("checkout") && args.length == 3) {
            if (!args[1].equals("--")) {
                Utils.exitWithError("Incorrect operands.");
            }
            Commit nHead = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
            if (!nHead.getSnapshot().containsKey(args[2])) {
                exitWithError("File does not exist in that commit.");
            } else {
                File targetFile = Utils.join(CWD, args[2]);
                createFile(targetFile);
                String targetId = nHead.getSnapshot().get(args[2]);
                File targetBlob = Utils.join(BLOBS, targetId);
                byte[] content = readContents(targetBlob);
                Utils.writeContents(targetFile, content);
            }
        } else if (args[0].equals("checkout") && args.length == 4) {
            if (!args[2].equals("--")) {
                Utils.exitWithError("Incorrect operands.");
            }
            File targetFile = Utils.join(CWD, args[3]);
            createFile(targetFile);
            File commitFile = Utils.join(COMMITS, args[1]);
            if (args[1].length() < 10) {
                for (String commitId : Utils.plainFilenamesIn(COMMITS)) {
                    if (shortenId(commitId, args[1].length()).equals(args[1])) {
                        commitFile = Utils.join(COMMITS, commitId);
                    }
                }
            }
            if (!commitFile.exists()) {
                Utils.exitWithError("No commit with that id exists.");
            }
            Commit targetCommit = Utils.readObject(commitFile, Commit.class);
            String targetId = targetCommit.getSnapshot().get(args[3]);
            if (targetId == null) {
                exitWithError("File does not exist in that commit.");
            }
            File targetBlob = Utils.join(BLOBS, targetId);
            byte[] content = readContents(targetBlob);
            Utils.writeContents(targetFile, content);
        } else {
            String targetName = args[1];
            File targetBranch = Utils.join(BRANCHCOLLECTION, targetName);
            if (!targetBranch.exists()) {
                Utils.exitWithError("No such branch exists.");
            }
            Commit headCommit = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
            Branch givenBranch = Utils.readObject(targetBranch, Branch.class);
            Commit givenCommit = givenBranch.getCurrentCommit();
            checkoutFailure(givenCommit);
            Branch head = Utils.readObject(HEAD, Branch.class);
            if (givenBranch.getName().equals(head.getName()) && !givenBranch.getName().contains("/")) {
                Utils.exitWithError("No need to checkout the current branch.");
            } else {
                Commit.helpDelete(StagingArea.ADDITION);
                Commit.helpDelete(StagingArea.REMOVAL);
            }

            for (String fileName : givenCommit.getSnapshot().keySet()) {
                String blobId = givenCommit.getSnapshot().get(fileName);
                File targetBlob = Utils.join(BLOBS, blobId);
                byte[] content = Utils.readContents(targetBlob);
                File targetFile = Utils.join(CWD, fileName);
                createFile(targetFile);
                writeContents(targetFile, content);
            }
            for (String fileName : Utils.plainFilenamesIn(CWD)) {
                if (!givenCommit.getSnapshot().containsKey(fileName)
                        && headCommit.getSnapshot().containsKey(fileName)) {
                    File target = Utils.join(CWD, fileName);
                    target.delete();
                }
            }
            Utils.writeObject(HEAD, givenBranch);
        }
    }

    public static void find(String message) {
        boolean exist = false;
        if (Utils.plainFilenamesIn(COMMITS) != null) {
            for (String name : Utils.plainFilenamesIn(COMMITS)) {
                File currFIle = Utils.join(COMMITS, name);
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
            String[] remoteNames = branchName.split(File.separator);
            String remoteName = remoteNames[0];
            for (String branchName2 : Utils.plainFilenamesIn(BRANCHCOLLECTION)) {
                if (branchName2.equals(branchName)) {
                    Utils.exitWithError("A branch with that name already exists.");
                }
            }
            Commit head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
            Branch newBranch = new Branch(branchName, head);
            File branch1 = Utils.join(Repository.BRANCHCOLLECTION, branchName);
            File remoteNameFile = Utils.join(Repository.BRANCHCOLLECTION, remoteName);
            if (!remoteNameFile.exists()) {
                remoteNameFile.mkdir();
            }
            createFile(branch1);
            Utils.writeObject(branch1, newBranch);
        }
    }

    public static void remove(String fileName) {
        File inAddition = Utils.join(StagingArea.ADDITION, fileName);
        if (!inAddition.exists() && untracked(fileName)) {
            Utils.exitWithError("No reason to remove the file.");
        } else if (inAddition.exists() && !inAddition.isDirectory()) {
            inAddition.delete();
        } else if (!untracked(fileName)) {
            Commit head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
            String blobId = head.getSnapshot().get(fileName);
            if (blobId != null) {
                File toRemoval = Utils.join(StagingArea.REMOVAL, fileName);
                createFile(toRemoval);
                File targetBlob = Utils.join(BLOBS, blobId);
                byte[] content = Utils.readContents(targetBlob);
                writeContents(toRemoval, content);
                File inCwd = Utils.join(CWD, fileName);
                if (inCwd.exists() && !inCwd.isDirectory()) {
                    Utils.restrictedDelete(inCwd);
                }
            }
        }
    }

    public static void status() {
        // phase 1
        System.out.println("=== Branches ===");
        Branch head = Utils.readObject(HEAD, Branch.class);
        System.out.println("*" + head.getName());
        for (String branchName : Utils.plainFilenamesIn(BRANCHCOLLECTION)) {
            if (!branchName.equals(head.getName()) && !branchName.equals("Head")) {
                System.out.println(branchName);
            }
        }
        System.out.println();
        // phase 2
        System.out.println("=== Staged Files ===");
        for (String fileName : Utils.plainFilenamesIn(StagingArea.ADDITION)) {
            System.out.println(fileName);
        }
        System.out.println();
        // phase 3
        System.out.println("=== Removed Files ===");
        for (String fileName : Utils.plainFilenamesIn(StagingArea.REMOVAL)) {
            System.out.println(fileName);
        }
        System.out.println();
        //phase 4
        System.out.println("=== Modifications Not Staged For Commit ===");
        HashMap<String, String> modifiedFiles = modifiedButNStag();
        for (String fileName : modifiedFiles.keySet()) {
            System.out.println(fileName + "(" + modifiedFiles.get(fileName) + ")");
        }
        System.out.println();
        //phase 5
        System.out.println("=== Untracked Files ===");
        LinkedList<String> untrackedFiles = untrackedFiles();
        for (String fileName : untrackedFiles) {
            File inAdd = Utils.join(StagingArea.ADDITION, fileName);
            if (inAdd.exists()) {
                untrackedFiles.remove(fileName);
            }
        }
        for (String fileName : untrackedFiles) {
            System.out.println(fileName);
        }
        System.out.println();
    }

    public static void rmBranch(String targetBranch) {
        Branch currentBranch = Utils.readObject(HEAD, Branch.class);
        if (currentBranch.getName().equals(targetBranch)) {
            Utils.exitWithError("Cannot remove the current branch.");
        }
        boolean error = true;
        for (String branchName : Utils.plainFilenamesIn(BRANCHCOLLECTION)) {
            if (branchName.equals(targetBranch)) {
                error = false;
            }
        }
        if (error) {
            Utils.exitWithError("A branch with that name does not exist.");
        }
        for (String branchName : Utils.plainFilenamesIn(BRANCHCOLLECTION)) {
            File targetFile = Utils.join(BRANCHCOLLECTION, branchName);
            targetFile.delete();
        }
    }

    private static void checkoutFailure(Commit givenCommit) {
        for (String fileName : Utils.plainFilenamesIn(CWD)) {
            File untracked = Utils.join(CWD, fileName);
            if (untracked.isDirectory()) {
                continue;
            }
            if (untracked(fileName) && beOverwritten(fileName, givenCommit)) {
                // to make sure that this file is untracked in the current branch
                Utils.exitWithError("There is an untracked file in the way;"
                        + " delete it, or add and commit it first.");
            }
        }
    }

    /**
     * @param fileName
     */
    // return true if the file is untracked by the Head commit.
    private static boolean untracked(String fileName) {
        File target = Utils.join(StagingArea.ADDITION, fileName);
        Commit head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
        return !head.getSnapshot().containsKey(fileName) && !target.exists();
    }

    /**
     * when will the file be overwritten?
     * The file in the current branch has the different content as the given Commit
     * @param fileName
     * @return
     */
    private static boolean beOverwritten(String fileName, Commit givenCommit) {
        File inCWD = Utils.join(CWD, fileName);
        String blobId = givenCommit.getSnapshot().get(fileName);
        if (blobId == null) {
            return false;
        }
        File targetBlob = Utils.join(BLOBS, blobId);
        if (inCWD.exists() && targetBlob.exists()) {
            String content1 = readContentsAsString(inCWD);
            String content2 = readContentsAsString(targetBlob);
            if (!content1.equals(content2)) {
                return true;
            }
        }
        return false;
    }

    public static void reset(String givenId) {
        Commit head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
        File givenCommitFile = Utils.join(COMMITS, givenId);
        if (!givenCommitFile.exists()) {
            Utils.exitWithError("No commit with that id exists.");
        }
        Commit givenCommit = Utils.readObject(givenCommitFile, Commit.class);
        checkoutFailure(givenCommit);
        for (String fileName : givenCommit.getSnapshot().keySet()) {
            String[] args = {"checkout", givenId, "--", fileName};
            checkout(args);
        }
        for (String fileName : Utils.plainFilenamesIn(CWD)) {
            //not in given commit but present in the current commit
            File target = Utils.join(CWD, fileName);
            if (!givenCommit.getSnapshot().containsKey(fileName) && !target.isDirectory()
                    && head.getSnapshot().containsKey(fileName)) {
                target.delete();
            }
        }
        Branch currentBranch = Utils.readObject(HEAD, Branch.class);
        currentBranch.setCurrentCommit(givenCommit);
        Utils.writeObject(HEAD, currentBranch);
        File lastFile = Utils.join(BRANCHCOLLECTION, currentBranch.getName());
        Branch lastBranch = Utils.readObject(lastFile, Branch.class);
        lastBranch.setCurrentCommit(givenCommit);
        writeObject(lastFile, lastBranch);
        for (String fileName : Utils.plainFilenamesIn(StagingArea.ADDITION)) {
            File target = Utils.join(StagingArea.ADDITION, fileName);
            target.delete();
        }
    }

    public static void merge(String givenBranch1) {
        // step 1: catch splitPoint;
        File givenBranchName = Utils.join(BRANCHCOLLECTION, givenBranch1);
        mergeFailures2(givenBranch1);
        Branch givenBranch = Utils.readObject(givenBranchName, Branch.class);
        Commit givenCommit = givenBranch.getCurrentCommit();
        Branch currentBranch = Utils.readObject(HEAD, Branch.class);
        checkoutFailure(givenCommit);
        Commit splitPoint = findSplitPoint(givenCommit, currentBranch.getCurrentCommit());
        // step 2: 2 failure cases
        if (splitPoint == null || isAncestor(givenCommit, currentBranch.getCurrentCommit())) {
            Utils.exitWithError("Given branch is an ancestor of the current branch.");
        } else if (isAncestor(currentBranch.getCurrentCommit(), givenCommit)
                && splitPoint != givenCommit) {
            String[] args = {"checkout", givenBranch1};
            checkout(args);
            System.out.println("Current branch fast-forwarded.");
            return;
        }

        // step 3: create the new mergeCommit
        Commit mergeCommit = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
        //step 4: follow up steps
        Commit givenBranchCurrCommit = givenBranch.getCurrentCommit();
        mergeCommit.setParentId(currentBranch.getCurrentCommit().getId());
        mergeCommit.setParent2Id(givenBranchCurrCommit.getId());
        merHelper1(splitPoint, givenBranchCurrCommit, mergeCommit);
        merHelper2(splitPoint, givenBranchCurrCommit, mergeCommit);
        merHelper3(splitPoint, givenBranchCurrCommit, mergeCommit);
        boolean conflict = false;
        if (conflict1(splitPoint, givenBranchCurrCommit, mergeCommit)) {
            conflict = true;
        }
        if (conflict2(splitPoint, givenBranchCurrCommit, mergeCommit)) {
            conflict = true;
        }
        if (conflict3(splitPoint, givenBranchCurrCommit, mergeCommit)) {
            conflict = true;
        }
        if (conflict4(splitPoint, givenBranchCurrCommit, mergeCommit)) {
            conflict = true;
        }
        if (conflict) {
            System.out.println("Encountered a merge conflict.");
        }
        mergeCommit.setMessage("Merged " + givenBranch1 + " into " + currentBranch.getName() + ".");
        byte[] idPara = Utils.serialize(mergeCommit);
        mergeCommit.setId(Utils.sha1(idPara));
        currentBranch.setCurrentCommit(mergeCommit);
        writeObject(HEAD, currentBranch);
        File file = Utils.join(BRANCHCOLLECTION, currentBranch.getName());
        writeObject(file, currentBranch);
    }



    // case 1
    private static void merHelper1(Commit splitPoint,
                                   Commit givenBranchCurrCommit, Commit mergeCommit) {
        for (String filename : splitPoint.getSnapshot().keySet()) {
            if (givenBranchCurrCommit.getSnapshot().containsKey(filename)
                    && mergeCommit.getSnapshot().containsKey(filename)) {
                String blobId = splitPoint.getSnapshot().get(filename);
                String compared = givenBranchCurrCommit.getSnapshot().get(filename);
                String curr = mergeCommit.getSnapshot().get(filename);
                if (blobId.equals(curr) && !blobId.equals(compared)) {
                    String[] args = {"checkout", givenBranchCurrCommit.getId(), "--", filename};
                    checkout(args);
                    mergeCommit.getSnapshot().put(filename, compared);
                }
            }
        }
    }

    // case 6
    private static void merHelper2(Commit splitPoint,
                                   Commit givenBranchCurrCommit, Commit mergeCommit) {
        for (String fileName : splitPoint.getSnapshot().keySet()) {
            if (mergeCommit.getSnapshot().containsKey(fileName)
                    && !givenBranchCurrCommit.getSnapshot().containsKey(fileName)) {
                String blobId = splitPoint.getSnapshot().get(fileName);
                if (blobId.equals(mergeCommit.getSnapshot().get(fileName))) {
                    mergeCommit.getSnapshot().remove(fileName);
                    File inCWD = Utils.join(CWD, fileName);
                    if (inCWD.exists()) {
                        inCWD.delete();
                    }
                }
            }
        }
    }

    //case 5
    private static void merHelper3(Commit splitPoint,
                                   Commit givenBranchCurrentCommit, Commit mergeCommit) {
        for (String fileName : givenBranchCurrentCommit.getSnapshot().keySet()) {
            if (!splitPoint.getSnapshot().containsKey(fileName)
                    && !mergeCommit.getSnapshot().containsKey(fileName)) {
                String[] args = {"checkout", givenBranchCurrentCommit.getId(), "--", fileName};
                checkout(args);
                mergeCommit.getSnapshot().put(fileName,
                        givenBranchCurrentCommit.getSnapshot().get(fileName));
            }
        }
    }

    private static boolean conflict1(Commit splitPoint, Commit givenBranch, Commit mergeCommit) {
        boolean conflict = false;
        for (String fileName : splitPoint.getSnapshot().keySet()) {
            if (givenBranch.getSnapshot().containsKey(fileName)
                    && mergeCommit.getSnapshot().containsKey(fileName)) {
                String spContent = splitPoint.getSnapshot().get(fileName);
                String gBContent = givenBranch.getSnapshot().get(fileName);
                String mergeContent = mergeCommit.getSnapshot().get(fileName);
                if (!spContent.equals(gBContent) && !spContent.equals(mergeContent)
                        && !gBContent.equals(mergeContent)) {
                    conflict = true;
                    File file2 = Utils.join(BLOBS, gBContent);
                    File file1 = Utils.join(BLOBS, mergeContent);
                    File inCWD = Utils.join(CWD, fileName);
                    if (inCWD.exists()) {
                        writeContents(inCWD, conflict(file1, file2));
                    }
                    writeContents(file1, conflict(file1, file2));
                }
            }
        }
        return conflict;
    }

    private static boolean conflict2(Commit splitPoint, Commit givenBranch, Commit mergeCommit) {
        boolean conflict = false;
        for (String fileName : mergeCommit.getSnapshot().keySet()) {
            if (!splitPoint.getSnapshot().containsKey(fileName)
                    && givenBranch.getSnapshot().containsKey(fileName)) {
                String gBContent = givenBranch.getSnapshot().get(fileName);
                String mergeContent = mergeCommit.getSnapshot().get(fileName);
                if (!mergeContent.equals(gBContent)) {
                    conflict = true;
                    File file1 = Utils.join(BLOBS, mergeContent);
                    File file2 = Utils.join(BLOBS, gBContent);
                    File inCWD = Utils.join(CWD, fileName);
                    if (inCWD.exists()) {
                        writeContents(inCWD, conflict(file1, file2));
                    }
                    writeContents(file1, conflict(file1, file2));
                }
            }
        }
        return conflict;
    }

    private static boolean conflict3(Commit splitPoint, Commit givenBranch, Commit mergeCommit) {
        boolean conflict = false;
        for (String fileName : splitPoint.getSnapshot().keySet()) {
            if (!mergeCommit.getSnapshot().containsKey(fileName)
                    && givenBranch.getSnapshot().containsKey(fileName)) {
                String gBContent = givenBranch.getSnapshot().get(fileName);
                String mergeContent = "null";
                String spContent = splitPoint.getSnapshot().get(fileName);
                if (!gBContent.equals(spContent)) {
                    conflict = true;
                    File file1 = Utils.join(BLOBS, mergeContent);
                    File file2 = Utils.join(BLOBS, gBContent);
                    File inCWD = Utils.join(CWD, fileName);
                    if (inCWD.exists()) {
                        writeContents(inCWD, conflict(file1, file2));
                    }
                    writeContents(file1, conflict(file1, file2));
                }
            }
        }
        return conflict;
    }

    private static boolean conflict4(Commit splitPoint, Commit givenBranch, Commit mergeCommit) {
        boolean conflict = false;
        for (String fileName : splitPoint.getSnapshot().keySet()) {
            if (splitPoint.getSnapshot().containsKey(fileName)
                    && mergeCommit.getSnapshot().containsKey(fileName)
                    && !givenBranch.getSnapshot().containsKey(fileName)) {
                String spContent = splitPoint.getSnapshot().get(fileName);
                String gBContent = "null";
                String mergeContent = mergeCommit.getSnapshot().get(fileName);
                if (!mergeContent.equals(spContent)) {
                    conflict = true;
                    File file1 = Utils.join(BLOBS, mergeContent);
                    File file2 = Utils.join(BLOBS, gBContent);
                    File inCWD = Utils.join(CWD, fileName);
                    if (inCWD.exists()) {
                        writeContents(inCWD, conflict(file1, file2));
                    }
                    writeContents(file1, conflict(file1, file2));
                }
            }
        }
        return conflict;
    }


    private static String conflict(File file1, File file2) {
        String firstLine = "<<<<<<< HEAD\n";
        String secondLine;
        String fourthLine;
        if (file1.exists()) {
            secondLine = Utils.readContentsAsString(file1);
        } else {
            secondLine = "";
        }
        String thirdLine = "=======\n";
        if (file2.exists()) {
            fourthLine = Utils.readContentsAsString(file2);
        } else {
            fourthLine = "";
        }
        String fifthLine = ">>>>>>>\n";
        return firstLine + secondLine + thirdLine + fourthLine + fifthLine;
    }

    private static void mergeFailures2(String givenBranch) {
        Branch currentBranch = Utils.readObject(HEAD, Branch.class);
        // case 1
        for (String fileName : Utils.plainFilenamesIn(StagingArea.ADDITION)) {
            Utils.exitWithError("You have uncommitted changes.");
        }
        for (String fileName : Utils.plainFilenamesIn(StagingArea.REMOVAL)) {
            Utils.exitWithError("You have uncommitted changes.");
        }
        //case 2
        File targetBranch = Utils.join(BRANCHCOLLECTION, givenBranch);
        if (!targetBranch.exists()) {
            Utils.exitWithError("A branch with that name does not exist.");
        }
        //case 3
        if (givenBranch.equals(currentBranch.getName())) {
            Utils.exitWithError("Cannot merge a branch with itself.");
        }
    }


    private Commit shortId(String shortId) {
        for (String id : Utils.plainFilenamesIn(COMMITS)) {
            String subId = id.substring(0, 5);
            if (subId.equals(shortId)) {
                File target = Utils.join(COMMITS, id);
                Commit targetCommit = Utils.readObject(target, Commit.class);
                return targetCommit;
            }
        }
        return null;
    }

    private static String shortenId(String id, int length) {
        return id.substring(0, length);
    }

    private static void logHelper(Commit cur) {
        System.out.println("===");
        System.out.println("commit " + cur.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
        System.out.println("Date: " + formatter.format(cur.getTimestamp()));
        System.out.println(cur.getMessage());
        if (cur.parent2Exist()) {
            System.out.println("Merged " + cur.getParentId());
        }
        System.out.println();
    }
    // this declares that the given commit is the merged commit
    private static void logMerge(Commit target, String message) {
        System.out.println("===");
        System.out.println("commit " + target.getId());
        System.out.println("Merge: " + shortenId(target.getParentId(), 7) + " "
                + shortenId(target.getParent2Id(), 7));
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
        System.out.println("Date: " + formatter.format(target.getTimestamp()));
        System.out.println(message); //Merged development into master.
        System.out.println();
    }

    private static HashMap<String, String> modifiedButNStag() {
        HashMap returnList = new HashMap();
        Commit head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
        for (String fileName : Utils.plainFilenamesIn(CWD)) {
            File file = Utils.join(CWD, fileName);
            File inAddition = Utils.join(StagingArea.ADDITION, fileName);
            if (!file.isDirectory() && !untracked(fileName)
                    && different(fileName) && !inAddition.exists()) {
                returnList.put(fileName, "modified");
            }
        }
        for (String fileName : Utils.plainFilenamesIn(StagingArea.ADDITION)) {
            if (different2(fileName)) {
                returnList.put(fileName, "modified");
            }
        }
        for (String fileName : head.getSnapshot().keySet()) {
            File targetFile = Utils.join(CWD, fileName);
            File inRemoval = Utils.join(StagingArea.REMOVAL, fileName);
            if (!targetFile.exists() && !inRemoval.exists()) {
                returnList.put(fileName, "deleted");
            }
        }
        for (String fileName : Utils.plainFilenamesIn(StagingArea.ADDITION)) {
            File file = Utils.join(CWD, fileName);
            if (!file.exists()) {
                returnList.put(fileName, "deleted");
            }
        }
        return returnList;
    }

    private static LinkedList<String> untrackedFiles() {
        LinkedList<String> returnList = new LinkedList<>();
        for (String fileName : Utils.plainFilenamesIn(CWD)) {
            File file = Utils.join(CWD, fileName);
            if (!file.isDirectory() && untracked(fileName)) {
                returnList.addLast(fileName);
            }
        }
        return returnList;
    }

    /**
     *
     * @param fileName the file you want to check if it is unStaged
     * @return false if it has the same content as in the addition area return true otherwise
     */
    private static boolean unStaged(String fileName) {
        File target = Utils.join(CWD, fileName);
        File inAddition = Utils.join(StagingArea.ADDITION, fileName);
        if (target.exists() && inAddition.exists()) {
            String content1 = Utils.readContentsAsString(target);
            String content2 = Utils.readContentsAsString(inAddition);
            if (content1.equals(content2)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param fileName the file you want to check
     * @return true if it is different from the head commit return false otherwise
     */
    private static boolean different(String fileName) {
        File target = Utils.join(CWD, fileName);
        Commit head = Utils.readObject(HEAD, Branch.class).getCurrentCommit();
        String blobId = head.getSnapshot().get(fileName);
        if (blobId != null && target.exists()) {
            File targetBlob = Utils.join(BLOBS, blobId);
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
        File inAddition = Utils.join(StagingArea.ADDITION, fileName);
        String content1 = Utils.readContentsAsString(target);
        String content2 = Utils.readContentsAsString(inAddition);
        if (!content1.equals(content2)) {
            return false;
        }
        return true;
    }

    // return false if there is file in the addition area
    private static boolean commitFailure() {
        for (String fileName : Utils.plainFilenamesIn(StagingArea.REMOVAL)) {
            return false;
        }
        for (String fileName : Utils.plainFilenamesIn(StagingArea.ADDITION)) {
            return false;
        }
        return true;
    }

    public static void checkIn() {
        if (!GITLET_DIR.exists()) {
            Utils.exitWithError("Not in an initialized Gitlet directory.");
        }
    }

    /**
     *
     * @param commit1 the former commit;
     * @param commit2 the latter commit;
     * @return true if the commit1 is the ancestor of commit2 return false otherwise;
     */
    public static boolean isAncestor(Commit commit1, Commit commit2) {
        Commit curr = commit2;
        if (curr == null) {
            return false;
        } else if (curr.getId().equals(commit1.getId())) {
            return true;
        }
        return isAncestor(commit1, commit2.getParent());
    }


    /**
     *
     * @param commit1 one of the commits  the order doesn't matter
     * @return the split point
     */
    private static Commit findSplitPoint(Commit commit1, Commit commit2) {
        Commit curr = commit2;
        while (curr != null) {
            if (isAncestor(curr, commit1)) {
                return curr;
            }
            if (isAncestor(commit1, curr)) {
                return commit1;
            }
            if (curr.parent2Exist()) {
                curr = curr.getParent2();
            } else {
                curr = curr.getParent();
            }
        }
        return null;
    }



}
