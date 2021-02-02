package deque;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.Iterator;
public class LinkedListDeque<BleepBlorp> {
    /* the same name as well as the function in the lecture */
    public DequeNode sentinel;
    private DequeNode last;
    int size;


    public LinkedListDeque(){
        sentinel = new DequeNode(null,0,null);
        last = new DequeNode(null,0,null);
        sentinel.next = last;
        last.ahead = sentinel;
        size = 0;
    }
    public LinkedListDeque(int t){
        sentinel = new DequeNode(null,0,null);
        last = new DequeNode(null,0,null);
        DequeNode D = new DequeNode(null,t,null);
        D.next = last;
        sentinel.next = D;
        D.ahead = sentinel;
        last.ahead = D;
        size = 0;
    }
    public void addfirst(int t){
        /* this variable is to inherit the sentinel.next's information */
        DequeNode D = new DequeNode(sentinel,t,sentinel.next);
        sentinel.next.ahead = D;
        sentinel.next = D;
        size ++;
    }
    public void addlast(int t){
        DequeNode D = new DequeNode(last.ahead,t,last);
        sentinel.ahead.next = D;
        sentinel.ahead = D;
        size ++;
    }
    public boolean isEmpty(){
        if (sentinel.next == last){
            return true;
        }
        return false;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        DequeNode currentnode = sentinel;   /* plays the same function as the former one */
        while (currentnode.next != last) {
            System.out.print(currentnode.next.item + " ");
        }
        System.out.println();
    }
    public T removefirst(){
        
        size -= 1;
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
    public Iterator iterator(){
        return
    }

    private boolean hasNext(DequeNode t){
        if (t.next == null){
            return false;
        }
        return true;
    }

    private int next()


    private class DequeNode{
        public DequeNode ahead;
        public <BleepBlorp> item;
        public DequeNode next;
        public DequeNode(DequeNode a,int i,DequeNode n){
            ahead = a;
            item = i;
            next = n;
        }
    }
}
