class Solution {
    public static String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        int length = s.length();
        String[] stringSet = new String[numRows];
        // initialize the stringSet;
        for (int i = 0; i < numRows; i++) {
            stringSet[i] = "";
        }
        // add the first letter to the substring
        while (!s.equals("")) {
            for (int i = 0; i < numRows; i++) {
                try {
                    stringSet[i] += s.substring(i, i + 1);
                } catch (StringIndexOutOfBoundsException e) {
                    break;
                }
            }
            for (int i = numRows - 2, j = 1; i > 0; i--, j++) {
                try {
                    stringSet[i] += s.substring(numRows + j - 1, numRows + j);
                } catch (StringIndexOutOfBoundsException e) {
                    break;
                }
            }
            if (s.length() > numRows) {
                s = s.substring(2 * numRows - 2);
            } else {
                s = "";
            }
        }
        String output = "";
        for (int i = 0; i < numRows; i++) {
            output += stringSet[i];
        }
        return output;
    }

    public static void main(String[] args) {
        System.out.println(convert("ABCDEFG", 2));
    }
}