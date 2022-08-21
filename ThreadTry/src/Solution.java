
import jh61b.junit.In;

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


    // if no built-in converter allowed, use this functiont to convert an int to a binary representation.
    public static String int2Binary(int n)
    {
        String s = "";
        while (n > 0)
        {
            s =  ( (n % 2 ) == 0 ? "0" : "1") +s;
            n = n / 2;
        }
        return s;
    }

    public static List<Integer> cardinality_sorting(List<Integer> arr) {
        Integer[] arr_list = new Integer[arr.size()];
        arr_list = arr.toArray(arr_list);
        Arrays.sort(arr_list, (a, b)->{
            String bin_a = int2Binary(a);
            String bin_b = int2Binary(b);
            return (!bin_a.equals(bin_b) ? bin_a.compareTo(bin_b):a.compareTo(b));
        });
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < arr_list.length; i++) {
            result.add(arr_list[i]);
        }
        return result;
    }

    // real cardinality sorting.
    public static int[] sortByBits(int[] arr) {
        int[] bits = new int[arr.length];
        int[] ans = new int[arr.length];
        Arrays.sort(arr);
        for(int i = 0; i < arr.length; i++){
            int count = 0;
            int n = arr[i];
            while(n > 0){
                if((n & 1) == 1){
                    count++;
                }
                n >>= 1;
            }
            bits[i] = count;
        }
        int l = 0;
        for(int j = 0; j <= 14; j++){
            int length = 0;
            for(int k = 0; k < bits.length; k++){
                if(j == bits[k]){
                    length++;
                    ans[l] = arr[k];
                    l++;
                }
            }
        }
        return ans;
    }

    //is possible
    public boolean possible(int a, int b, int c, int d) {
        if (a > c || b > d) {
            return false;
        }
        if (a == c && b==d){
            return true;
        }
        return possible(a+b, b, c, d) || possible(a, a+b, c, d);
    }

    


    public static void main(String[] args) {
        System.out.println(binary_palindrome("01111"));
        System.out.println();
    }



}