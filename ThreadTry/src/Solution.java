class Solution {
    public static int reverse(int x) {
        String variable = String.valueOf(x);
        String integer = variable;
        if (variable.contains("-")) {
            integer = variable.substring(1);
        }
        String output = "";
        for (int i = integer.length() - 1; i >= 0; i--) {
            output += integer.substring(i, i + 1);
        }
        if (variable.contains("-")) {
            output = "-" + output;
        }
        int returnStaff;
        try {
            returnStaff = Integer.valueOf(output);
        } catch (ArithmeticException e) {
            return 0;
        }
        return returnStaff;
    }

    public static void main(String[] args) {
        System.out.println(reverse(-123));
    }
}