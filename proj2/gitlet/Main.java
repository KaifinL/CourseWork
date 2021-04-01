package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Kaifeng Lin
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            Utils.exitWithError("Please enter a command.");
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                Repository.setupPersistence();
                break;
            case "add":
                Repository.checkIn();
                Repository.add(args);
                break;
            case "commit":
                Repository.checkIn();
                Repository.finalCommit(args);
                break;
            case "rm":
                Repository.checkIn();
                Repository.remove(args[1]);
                break;
            case "log":
                Repository.checkIn();
                Repository.log();
                break;
            case "checkout":
                Repository.checkIn();
                Repository.checkout(args);
                break;
            case "global-log":
                Repository.checkIn();
                Repository.globalLog();
                break;
            case "find":
                Repository.checkIn();
                String commitMess = args[1];
                Repository.find(commitMess);
                break;
            case "status":
                Repository.checkIn();
                Repository.status();
                break;
            case "branch":
                Repository.checkIn();
                Repository.branchFunc(args);
                break;
            case "rm-branch":
                Repository.checkIn();
                Repository.rmBranch(args[1]);
                break;
            case "reset":
                Repository.checkIn();
                Repository.reset(args[1]);
                break;
            case "merge":
                Repository.checkIn();
                Repository.merge(args[1]);
                break;
            case "add-remote":
                Remote.addRemote(args[1], args[2]);
                break;
            case "rm-remote":
                Remote.rmRemote(args[1]);
                break;
            case "push":
                Remote.push(args);
                break;
            case "fetch":
                Remote.fetch(args);
                break;
            case "pull":
                Remote.pull(args);
                break;
            default:
                Utils.exitWithError("No command with that name exists.");
        }
    }
}
