package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T [] item;
    private int size;
    private int nextFirst;
    private int nextLast;


    public ArrayDeque() {
        item = (T[]) new Object[8];
        size = 0;
        nextFirst = 7;
        nextLast = 0;

    }

    @Override
    public void addFirst(T t) {
        item[nextFirst] = t;
        nextFirst -= 1;
        size++;
        if (nextLast - nextFirst == 1) {
            RaiseSize();
        }
    }

    @Override
    public void addLast(T x) {
        item[nextLast] = x;
        nextLast++;
        size++;
        if (nextLast - nextFirst == 1) {
            RaiseSize();
        }
    }

    @Override
    public int size() {
        if (size <= 0) {
            return 0;
        }
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        nextFirst = (nextFirst + 1) % item.length;
        T returnStuff = item[nextFirst];
        item[nextFirst] = null;
        size--;
        if (size <= 0.25 * item.length) {
            CutSize();
        }
        return returnStuff;
    }

    @Override
    public T removeLast() {
        nextLast = (nextLast - 1 + item.length) % item.length;
        T returnStuff = item[nextLast];
        item[nextLast] = null;
        size--;
        if (size <= 0.25 * item.length) {
            CutSize();
        }
        return returnStuff;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int trueIndex = (index + nextFirst + 1) % item.length;
        return item[trueIndex];
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Deque) {
            int counter = 0;
            if (size != ((Deque<Object>) o).size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                T compare = (T) ((Deque) o).get(i);
                if (get(i).equals(compare)) {
                    counter++;
                }
            }
            if (counter == size) {
                return true;
            }
        }
        return false;
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int inSize;
        ArrayDequeIterator() {
            inSize = 0;
        }
        public boolean hasNext() {
            return inSize < size();
        }
        public T next() {
            T returnItem = get(inSize);
            inSize++;
            return returnItem;
        }

    }

    private void RaiseSize() {
        T[] newItem = (T[]) new Object[item.length * 2];
        System.arraycopy(item, 0, newItem, 0, nextLast);
        System.arraycopy(item, nextFirst, newItem, nextLast + item.length, item.length - nextFirst + 1);
        nextFirst += item.length;
        item = newItem;
    }

    private void CutSize(){
        T[] newItem = (T[]) new Object[item.length / 2];
        if (nextFirst < nextLast) {
            System.arraycopy(item, nextFirst + 1, newItem, item.length / 4 - 1, size);
            item = newItem;
            nextFirst -= item.length / 4;
            nextLast -= item.length / 4;
        }
        else {
            System.arraycopy(item, nextFirst + 1, newItem, nextFirst - item.length / 2 + 1, item.length - nextFirst);
            System.arraycopy(item, 0, newItem, 0, nextLast);
            nextFirst -= item.length / 2;
        }
    }
}
