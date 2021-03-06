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
                Repository.add(args[1]);
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
                    File targetFile = Utils.join(Repository.GITLET_DIR, args[2]);
                    Repository.createFile(targetFile);
                    byte[] targetContent = Repository.Head.snapshot.get(args[2]).getBlob();
                    Utils.writeObject(targetFile, targetContent);
                }else if (args[2].equals("--")) {
                    File targetFile = Utils.join(Repository.GITLET_DIR, args[3]);
                    Repository.createFile(targetFile);
                    File commitFile = Utils.join(Repository.Commits, args[1]);
                    Commit targetCommit = Utils.readObject(commitFile, Commit.class);
                    byte[] targetContent = targetCommit.snapshot.get(args[3]).getBlob();
                    Utils.writeObject(targetFile, targetContent);
                }else {
                    /** TODO: haven't done anything with this situation yet!
                     *
                     */
                    String targetName = args[1];
                }
            case "global-log":
                Repository.globalLog();
            case "find":
                String commitMess = args[1];
                Repository.find(commitMess);
            default:
                Utils.exitWithError("No command with that name exists.");
        }
    }
}
