import org.junit.Assert;
import org.junit.Test;

class Solution {
    public static String longestPalindrome(String s) {
        String output = s.substring(0, 1);
        for (int i = 0; i < s.length(); i++) {
            int k = output.length();
            while (i + k < s.length()) {
                String subString = s.substring(i, i + k + 1);
                if (judgement(subString) && subString.length() > output.length()) {
                    output = subString;
                }
                k += 1;
            }
        }
        return output;
    }

    public static boolean judgement(String s) {
        String reverse = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            reverse += s.substring(i, i + 1);
        }
        if (!reverse.equals(s)) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        String s = "bb";
        System.out.println(longestPalindrome(s));
    }
}