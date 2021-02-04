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
    public void testThreeAddThreeRemove(){
        AListNoResizing origin = new AListNoResizing();
        origin.addLast(4);
        origin.addLast(5);
        origin.addLast(6);
        origin.removeLast();
        AListNoResizing expect = new AListNoResizing();
    }
}
