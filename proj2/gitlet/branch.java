package gitlet;

import java.io.Serializable;
import java.util.TreeMap;

public class Branch implements Serializable {

    //variable
    private String name; // to indicates the branch name this is a instance variable.
    private Commit currentCommit; // the current commit that this branch points to.
    private static TreeMap<String, Commit> branches = new TreeMap<>();

    //main methods
    public Branch(String name, Commit currentCommit) {
        this.name = name;
        this.currentCommit = currentCommit;
        branches.put(name, currentCommit);
    }

    public void setCurrentCommit(Commit headCommit) {
        this.currentCommit = headCommit;
    }

    public Commit getCurrentCommit() {
        return currentCommit;
    }

    public String getName() {
        return name;
    }

    public static TreeMap<String, Commit> getBranches() {
        return branches;
    }

    public void setName(String name) {
        this.name = name;
    }

    //helper function

}
