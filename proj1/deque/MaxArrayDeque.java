package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque{
    public Comparator givenComparator;
    public MaxArrayDeque<T> items;

    public MaxArrayDeque(Comparator<T> c){
        this.givenComparator = c;
    }

    public T max(){
        if (items == null){
            return null;
        }
        for (int i=0; i< items.size();i ++){
            
        }
    }

}
