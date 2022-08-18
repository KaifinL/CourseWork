
import java.util.*;

class Solution {

    public static int[] traffic(int []arrival, int[]street) {
        LinkedList<Integer> main_str = new LinkedList();
        LinkedList<Integer> avenue = new LinkedList();
        // we store the index in the array, so that arrival[i] represent the time.
        for (int i = 0; i < arrival.length; i++) {
            if (street[i] == 0) {
                main_str.add(i);
            } else {
                avenue.add(i);
            }
        }

        int curTime = -1;
        boolean first_priority = true;
        int []result = new int[arrival.length];
        while (!main_str.isEmpty() && !avenue.isEmpty()) {
            int first_main = arrival[main_str.peek()];
            int first_aven = arrival[avenue.peek()];
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
                result[curr_index] = Math.max(curTime, arrival[curr_index]);
                curTime = Math.max(arrival[curr_index], curTime+1);
            }
        } else {
            while (!main_str.isEmpty()) {
                int curr_index = main_str.poll();
                result[curr_index] = Math.max(curTime, arrival[curr_index]);
                curTime = Math.max(arrival[curr_index], curTime+1);
            }
        }
        return result;
    }

    public static boolean reachingPoints(int sx, int sy, int tx, int ty) {
        boolean[][] reachable = new boolean[tx-sx+1][ty-sy+1];
        reachable[tx-sx][ty-sy] = true;
        for (int i = tx-sx; i >= 0; i--) {
            for (int j = ty-sy; j >= 0; j--) {
                if (i+j <= tx-sx && reachable[i+j][j]) {
                    reachable[i][j] = true;
                } else if (i+j <= ty-sy && reachable[i][i+j]) {
                    reachable[i][j] = true;
                } else {
                    reachable[i][j] = false;
                }
            }
        }
        return reachable[0][0];
    }

    public static void main(String[] args) {
//        int []arrival = new int[]{0, 0, 1, 4};
//        int []street = new int[] {0, 1, 1, 0};
        boolean test = reachingPoints(1, 1, 3, 5);
    }

}