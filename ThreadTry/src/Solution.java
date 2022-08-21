
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