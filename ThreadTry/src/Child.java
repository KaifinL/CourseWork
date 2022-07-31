import java.util.ArrayList;

public class Child extends Parent {
    {
        System.out.println("C");
    }
    static {
        System.out.println("D");
    }

    public static void main(String[] args) {
        Child child = new Child();
    }

    private static void addPi(ArrayList<Double> list) {
        list.add(Math.PI);
    }

    
}
