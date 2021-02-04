package deque;
public class ArrayDeque<Glorp> {
    Glorp [] item;
    int beginIndex;
    int endIndex;
    int size;



    public ArrayDeque(){
        item = (Glorp[]) new Object[8];
        beginIndex = 4;
        endIndex = 4;
        size = 0;

    }

    public void addFirst(Glorp t){
        item[beginIndex-1] = t;
        beginIndex --;
        size ++;

    }

    public void addLast(Glorp t){
        item[endIndex] = t;
        endIndex ++;
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
        for (int i= beginIndex;i < endIndex;i++){
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public Glorp removeFirst(){
        Glorp returnStuff = item[beginIndex];
        item[beginIndex] = null;
        beginIndex ++;
        size --;
        return returnStuff;
    }

    public Glorp removeLast(){
        Glorp returnStuff = item[endIndex];
        item[endIndex] = null;
        endIndex --;
        size--;
        return returnStuff;
    }

    public Glorp get(int index){
         return item[beginIndex + index];
    }

    public boolean equals(Object o){
        if (o instanceof ArrayDeque){
            Glorp [] compare = (Glorp[]) new Object[8];
            System.arraycopy(o,0,compare,beginIndex,endIndex);
            int counter = 0;
            for (int i = beginIndex;i<=endIndex;i++){
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
    public void helpDecrease(){
        if (size <= (item.length/4)){
            Glorp[] newItem;
            newItem = (Glorp[]) new Object[size*2];
            System.arraycopy(item,beginIndex,newItem,size/3,4*size/3);
            item = newItem;
            beginIndex = size / 3;
            endIndex = size *4 /3;
        }
    }
    public void helpIncrease(){
        if (size >= (item.length*2 /3)){
            Glorp [] newItem;
            newItem = (Glorp[]) new Object[size*2];
        }

    }


}
