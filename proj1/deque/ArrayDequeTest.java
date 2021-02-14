package deque;


import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    public static void main(String[] args) {
        //testThreeAddThreeRemove();
        //randomTest();
        ArrayDeque<Integer> test = new ArrayDeque<>();
        test.addLast(0);
        test.removeLast();
        System.out.println(test.get(0));
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
        AListNoResizing<Integer> M = new AListNoResizing<>();
        int N = 50000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                M.addLast(randVal);
                assertEquals(L.size(),M.size());
            } else if (operationNumber == 1 & L.size() > 0) {
                // getLast
                assertEquals(L.getLast(),M.getLast());

            } else if (operationNumber == 2 & L.size() > 0) {
                // removeLast
                assertEquals(L.removeLast(),M.removeLast());

            } else if (operationNumber == 3) {
                //get(0)
                assertEquals(L.get(0),M.get(0));

            }
        }
    }

}
