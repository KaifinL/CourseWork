package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    /* the same name as well as the function in the lecture */
    private DequeNode sentinel;
    private DequeNode last;
    private int size;


    public LinkedListDeque() {
        sentinel = new DequeNode(null, null, null);
        last = new DequeNode(sentinel, null, sentinel);
        sentinel.next = last;
        sentinel.ahead = last;
        size = 0;
    }

    @Override
    public void addFirst(T t) {
        /* this variable is to inherit the sentinel.next's information */
        DequeNode D = new DequeNode(sentinel, t, sentinel.next);
        sentinel.next.ahead = D;
        sentinel.next = D;
        size++;
    }

    @Override
    public void addLast(T t) {
        DequeNode D = new DequeNode(last.ahead, t, last);
        last.ahead.next = D;
        last.ahead = D;
        size++;
    }

    @Override
    public int size() {
        if (size < 0) {
            return 0;
        }
        return size;
    }

    @Override
    public void printDeque() {
        DequeNode currentnode = sentinel;   /* plays the same function as the former one */
        while (currentnode.next != last) {
            System.out.print(currentnode.next.item + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        DequeNode deletenode = sentinel.next;
        sentinel.next.next.ahead = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return deletenode.item;
    }

    @Override
    public T removeLast() {
        DequeNode deleteNode = last.ahead;
        last.ahead.ahead.next = last;
        last.ahead = last.ahead.ahead;
        size--;
        return deleteNode.item;
    }

    @Override
    public T get(int index) {
        int counter = 0;  /* to count the node's rank */
        DequeNode currentnode = sentinel.next;
        while (index != counter) {
            currentnode = currentnode.next;
            counter++;
        }
        return currentnode.item;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LinkedListDeque) {
            int counter = 0;
            int t = 0;
            DequeNode current = sentinel;
            DequeNode compare = ((LinkedListDeque) o).sentinel;
            while (current.next != last & t < ((LinkedListDeque<T>) o).size) {
                if (current.next.item.equals(compare.next.item)) {
                    counter++;
                }
                t++;
                current = current.next;
                compare = compare.next;

            }
            if (size != ((LinkedListDeque<T>) o).size) {
                return false;
            }
            if (counter == size) {
                return true;
            }
        }
        return false;
    }
    public T getRecursive(int index) {
        return helpToGet(index, sentinel.next).item;
    }

    private DequeNode helpToGet(int index, DequeNode p) {
        if (index == 0) {
            return p;
        }
        return helpToGet(index - 1, p.next);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private int inSize;

        LinkedListDequeIterator() {
            inSize = 0;
        }

        @Override
        public boolean hasNext() {
            return inSize < size;
        }

        @Override
        public T next() {
            T returnItem = get(inSize);
            inSize++;
            return returnItem;
        }
    }


    private class DequeNode {
        private DequeNode ahead;
        private T item;
        private DequeNode next;
        public DequeNode(DequeNode a, T i, DequeNode n) {
            ahead = a;
            item = i;
            next = n;
        }
    }
}
