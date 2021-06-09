public class Solution {
    public static int myAtoi(String s) {
        s = s.replace(" ", "");
        
        return Integer.parseInt(s);
    }

    public static void main(String[] args) {
        String s = "    777";
        System.out.println(myAtoi(s));
    }
}