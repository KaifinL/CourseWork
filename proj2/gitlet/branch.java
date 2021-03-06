package gitlet;

public class branch {

    public String name;
    public Commit pointer;

    public branch(String name, Commit pointer) {
        this.name = name;
        this.pointer = pointer;
    }

}
