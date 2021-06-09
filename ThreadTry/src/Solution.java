class Solution {
    public static int maxArea(int[] height) {
        int smaller = Math.min(height[0], height[1]);
        int output = smaller ^ 2;
        for (int i = 0; i < height.length; i++) {
            int curr = height[i];
        }
        return output;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[] {2, 1}));
    }
}