
import java.lang.reflect.Array;
import java.util.*;

class Solution {

    public static List<Integer> traffic(List<Integer> arrival, List<Integer> street) {
        LinkedList<Integer> main_str = new LinkedList();
        LinkedList<Integer> avenue = new LinkedList();
        // we store the index in the array, so that arrival[i] represent the time.
        for (int i = 0; i < arrival.size(); i++) {
            if (street.get(i) == 0) {
                main_str.add(i);
            } else {
                avenue.add(i);
            }
        }

        int curTime = -1;
        boolean first_priority = true;
        int []result = new int[arrival.size()];
        while (!main_str.isEmpty() && !avenue.isEmpty()) {
            int first_main = arrival.get(main_str.peek());
            int first_aven = arrival.get(avenue.peek());
            if ((curTime < first_main && !first_priority) || (curTime < first_aven && first_priority)) {
                curTime = Math.max(Math.min(first_aven, first_main), curTime);
                first_priority = (first_aven<=first_main);
            }

            if (first_priority && first_aven-curTime <= 1) {
                result[avenue.poll()] = curTime;
                curTime++;
                first_priority = true;
            } else {
                result[main_str.poll()] = curTime;
                curTime++;
                first_priority = false;
            }
        }
        if (main_str.isEmpty()) {
            while (!avenue.isEmpty()) {
                int curr_index = avenue.poll();
                result[curr_index] = Math.max(curTime, arrival.get(curr_index));
                curTime = Math.max(arrival.get(curr_index), curTime+1);
            }
        } else {
            while (!main_str.isEmpty()) {
                int curr_index = main_str.poll();
                result[curr_index] = Math.max(curTime, arrival.get(curr_index));
                curTime = Math.max(arrival.get(curr_index), curTime+1);
            }
        }
        List<Integer> result_lst = new ArrayList<>();
        for (int j : result) {
            result_lst.add(j);
        }
        return result_lst;
    }

    HashSet<String> seen;

    private boolean isPerfect(int num) {
        return Math.pow(Math.ceil(Math.sqrt(num)), 2) == num;
    }

    public boolean reach_helper(int x1, int y1, int x2, int y2, int c) {
        if (x1 > x2 || y1>y2) {
            return false;
        }
        if (isPerfect(x1+y1)) {
            return false;
        }
        String curr = String.valueOf(x1) + "," + String.valueOf(y1);
        if (seen.contains(curr)) {
            return false;
        }

        if (x1 == x2 && y1 == y2) {
            return true;
        }

        seen.add(curr);

        return reach_helper(x1+y1, y1, x2, y2, c) || reach_helper(x1, x1+y1, x2, y2, c) || reach_helper(x1+c, y1+c, x2, y2, c);
    }


    public boolean reachingPoints(int x1, int y1, int x2, int y2, int c) {
        seen = new HashSet<>();
        return reach_helper(x1, y1, x2, y2, c);
    }

    public int minSetSize(int[] arr) {
        HashMap<Integer, Integer> frequency = new HashMap();
        for (int i = 0; i < arr.length; i++) {
            frequency.put(arr[i], frequency.getOrDefault(arr[i], 0)+1);
        }

        PriorityQueue<Integer> max_heap = new PriorityQueue<Integer>(frequency.size(), (a, b)->(Integer.compare(b, a)));
        for (int freq : frequency.values()) {
            max_heap.add(freq);
        }
        int result = 0;
        for (int i = 0; i < frequency.size(); i++) {
            result += max_heap.poll();
            if (result >= arr.length/2) {
                return i+1;
            }
        }
        return -1;
    }

    public static int maxLength(int[] a, int k) {
        int curr_sum = a[0];
        int max_length = 0;
        int left = 0, right = 0;
        while (right < a.length) {
            if (left == right) {
                right++;
            } else {
                if (curr_sum > k) {
                    curr_sum -= a[left];
                    left++;
                } else {
                    curr_sum += a[right];
                    right++;
                    max_length = Math.max(max_length, right-left);
                }
            }
        }
        return max_length;
    }

    public static void main(String[] args) {
        
    }

}