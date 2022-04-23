import com.sun.source.tree.Tree;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

import static java.lang.Math.floorDiv;
import static java.lang.Math.min;
import static java.lang.Math.pow;

class Solution {
    public static int isNumber(int[] arr) {
        return (int) (Math.random() * arr.length);
    }

    public static boolean isPowerOfTwo(int n) {
        int index = 0;
        while (pow(2, index) <= n) {
            if (n == pow(2, index)) {
                return true;
            }
            index += 1;
        }
        return false;
    }

    public int maxSubarraySum(int [][] arr) {
        assert(arr != null);
        int max = 0;
        int sum = 0;
        boolean record = false;
        for (int i = 0; i < arr.length; i++) {
            sum = 0;
            for (int j = 0; j < arr[i].length; j++) {
                sum += arr[i][j];
            }
            if (!record) {
                max = sum;
                record = true;
            } else if (sum > max) {
                max = sum;
            }
        }
        return max;
    }

    public static Map helper_script(String script) {
        Map test1 = new HashMap<String, List<String>>();
        for (String line : script.split("\n")) {
            String[] parts = line.split(":");
            String key = parts[0].trim();
            String value = parts[1].trim();
            ArrayList<String> newList = (ArrayList<String>) test1.getOrDefault(key, new ArrayList<String>());
            newList.add(value);
            test1.put(key, newList);
            }

        return test1;
    }

    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        int smallest = arr[1] - arr[0];
        for (int i = 1; i < arr.length-1; i++) {
            if (arr[i+1] - arr[i] < smallest) {
                smallest = arr[i+1] - arr[i];
            }
        }
        List <List<Integer>> return_list = new ArrayList();
        for (int i = 0; i < arr.length-1; i++) {
            if (arr[i+1] - arr[i] == smallest) {
                List<Integer> element_List = new ArrayList<>();
                element_List.add(arr[i]);
                element_List.add(arr[i+1]);
                return_list.add(element_List);
            }
        }
        return return_list;
    }

    public static ListNode reverse(ListNode head) {
        ListNode previous = null;
        ListNode current = head;
        ListNode forward;
        while (current != null) {
            forward = current.next;
            current.next = previous;
            previous = current;
            current = forward;
        }

        return previous;
    }


    public void reorderList(ListNode head) {
        //count the number of elements in the list
        ListNode curr = head;
        int amount = 0;
        while (curr != null) {
            amount += 1;
            curr = curr.next;
        }
        //cut down the nodes!
        int counter = 0;
        ListNode previous = head;
        while (counter < floorDiv(amount, 2)) {
            previous = previous.next;
            counter += 1;
        }
        curr = previous.next;
        previous.next = null;
        ListNode reversed = reverse(curr);
        //insert element in the head
        curr = head;
        while (reversed != null) {
            curr.next = new ListNode(reversed.val, curr.next);
            reversed = reversed.next;
            curr = curr.next.next;
        }
    }


    public int calculate(String s) {
        // create two array list to hold the elements of integers and operators respectively.
        ArrayList<Integer> integers = new ArrayList<>();
        ArrayList<Character> operators = new ArrayList<>();
        boolean cont = true;
        int curr = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                if (cont) {
                    curr = 10 * curr + Integer.parseInt(s.substring(i,i+1));
                } else {
                    curr = Integer.parseInt(s.substring(i, i+1));
                    cont = true;
                }
            } else if (s.charAt(i) == '*' || s.charAt(i) == '+'
                    || s.charAt(i) == '-' || s.charAt(i) == '/'){
                cont = false;
                integers.add(curr);
                operators.add(s.charAt(i));
            }
        }
        integers.add(curr);
        int ints[] = new int[integers.size()];
        char ops[] = new char[operators.size()];
        int i;
        for (i=0; i < operators.size(); i++) {
            ints[i] = integers.get(i);
            ops[i] = operators.get(i);
        }
        ints[i] = integers.get(i);
        return operator(ints, ops);
    }


    public static int operator(int []ints, char []ops) {
        for (int i = 0; i <ops.length; i ++) {
            if (ops[i] == '*') {
                ints[i+1] = ints[i] * ints[i+1];
                ints[i] = 0;
                if (i == 0) {
                    ops[i] = '+';
                } else {
                    ops[i] = ops[i-1];
                }
            } else if (ops[i] == '/') {
                ints[i+1] = ints[i] / ints[i+1];
                ints[i] = 0;
                if (i == 0) {
                    ops[i] = '+';
                } else {
                    ops[i] = ops[i-1];
                }
            }
        }
        int output = ints[0];
        for (int i = 0; i < ops.length; i++) {
            if (ops[i] == '+') {
                output += ints[i+1];
            } else if (ops[i] == '-') {
                output -= ints[i+1];
            }
        }
        return output;
    }

