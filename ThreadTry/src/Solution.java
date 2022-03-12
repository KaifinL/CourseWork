import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.floorDiv;
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

    public Node connect(Node root) {
        return connect(root, null);
    }

    public Node connect(Node root, Node right) {
        if (root.left == null) {
            root.next = right;
        } else {
            root.left = connect(root.left, root.right);
            root.right = connect(root.right, null);
        }
        return root;
    }

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





    public static void main(String[] args) {
        Map<String, List> test1 = helper_script("why:is\n" +
                "why:that\n is:who");
        for (String i : test1.keySet()) {
            System.out.println(i);
            System.out.println(test1.get(i));
        }
    }

}