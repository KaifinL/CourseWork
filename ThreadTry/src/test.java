import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;


public class test {

    @Test
    public void test1() {
        List tst1 = new ArrayList();
        tst1.add(1);
        tst1.add(2);
        List tst2 = new ArrayList();
        tst2.add(2);
        tst2.add(1);
        assertTrue(new HashSet<Integer>(tst1).equals(new HashSet<>(tst2)));
    }
}
