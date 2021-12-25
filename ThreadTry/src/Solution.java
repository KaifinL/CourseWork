import org.junit.Test;

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
        for (int i = 0; i < s.length(); i++) {
            
        }
    }



    public static void main(String[] args) {
        String test = "testsyourself";
        System.out.println(test.substring(0, 1));
    }

}