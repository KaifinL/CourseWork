package tester;
import static org.junit.Assert.*;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void DequeTest() {
        StudentArrayDeque<Integer> task1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> forTask1 = new ArrayDequeSolution<>();

        for (int i = 0; i < 100; i += 1) {  /*  @StudentArrayDequeLaucher.java */
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.1 && task1.size() > 0) {
                Integer a = task1.removeLast();
                Integer b = forTask1.removeLast();
                assertEquals(a,b);
            } else {
                task1.addFirst(i);
                forTask1.addFirst(i);
            }
        }

        for (int i = 0; i < 100; i += 1) {  /*  @StudentArrayDequeLaucher.java */
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.5 && task1.size() > 0) {
                Integer a = task1.removeLast();
                Integer b = forTask1.removeLast();
                assertEquals(a,b);
            } else {
                task1.addLast(i);
                forTask1.addLast(i);
            }
        }
    }
}
