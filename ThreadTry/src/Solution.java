class Solution {
    public String longestCommonPrefix(String[] strs) {
        String shortest = "";
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].length() < shortest.length()) {
                shortest = strs[i];
            }
        }

    }

    public boolean judgement(String strs, String prefix) {
        
    }
}