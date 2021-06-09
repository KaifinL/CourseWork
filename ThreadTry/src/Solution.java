public class Solution {
    public static int myAtoi(String s) {
        s = s.replace(" ", "");
        boolean negative = false;
        if (s.contains("-")) {
            negative = true;
            s = s.replace("-", "");
        }
        

        if (negative) {
            s = "-" + s;
        }
        return Integer.parseInt(s);
    }

    public static void main(String[] args) {
        String s = "    777";
        System.out.println(myAtoi(s));
    }
}