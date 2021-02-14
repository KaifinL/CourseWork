package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque{
    public Comparator c;
    public MaxArrayDeque<T> m;

    public MaxArrayDeque(Comparator<T> c){
        this.c = c;

    }

    public T max(){
        if (m == null){
            return null;
        }
        T maxItem = (T) m.getFirst();
        for (int i = 0; i < m.size();i++){
            if (maxItem.compareTo())
        }
    }

    public int compareTo(T a,T b){
        return a.
    }

}
