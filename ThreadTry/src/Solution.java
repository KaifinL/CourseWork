
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
    public static int count(String a, String b) {
        int m = a.length();
        int n = b.length();
        int lookup[][] = new int[m + 1][n + 1];
        for (int i = 0; i <= n; ++i)
            lookup[0][i] = 0;
        for (int i = 0; i <= m; ++i)
            lookup[i][0] = 1;

        // Fill lookup[][] in
        // bottom up manner
        for (int i = 1; i <= m; i++)
        {
            for (int j = 1; j <= n; j++)
            {
                // If last characters are
                // same, we have two options -
                // 1. consider last characters
                //    of both strings in solution
                // 2. ignore last character
                //    of first string
                if (a.charAt(i - 1) == b.charAt(j - 1))
                    lookup[i][j] = lookup[i - 1][j - 1] +
                            lookup[i - 1][j];

                else
                    // If last character are
                    // different, ignore last
                    // character of first string
                    lookup[i][j] = lookup[i - 1][j];
            }
        }

        return lookup[m][n];
    }


    public int binary_palindrome(String s) {
        HashSet<String> palindromes = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append(i);
            for (int j = 0; j < 2; j++) {
                sb.insert(1, String.valueOf(i).repeat(2));
                for (int m = 0; m < 2; m++) {
                    sb.insert(2, i);
                    palindromes.add(sb.toString());
                }
            }
        }

        for (int i = 0; i+5 < s.length(); i++) {
            String curr = s.substring(i, i+5);
            if (palindromes.contains(curr)) {

            }
        }
    }

    public static void main(String[] args) {
        System.out.println(palindrome_subsequence("aabccba"));
    }



}