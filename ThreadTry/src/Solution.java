
import java.util.*;

class Solution {

    private int[][] mat;

    public int[][] diagonalSort(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            int []curr_arr = new int[(int)Math.floor(Math.sqrt(2)*Math.min(mat.length, mat[0].length))];
            int row = i, col = 0, size = 0;
            while (row < mat.length && col < mat[0].length) {
                curr_arr[size] = mat[row][col];
                size++;
                row++;
                col++;
            }
            while (size < curr_arr.length) {
                curr_arr[size] = Integer.MAX_VALUE;
                size++;
            }
            Arrays.sort(curr_arr);
            row = i;
            col = 0;
            size = 0;
            while (row < mat.length && col < mat[0].length) {
                mat[row][col] = curr_arr[size];
                size++;
                row++;
                col++;
            }
        }


    }

    public static void main(String[] args) {

    }

}