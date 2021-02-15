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
        for (int i = 0; i < 10000; i += 1) {  /*  @StudentArrayDequeLaucher.java */
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.5) {
                task1.addLast(i);
                forTask1.addLast(i);
            } else {
                task1.addFirst(i);
                forTask1.addFirst(i);
            }

        }
        for (int i = 0; i < task1.size(); i++) {
            assertEquals(task1.get(i), forTask1.get(i));
        }
        for (int i = 0; i < 1000; i += 1) {  /*  @StudentArrayDequeLaucher.java */
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.5 && task1.size() > 0) {
                task1.removeFirst();
                forTask1.removeFirst();
            } else {
                task1.addFirst(i);
                forTask1.addFirst(i);
            }
        }
        for (int i = 0; i < task1.size(); i++) {
            assertEquals(task1.get(i), forTask1.get(i));
        }
    }
}
