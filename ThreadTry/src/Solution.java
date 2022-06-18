import com.sun.source.tree.Tree;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

import static java.lang.Math.floorDiv;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static org.junit.Assert.assertTrue;

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

    // find the maximum index that is smaller than the target
    public static int find(int skill[], int target) {
        for (int i = skill.length-1; i >= 0; i--) {
            if (skill[i] < target) {
                return i;
            }
        }
        return -1;
    }



    public static void tackle_login(Scanner in) {
        String first = in.next();
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

    class charCount {
        private char c;
        private int count;

        public charCount(char c, int count) {
            this.c = c;
            this.count = count;
        }

        public char getC() {
            return c;
        }

        public int getCount() {
            return count;
        }

        public void increment() {
            this.count++;
        }
    }

    public String removeDuplicates(String s, int k) {
        Stack charCounter = new Stack();
        for (int i = 0; i < s.length(); i++) {

            if (!charCounter.empty() && (s.charAt(i) == ((charCount) charCounter.peek()).getC())) {
                    charCount top = (charCount) charCounter.peek();
                    if (top.getCount() == (k-1)) {
                        charCounter.pop();
                    } else {
                        top.increment();
                    }


            } else {
                charCounter.push(new charCount(s.charAt(i), 1));
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!charCounter.empty()) {
            charCount top = (charCount) charCounter.pop();
            sb.append(String.valueOf(top.getC()).repeat(Math.max(0, top.getCount())));
        }
        return sb.reverse().toString();
    }

    public String removeDuplicates2(String s, int k) {
        Stack <Integer> counter = new Stack<>();
        Stack <Character> chars = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (counter.empty() || s.charAt(i) != chars.peek()) {
                counter.push(1);
                chars.push(s.charAt(i));
            } else {
                if (counter.peek() < k-1) {
                    counter.push(counter.pop()+1);
                } else {
                    counter.pop();
                    chars.pop();
                }
            }
        }
        while (!counter.empty()) {
            int limit = counter.pop();
            for (int i= 0; i < limit; i++) {
                sb.append(chars.peek());
            }
            chars.pop();
        }
        return sb.reverse().toString();
    }

    public String longestPalindrome(String s) {
        boolean [][]LPM = new boolean[s.length()][s.length()];
        for (int i = 1; i < s.length(); i++) {
            for (int j = 0; j < i; j++) {
                LPM[i][j] = false;
            }
        }

        for (int i = s.length(); i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j) && j - i <= 1) {
                    LPM[i][j] = true;
                } else if (s.charAt(i) != s.charAt(j) || !LPM[i + 1][j - 1]) {
                    LPM[i][j] = false;
                } else {
                    LPM[i][j] = true;
                }
            }
        }

        int maximum = 0;
        int begin_index = 0;
        int end_index = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (LPM[i][j] && (j-i) > maximum) {
                    maximum = j - i;
                    begin_index = i;
                    end_index = j;
                }
            }
        }
        return s.substring(begin_index, end_index+1);
    }

    public boolean isMatch(String s, String p) {
        boolean IMM[][] = new boolean[s.length()+1][p.length()+1];
        for (int i = 0; i < s.length() + 1; i++) {
            IMM[i][p.length()] = false;
        }
        for (int i = 0; i < p.length()+1; i++) {
            IMM[s.length()][i] = false;
        }
        IMM[s.length()][p.length()] = true;

        char curr = '!';
        for (int i = s.length()-1; i>= 0; i--) {
            for (int j = p.length()-1; j>= 0; j--) {
                if (p.charAt(j) == '*') {
                    curr = p.charAt(j-1);
                    if (curr == '.') {
                        curr = s.charAt(i);
                    }
                }
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
                    IMM[i][j] = (IMM[i+1][j+1]);
                    curr = s.charAt(i);
                } else if (curr == s.charAt(i) && p.charAt(j) == '*') {
                    IMM[i][j] = (IMM[i+1][j] || IMM[i+1][j+1] || IMM[i][j+1]);
                } else if (p.charAt(j) == '*') {
                    IMM[i][j] = IMM[i][j+1];
                } else {
                    IMM[i][j] = false;
                }
            }
        }
        return IMM[0][0];
    }

    class interval {
        private int max;
        private int min;

        public interval(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMax() {
            return max;
        }

        public int getMin() {
            return min;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public boolean in_interval(int target) {
            return (target < this.max) && (target > this.min);
        }
    }


    // return if the integer is within any of the inteval in the vector.
    public boolean is_minimal(Vector<interval> v, int target) {
        for (interval intval :v) {
            if (intval.in_interval(target)) {
                return true;
            }
        }
        return false;
    }

    public boolean find132pattern(int[] nums) {

        for (int j = 1; j < nums.length; j++) {
            int leftMin = Integer.MAX_VALUE;
            for (int i = 0; i < j; i++) {
                leftMin = Math.min(leftMin, nums[i]);
            }
            int rightMin = Integer.MAX_VALUE;
            boolean rightFlag = false;
            for (int k = j+1; k < nums.length; k++) {
                if (nums[k] > leftMin) {
                    rightMin = Math.min(rightMin, nums[k]);
                    rightFlag = true;
                }
            }

            if (rightFlag && rightMin < nums[j]) {
                return true;
            }
        }
        return false;
    }


    // return the index of the target in the array if there is any.
    // Otherwise, return -1;
    public int find2(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        while (left <= right) {
            int mid = (left + right)/2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid+1;
            } else {
                right = mid-1;
            }
        }
        return -1;
    }

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> num_index = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            num_index.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            if (num_index.get(target-nums[i]) != null && i != num_index.get(target-nums[i])) {
                return new int[]{i, num_index.get(target-nums[i])};
            }
        }
        return null;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int[] new_nums = new int[nums.length-1-i];
            //copy array
            System.arraycopy(nums, i+1, new_nums, i, nums.length-i-1);
            int []rest = twoSum(new_nums, -nums[i]);
            if (rest != null) {
                List<Integer> new_list = new ArrayList<>();
                new_list.add(nums[i]);
                new_list.add(new_nums[rest[0]]);
                new_list.add(new_nums[rest[1]]);
                result.add(new_list);
            }
        }
        return result;
    }

     public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }

  public class mapping {
        int val;
        int level;

          public mapping(int val, int level) {
              this.level = level;
              this.val = val;
          }
  }


    public Stack<mapping> nodes;

    public void constructor(TreeNode root, int level) {
        if (root == null) {
            return;
        }
        nodes.push(new mapping(root.val, level));
        constructor(root.left, level+1);
        constructor(root.right, level+1);
    }

    public int deepestLeavesSum(TreeNode root) {
        nodes = new Stack<>();
        constructor(root, 0);
        int level = nodes.peek().level;
        int sum = 0;
        while (!nodes.empty()) {
            mapping curr = nodes.pop();
            if (curr.level == level) {
                sum += curr.val;
            } else if (curr.level > level) {
                sum = curr.val;
                level = curr.level;
            }
        }
        return sum;
    }

    @Test
    public void test_find2() {
        assertTrue(find2(new int[]{1, 2, 3, 4, 5}, 5) == 4);
    }

    public int shortestPathBinaryMatrix(int[][] grid) {
        int length = grid.length;
        if (grid.length == 1) {
            if (grid[0][0] == 0) {
                return 1;
            } else {
                return -1;
            }
        }
        if (grid[0][0] == 1 || grid[length-1][length-1] == 1) {
            return -1;
        }
        int [][]spbm = new int[length+1][length+1];
        for (int i = 0; i <= length; i++) {
            spbm[i][length] = length*length;
        }
        for (int j = 0; j <= length; j++) {
            spbm[length][j] = length*length;
        }
        spbm[length-1][length-1] = 1;
        for (int j = length-1; j >= 0; j--) {
            for (int i = length-1; i >= 0; i--) {
                if (i == length-1 && j == length-1) {
                    continue;
                }
                if (grid[i][j] == 1) {
                    spbm[i][j] = length*length;
                } else {
                    if (i == 0) {
                        spbm[i][j] = min(min(spbm[i][j+1], spbm[i+1][j]), spbm[i+1][j+1]) + 1;
                    } else {
                        spbm[i][j] = min(min(min(spbm[i][j+1], spbm[i-1][j+1]), spbm[i+1][j]), spbm[i+1][j+1]) + 1;
                    }
                }
            }
        }
        if (spbm[0][0] >= length*length) {
            return -1;
        }
        return spbm[0][0];
    }

    // it is guaranteed that the number of elements in these two arrays is the same.
    public double median(int []nums1, int[] nums2, boolean odd) {
        int length = nums1.length;
        if (length < 20) {
            int []merge = new int[length*2];
            System.arraycopy(nums1, 0, merge, 0, length);
            System.arraycopy(nums2, 0, merge, length, length);
            Arrays.sort(merge);
            if (odd) {
                return (double) merge[length];
            } else {
                return ((double) merge[length-1] + (double) merge[length]) / 2.0;
             }
        } else {
            int middle_index = (length-1)/2;
            if (nums1[middle_index] > nums2[middle_index]) {
                return median(Arrays.copyOfRange(nums1, 0, middle_index+1), Arrays.copyOfRange(nums2, middle_index+2, length), odd);
            } else {
                return median(Arrays.copyOfRange(nums1, middle_index+1, length+1), Arrays.copyOfRange(nums2, 0, middle_index+1), odd);
            }
        }
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int middle_index = (nums1.length + nums2.length)/2;
        if ((nums1.length+nums2.length) % 2 == 1) {
            return median(Arrays.copyOfRange(nums1, middle_index-nums2.length, nums1.length),
                    Arrays.copyOfRange(nums2, middle_index-nums1.length, nums2.length), true);
        } else {
            return median(Arrays.copyOfRange(nums1, middle_index-nums2.length, nums1.length),
                    Arrays.copyOfRange(nums2, middle_index-nums1.length, nums2.length), false);
        }
    }



    public static int minimum(int hp, int upper, int lower, int skill[]) {
        if (hp < lower) {
            return Integer.MAX_VALUE/100;
        }
        if (hp >= lower && hp <= upper) {
            return 0;
        }
        int maximum_index = find(skill, hp-lower);
        if (maximum_index == -1) {
            return Integer.MAX_VALUE/100;
        }
        int final_array[] = new int[maximum_index+1];
        for (int i = maximum_index; i >= 0; i--) {
            final_array[i] = minimum(hp-skill[i], upper, lower, skill) + 1;
        }
        Arrays.sort(final_array);
        return final_array[0];
    }


    public static void tackle_each(Scanner in) {
        int hp = in.nextInt();
        int lower = in.nextInt();
        int upper = in.nextInt();
        int length = in.nextInt();
        int skill[] = new int[length];
        for (int i = 0; i < length; i++) {
            skill[i] = in.nextInt();
        }
        int mini_each = minimum(hp, upper, lower, skill);
        if (mini_each >= Integer.MAX_VALUE/1000) {
            System.out.println(0);
        } else {
            System.out.println(mini_each);
        }

    }

    public void printArray(int []nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.println(i);
        }
    }

    public int lengthOfLIS(int[] nums) {
        int []new_array = new int[nums.length+1];
        new_array[0] = Integer.MIN_VALUE;
        System.arraycopy(nums, 0, new_array, 1, nums.length);
        int length = nums.length+1;
        int [][]lis = new int[length][length+1];
        for (int j = length; j>= 0; j--) {
            for (int i = 0; i < j; i++) {
                if (j == length) {
                    lis[i][j] = 0;
                } else if (new_array[j] <= new_array[i]) {
                    lis[i][j] = lis[i][j+1];
                } else {
                    lis[i][j] = max(lis[j][j+1]+1, lis[i][j+1]);
                }
            }
        }
        return lis[0][1];
    }

