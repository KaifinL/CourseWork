import java.util.ArrayList;
import java.util.Arrays;

public class Child extends Parent {
    {
        System.out.println("C");
    }
    static {
        System.out.println("D");
    }

    public static void main(String[] args) {
        ArrayList<Double> dlist = new ArrayList<>();
        ArrayList<Number> nlist = new ArrayList<>();
        addPi(nlist);
        addPi(dlist);
    }

    private static void addPi(ArrayList<? super Double> list) {
        list.add(Math.PI);
    }


}
