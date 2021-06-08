public class Solution {
    
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
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
        while (l1 != null && l2 != null) {
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
            } else {
                if (l1.val + l2.val < 10) {
                    if (!forward) {
                        curr.next = new ListNode(l1.val + l2.val, null);
                    } else {
                        curr.next = new ListNode(l1.val + l2.val + 1, null);
                    }
                    curr = curr.next;
                    forward = false;
                } else{
                    if (!forward) {
                        curr.next = new ListNode (l1.val + l2.val - 10, null);
                    } else {curr.next = new ListNode (l1.val + l2.val - 9, null);}
                    curr = curr.next;
                    forward = true;
                }
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        return solution;
    }
}