public class InsertionSorter {
    public static int sort(Comparable array[]) {
        int counter = 0;
        for (int ori_index = 0; ori_index < array.length; ori_index++) {
            int curr_index = ori_index;
            for (int prev = ori_index-1; prev>=0; prev--) {
                if (array[curr_index].compareTo(array[prev]) < 0) {
                    Comparable middle = array[curr_index];
                    array[curr_index] = array[prev];
                    array[prev] = middle;
                    curr_index--;
                    counter++;
                }
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        Solution.InsertionSorter test2 = new Solution.InsertionSorter();
        Comparable testDa[] =  new Comparable[]{5, 1, 6, 9, 2};
        int result = test2.sort(testDa);
        System.out.println(result);
    }
    
}
