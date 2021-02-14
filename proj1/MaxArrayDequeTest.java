import deque.MaxArrayDeque;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Comparator;

public class MaxArrayDequeTest {
    Comparator ForTest1 = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return (Integer) o1 - (Integer) o2;
        }
    };

    Comparator ForTest2 = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return (String)o1. - (String)o2.;
        }
    };

    @Test
    public void Test1(){

        MaxArrayDeque test1 = new MaxArrayDeque(ForTest1);
        test1.addFirst(28);
        test1.addFirst(34);
        test1.addFirst(48);
        test1.addFirst(3);
        assertEquals(test1.max(),48);


    }

    @Test
    public void Test2(){



    }
}
