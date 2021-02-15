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
}
