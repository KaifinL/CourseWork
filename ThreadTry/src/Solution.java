import java.util.*;

class Solution {
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        HashMap<Integer, Integer> num_index = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            if (num_index.getOrDefault(nums[i], -1) == -1) {
                num_index.put(nums[i], i);
            } else {
                int index = num_index.get(nums[i]);
                if (i - index <= k) {
                    return true;
                } else {
                    num_index.put(nums[i], i);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int []nums = new int[]{1,2,3,1,2,3};
        boolean a = containsNearbyDuplicate(nums, 2);
        System.out.println(a);
    }
}