
public class LinkedListDeque {
    DequeNode 

    public LinkedListDeque(){

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
