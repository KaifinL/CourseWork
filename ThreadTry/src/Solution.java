import static java.lang.Math.pow;

class Solution {
    public boolean isNumber(String s) {

        return false;
    }

    public static boolean isPowerOfTwo(int n) {
        int index = 0;
        while (true) {
            if (n > pow(2, index)) {
                return false;
            }
            if (n == pow(2, index)) {
                return true;
            }
            index += 1;
        }
    }

    public static void main(String[] args) {
        System.out.println(isPowerOfTwo(2));
    }
}