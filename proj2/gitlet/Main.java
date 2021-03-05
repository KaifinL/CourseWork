package gitlet;

import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.io.IOException;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Kaifeng Lin
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            Utils.exitWithError("Must have at least one argument.");
        }
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                File initial = Repository.GITLET_DIR;
                if (initial.exists()) {
                    Utils.exitWithError("A Gitlet version-control system already exists in the current directory.");
                }else {
                    Repository.setupPersistence();
                }
                // TODO: handle the `init` command
                break;
            case "add":
                File toBeAdded = Utils.join(Repository.GITLET_DIR, args[1]);
                if (!toBeAdded.exists()) {
                    Utils.exitWithError("File does not exist.");
                }else {
                    if (StagingArea.containsName(args[1])) {
                        StagingArea.addition.remove(args[1]);
                    }
                    StagingArea.addition.put(args[1], toBeAdded);
                }
                // TODO: handle the `add [filename]` command
                break;

            // TODO: FILL THE REST IN
            case "commit":
                if (args.length == 1) {
                    Utils.exitWithError("Please enter a commit message.");
                }
                String message = args[1];
                if (StagingArea.addition == null) {
                    Utils.exitWithError("No changes added to the commit.");
                }else {
                    Repository.makeCommit(message);
                }
                break;

            case "rm":
                String fileName = args[1];
                if (!Repository.removeFile(fileName)) {
                    Utils.exitWithError("No reason to remove the file.");
                }
                break;

            case "log":
                Repository.log();
                break;
            case "checkout":
                /**
                 * Takes the version of the file as it exists in the head commit and puts it
                 * in the working directory, overwriting the version of the file that’s already there
                 * if there is one. The new version of the file is not staged.
                 */
                if (args[1].equals("--")) {
                    String target = args[2];
                    if (!Repository.Head.snapshot.containsKey(target)) {
                        Utils.exitWithError("File does not exist in that commit.");
                    }else {
                        File targetFile = Repository.Head.snapshot.get(target);
                        Repository.checkout(targetFile, target);
                    }
                }else if (args[2].equals("--")) {
                    File targetCom = Utils.join(Repository.Commits, args[1]);
                    Commit targetCommit = Utils.readObject(targetCom, Commit.class);
                    File targetFile = targetCommit.snapshot.get(args[3]);
                    Repository.checkout(targetFile, args[3]);
                    /**Takes the version of the file as it exists in the commit with the given id,
                     * and puts it in the working directory, overwriting the version of the file
                     * that’s already there if there is one. The new version of the file is not staged.
                     */
                }
            default:
                Utils.exitWithError("No command with that name exists.");
        }
    }
}
