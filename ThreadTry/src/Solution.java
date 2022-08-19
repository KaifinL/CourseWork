
import java.lang.reflect.Array;
import java.util.*;

class Solution {

    public static int maxLength(int[] a, int k) {
        int curr_sum = 0;
        int max_length = 0;
        int left = 0, right = 0;
        while (right < a.length) {
            curr_sum+=a[right];
            while (curr_sum > k) {
                curr_sum-=a[left];
                left++;
            }
            max_length = Math.max(max_length, right-left+1);
            right++;
        }
        return max_length;
    }

    public static int smallestRangeII(int[]a, int )

    public static void main(String[] args) {
        int[] test_arr = new int[]{1, 2, 3};
        System.out.println(maxLength(test_arr, 3));
    }

}