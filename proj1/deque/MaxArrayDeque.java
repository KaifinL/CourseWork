package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque{
    public Comparator c;
    public MaxArrayDeque m;

    public MaxArrayDeque(Comparator<T> c){
        this.c = c;

    }

    public T max(){
        if (m == null){
            return null;
        }


    }
    public int compareTo(T a,T b){
        return a.
    }

}
