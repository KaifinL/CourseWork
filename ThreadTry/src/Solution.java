public class Solution {
    public static int lengthOfLongestSubstring(String s) {
        int solution = 1;
        for (int i = 0; i < s.length(); i++) {
            int length = helperSubstring(s.substring(i));
            if (length > solution) {
                solution = length;
            }
        }
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
        System.out.println(lengthOfLongestSubstring("bbbbb"));
    }
}