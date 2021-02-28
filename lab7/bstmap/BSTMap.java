package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<Key extends Comparable<Key>,V> implements Map61B<Key,V> {
    BSTNode KeFinTree;
    int size;

    private void BSTMap() {
        size = 0;
    }

    public void clear() {
        size = 0;
        KeFinTree = new BSTNode();
    }

    public boolean containsKey(Key key) {
        BSTNode cur = KeFinTree;
        while (cur != null) {
            if (key.equals(cur.key)) {
                return true;
            }
            if (key.compareTo((Key) cur.key) > 0) {
                cur = cur.right;
            }else {
                cur = cur.left;
            }
        }
        return false;
    }

    public V get(Key key) {
        BSTNode cur = KeFinTree;
        while (cur != null) {
            if (key.equals(cur.key)) {
                return (V) cur.value;
            }
            if (key.compareTo((Key) cur.key) > 0) {
                cur = cur.right;
            }else {
                cur = cur.left;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public void put(Key key, V value) {
        put(key, value, KeFinTree);
        size += 1;
    }

    private void put(Key key, V value, BSTNode cur) {
        
    }

    public Set<Key> keySet() {
    }

    public V remove(Key key) {

    }

    public V remove(Key key, V value) {

    }

    @Override
    public Iterator<Key> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<Key> {
        private BSTNode cur;

        BSTMapIterator() {
            cur = KeFinTree;
        }

        @Override
        public boolean hasNext() {
            return next() == null;
        }

        @Override
        public Key next() {


        }

    }
    private class BSTNode<Key extends Comparable<Key>, Value> {
        private Key key;
        private Value value;
        private BSTNode left;
        private BSTNode right;
        public BSTNode() {}
        public BSTNode(Key key, Value value, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

}
