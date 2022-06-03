public class NumMatrix {

    private int[][] sumGrid;

    public NumMatrix(int[][] matrix) {
        sumGrid = new int[matrix.length+1][matrix[0].length+1];
        for (int i = 0; i <= matrix.length; i++) {
            sumGrid[i][0] = 0;
        }
        for (int i = 0; i <= matrix[0].length; i++) {
            sumGrid[0][i] = 0;
        }
        for (int i = 1; i <= matrix.length; i++) {
            for (int j = 1; j <= matrix[0].length; j++) {
                sumGrid[i][j] = sumGrid[i-1][j] + sumGrid[i][j-1] + matrix[i-1][j-1] - sumGrid[i-1][j-1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sumGrid[row2+1][col2+1] - sumGrid[row1][col2+1] - sumGrid[row2+1][col1] + sumGrid[row1][col1];
    }

    public static void main(String[] args) {

    }
}
