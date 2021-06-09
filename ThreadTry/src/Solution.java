public class Solution {
    public static int myAtoi(String s) {
        boolean negative = false;
        if (s.isEmpty()) {
            return 0;
        }
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(0) == ' ') {
                s = s.substring(1);
                if (s.isEmpty()) {
                    return 0;
                }
            } else {
                break;
            }
        }
        if (s.charAt(0) == '-' || s.charAt(0) == '+') {
            if (s.charAt(0) == '-') {
                negative = true;
            }
            s = s.substring(1);
        }
        int index = s.length();
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                index = i;
                break;
            }
        }
        s = s.substring(0, index);
        if (negative && !s.isEmpty()) {
            s = "-" + s;
        }
        int returnStaff;
        try {
            returnStaff = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            if (s.equals("")) {
                return 0;
            }
            if (Long.parseLong(s) > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }
        return returnStaff;
    }

    public static void main(String[] args) {
        String s = "20000000000000000000";
        System.out.println(myAtoi(s));
    }
}