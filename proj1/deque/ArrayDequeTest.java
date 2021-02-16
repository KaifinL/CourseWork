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

    @Test
    public void testGet(){
        Deque test3 = new ArrayDeque();
        test3.removeFirst();
        test3.addFirst(0);
        test3.addFirst(7);
        test3.addFirst(9);
        assertEquals(test3.get(0),9);
        assertEquals(test3.get(1),7);
        assertEquals(test3.get(2),0);
    }

    @Test
    public void testAddLast(){
        Deque test4 = new ArrayDeque();
        Deque test9 = new LinkedListDeque();
        test4.removeLast();
        test4.addLast(0);
        test4.addLast(7);
        test4.addLast(9);
        test9.addLast(0);
        test9.addLast(7);
        test9.addLast(9);
        Integer a = (Integer) test4.removeFirst();
        Integer b = (Integer) test9.removeFirst();
        assertEquals(a,b);
        assertEquals(test4.get(0),test9.get(0));
        assertEquals(test4.get(1),test9.get(1));
        assertEquals(test4.get(2),test9.get(2));
    }

    @Test
    public void testEquals(){
        Deque test5 = new ArrayDeque();
        LinkedListDeque test6 = new LinkedListDeque();
        test5.addLast(5);
        test5.addLast(6);
        test5.addLast(7);
        test6.addLast(5);
        test6.addLast(6);
        test6.addLast(7);
        assertTrue(test5.equals(test6));
    }

    @Test
    public void testRemoveLast(){
        Deque test8 = new ArrayDeque();
        LinkedListDeque test7 = new LinkedListDeque();
        test7.addLast(5);
        test7.addLast(6);
        test7.addLast(7);
        test8.addLast(5);
        test8.addLast(6);
        test8.addLast(7);
        for (int i = 0; i < test7.size(); i++) {
            Integer a = (Integer) test7.removeFirst();
            Integer b = (Integer) test8.removeFirst();
            assertEquals(a,b);
        }
    }

    @Test
    public void testAddGet(){
        Deque test1 = new ArrayDeque();
        test1.addFirst(0);
        test1.addLast(1);
        test1.addLast(2);
        test1.addLast(3);
        assertEquals(0,test1.get(0));
        assertEquals(1,test1.get(1));
        assertEquals(2,test1.get(2));
        assertEquals(3,test1.get(3));
    }

    @Test
    public void AGtest(){
        ArrayDeque newone = new ArrayDeque();
        newone.addLast(0);
        newone.addLast(1);
        int a = (Integer) newone.removeLast();
        assertEquals(1,a);

    }

    @Test
    public void AGtest2(){
        ArrayDeque ugly = new ArrayDeque();
        ugly.addLast(0);
        ugly.addLast(1);
        ugly.addLast(2);
        ugly.addLast(3);
        ugly.removeFirst();
        ugly.addLast(5);
        int a = (int) ugly.removeFirst();
        assertEquals(1,a);
    }

    @Test
    public void AGtest3(){
        Deque ArrayDeque = new ArrayDeque();
        ArrayDeque.addLast(0);
        ArrayDeque.removeFirst();
        ArrayDeque.addLast(2);
        ArrayDeque.addLast(3);
        ArrayDeque.addLast(4);
        ArrayDeque.addFirst(5);
        ArrayDeque.removeFirst();
        ArrayDeque.addFirst(7);
        int a = (int) ArrayDeque.removeLast();
        assertEquals();

    }
}


