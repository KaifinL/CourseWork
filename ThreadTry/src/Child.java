import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

public class Child extends Parent {
    {
        System.out.println("C");
    }
    static {
        System.out.println("D");
    }

    public static void main(String[] args) {
        Collection<String> collection = new LinkedHashSet<>();
        collection.add("foo");
        collection.add("bar");
        collection.add("")
    }



}
