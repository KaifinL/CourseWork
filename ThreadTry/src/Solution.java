public class Solution {
    public static int myAtoi(String s) {
        s = s.replace(" ", "");
        boolean negative = false;
        if (s.contains("-")) {
            negative = true;
            s = s.replace("-", "");
        }
        int index = s.length();
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                index = i;
                break;
            }
        }
        s = s.substring(0, index);
        if (negative) {
            s = "-" + s;
        }
        int returnStaff;
        try {
            returnStaff = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            if (Long.parseLong(s) > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }
        return returnStaff;
    }

    public static void main(String[] args) {
        String s = "    -199219211wwwww";
        System.out.println(myAtoi(s));
    }
}