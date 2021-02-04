package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
  public static void main(String[] args) {
      testThreeAddThreeRemove();
      randomTest();
  }
    public static void testThreeAddThreeRemove(){
        AListNoResizing<Integer> origin = new AListNoResizing<>();
        BuggyAList<Integer> origin2 = new BuggyAList<>();
        origin.addLast(4);
        origin2.addLast(4);
        origin.addLast(5);
        origin2.addLast(5);
        origin.addLast(6);
        origin2.addLast(6);

        assertEquals(origin.size(),origin2.size());
        assertEquals(origin.removeLast(),origin2.removeLast());
    }
    public static void randomTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> M = new BuggyAList<>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                M.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1 & L.size() > 0) {
                // getLast
                System.out.println(L.getLast());
                assertEquals(L.getLast(),M.getLast());
            }else if (operationNumber == 2 & L.size() > 0){
                // removeLast
                assertEquals(L.removeLast(),M.removeLast());

            }
        }
    }
}