/*
    this question really needs some calculations and observations. For example, if n = 3,
    there are three possibilities, either the root is 1, 2, or 3. If the root is 1, both 2 and 3
    must be on its right. then there are dp(2) possibilities(if the root is 1), so on and so forth.
    For more puzzles, see https://www.youtube.com/watch?v=Ox0TenN3Zpg.
 */
    public int numTrees(int n) {
        int []nt = new int[n+1];
        nt[0] = 1;
        for (int i = 1; i <= n; i++) {
            int sum = 0;
            for (int j = 1; j <= i; j ++) {
                sum += nt[j-1] * nt[i-j];
            }
            nt[i] = sum;
        }
        return nt[n];
    }

    public int coinChange(int[] coins, int amount) {
        int []cc = new int[amount+1];
        cc[0] = 0;
        int minimum_coin = Integer.MAX_VALUE;
        for (int coin : coins) {
            if (coin < minimum_coin) {
                minimum_coin = coin;
            }
        }
        for (int i = 1; i <= amount; i++) {
            if (i <  minimum_coin) {
                cc[i] = Integer.MAX_VALUE/2;
            } else {
                int minimum = Integer.MAX_VALUE;
                for (int coin : coins) {
                    if (i < coin) {
                        continue;
                    }
                    int curr = cc[i - coin];
                    if (curr < minimum) {
                        minimum = curr;
                    }
                }
                cc[i] = minimum+1;
            }
        }
        if (cc[amount] >= Integer.MAX_VALUE/2) {
            return -1;
        }
        return cc[amount];
    }

    public int rob2(int[] nums) {
        int robs[] = new int[nums.length+2];
        robs[nums.length] = 0;
        robs[nums.length+1] = 0;
        for (int i = nums.length-1; i >= 0; i--) {
            robs[i] = max(robs[i+1], nums[i]+robs[i+2]);
        }
        return robs[0];
    }

    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int first = rob2(Arrays.copyOfRange(nums, 0, nums.length-1));
        int second = rob2(Arrays.copyOfRange(nums, 1, nums.length));
        return Math.max(first, second);
    }

    public Vector<Integer> val;

    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return Math.max(root.val, rob(root.left)+rob(root.right));
        }
    }

    public void rob_helper(TreeNode root, int level) {
        if (root == null) {
            return;
        }
        if (val.size() <= level) {
            val.add(root.val);
        } else {
            val.set(level, val.elementAt(level)+root.val);
        }
        rob_helper(root.left, level+1);
        rob_helper(root.right, level+1);
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int width = obstacleGrid[0].length;
        int high = obstacleGrid.length;
        if (obstacleGrid[0][0] == 1 || obstacleGrid[high-1][width-1] == 1) {
            return 0;
        }
        int [][]upwo = new int[high+1][width+1];
        for (int i = high; i >= 0; i--) {
            for (int j = width; j>= 0; j--) {
                if (i == high-1 && j == width-1) {
                    upwo[i][j] = 1;
                } else if (i == high || j == width || obstacleGrid[i][j] == 1) {
                    upwo[i][j] = 0;
                } else {
                    upwo[i][j] = upwo[i+1][j] + upwo[i][j+1];
                }
            }
        }
        return upwo[0][0];
    }

    public int countSubstrings(String s) {
        boolean [][]csm = new boolean[s.length()][s.length()];
        int counter = 0;
        for (int i = s.length(); i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j) && (j-i <= 1 || csm[i+1][j-1])) {
                    csm[i][j] = true;
                }
                if (csm[i][j]) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int findMaxForm(String[] strs, int m, int n) {
        if (m < 0 || n < 0 || (strs.length == 0 && (m != 0 || n != 0))) {
            return Integer.MIN_VALUE;
        }
        if (m == 0 && n == 0) {
            return 0;
        }
        int ones = 0;
        int zeros = 0;
        for (int i = 0; i < strs[0].length(); i++) {
            if (strs[0].charAt(i) == '0') {
                zeros++;
            } else {
                ones++;
            }
        }
        String []next = new String[strs.length-1];
        System.arraycopy(strs, 1, next, 0, strs.length-1);
        return Math.max(findMaxForm(next, m-zeros, n-ones)+1, findMaxForm(next, m, n));
    }

    public int longestValidParentheses(String s) {
        boolean lvp[][] = new boolean[s.length()][s.length()];
        int length = 0;
        for (int i = s.length()-1; i >= 0; i--) {
            for (int j = i+1; j < s.length(); j++) {
                if ((lvp[i+1][j-1] || i == j-1) && s.charAt(i) == '(' && s.charAt(j) == ')') {
                    lvp[i][j] = true;
                    if (j - i + 1 > length) {
                        length = j-i+1;
                    }
                } else {
                    lvp[i][j] = false;
                }
            }
        }
        return length;
    }

    class envelope_struct {
        int length;
        int max_length;
        int max_width;
        int min_length;
        int min_width;

        public envelope_struct(int length, int max_length, int max_width, int min_length, int min_width) {
            this.length = length;
            this.max_length = max_length;
            this.max_width = max_width;
            this.min_length = min_length;
            this.min_width = min_width;
        }
    }

    public int hammingWeight(int n) {
        String s = Integer.toUnsignedString(n, 2);
        int ones = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                ones++;
            }
        }
