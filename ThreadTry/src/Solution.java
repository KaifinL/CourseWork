import java.util.List;

public class Solution {
    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int judgement = l1.val + l2.val;
        ListNode solution;
        boolean forward;
        if (judgement > 10) {
            forward = true;
        } else {
            forward = false;
        }
        if (!forward) {
            solution = new ListNode(l1.val + l2.val, null);
        } else {
            solution = new ListNode(l1.val + l2.val - 10, null);
        }
        ListNode curr = solution;
        l1 = l1.next;
        l2 = l2.next;
        while (l1 != null || l2 != null) {
            if (l1 == null) {
                if (!forward) {
                    curr.next = new ListNode(l2.val, null);
                } else {
                    if (l2.val == 9) {
                        curr.next = new ListNode (0,null);
                    } else {
                        curr.next = new ListNode(l2.val + 1, null);
                        forward = false;
                    }
                }
                curr = curr.next;
                l2 = l2.next;
            } else if (l2 == null) {
                if (!forward) {
                    curr.next = new ListNode(l1.val, null);
                } else {
                    if (l1.val == 9) {
                        curr.next = new ListNode(0, null);
                    } else {
                        curr.next = new ListNode(l1.val + 1, null);
                        forward = false;
                    }
                }
                curr = curr.next;
                l1 = l1.next;
            } else {
                if (l1.val + l2.val < 10) {
                    if (!forward) {
                        curr.next = new ListNode(l1.val + l2.val, null);
                    } else {
                        if (l1.val + l2.val == 9) {
                            curr.next = new ListNode(0, null);
                            forward = true;
                        }
                        else {
                            curr.next = new ListNode(l1.val + l2.val + 1, null);
                            forward = false;
                        }
                    }
                    curr = curr.next;
                } else{
                    if (!forward) {
                        curr.next = new ListNode (l1.val + l2.val - 10, null);
                    } else {curr.next = new ListNode (l1.val + l2.val - 9, null);}
                    curr = curr.next;
                    forward = true;
                }
                l1 = l1.next;
                l2 = l2.next;
            }
        }
        if (forward) {
            curr.next = new ListNode(1, null);
        }
        return solution;
    }

    public static void main(String[] args) {
        ListNode test1 = new ListNode(9,null);
        ListNode curr = test1;
        /**
        int count = 0;
        while (count < 7) {
            curr.next = new ListNode(9, null);
            curr = curr.next;
            count += 1;
        }
        ListNode test2 = new ListNode(9, null);
        ListNode curr2 = test2;
        count = 0;
        while (count < 4) {
            curr2.next = new ListNode(9,null);
            curr2 = curr2.next;
            count += 1;
        }
        ListNode test3 = addTwoNumbers(test1, test2);
        while (test3 != null) {
            System.out.print(test3.val);
            test3 = test3.next;
        }
         **/
        
    }

}