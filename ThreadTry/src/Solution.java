class Solution {
    public static int maxArea(int[] height) {
        int smaller = Math.min(height[0], height[1]);
        int output = smaller * smaller;
        for (int i = 0; i < height.length; i++) {
            for (int j = 0; j < height.length - i; j++) {
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
        System.out.println(maxArea(new int[] {}));
    }
}