//        int factor = 31;
//        int ones = 0;
//        while (n != 0) {
//            if (n >= Math.pow(2, factor)) {
//                n -= Math.pow(2, factor);
//                ones++;
//            }
//            factor--;
//        }
        return ones;
    }

//    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
//        int []budges = new int[baseCosts.length];
//        for (int base : baseCosts) {
//            int curr = target - base;
//            int []toppings = new int[toppingCosts.length*2];
//            for (int i = 0; i < toppingCosts.length; i+=2) {
//                toppings[i] = toppingCosts[i/2];
//                toppingCosts[i+1] = toppingCosts[i/2];
//            }
//            Arrays.sort(toppings);
//        }
//    }

    public int numberOfSteps(int num) {
        int steps = 0;
        while (num != 0) {
            if (num % 2==0) {
                num /= 2;
            } else {
                num--;
            }
            steps++;
        }
        return steps;
    }

    public int findKthNumber(int n, int k) {
        int counter = 1;
        for (int i = 1; i < 10; i++) {
            if (counter == k) {
                return i;
            } else {
                counter++;
            }
            int base = 1;
            while (i * Math.pow(10, base) <= n) {
                for (int j = 0; j < 10; j++) {
                    int curr = (int)((i * Math.pow(10, base)) + j);
                    if (curr > n) {
                        break;
                    } else if (counter == k) {
                        return curr;
                    } else {
                        counter++;
                    }
                }
                base++;
            }
        }
        return counter;
    }

    public boolean no_common(String a, String b) {
        HashMap<Character, Integer> curr = new HashMap<>();
        for (int i = 0; i < a.length(); i++) {
            curr.put(a.charAt(i), 1);
        }
        for (int i = 0; i < b.length(); i++) {
            if (curr.get(b.charAt(i)) != null) {
                return false;
            }
        }
        return true;
    }

    public int maxProduct(String[] words) {
        int max = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i+1; j <words.length; j++) {
                if (no_common(words[i], words[j])) {
                    max = Math.max(max, words[i].length()*words[j].length());
                }
            }
        }
        return max;
    }

    public int missingNumber(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                return i;
            }
        }
        return nums.length;
    }

    public int nearestValidPoint(int x, int y, int[][] points) {
        int index = -1;
        int han = Integer.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            if (points[i][0] == x || points[i][1] == y) {
                // this is valid
                int curr_han = Math.abs(points[i][0] - x) + Math.abs(points[i][1]-y);
                if (index == -1 || curr_han < han) {
                    index = i;
                    han = curr_han;
                }
            }
        }
        return index;
    }

    public int minimum(TreeNode root) {
        if (root == null) {
            return Integer.MAX_VALUE;
        }
        return Math.min(root.val, Math.min(minimum(root.left), minimum(root.right)));
    }

    public int maximum(TreeNode root) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        return Math.max(root.val, Math.max(maximum(root.left), maximum(root.right)));
    }

    public int maxAncestorDiff(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return -1;
        }
        int diff = Math.max(Math.abs(root.val-maximum(root)), Math.abs(root.val-minimum(root)));
        return Math.max(diff, Math.max(maxAncestorDiff(root.left), maxAncestorDiff(root.right)));
    }

    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE) {
            dividend++;
        }
        int unsigned_dividend = Math.abs(dividend);
        int unsigned_divisor = Math.abs(divisor);
        int counter = 0;
        int bias_divisor = unsigned_divisor;
        while (bias_divisor <= unsigned_dividend && counter < Integer.MAX_VALUE && counter > Integer.MIN_VALUE) {
            bias_divisor += unsigned_divisor;
            counter++;
        }
        return ((dividend < 0 && divisor > 0) || (dividend >0 && divisor <0)) ? -counter : counter;
    }

