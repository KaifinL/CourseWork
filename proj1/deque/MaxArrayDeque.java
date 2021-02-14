package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque{
    public Comparator givenComparator;

    public MaxArrayDeque(Comparator<T> c){
        this.givenComparator = c;
    }

    public T max(){
        if (this == null){
            return null;
        }
        int comparation;
        T maxDex = (T) getFirst();
        for (int i=1; i< size();i ++){
            comparation = getGivenComparator().compare(maxDex,get(i));
            if (comparation < 0){
                maxDex = (T)get(i);
            }
        }
        return maxDex;
    }

    public T max(Comparator c){
        if (this == null){
            return null;
        }
        int comparation;
        T maxDex = (T) getFirst();
        for (int i=1; i< size();i ++){
            comparation = c.compare(maxDex,get(i));
            if (comparation < 0){
                maxDex = (T) get(i);
            }
        }
        return maxDex;
    }

    public Comparator getGivenComparator() {
        return givenComparator;
    }
}
