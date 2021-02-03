package deque;
import org.junit.Test;

import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.Iterator;
public class LinkedListDeque {
    /* the same name as well as the function in the lecture */
    public DequeNode sentinel;
    private DequeNode last;
    int size;


    public LinkedListDeque(){
        sentinel = new DequeNode(null,null,null);
        last = new DequeNode(null,null,null);
        sentinel.next = last;
        last.ahead = sentinel;
        last.next = sentinel;
        size = 0;
    }
    public LinkedListDeque(Type t){
        sentinel = new DequeNode(null,null,null);
        last = new DequeNode(null,null,null);
        DequeNode D = new DequeNode(null,t,null);
        D.next = last;
        sentinel.next = D;
        D.ahead = sentinel;
        last.ahead = D;
        last.next = sentinel;
        size = 0;
    }
    public void addFirst(Type t){
        /* this variable is to inherit the sentinel.next's information */
        DequeNode D = new DequeNode(sentinel,t,sentinel.next);
        sentinel.next.ahead = D;
        sentinel.next = D;
        size ++;
    }
    public void addLast(Type t){
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
    public Type removeFirst(){
        DequeNode deletenode = sentinel.next;
        sentinel.next.next.ahead = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return deletenode.item;
    }
    public Object removeLast(){
        DequeNode deleteNode = last.ahead;
        last.ahead.ahead.next = last;
        last.ahead = last.ahead.ahead;
        size --;
        return deleteNode.item;
    }
    public Type get(int index){
        int counter = 0;  /* to count the node's rank */
        DequeNode currentnode = sentinel.next;
        while (index != counter){
            currentnode = currentnode.next;
            counter ++;
        }
        return currentnode.item;
    }


    private boolean hasNext(DequeNode t){
        if (t.next == null){
            return false;
        }
        return true;
    }

    public boolean equals(Object o) {
        if (o instanceof LinkedListDeque) {
            int counter = 0;
            int t = 0;
            DequeNode current = sentinel;
            DequeNode compare = ((LinkedListDeque) o).sentinel;
            while (current.next != last & t < ((LinkedListDeque)o).size) {
                if (current.next.item == compare.next.item){
                    counter ++;
                    t ++;
                }
                current = current.next;
                compare = compare.next;

            }
            if (counter == size){
                return true;
            }
        }
        return false;
    }


    private class DequeNode{
        public DequeNode ahead;
        public Type item;
        public DequeNode next;
        public DequeNode(DequeNode a,Type i,DequeNode n){
            ahead = a;
            item = i;
            next = n;
        }
    }
}
