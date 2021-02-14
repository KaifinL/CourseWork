import deque.MaxArrayDeque;
import net.sf.saxon.trans.SymbolicName;
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
            String a = (String) o1;
            String b = (String) o2;
            return a.length()-b.length();
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
        assertEquals(test1.max(ForTest1),48);


    }

    @Test
    public void Test2(){
        MaxArrayDeque test2 = new MaxArrayDeque(ForTest2);
        test2.addLast("Kefin");
        test2.addLast("handsome");
        test2.addLast("genius");
        assertEquals(test2.max(),"handsome");
        assertEquals(test2.max(ForTest2),"handsome");
    }
}
