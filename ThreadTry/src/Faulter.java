import com.sun.source.tree.BinaryTree;

import java.util.HashMap;
import java.util.Map;

public class Faulter {
    private String error_message;
    public Faulter(String error_message) {
        if (error_message == null || (error_message != "NullPointer" && error_message != "ArrayIndexOutOfBounds" && error_message != "ClassCast")) {
            throw new IllegalArgumentException();
        }
        this.error_message = error_message;
    }

    public Exception fault() {
        if (this.error_message == "NullPointer") {
            return new NullPointerException("null pointer!!");
        } else if (this.error_message == "ArrayIndexOutOfBounds") {
            return new ArrayIndexOutOfBoundsException("");
        } else {
            return new ClassCastException();
        }

    }

    public class BinaryTree {
        protected class Node {
            protected Object value;
            protected Node right;
            protected Node left;
            Node(Object setValue) { value = setValue; }
        } protected Node root;
    }

    public class YourBinaryTree extends BinaryTree {
        protected Map<Object, Integer> valueCount() {
            Map toReturn = new HashMap();
            toReturn.
        }
    }

    public static void main(String[] args) {
        Faulter test1 = new Faulter("NullPointer");
        test1.fault();
    }
}
