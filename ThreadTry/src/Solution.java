import java.util.Random;

import static java.lang.Math.pow;

class Solution {
    public static int isNumber(int[] arr) {
        return (int) (Math.random() * arr.length);
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
        int a[] = {1, 2, 3, 4, 5};
        System.out.println(isNumber(a));
    }
}