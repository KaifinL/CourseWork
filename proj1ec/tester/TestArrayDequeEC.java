package tester;
import static org.junit.Assert.*;
import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

import java.util.ArrayList;

public class TestArrayDequeEC {
    @Test
    public void DequeTest() {
        StudentArrayDeque<Integer> task1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> forTask1 = new ArrayDequeSolution<>();
        StringBuilder returnList = new StringBuilder();
        for (int i = 0; i < 500; i += 1) {  /*  @StudentArrayDequeLaucher.java */
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.25 && task1.size() > 0) {
                Integer a = task1.removeLast();
                Integer b = forTask1.removeLast();
                returnList.append("removeLast()\n");
                assertEquals(returnList.toString() , a, b);
            } else if (numberBetweenZeroAndOne < 0.5) {
                task1.addFirst(i);
                forTask1.addFirst(i);
                returnList.append("addFirst(" + i + ")");
            } else if (numberBetweenZeroAndOne < 0.75) {
                task1.addLast(i);
                forTask1.addLast(i);
                returnList.append("addLast(" + i + ")");
            } else if (numberBetweenZeroAndOne < 1 && task1.size() > 0) {
                Integer a = task1.removeFirst();
                Integer b = forTask1.removeFirst();
                returnList.append("removeFirst()");
                assertEquals(returnList.toString(), a, b);
            }
        }
    }
}
