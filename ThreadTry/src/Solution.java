class Solution {
    public static int maxArea(int[] height) {
        int smaller = Math.min(height[0], height[1]);
        int output = smaller * 1;
        for (int i = 0; i < height.length; i++) {
            for (int j = output / height[i]; j < height.length - i; j++) {
                int currSmaller = Math.min(height[i], height[i + j]);
                int currArea = currSmaller * j;
                if (currArea > output) {
                    output = currArea;
                }
            }
        }
        return output;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[] {1,2,1}));
    }
}