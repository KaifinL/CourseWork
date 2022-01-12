import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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



    public static void main(String[] args) {
        Solution testy = new Solution();
        String test = Solution.rotateRight("cs124", 1);
        System.out.println(test);
    }

}