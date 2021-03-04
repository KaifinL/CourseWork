package gitlet;

import java.io.File;

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
                    String newFile = Utils.readContentsAsString(toBeAdded);
                    StagingArea.addition.put(args[1], newFile);
                }
                // TODO: handle the `add [filename]` command
                break;
            // TODO: FILL THE REST IN
            case "commit":
                String message = args[1];
                Commit newCommit;
                newCommit = Commit.copy(Commit.Commits.getLast());
                newCommit.setMessage(message);

            default:
                Utils.exitWithError("No command with that name exists.");
        }
    }
}
