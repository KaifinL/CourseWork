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
        origin.removeLast();
        origin2.removeLast();
        assertEquals(origin,origin2);
    }
}
