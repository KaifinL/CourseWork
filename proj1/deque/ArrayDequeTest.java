package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void testAddFirst(){
        ArrayDeque test1 = new ArrayDeque();
        test1.addFirst(0);
        test1.addFirst("newlymade");
        test1.addFirst(7);
        assertEquals(test1.get(0),7);
        assertEquals(test1.get(1),"newlymade");
        assertEquals(test1.get(2),0);
    }

    @Test
    public void testRemoveFirst(){
        ArrayDeque test2 = new ArrayDeque();
        test2.removeFirst();
        test2.addFirst(0);
        test2.addFirst(7);
        test2.addFirst(9);
        int a = (int) test2.removeFirst();
        int b = (int) test2.removeFirst();
        int c = (int) test2.removeFirst();
        assertEquals(a,9);
        assertEquals(b,7);
        assertEquals(c,0);
    }
}
