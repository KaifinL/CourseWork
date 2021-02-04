package deque;

public class ArrayDeque<Glorp> {
    Glorp [] item;
    int size;



    public ArrayDeque(){
        item = (Glorp[]) new Object[8];
        size = 0;

    }

    public void addFirst(Glorp t){
        Glorp [] a = (Glorp[]) new Object[item.length+1];
        System.arraycopy(item,0,a,1,size);
        a[0] = t;
        item = a;
        size ++;
    }

    public void addLast(Glorp t){
        Glorp [] a = (Glorp[]) new Object[item.length+1];
        System.arraycopy(item,0,a,0,size);
        a[size+1] = t;
        item = a;
        size ++;
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

    public Glorp removeFirst(){
        Glorp [] a = (Glorp[]) new Object[item.length-1];
        Glorp returnStuff = item[0];
        System.arraycopy(item,1,a,0,item.length-1);
        size --;
        item = a;
        return returnStuff;
    }

    public Glorp removeLast(){
        Glorp [] a = (Glorp[]) new Object[item.length-1];
        Glorp returnStuff = item[size-1];
        System.arraycopy(item,0,a,0,item.length-1);
        if (size <= 0){
            returnStuff = null; }
        item = a;
        size--;
        return returnStuff;
    }

    public Glorp get(int index){
        if (index < 0 || index >= size){
            return null;
        }
         return item[index];
    }

    public boolean equals(Object o){
        if (o instanceof ArrayDeque){
            Glorp [] compare = (Glorp[]) new Object[item.length];
            System.arraycopy(o,0,compare,0,((ArrayDeque)o).size);
            int counter = 0;
            for (int i = 0;i<min(size,((ArrayDeque)o).size);i++){
                if (item[i] == compare[i]) {
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
        Glorp[] a = (Glorp[])  new Object[capacity];
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
}