//    public Node connect(Node root) {
//        return connect(root, null);
//    }
//
//    public Node connect(Node root, Node right) {
//        if (root.left == null) {
//            root.next = right;
//        } else {
//            root.left = connect(root.left, root.right);
//            root.right = connect(root.right, null);
//        }
//        return root;
//    }

    public static String rotateRight(String input, int rotateNum) {
        char conversion[] = new char[input.length()];
        char origin[] = input.toCharArray();
        for (int i = 0; i < input.length(); i++) {
            conversion[(i+rotateNum)%input.length()] = origin[i];
        }
        return new String(conversion);
    }

    public static char checkBoard(char input[][]) {
        if (null == input) {
            return '.';
        }
        for (int row = 0; row < 3; row++) {
            if (input[row][0] == 'X' && input[row][1] == 'X' && input[row][2] == 'X') {
                return 'X';
            } else if (input[row][0] == '0' && input[row][1] == '0' && input[row][2] == '0') {
                return '0';
            }
        }
        for (int col = 0; col < 3; col ++) {
            if (input[0][col] == 'X' && input[1][col] == 'X' && input[2][col] == 'X') {
                return 'X';
            } else if (input[0][col] == '0' && input[1][col] == '0' && input[2][col] == '0') {
                return '0';
            }
        }
        return '.';
    }

    public static class Max {
        public static Comparable max(Comparable first, Comparable second) {
            return first.compareTo(second);
        }
    }

    public static class InsertionSorter {
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
    }


/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }



//
//
//    public Node copyRandomList(Node head) {
//        Node origin_head = head;
//        Map nodeMap = new HashMap<Node, Node>();
//        Node newHead = null;
//        Node prev = null;
//        while (head != null) {
//            Node newNode = new Node(head.val);
//            if (prev != null) {
//                prev.next = newNode;
//            } else {
//                newHead = newNode;
//            }
//            nodeMap.put(head, newNode);
//            prev = head;
//            head = head.next;
//        }
//
//        Node curr = origin_head;
//        while (curr != null) {
//            Node new_node = (Node) nodeMap.get(curr);
//            if (curr.random != null) {
//                new_node.random = (Node) nodeMap.get(curr.random);
//            }
//
//            curr= curr.next;
//        }
//        System.out.println(12%10);
//
//        return newHead;
//    }
//
//    public static int find(int[] nums, int target) {
//        if (nums.length == 0) {
//            return -1;
//        }
//        if (nums.length == 2) {
//            if (nums[0] == target) {
//                return 0;
//            } else if (nums[1] == target) {
//                return 1;
//            } else {
//                return -1;
//            }
//        }
//
//        int middle = nums.length/2; // round up
//        if (nums[middle] == target) {
//            return middle;
//        }
//        if (nums[middle] < target) {
//            return middle + find(Arrays.copyOfRange(nums, middle, nums.length-1), target);
//        } else {
//            return find(Arrays.copyOfRange(nums, 0, middle), target);
//        }
//    }
//
//    public char * twoSum(int[] nums, int target) {
////        Arrays.sort(nums);
////        for (int i = 0; i<nums.length; i++) {
////            int sec_index = find(nums, target-nums[i]);
////            if (sec_index != -1) {
////                int []toReturn = {i, sec_index};
////                return toReturn;
////            }
////        }
//
//        switch (target) {
//            case 1:
//                return "I";
//            case 5:
//                return "V";
//            case 10:
//                return "X";
//            case 50:
//                return "L";
//            case 100:
//                return "C";
//            case 500:
//                return "D";
//            case 1000:
//                return "M";
//        }
//        case
//        return null;
//    }

