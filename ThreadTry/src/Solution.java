class Solution {
    public static boolean isPalindrome(int x) {
        String variable = String.valueOf(x);
        String output = "";
        for (int i = variable.length() - 1; i >= 0; i--) {
            output += variable.charAt(i);
        }
        if (output.equals(variable)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(0));
    }
}