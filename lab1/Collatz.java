/** Class that prints the Collatz sequence starting from a given number.
 *  @author YOUR NAME HERE
 */
public class Collatz {
<<<<<<< HEAD
    public static int nextnumber(int t) {
        if (t % 2 == 0) {
            return (t / 2);
        }
        return (3 * t + 1);
    }
    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");
        while (n!=1) {
            n = nextnumber(n);
            System.out.print(n + " ");
        }
    }

=======

    /** Buggy implementation of nextNumber! */
    public static int nextNumber(int n) {
        if (n  == 128) {
            return 1;
        } else if (n == 5) {
            return 3 * n + 1;
        } else {
            return n * 2;
        }
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");
        while (n != 1) {
            n = nextNumber(n);
            System.out.print(n + " ");
        }
        System.out.println();
>>>>>>> 7aa1b6fc79cb752e1ed844cd9cdd8c9c21e7f3d4
    }

