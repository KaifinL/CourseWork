import org.junit.Assert;
import org.junit.Test;

class Solution {
    public static String longestPalindrome(String s) {
        String output = "";
        for (int i = 0; i < s.length(); i++) {
            int k = 0;
            while (i + k < s.length() - 1) {
                String subString = s.substring(i, i + k + 1);
                if (judgement(subString) && subString.length() > output.length()) {
                    output = subString;
                }
                k += 1;
            }
        }
        return output;
    }

    public static String helper1(String s, int index) {
        int k = 1;
        String output = s.substring(0, 1);
        while (index >= k) {
            if (s.substring(index - k, index - k + 1).equals(s.substring(index + k, index + k + 1))) {
                output = s.substring(index - k, index + k + 1);
                k += 1;
            }
            else {
                break;
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
        String s = "babad";
        System.out.println(longestPalindrome(s));
    }
}