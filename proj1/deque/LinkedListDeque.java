
public class LinkedListDeque {
    /* the same name as well as the function in the lecture */
    public DequeNode sentinel;
    int size;


    public LinkedListDeque(){
        sentinel = new DequeNode(null,7,null);
        size = 0;
    }
    public LinkedListDeque(int t){
        sentinel = new DequeNode(null,7,null);
        sentinel.next = new DequeNode(null,t,null);
        size = 0;
    }
    public void addfirst(int t){
        /* this variable is to inherit the sentinel.next's information */
        DequeNode formerfirst = sentinel.next;
        sentinel.next = new DequeNode(sentinel,t,formerfirst);
        size ++;
    }
    public void addlast(int t){
        DequeNode currentnode = sentinel;    /* this variable to represent the current node */
        while (currentnode.next != null){
            currentnode = currentnode.next;
        }
        currentnode.next = new DequeNode(currentnode,t,null);
        size ++;
    }
    public boolean isEmpty(){
        if (sentinel.next == null){
            return true;
        }
        return false;
    }
    public int size(){
        return size;
    }

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
