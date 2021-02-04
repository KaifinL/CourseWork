package deque;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    public static void main(String[] args) {
        testThreeAddThreeRemove();
        //randomTest();
    }
    public static void testThreeAddThreeRemove(){
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.removeFirst();
        input.addLast(5);
        input.addLast(6);
        ArrayDeque<Integer> expect = new ArrayDeque<>();
        expect.removeFirst();
        expect.addFirst(6);
        expect.addFirst(5);

        assertEquals(input.removeLast(),expect.removeLast());
        assertEquals(input.getFirst(),expect.getFirst());
        assertEquals(input.size(),expect.size());

    }
    public static void randomTest() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        ArrayDeque<Integer> M = new ArrayDeque<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
            } else if (operationNumber == 1 & L.size() > 0) {
                // getLast

            } else if (operationNumber == 2 & L.size() > 0) {
                // removeLast


            } else if (operationNumber == 3) {
                //size
                int size = L.size();

            }
        }
    }

}
