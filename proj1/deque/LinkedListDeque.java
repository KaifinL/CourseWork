
public class LinkedListDeque {
    /* the same name as well as the function in the lecture */
    public DequeNode sentinel;


    public LinkedListDeque(int t){
        sentinel = new DequeNode(null,t,null);

    }
    public void addfirst(int t){}

    private class DequeNode{
        public DequeNode ahead;
        public int item;
        public DequeNode next;
        public DequeNode(DequeNode a,int i,DequeNode n){
            ahead = a;
            item = i;
            next = n;
        }
    }
}
