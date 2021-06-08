public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int solution = 1;

        return solution;
    }

    public static int helperSubstring(String s) {
        String finalString = "";
        for (int i = 0; i < s.length(); i++) {
            if (!finalString.contains(s.substring(i, i + 1))) {
                finalString += s.substring(i, i + 1);
            } else {
                break;
            }
        }
        return finalString.length();
    }


    public static void main(String[] args) {
        System.out.println(helperSubstring("Iamsohandsome"));
    }
}