import java.lang.reflect.Type;
import java.net.Proxy;

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
    public void printDeque(){
        DequeNode currentnode = sentinel;   /* plays the same function as the former one */
        while (currentnode.next != null) {
            System.out.print(currentnode.next.item + " ");
        }
        System.out.println(currentnode.item);
    }
    public Object removefirst(){
        if (sentinel.next ==null){
            return null;
        }
        else {
            DequeNode secondnode = sentinel.next.next;
            sentinel.next = secondnode;
            return sentinel.next.item;
        }
    }
    public Object removelast(){
        if (sentinel.next == null){
            return null;
        }
        else {
            DequeNode currentnode = sentinel.next;
            DequeNode pastnode = sentinel;
            while (currentnode.next != null){
                currentnode = currentnode.next;
                pastnode = pastnode.next;
            }
            pastnode.next = null;
            return currentnode.item;
        }
    }
    public Object get(int index){
        if (sentinel.next == null){
            return null;
        }
        int counter = 0;  /* to count the node's rank */
        DequeNode currentnode = sentinel;
        while (index != counter){
            currentnode = currentnode.next;
            counter ++;
        }
        return currentnode.item;
    }
    public Iterable 

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
