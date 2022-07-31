import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class Child extends Parent {
    {
        System.out.println("C");
    }
    static {
        System.out.println("D");
    }

    public static void main(String[] args) {
        Collection<String> collection = new LinkedList<>();
        collection.add("foo");
        collection.add("bar");
        collection.add("baz");
        collection.forEach(System.out::println);
    }



}
