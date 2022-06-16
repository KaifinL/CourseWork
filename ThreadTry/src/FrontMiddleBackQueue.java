public class FrontMiddleBackQueue {

    private class node {
        int val;
        node prev;
        node next;

        public node(int val) {
            this.val = val;
            this.prev = null;
            this.next = null;
        }

        public node(int val, node prev, node next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }

    node front;
    node middle;
    node last;
    boolean odd;

    public FrontMiddleBackQueue() {
        this.front = null;
        this.middle = null;
        this.last = null;
        this.odd = false;
    }

    public void pushFront(int val) {
        this.front = new node(val, null, this.front);
        
    }

    public void pushMiddle(int val) {

    }

    public void pushBack(int val) {

    }

    public int popFront() {

    }

    public int popMiddle() {

    }

    public int popBack() {

    }
}
