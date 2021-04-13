package byow.Core;

import org.junit.*;
import org.junit.Assert.*;
import java.util.PriorityQueue;

public class Tested {
    static PriorityQueue<Position> pq = new PriorityQueue();

    @Test
    public void test() {
        Position test1 = new Position(3, 5);
        pq.add(test1);
        System.out.println(pq.poll());
    }
}
