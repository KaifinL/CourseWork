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
        int comparation;
        T maxDex = (T) items.getFirst();
        for (int i=1; i< items.size();i ++){
            comparation = getGivenComparator().compare(maxDex,items.get(i));
            if (comparation < 0){
                maxDex = (T) items.get(i);
            }
        }
        return maxDex;
    }

    public T max(Comparator newComparator){
        
    }

    public Comparator getGivenComparator() {
        return givenComparator;
    }
}
