package deque;

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
        if (beginIndex == 0){
            beginIndex = item.length-1;
        }
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

    }


}
