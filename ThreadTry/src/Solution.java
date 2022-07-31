import java.util.*;

class Solution {


    // given the array nums, find all the possible permutations from
    // nums[i] to nums[j] and append these arrays to temp accordingly.
    private void permute_helper(List<List<Integer>> result, List<Integer> temp, int[]nums, int begin, int end) {
        if (temp.size() == nums.length) {
            result.add(new ArrayList<>(temp));
            return;
        }

        for (int i = begin; i < end; i++) {
            temp.add(nums[i]);
            if (temp.size() == nums.length) {
                result.add(new ArrayList<>(temp));
            } else {
                permute_helper(result, temp, nums, begin, i);
                permute_helper(result, temp, nums, i + 1, end);
            }
            temp.remove(temp.size()-1);
            int j = i+1;
            while (j < end&&nums[i]==nums[j]) {
                i++;
                j++;
            }
        }
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList();
        permute_helper(result, new ArrayList(), nums, 0, nums.length);
        return result;
    }

    public static void main(String[] args) {
        Solution test = new Solution();
        List<List<Integer>> result = test.permuteUnique(new int[]{1, 2, 3});
        System.out.println(result);
    }
}