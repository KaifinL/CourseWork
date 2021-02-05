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
        input.removeLast();
        //input.addLast(5);
        //input.addLast(6);
        ArrayDeque<Integer> expect = new ArrayDeque<>();
        expect.removeLast();
        //expect.addFirst(6);
        //expect.addFirst(5);
        assertEquals(input.get(0),expect.get(0) );

        assertEquals(input.removeLast(),expect.removeLast());
        assertEquals(input.getFirst(),expect.getFirst());
        assertEquals(input.size(),expect.size());
        assertEquals(input.get(0),expect.get(0));

    }
    public static void randomTest() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        int [] t = new int[1000];
        int N = 500;
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
