
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

    // word compression
    public String removeDuplicates(String s, int k) {
        Stack <Integer> counter = new Stack();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (counter.empty() || s.charAt(i) != sb.charAt(sb.length()-1)) {
                counter.push(1);
                sb.append(s.charAt(i));
            } else {
                if (counter.peek() < k-1) {
                    counter.push(counter.pop()+1);
                    sb.append(s.charAt(i));
                } else {
                    counter.pop();
                    sb.delete(sb.length()-(k-1), sb.length());
                }
            }
        }
        return sb.toString();
    }

    //whole minute dilemma
    public int playlist(int []songs){
        int result = 0;
        for (int i = 0; i < songs.length; i++) {
            for (int j = i+1; j< songs.length; j++) {
                if (songs[i]+songs[j] % 60 == 0) {
                    result++;
                }
            }
        }
        return result;
    }

    private int digit_sum(int n) {
        int result = 0;
        while (n!=0) {
            result += (n%10);
            n/=10;
        }
        return result;
    }

    //lottory ticket
    public int lottery(int n) {
        int[] arr = new int[28];
        int maximum = 0;
        for (int i = 1; i <= n; i++) {
            int curr = digit_sum(i);
            arr[curr]++;
            maximum = Math.max(maximum, arr[curr]);
        }
        int result = 0;
        for (int i = 1; i <= 27; i++) {
            if (arr[i] == maximum) {
                result++;
            }
        }
        return result;
    }


    // time complexity O(n), in terms of the length of a or b.
    private boolean anagram(String a, String b) {
        char[] letters = new char[26];
        if (a.length() != b.length()) {
            return false;
        }
        for (int i = 0; i < a.length(); i++) {
            letters[a.charAt(i)-'a']++;
            letters[b.charAt(i)-'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (letters[i] != 0) {
                return false;
            }
        }
        return true;
    }

    // return the number of anagrams we can shape given the sentence.
    private int count_helper(HashMap<String, Integer>set, String sentence) {
        String[] curr = sentence.split(" ");
        int result = 1;
        for (String temp:curr) {
            for (String target : set.keySet()) {
                if (set.get(target) != 1 && anagram(target, temp)) {
                    result *= set.get(target);
                    break;
                }
            }
        }
        return result;
    }

    // how many sentences
    public int[] countSentences(String[] wordSet, String[] sentenceSet) {
        HashMap<String, Integer> set = new HashMap<>();
        for (int i = 0; i < wordSet.length; i++) {
            boolean found = false;
            for (String curr : set.keySet()) {
                if (anagram(curr, wordSet[i])) {
                    set.put(curr, set.get(curr)+1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                set.put(wordSet[i], 1);
            }
        }
        int[] result = new int[sentenceSet.length];
        for (int i = 0; i < sentenceSet.length; i++) {
            result[i] = count_helper(set, sentenceSet[i]);
        }
        return result;
    }

    private int nums_smaller(List<Integer> a, int b) {
        if (a.size() == 1) {
            return (a.get(0)>b) ? 0: 1;
        }
        int left = 0;
        int right = a.size()-1;
        while (left < right) {
            int mid = (left + right) / 2;
            int curr = a.get(mid);
            if (mid == 0) {
                return (a.get(0)>b) ? 0 : (a.get(1)>b) ? 1 : 2;
            }
            if (curr>b && a.get(mid-1) < b) {
                return mid;
            } else if (curr < b) {
                left = mid+1;
            } else {
                right = mid;
            }
        }
        return a.get(left) > b ? left : left+1;
    }

    //football score
//    public List<Integer> football_score(List<Integer> a, List<Integer> b) {
//        a.sort((c, d)->{
//            return c.compareTo(d);
//        });
//        List<Integer> result = new ArrayList<>();
//
//    }

    private boolean exist_helper(char[][] board, HashSet<String> seen, String word, int index, int row, int col) {
        if (index == word.length()) {
            return true;
        }
        String coordinate = row + "," + col;
        if (row >= board.length || col>=board[0].length || row< 0 || col < 0 || board[row][col]!=word.charAt(index) || seen.contains(coordinate)) {
            return false;
        }
        seen.add(coordinate);
        if (!(exist_helper(board, seen, word, index+1, row+1, col) || exist_helper(board, seen, word, index+1, row-1, col) || exist_helper(board, seen, word, index+1, row, col+1) || exist_helper(board, seen, word, index+1, row, col-1))) {
            seen.remove(coordinate);
            return false;
        }
        return true;
    }

    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (exist_helper(board, new HashSet(), word, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private HashMap<String, HashSet<String>> mapping;


    public void enter(String start, String destination) {
        if (mapping==null) {
            mapping = new HashMap();
        }
        HashSet<String> curr = mapping.getOrDefault(start, new HashSet());
        curr.add(destination);
        mapping.put(start, curr);
    }

    private void print_helper(String start, String destination, StringBuilder temp) {
        if (start.equals(destination)) {

            System.out.println(temp.toString()+destination);
            return;
        }
        temp.append(start);
        HashSet<String> stop = mapping.get(start);
        if (stop == null) {
            return;
        }
        for (Object curr : stop.toArray()) {
            print_helper((String)curr, destination, temp);
        }
        temp.delete(temp.length()-start.length(), temp.length());
    }

    public void printRoute(String start, String destination) {
        print_helper(start, destination, new StringBuilder());
    }

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] next_greater = new int[nums2.length];
        next_greater[nums2.length-1]=-1;
        HashMap<Integer, Integer> greater_index = new HashMap();
        greater_index.put(nums2[nums2.length-1], -1);
        for (int i = nums2.length-2; i>=0; i--) {
            if (nums2[i+1]>nums2[i]) {
                next_greater[i] = i+1;
            } else {
                int curr = next_greater[i+1];
                while (curr!=-1 && nums2[i]>nums2[curr]) {
                    curr = next_greater[curr];
                }
                next_greater[i] = curr;
            }
            greater_index.put(nums2[i], (next_greater[i]==-1)?-1:nums2[next_greater[i]]);
        }
        int result[] = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = greater_index.get(nums1[i]);
        }
        return result;
    }


    public static void main(String[] args) {
        Solution test = new Solution();
//        test.enter("A", "B");
//        test.enter("B", "C");
//        test.enter("A", "C");
//        test.printRoute("A", "C");
//        int[] test1 = new int[]{4,1,2};
//        int[] test2 = new int[]{1,3,4,2};
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(test.nums_smaller(list, 5));
    }



}