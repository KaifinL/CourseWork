class Solution {
    public String convert(String s, int numRows) {
        String[] stringSet = new String[numRows];
        // initialize the stringSet;
        for (int i = 0; i < numRows; i++) {
            stringSet[i] = "";
        }
        // add the first letter to the substring
        while (!s.equals("")) {
            for (int i = 0; i < numRows; i++) {
                stringSet[i] += s.substring(i, i + 1);
            }
            for (int i = numRows - 2, j = 1; i > 0; i--, j++) {
                stringSet[i] += s.substring(numRows + j, numRows + j + 1);
            }
            s = s.substring(2 * numRows - 2);
        }
        return s;
    }

    public static void main(String[] args) {
        
    }
}