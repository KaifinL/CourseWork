
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

    public static int smallestRangeII(int[]a, int k) {
        int left_most = a[0]+k;
        int right_most = a[a.length-1]-k;
        int minimum = Integer.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            int curr_left = Math.min(a[i+1]-k, left_most);
            int curr_right = Math.max(a[i]+k, right_most);
            int curr_diff = curr_right-curr_left;
            minimum = Math.min(curr_diff, minimum);
        }
        return minimum;
    }

    public static void main(String[] args) {
        int[] test_arr = new int[]{1, 2, 3};
        System.out.println(maxLength(test_arr, 3));
    }

}