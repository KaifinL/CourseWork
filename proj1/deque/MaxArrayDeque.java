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

    }

    public class NameSort implements c{
        public int compare(T a,T b){
            return a.compareTo(b);
        }
    }

    public static T max(m) {
        int maxDex = 0;
        for (int i = 0; i < items.length; i += 1) {
            int cmp = items[i].compareTo(items[maxDex]);

            if (cmp > 0) {
                maxDex = i;
            }
        }
        return items[maxDex];

}
