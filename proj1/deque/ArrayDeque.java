package deque;

import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.ls.LSInput;

import java.lang.reflect.Array;

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

    public void addfirst(Glorp t){
        item[beginIndex-1] = t;
        beginIndex --;
        size ++;
    }

    public void addlast(Glorp t){
        item[endIndex] = t;
        endIndex ++;
        size ++;
    }

    public static void main(String[] args) {
        ArrayDeque L = new ArrayDeque();
        L.addfirst(3);
        System.out.println(L);
    }

}