//    public List<List<Integer>> groupThePeople(int[] groupSizes) {
//        if (groupSizes.length == 0) {
//            return null;
//        }
//        int size = groupSizes[0]-1;
//        int origin = groupSizes[0];
//        List<Integer> indices = new ArrayList<>();
//        indices.add(0);
//        for (int i = 1; i < groupSizes.length && size > 0; i++) {
//             if (groupSizes[i] == origin) {
//                 size--;
//                 indices.add(i);
//             }
//        }
//    }
    public StringBuilder builder(int target, int length) {
        StringBuilder curr = new StringBuilder(Integer.toBinaryString(target));
        while (curr.length() < length) {
            curr = new StringBuilder("0").append(curr);
        }
        return curr;
    }

    public boolean hasAllCodes(String s, int k) {
        StringBuilder curr = new StringBuilder(s);
        for (int i = 0; i < Math.pow(2, k); i++) {
            StringBuilder target = builder(i, k);
            boolean equal = false;
            for (int j = 0; j <= s.length()-k; j++) {
                if (curr.substring(j, j+k).equals(target.toString())) {
                    equal = true;
                    break;
                }
            }
            if (!equal) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        Solution test = new Solution();
//        ListNode common = new ListNode(2, new ListNode(4));
//        ListNode headA = new ListNode(1, new ListNode(9, new ListNode(1, common)));
//        ListNode headB = new ListNode(3, common);
//
//        ListNode result = test.getIntersectionNode(headA, headB);

        Solution test3 = new Solution();
        int result = test3.find_first(new int[]{})
        System.out.println(result.val);
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        HashSet<ListNode> hash_nodes = new HashSet<>();
        while (headA != null || headB != null) {
            if (headA == headB) {
                return headA;
            }
            if (headA != null) {
                if (!hash_nodes.contains(headA)) {
                    hash_nodes.add(headA);
                    headA = headA.next;
                } else {
                    return headA;
                }
            }
            if (headB != null) {
                if (!hash_nodes.contains(headB)) {
                    hash_nodes.add(headB);
                    headB = headB.next;
                } else {
                    return headB;
                }
            }
        }
        return null;
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return null;
        } else if (head.next == null) {
            return head;
        }
        ListNode result = head.next;
        ListNode prev = null;
        while (head != null && head.next != null) {
            if (prev != null) {
                prev.next = head.next;
            }
            prev = head;
            ListNode next = head.next;
            head.next = next.next;
            next.next = head;
            head = head.next;
        }
        return result;
    }

    private int find_first(int [] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        while (left < right) {
            int mid = (left+right)/2;
            if (mid >= nums.length-1) {
                return (nums[nums.length-1] == target) ? nums.length-1 : -1;
            }
            if (nums[mid] != target && nums[mid+1]==target) {
                return mid+1;
            }
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid+1;
            }
        }
        return (nums[left] == target) ? left : -1;
    }


    // 注意类名必须为 Main, 不要有任何 package xxx 信息

//        public static void main(String[] args) {
//            Scanner in = new Scanner(System.in);
////            tackle_login(in);
////            int num = in.nextInt();
////            for (int i =0 ; i < num; i++) {
////                System.out.println(each(in));
////            }
////            String target = "01000000";
////            String temp = "0x" + target;
////
////            int a = Integer.decode(temp);
//
////            System.out.println(a);
//        }




}