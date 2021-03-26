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
                Repository.add(args[1]);
                // TODO: handle the `add [filename]` command
                break;
            // TODO: FILL THE REST IN
            case "commit":
                Repository.finalCommit(args);
                break;

            case "rm":
                Repository.remove(args[1]);
                break;
            case "log":
                Repository.log();
                break;
            case "checkout":
                Repository.checkout(args);
                break;
            case "global-log":
                Repository.globalLog();
                break;
            case "find":
                String commitMess = args[1];
                Repository.find(commitMess);
                break;
            case "status":
                Repository.status();
            case "branch":
                Repository.branchFunc(args[1]);
            case "rm-branch":
                Repository.rmBranch(args[1]);
            case "reset":
                Repository.reset(args[1]);
            case "merge":
                Repository.merge(args[1]);
            default:
                Utils.exitWithError("No command with that name exists.");
        }
    }
}
