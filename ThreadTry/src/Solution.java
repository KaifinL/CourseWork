

class Solution {
    public static int isNumber(int[] arr) {
        return (int) (Math.random() * arr.length);
    }

    public int mySqrt(int x) {
        if (x == 0) return 0;
        int left = 1;
        int right = x/2;
        while (left < right) {
            int mid = (left+right)/2;
            if (mid * mid <= x && (mid+1)*(mid+1) >x) {
                return mid;
            } else if (mid * mid < x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        Solution test = new Solution();
        System.out.println(test.mySqrt(4444444));
    }

}


