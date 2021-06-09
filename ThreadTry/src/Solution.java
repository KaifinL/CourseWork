class Solution {
    public static int romanToInt(String s) {
        if (s.equals("")) {
            return 0;
        } else if (s.substring(0, 2).equals("IV")) {
            return 4;
        } else if (s.substring(0, 2).equals(""))
        return transferring(s.substring(0, 1)) + romanToInt(s.substring(1));
    }


    public static int transferring(String s) {
        return switch (s) {
            case "I" -> 1;
            case "V" -> 5;
            case "X" -> 10;
            case "L" -> 50;
            case "C" -> 100;
            case "D" -> 500;
            case "M" -> 1000;
            default -> 0;
        };
    }

    public static void main(String[] args) {
        System.out.println(romanToInt("IV"));
    }
}