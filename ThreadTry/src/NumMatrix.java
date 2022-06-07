public class NumMatrix {

    private int[][] sumGrid;

    public NumMatrix() {
        this.sumGrid = null;
    }

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

    public int[][] transpose(int[][] matrix) {
        int [][] result = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i][j] = matrix[j][i];
            }
        }
        return result;
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int []result = new int[m+n];
        int first = 0;
        int second = 0;
        int index = 0;
        while (first < m && second < n) {
            if (nums1[first] < nums2[second]) {
                result[index] = nums1[first];
                first++;
            } else {
                result[index] = nums2[second];
                second++;
            }
            index++;
        }
        if (first < m) {
            for (int counter = first; counter < m; counter++) {
                result[index+counter] = nums1[counter];
            }
        } else {
            for (int counter = second; counter < n; counter++) {
                result[index+counter] = nums2[counter];
            }
        }

        for (int i =0 ; i < m+n; i++) {
            nums1[i] = result[i];
        }
    }


}
