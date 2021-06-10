class Solution {
    public static String longestCommonPrefix(String[] strs) {
        String shortest = strs[0];
        for (int i = 1; i < strs.length; i++) {
            if (strs[i].length() < shortest.length()) {
                shortest = strs[i];
            }
        }
        String output = "";
        for (int i = 0; i < shortest.length(); i++) {
            String curr = shortest.substring(0, i + 1);
            if (judgement(strs, curr) && curr.length() > output.length()) {
                output = curr;
            }
        }
        return output;
    }

    public static boolean judgement(String[] strs, String prefix) {
        for (int i = 0; i < strs.length; i++) {
            if (!strs[i].contains(prefix)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[] {""}));
    }
}