//    public static String reverse(String s) {
//        String toReturn = s.substring(s.length()-1);
//        for (int i = s.length()-2; i >= 0; i--) {
//            toReturn += s.substring(i, i+1);
//        }
//        return toReturn;
//    }
//
//    // check if the passed in string is a palindrom or not.
//    // return 1 if it is and 0 otherwise.
//    // time complexity : O(length(s))
//    public static int palindromic(String s) {
//        String reversion = reverse(s);
//        if (s.equals(reversion)) {
//            return 1;
//        }
//        return 0;
//    }
//
//    public static String longest_helper(String s) {
//        for (int length = s.length(); length > 0; length--) {
//            for (int i = 0; i+length <= s.length(); i++) {
//                String target = s.substring(i, i+length);
//                if (palindromic(target) > 0) {
//                    return target;
//                }
//            }
//        }
//        return null;
//    }
//
//    public static String longest_helper(String s, int begin, int end) {
//        if (begin == end) {
//            return s.substring(begin, begin+1);
//        } else if (begin > end) {
//            return "";
//        }
//
//        if (s.charAt(begin) == s.charAt(end)) {
//            String a = s.substring(begin, begin+1);
//            String middle = longest_helper(s, begin+1, end-1);
//            a += middle;
//            a += s.substring(begin, begin+1);
//            return a;
//        } else {
//            String first = longest_helper(s, begin+1, end);
//            String second = longest_helper(s, begin, end-1);
//            if (first.length() > second.length()) {
//                return first;
//            }
//            return second;
//        }
//    }
//    public String longestPalindrome(String s) {
//        return longest_helper(s, 0, s.length()-1);
//    }
//
//    public static int shuixianNum(int target) {
//        int first = target % 10;
//        int second = target / 10 % 10;
//        int third = target /100;
//        return first^3 + second^3 + third^3;
//    }
//
//    public static int printNum(int a, int b) {
//        int flag = 0;
//        int small = a;
//        int big = b;
//        for (; small <= big; small++) {
//            if (small == shuixianNum(small)) {
//                System.out.print(small);
//                flag = 1;
//            }
//        }
//        if (flag == 0) {
//            System.out.print("no");
//        }
//        return flag;
//    }
//
//    public static int detect(int [][]matrix, int x_coor, int y_coor, int high, int length) {
//        int counter = 0;
//        for (int i = x_coor; i < length; i++) {
//            if (matrix[y_coor][i] == 1) {
//                counter += 1;
//                break;
//            }
//        }
//
//        for (int i = 0; i < x_coor; i++) {
//            if (matrix[y_coor][i] == 1) {
//                counter += 1;
//                break;
//            }
//        }
//
//        for (int i = y_coor; i < high; i++) {
//            if (matrix[i][x_coor] == 1) {
//                counter += 1;
//                break;
//            }
//        }
//        Map test = new HashMap();
//        test.get()
//
//        for (int i = 0; i < y_coor; i++) {
//            if (matrix[i][x_coor] == 1) {
//                counter += 1;
//                break;
//            }
//        }
//
//        Vector test = new Vector();
//        test.add(1);
//        test.get(0);
//
//        return counter;
//
//
//
//
//
//    }
//
//    public String multiCheck(String path) {
//        path = path.replaceAll("\\/\\.\\/", "/");
//
//        while (path.contains("//")) {
//            path = path.replaceAll("//", "/");
//        }
//
//        path = path.replaceAll("\\/\\.\\/", "/");
//        if (path.endsWith("/.")) {
//            path = path.substring(0, path.length()-1);
//        }
//
//
//
//        return path;
//    }
//
//    public String simplifyPath(String path) {
//        // replace all the double slashes sign to single slash
//        path = multiCheck(path);
//
//
//
//        while (path.contains("/../")) {
//            path = path.replaceFirst("[a-zA-Z_]*\\/\\.\\.", "");
//            path = multiCheck(path);
//        }
//
//
//
//        if (path.endsWith("/..")) {
//            path = path.replaceFirst("[a-zA-Z_]*\\/\\.\\.", "");
//        }
//
//        while (path.endsWith("/") && !path.equals("/")) {
//            path = path.substring(0, path.length()-1);
//        }
//
//        if (path.length() == 0) {
//            path = "/";
//        }
//
//
//        return path;
//    }
//
//    public boolean isValid(String s) {
//        Stack orders = new Stack<>();
//        for (int i = 0; i<s.length(); i++) {
//            if (s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{') {
//                orders.push(s.charAt(i));
//            } else {
//                char top = (char) orders.pop();
//                char origin = s.charAt(i);
//                if ((origin == ')' && top != '(' ) || (origin == ']' && top != '[' ) || (origin == '}' && top != '{')) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }


    public static void test(int n, int m, String passage) {
        String split_ed[] = passage.split(" ");
        String answer[] = new String[2500];
        String curr_line = "";
        int answer_index = 0;
        int index = 0;
        int remain = n;
        boolean used_m = false;

        while (index < split_ed.length) {

            if (!used_m && split_ed[index].length() < remain+m) {
                curr_line += split_ed[index];
                curr_line += " ";
                remain -= split_ed[index].length();
                if (split_ed[index].length() > remain) {
                    used_m = true;
                }
                index++;
            } else {
                // havent changed the punctuation
                curr_line.trim();
                answer[answer_index] = curr_line;
                answer_index++;
                curr_line = "";
                remain = n;
                used_m = false;

            }
        }

        System.out.println(answer_index);
        for (int i = 0; i < answer_index; i++) {
            System.out.println(answer[i]);
        }
    }

    // return the number of the same elements in the array
    public static int same_nums(int nums[]) {
        Arrays.sort(nums);
        int index = 1;
        int max = 1;
        int curr = nums[0];
        int record = 1;
        while (index < nums.length) {
            if (nums[index] == curr) {
                record++;
                if (record > max) {
                    max = record;
                }
            } else {
                record = 1;
                curr = nums[index];
            }
            index++;
        }
        return max;
    }

    public static boolean five_consecutive(int nums[]) {
        if (nums.length != 5) {
            return false;
        }
        if (same_nums(nums) == 5 && (nums[0] - nums[4] == 4 || nums[0] - nums[4] == -4) ) {
            return true;
        }
        return false;
    }

    public static boolean same_type(String types[]) {
        if (types.length != 5) {
            return false;
        }
        return types[0].equals(types[1]) && types[0].equals(types[2]) && types[0].equals(types[3]) && types[0].equals(types[4]);
    }

    public static void perOperation(Scanner in) {

        int cards_num = in.nextInt();

        int nums_array[] = new int [cards_num];
        for (int j = 0; j < cards_num; j++) {
            int next_int = in.nextInt();
            nums_array[j] = next_int;
        }
        String char_array[] = new String [cards_num];
        for (int j = 0; j < cards_num; j++) {
            String next_int = in.next();
            char_array[j] = next_int;
        }

        int same_num = same_nums(nums_array);
        if (same_num == 5) {
            System.out.println(15000);
            return;
        }
        if (five_consecutive(nums_array) && same_type(char_array)) {
            System.out.println(8000);
            return;
        }
        if (same_type(char_array)) {
            System.out.println(300);
            return;
        }
        if (same_num == 4) {
            System.out.println(150);
            return;
        }
        Arrays.sort(nums_array);
        if (same_num == 3 && (nums_array[0] == nums_array[1] && nums_array[3] == nums_array[4])) {
            System.out.println(40);
            return;
        }
        if (five_consecutive(nums_array)) {
            System.out.println(20);
            return;
        }
        if (same_num == 3) {
            System.out.println(6);
            return;
        }
        if (same_num == 2) {
            int index = 0;
            while (index < nums_array.length-1) {
                if (nums_array[index] == nums_array[index+1]) {
                    nums_array[index] = -1;
                    nums_array[index] = -2;
                    break;
                }
                index++;
            }
            same_num = same_nums(nums_array);
            if (same_num == 2) {
                System.out.println(4);
            } else {
                System.out.println(2);
            }
            return;
        }
        System.out.println(1);

    }

