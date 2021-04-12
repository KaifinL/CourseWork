package byow.Core;

public class Counter {
    // variable here
    private static int size = 0;
    private static double complexity = 0;
    private final static int SQUARE = 400;

    public static double getComplexity() {
        return complexity;
    }

    public static int getSize() {
        return size;
    }

    public static void changeSize(int change) {
        size = size + change;
        complexity = size / SQUARE;
    }
}
