class Solution {
    public String longestPalindrome(String s) {
        return s;
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


    public static void main(String[] args) {
        String s = "babad";
        System.out.println(helper1(s, 3));
    }
}