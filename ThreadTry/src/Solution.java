class Solution {
    public static String convert(String s, int numRows) {
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
                    stringSet[i] += s.substring(numRows + j, numRows + j + 1);
                } catch (StringIndexOutOfBoundsException e) {
                    break;
                }
            }
            s = s.substring(2 * numRows - 2);
        }
        String output = "";
        int k = 0;
        while (k < length) {
            output += stringSet[k % numRows - 1].substring(k / numRows, k / numRows + 1);
            k += 1;
        }
        return output;
    }

    public static void main(String[] args) {
        System.out.println(convert("PAYPALISHIRING", 3));
    }
}