
package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T [] item;
    private int size;
    private int beginIndex;
    private int endIndex;

    public ArrayDeque() {
        item = (T[]) new Object[8];
        size = 0;
        beginIndex = 4;
        endIndex = 4;

    }

    @Override
    public void addFirst(T t) {
        if (beginIndex == 0) {
            resize(size * 2);
        }
        item[beginIndex - 1] = t;
        beginIndex -= 1;
        size++;
    }
    @Override
    public void addLast(T x) {
        if (endIndex == item.length - 1) {
            resize(size * 2);
        }
        item[endIndex] = x;
        size += 1;
        endIndex += 1;
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
            System.out.print(item[beginIndex] + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if ((size < item.length / 4) && (size > 4)) {
            resize(item.length / 4);
        }
        T returnStuff = item[beginIndex];
        if (size > 0) {
            beginIndex += 1;
            size--;
        }
        return returnStuff;
    }

    @Override
    public T removeLast() {
        if ((size < item.length / 4) && (size > 4)) {
            resize(item.length / 4);
        }
        if (size > 0) {
            T x = get(endIndex - 1);
            item[endIndex - 1] = null;
            size--;
            endIndex--;
            return x;
        }
        return null;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return item[(index + beginIndex) % item.length];
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

    private void resize(int capacity) {
        T[] a = (T[])  new Object[capacity];
        System.arraycopy(item, beginIndex, a, item.length / 4, size);
        item = a;
        beginIndex = item.length / 4;
        endIndex = item.length / 4 + size - 1;
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
}