//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int width = in.nextInt();
//        int high = in.nextInt();
//        int origin[][] = new int[width+1][high+1];
//        for (int i = 0; i <= width; i++) {
//            for (int j = 0; j <= high; j++) {
//                if (i == width) {
//                    origin[i][j] = Integer.MAX_VALUE;
//                } else if (j == high) {
//                    origin[i][j] = Integer.MAX_VALUE;
//                } else {
//                    origin[i][j] = in.nextInt();
//                }
//
//            }
//        }
//
//        int graph[][] = new int[width+1][high+1];
//        for (int i = width; i >= 0; i--) {
//            for (int j =0; j >= 0; j--) {
//                if (i == width) {
//                    graph[i][j] = Integer.MAX_VALUE;
//                } else if (j == high) {
//                    graph[i][j] = Integer.MAX_VALUE;
//                } else {
//                    graph[i][j] = min(graph[i][j+1]+origin[i][j+1], graph[i+1][j]+origin[i+1][j]);
//                }
//            }
//        }
//
//        System.out.println(graph[0][0]);
//
//    }


//
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int n = in.nextInt();
//        int m = in.nextInt();
//        in.nextLine();
//        String passage = in.nextLine();
//        System.out.println(n);
//        System.out.println(m);
//        System.out.println(passage);
//
//        String split_ed[] = passage.split(" ");
//        String answer[] = new String[2500];
//        String curr_line = "";
//        int answer_index = 0;
//        int index = 0;
//        int remain = n;
//        boolean used_m = false;
//
//        while (index < split_ed.length) {
//
//            if (!used_m && split_ed[index].length() <= remain+m && remain > 0) {
//                curr_line += split_ed[index];
//                curr_line += " ";
//
//                if (split_ed[index].length() > remain) {
//                    used_m = true;
//                }
//                remain -= (split_ed[index].length()+1);
//                index++;
//            } else {
//                // havent changed the punctuation
//                curr_line.trim();
//                if (curr_line.startsWith(",")) {
//                    answer[answer_index-1] += ",";
//                    curr_line = curr_line.substring(1);
//                    curr_line.trim();
//                }
//
//                answer[answer_index] = curr_line;
//                answer_index++;
//                curr_line = "";
//                remain = n;
//                used_m = false;
//
//            }
//        }
//        if (!curr_line.isEmpty()) {
//            curr_line.trim();
//            answer[answer_index] = curr_line;
//            answer_index++;
//        }
//
//
//        System.out.println(answer_index);
//        for (int i = 0; i < answer_index; i++) {
//            System.out.println(answer[i]);
//        }
//    }



