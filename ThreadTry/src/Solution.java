
import java.util.*;

class Solution {

    private int[][] mat;


    private void change_status(int x, int y) {
        int []curr_arr = new int[(int)Math.floor(Math.sqrt(2)*Math.min(mat.length, mat[0].length))];
        int row = x, col = y, size = 0;
        while (row < mat.length && col < mat[0].length) {
            curr_arr[size] = this.mat[row][col];
            size++;
            row++;
            col++;
        }
        while (size < curr_arr.length) {
            curr_arr[size] = Integer.MAX_VALUE;
            size++;
        }
        Arrays.sort(curr_arr);
        row = x;
        col = y;
        size = 0;
        while (row < mat.length && col < mat[0].length) {
            mat[row][col] = curr_arr[size];
            size++;
            row++;
            col++;
        }
    }

    public int[][] diagonalSort(int[][] mat) {
        this.mat = mat;
        for (int i = 0; i < mat.length; i++) {
            change_status(i, 0);
        }
        for (int i = 1; i < mat[0].length; i++) {
            change_status(0, i);
        }
        return this.mat;
    }

    public static void main(String[] args) {
        Solution test = new Solution();
        int mat[][] = new int[][];
    }

}