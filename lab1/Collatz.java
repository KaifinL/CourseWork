/** Class that prints the Collatz sequence starting from a given number.
 *  @author YOUR NAME HERE
 */
public class Collatz {
    public static void main(String[] args) {
        int n = 5;
        while (n!=1) {
            n = nextnumber(n);
            System.out.print(n + " ");
        }
        System.out.println("1");
    }
    public static int nextnumber(int t) {
        while (t != 1) {

            if (t % 2 == 0) {    /** this statement is to judge whether the peramator is an odd or not      */
                return (t / 2);
            }
            return (3*t + 1);
        }
        return t;

/** this method return the next number of Collatz Sequence
 * however, it doesn't judge anything,it just return the value of the next number.
 */


    }

}