//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int num_data = in.nextInt();
//        // 注意 hasNext 和 hasNextLine 的区别
//        for (int i = 0; i < num_data; i++) {
//            int cards_num = in.nextInt();
//            int nums_array[] = new int [cards_num];
//            for (int j = 0; j < cards_num; j++) {
//                nums_array[j] = in.nextInt();
//            }
//            char char_array[] = new char [cards_num];
//            for (int j = 0; j < cards_num; j++) {
//                char_array[j] = (char) in.nextByte();
//                System.out.println(char_array[j]);
//            }
//        }
//    }

    public static boolean valid_row(int sudo[][], int row_num) {
        int row[] = new int[6];
        for (int i = 0; i < 6; i++) {
            row[i] = sudo[row_num][i];
        }
        Arrays.sort(row);
        for (int i = 0; i < 6; i++) {
            if (row[i] != i+1) {
                return false;
            }
        }
        return true;
    }

    public static boolean valid_column(int sudo[][], int col_num) {
        int col[] = new int[6];
        for (int i = 0; i < 6; i++) {
            col[i] = sudo[i][col_num];
        }
        Arrays.sort(col);
        for (int i = 0; i < 6; i++) {
            if (col[i] != i+1) {
                return false;
            }
        }
        return true;
    }


//    public static boolean each(Scanner in) {
//        int sudo[][] = new int[6][6];
//        for (int i = 0; i < 6; i++) {
//            int curr_num = in.nextInt();
//            for (int j = 0; j < 6; j++) {
//                sudo[i][j] = (int) (curr_num/pow(10, 5-j))%10;
//            }
//        }
//        for (int i = 0; i < 6; i++) {
//            boolean row_valid = valid_row(sudo, i);
//            boolean col_valid = valid_column(sudo, i);
//            if (!row_valid || !col_valid) {
//                return false;
//            }
//        }
//        return true;
//    }

    public static int minimum(int arr[], int original, int lower, int upper, int curr) {

        if (original <= upper && original >= lower) {
            return 0;
        }
        if (original < lower || curr >= arr.length) {
            return arr.length+10;
        }
        if (arr[curr] <= original-lower) {
            return min(1+minimum(arr, original-arr[curr], lower, upper, curr+1), minimum(arr, original, lower, upper, curr+1));
        } else {
            return minimum(arr, original, lower, upper, curr+1);
        }
//        for (int i = curr; i >= 0; i++) {
//            if (original <= upper && original >= lower) {
//                all_minimum[i] = 0;
//            } else if (original < lower || i >= arr.length) {
//                all_minimum[i] = arr.length+10;
//            } else if (arr[curr] <= original-lower) {
//                all_minimum[i] = min(1+all_minimum[i+1], )
//            }
//
//        }

    }

    public static int each(Scanner in) {
        int original = in.nextInt();
        int lower = in.nextInt();
        int upper = in.nextInt();
        if (lower > upper) {
            return 0;
        }
        int length = in.nextInt();
        int skills[] = new int[length];
        for (int i = 0; i < length; i++) {
            skills[i] = in.nextInt();
        }
        int mini = minimum(skills, original, lower, upper, skills.length-1);
        if (mini < skills.length) {
            return mini;
        } else {
            return 0;
        }

    }

    public static int translate(String curr) {
        if (curr.equals("0xFFFFFFFF")) {
            return -1;
        } else {
            return Integer.decode(curr);
        }
    }

    public static void tackle_move(String para) {
        String toPrint = "MoveTo(";
        String curr = "0x";
        curr += para.substring(0, 8);
        int a = translate(curr);
        toPrint += String.valueOf(a);
        toPrint += ",";

        curr = "0x";
        curr += para.substring(8, 16);
        a = translate(curr);
        toPrint += String.valueOf(a);
        toPrint += ",";

        curr = "0x";
        curr += para.substring(17, 24);
        a = translate(curr);
        toPrint += String.valueOf(a);

        toPrint += ")";

        System.out.println(toPrint);

    }

    public static void tackle_login(Scanner in) {
        String first = in.next("[0-9][0-9]");
        String second = in.next("[0-9][0-9]");

        int length_1 = Integer.valueOf(first);
        int length_2 = Integer.valueOf(second);

        first = new String();
        for (int i = 0; i < length_1; i++) {
            first += in.next("[0-9A-Z]");
        }

        second = new String();
        for (int i = 0; i < length_2; i++) {
            second += in.next("[0-9A-Z]");
        }

        String integer = new String();
        for (int i = 0; i < 8; i++) {
            integer += in.next("[0-9A-F]");
        }

        int target_int = translate(integer);

        String final_s = new String();
        final_s = "Login(\"";
        final_s += first;
        final_s += "\",\"";
        final_s += second;
        final_s += "\",";
        final_s += Integer.toString(target_int);

        System.out.println(final_s);

    }

    public static void tackle(Scanner in, int type) {
        in.nextByte();
        String type_rpc = in.next("0[0-1]");
        if (type_rpc.equals("01")) {
            String target = new String();
            for (int i = 0; i < 24; i++) {
                target += in.next("[0-9A-F]");
            }
            tackle_move(target);
        } else {

        }



    }



    // 注意类名必须为 Main, 不要有任何 package xxx 信息

        public static void main(String[] args) {
//            Scanner in = new Scanner(System.in);
//            int num = in.nextInt();
//            for (int i =0 ; i < num; i++) {
//                System.out.println(each(in));
//            }
//            String target = "01000000";
//            String temp = "0x" + target;
//
//            int a = Integer.decode(temp);

//            System.out.println(a);
        }




}