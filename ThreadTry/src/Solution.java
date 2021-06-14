import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> finalList = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; j < nums.length; j++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> newList = new ArrayList<>() ;
                        newList.add(nums[i]);
                        newList.add(nums[j]);
                        newList.add(nums[k]);
                        if (finalList.isEmpty()) {
                            finalList.add(newList);
                        } else {
                            for (List l : finalList) {
                                if (!new HashSet<>(newList).equals(new HashSet<>(l))) {
                                    finalList.add(newList);
                                }
                            }
                            finalList.add(new ArrayList<>());
                        }
                    }
                }
            }
        }

        return finalList;
    }

    public static void main(String[] args) {

    }

}