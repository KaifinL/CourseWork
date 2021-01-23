/** Class that prints the Collatz sequence starting from a given number.
 *  @author YOUR NAME HERE
 */
public class Collatz {
    public static void main(String[] args) {
        int n = 5;
        while (n!=1) {
            n = nextnumber(n);
            System.out.print(n + " ");
            n = n+1;
        }
        System.out.println("1");
    }
    public static int nextnumber(int t) {
            if (t % 2 == 0) {    /** this statement is to judge whether the parameter is an odd or not      */
                return (t / 2);
            }
            return (3*t + 1);
    }

}
