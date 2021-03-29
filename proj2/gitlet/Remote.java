package gitlet;

import java.io.File;
import java.io.Serializable;

public class Remote implements Serializable {
    // variables here
    private String name;
    private String nameOfDir;
    public static final File Remotes = Utils.join(Repository.CWD, "Remotes");

    public Remote(String name, String nameOfDir) {
        this.name = name;
        this.nameOfDir = nameOfDir;
        File newRemote = Utils.join(Remotes, this.name);
        Repository.createFile(newRemote);
        Utils.writeObject(newRemote, this);
    }

    public static void addRemote(String name, String nameOfDir) {
        new Remote(name, nameOfDir);
    }

    public static void rmRemote(String name) {
        if (!checkRepetition(name)) {
            Utils.exitWithError("A remote with that name does not exists");
        }
        File target = Utils.join(Remotes, name);
        target.delete();
    }

    public static void push(String name, String branchName) {
        File targetRemoteF = Utils.join(Remotes, name);
        Remote targetRemote = Utils.readObject(targetRemoteF, Remote.class);
        String address = targetRemote.getNameOfDir();
        File gitLetSystem = new File(address);
        if (!gitLetSystem.exists()) {
            Utils.exitWithError("");
        }
    }

    /**
     * this is a helper function
     * @param name the given string you want to check
     * @return true if the given name exists in the remotes directory;
     */
    private  static boolean checkRepetition(String name) {
        for (String FileName : Utils.plainFilenamesIn(Remotes)) {
            if (FileName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getNameOfDir() {
        return nameOfDir;
    }
}
