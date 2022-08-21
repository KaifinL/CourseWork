
import java.lang.reflect.Array;
import java.util.*;

class Solution {

    public static String breakPalindrome(String palindrome) {
        if(palindrome.length()<=1)
            return "";
        char[] arr = palindrome.toCharArray();

        for(int i=0; i<arr.length/2;i++){
            if(arr[i] != 'a'){ // if not a then change it to be lexographically smallest
                arr[i] = 'a';
                return String.valueOf(arr);
            }
        }
        // if we reach here, there are ONLY 'a' in palindrome string, so we should change the last character to a b
        arr[arr.length-1] = 'b';
        return String.valueOf(arr);
    }

    // 假的 这个没有control binary form.
    public static int palindrome_subsequence(String s) {
        long [][]three_length = new long[s.length()][s.length()];
        for (int i = s.length()-2; i>=0; i--) {
            for (int j = i+2; j < s.length(); j++) {
                three_length[i][j] = three_length[i][j-1]+three_length[i+1][j]-three_length[i+1][j-1];
                if (s.charAt(i) == s.charAt(j)) {
                    three_length[i][j] += (j-i-1);
                }
            }
        }

        long result = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i+4; j<s.length(); j++) {
                if (s.charAt(i)==s.charAt(j)) {
                    result += three_length[i+1][j-1];
                }
            }
        }
        return (int) (result% (Math.pow(10, 9)+7));
    }

    /*
    @para s is the original string given.
    @para target is the string among those 8
     */
    public static long count(String a, String b) {
        int m = a.length();
        long dp[][] = new long[m + 1][6];
        for (int i = 0; i <= 5; ++i)
            dp[0][i] = 0;
        for (int i = 0; i <= m; ++i)
            dp[i][0] = 1;
        for (int i = 1; i <= m; i++)
        {
            for (int j = 1; j <= 5; j++)
            {
                if (a.charAt(i - 1) == b.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] +
                            dp[i - 1][j];

                else
                    dp[i][j] = dp[i - 1][j];
            }
        }

        return dp[m][5];
    }


    public static int binary_palindrome(String s) {
        HashSet<String> palindromes = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append(i);
            for (int j = 0; j < 2; j++) {
                sb.insert(1, String.valueOf(j).repeat(2));
                for (int m = 0; m < 2; m++) {
                    sb.insert(2, m);
                    palindromes.add(sb.toString());
                    sb.delete(2, 3);
                }
                sb.delete(1, 3);
            }
        }

        long result = 0;
        for (String curr:palindromes) {
            result += count(s, curr);
        }
        return (int) (result%(Math.pow(10, 9)+7));
    }

    public static void main(String[] args) {
        System.out.println(binary_palindrome("010110"));
    }



}