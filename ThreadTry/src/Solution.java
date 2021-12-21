import static java.lang.Math.pow;

class Solution {
    public boolean isNumber(String s) {

        return false;
    }

    public static boolean isPowerOfTwo(int n) {
        int index = 0;
        while (pow(2, index) <= n) {
            if (n == pow(2, index)) {
                return true;
            }
            index += 1;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isPowerOfTwo(3));
    }
}