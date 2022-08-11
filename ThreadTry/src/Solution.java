import java.util.*;

class Solution {
    public int nthUglyNumber(int n) {
        if (n <= 6) {
            return n;
        }
        List<Boolean> uglys = new ArrayList();
        // 0 is not ugly.
        uglys.add(false);
        // all ugly numbers from 1 to 5.
        uglys.add(true);
        uglys.add(true);
        uglys.add(true);
        uglys.add(true);
        uglys.add(true);
        int counter = 5;
        int curr = 6;
        while (counter < n) {
            if (curr % 2 == 0 && uglys.get(curr / 2)) {
                uglys.add(true);
                counter++;
            } else if (curr % 3 == 0 && uglys.get(curr / 3)) {
                uglys.add(true);
                counter++;
            } else if (curr % 3 == 0 && uglys.get(curr / 5)) {
                uglys.add(true);
                counter++;
            } else {
                uglys.add(false);
            }
            curr++;
        }
        return counter;
    }


    public static void main(String[] args) {
        Solution test = new Solution();
        System.out.println(test.nthUglyNumber(10));
    }
}