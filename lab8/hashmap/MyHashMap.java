package hashmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {


    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }


    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private int initialSize;
    private double loadFactor;
    private HashSet<K> keySet;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this.size = 0;
        this.initialSize = 16;
        this.loadFactor = 0.75;
        this.keySet = new HashSet();
        this.buckets = createTable(initialSize);
        fileUp(buckets); // make every element in the buckets a empty collection of LinkedList
    }

    public MyHashMap(int initialSize) {
        this.size = 0;
        this.initialSize = initialSize;
        this.loadFactor = 0.75;
        this.keySet = new HashSet();
        this.buckets = createTable(this.initialSize);
        fileUp(buckets); // make every element in the buckets a empty collection of LinkedList
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.size = 0;
        this.initialSize = initialSize;
        this.loadFactor = maxLoad;
        this.keySet = new HashSet();
        this.buckets = createTable(this.initialSize);
        fileUp(buckets); // make every element in the buckets a empty collection of LinkedList
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<Node>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // Your code won't compile until you do so!

    /**
     * this is basically done
     */
    @Override
    public void clear() {
        Collection<Node>[] empty = createTable(this.initialSize);
        this.buckets = empty;
        size = 0;
        keySet = new HashSet<>();
    }

    /**
     * this is basically done
     */
    @Override
    public boolean containsKey(K key) {
        return this.keySet.contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        } else {
            for (Collection<Node> row : buckets) {
                for (Node node : row) {
                    if (node.key.equals(key)) {
                        return node.value;
                    }
                }
            }
            return null;  //though knowing this makes no sense the compiler yields at me to return a value
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        double division = ((double)size) / initialSize;
        if (division > loadFactor) {
            resize(initialSize * 2);
        }
        if (containsKey(key)) {
            for (Collection<Node> row : buckets) {
                for (Node node : row) {
                    if (node.key.equals(key)) {
                        node.value = value;
                    }
                }
            }
        }else {
            int row = hash(key) % initialSize;
            buckets[row].add(new Node(key, value));
            keySet.add(key);
            size++;
        }
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        for (Collection bucket : buckets) {
            if (removeHelper(key, bucket) != null) {
                V returnStuff = (V) removeHelper(key, bucket);
                bucket.remove(key);
                size --;
                keySet.remove(key);
                return returnStuff;
            }
        }
        return null;
    }

    private V removeHelper(K key, Collection<Node> bucket) {
        for (Node i : bucket) {
            if (i.key.equals(key)) {
                V returnStuff = get(key);
                bucket.remove(key);
                return returnStuff;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if (get(key).equals(value)) {
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyIterator();
    }



    // helper method! To initialize each bucket to avoid nullPointerException
    private void fileUp(Collection target[]) {
        for (int i = 0; i < initialSize; i += 1) {
            Collection<Node> empty = new LinkedList<>();
            target[i] = empty;
        }
    }

    // get the hashcode @optional textbook
    private int hash(K key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
        return h & (this.initialSize-1);
    }

    private void resize(int capacity) {
        initialSize = capacity;
        Collection<Node>[] newBuckets = new Collection[capacity];
        fileUp(newBuckets);
        for (K key : keySet()) {
            int row = hash(key) % capacity;
            newBuckets[row].add(new Node(key, get(key)));
        }
        buckets = newBuckets;
    }

    private class MyIterator implements Iterator {

        private LinkedList keys = setKeyList();
        private K key = (K) keys.getFirst();
        @Override
        public boolean hasNext() {
            return key != null;
        }

        @Override
        public K next() {
            K curr = key;
            keys.removeFirst();
            key = (K) keys.getFirst();
            return curr;
        }
    }
    private LinkedList setKeyList() {
        LinkedList keyList = new LinkedList<K>();
        for (K key : keySet) {
            keyList.addLast(key);
        }
        return keyList;
    }

}
