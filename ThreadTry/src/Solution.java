import java.util.ArrayList;
import java.util.List;
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

    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        int sorted[] = sortArray(arr);
        int smallest = sorted[1] - sorted[0];
        List <List<Integer>> return_list = new ArrayList();
        List <Integer> element_List = new ArrayList();
        element_List.add(sorted[0]);
        element_List.add(sorted[1]);
        return_list.add(element_List);
        for (int i = 1; i < arr.length-1; i++) {
            if (sorted[i+1] - sorted[i] < smallest) {
                smallest = sorted[i+1] - sorted[i];
                return_list.clear();
                element_List.clear();
                element_List.add(sorted[i]);
                element_List.add(sorted[i+1]);
                return_list.add(element_List);
            } else if (sorted[i+1] - sorted[i] == smallest) {
                element_List = new ArrayList<>();
                element_List.add(sorted[i+1]);
                element_List.add(sorted[i]);
                return_list.add(element_List);
            }
        }
        return return_list;
    }

    public static void main(String[] args) {
        int test1[] = {1, 2, 3, 4};
        System.out.println(new Solution().minimumAbsDifference(test1));
    }

    //below is the quick sort implementation from LEETCODE!
    public int[] sortArray(int[] nums) {
        quickSort(nums, 0 , nums.length - 1);
        return nums;
    }
    public int partition(int[] arr, int start, int end) {
        int pIndex = (int) (Math.random() * (end - start + 1)) + start; // random-quick-sort
        int pivot = arr[pIndex];
        while (start < end) {
            // skip smaller in left
            while (start < end && arr[start] <= pivot) {
                start++;
            }
            // skip larger in right
            while (start < end && arr[end] >= pivot) {
                end--;
            }
            if (start < end) {
                swap(arr, start, end);
            }
        }
        if (arr[start] < pivot) {
            swap(arr, pIndex, start);
        }
        return start;
    }

    public void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int pIndex = partition(arr, start, end);
            if (pIndex > start) {
                quickSort(arr, start, pIndex - 1);
            }
            if (pIndex < end) {
                quickSort(arr, pIndex, end);
            }
        }
    }

    private void swap(int[] arr, int x, int y) {
        int temp;
        temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

}