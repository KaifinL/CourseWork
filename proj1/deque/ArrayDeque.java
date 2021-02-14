package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>,Deque<T> {
    T [] item;
    int size;

    public ArrayDeque(){
        item = (T[]) new Object[8];
        size = 0;

    }

    public void addFirst(T t){
        if (size == item.length) {
            resize(size * 2);
        }
        T [] a = (T[]) new Object[item.length+1];
        System.arraycopy(item,0,a,1,size);
        a[0] = t;
        item = a;
        size ++;
    }

    public void addLast(T x) {
        if (size == item.length) {
            resize(size * 2);
        }
        item[size] = x;
        size = size + 1;
    }

    public boolean isEmpty(){
        if (size <= 0){
            return true;
        }
        return false;
    }

    public int size(){
        if (size <= 0){
            return 0;
        }
        return size;
    }

    public void printDeque(){
        for (int i= 0;i < size;i++){
            System.out.print(item[i] + " ");
        }
        System.out.println();
    }

    public T removeFirst(){
        if ((size < item.length / 4) && (size > 4)) {
            resize(item.length / 4);
        }
        T [] a = (T[]) new Object[item.length-1];
        T returnStuff = item[0];
        System.arraycopy(item,1,a,0,item.length-1);
        size --;
        item = a;
        return returnStuff;
    }

    public T removeLast(){
        if ((size < item.length / 4) && (size > 4)) {
            resize(item.length / 4);
        }
        T x = getLast();
        if (size > 0){
            item[size - 1] = null;
            size--;
        }
        return x;
    }

    public T get(int index){
        if (index < 0 || index >= size){
            return null;
        }
         return item[index];
    }

    public boolean equals(Object o){
        if (o instanceof ArrayDeque){
            int counter = 0;
            for (int i = 0;i<min(size,((ArrayDeque)o).size);i++){
                if (item[i] ==((ArrayDeque)o).item[i]) {
                    counter++;
                }
            }
            if (counter == size){
                return true;
            }
        }
        return false;
    }

    private void resize(int capacity) {
        T[] a = (T[])  new Object[capacity];
        for (int i = 0; i < size; i += 1) {
            a[i] = item[i];
        }
        item = a;
    }

    public static int min(int a,int b){
        if (a <= b){
            return a;
        }
        return b;
    }

    public T getLast() {
        if (size > 0) {
            return item[size - 1];
        }
        return null;
    }

    public T getFirst(){
        return item[0];
    }

    public Iterator<T> iterator(){
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T>{
        private int inSize;
        public ArrayDequeIterator(){
            inSize = 0;
        }
        public boolean hasNext(){
            return inSize<size();
        }
        public T next(){
            T returnItem = get(inSize);
            inSize ++;
            return returnItem;
        }

    }
}
