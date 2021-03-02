package gitlet;
import jdk.jshell.execution.Util;

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
                    Commit initialCommit = new Commit();
                    initialCommit.commitMessage("initial commit");
                    initialCommit.setTimestamp(00, 00,  00, "Thursday", 1, "January", 1970);

                }
                // TODO: handle the `init` command
                break;
            case "add":
                File toBeAdded = Utils.join(Repository.GITLET_DIR, args[1]);
                if (!toBeAdded.exists()) {
                    Utils.exitWithError("Incorrect operands.");
                }
                // TODO: handle the `add [filename]` command
                break;
            // TODO: FILL THE REST IN
            default:
                Utils.exitWithError("No command with that name exists.");
        }
    }
}
