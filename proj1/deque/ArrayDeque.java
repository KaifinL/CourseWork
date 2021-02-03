package deque;

import afu.org.checkerframework.checker.oigj.qual.O;
import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.ls.LSInput;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

public class ArrayDeque<Glorp> {
    Glorp [] item;
    int beginIndex = 4;
    int endIndex = 4;
    int size = 0;



    public ArrayDeque(){
        item = (Glorp[]) new Object[8];
        
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
        if (item[beginIndex-1] == null & item[beginIndex] == null){
            return true;
        }
        return false;
    }

    public int size(){
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


}
