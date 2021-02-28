package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V> {
    private BSTNode KeFinTree;
    private int size;

    private void BSTMap() {
        size = 0;
    }

    @Override
    public void clear() {
        size = 0;
        KeFinTree = new BSTNode();
    }

    @Override
    public boolean containsKey(K key) {
        BSTNode cur = KeFinTree;
        if (size == 0) {
            return false;
        }
        while (cur != null) {
            if (key.equals(cur.key)) {
                return true;
            }
            if (key.compareTo((K) cur.key) > 0) {
                cur = cur.right;
            }else {
                cur = cur.left;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        BSTNode cur = KeFinTree;
        if (size == 0) {
            return null;
        }
        while (cur != null) {
            if (key.equals(cur.key)) {
                return (V) cur.value;
            }
            if (key.compareTo((K) cur.key) > 0) {
                cur = cur.right;
            }else {
                cur = cur.left;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (containsKey(key)) {
            BSTNode cur = KeFinTree;
            while (cur != null) {
                if (key.equals(cur.key)) {
                    cur.value = value;
                }
                if (key.compareTo((K) cur.key) > 0) {
                    cur = cur.right;
                }else {
                    cur = cur.left;
                }
            }
        }
        if (size == 0) {
            KeFinTree = new BSTNode(key, value, null, null);
        }
        BSTNode nothing = put(key, value, KeFinTree);
        size += 1;
    }

    private BSTNode put(K key, V value, BSTNode cur) {
        if (cur == null) {
            return new BSTNode(key, value, null, null);
        }else {
            if (key.compareTo((K) cur.key) > 0) {
                cur.right = put(key, value, cur.right);
            }else {
                cur.left = put(key, value, cur.left);
            }
        }
        return cur;
    }

    public void printInOrder() {
        
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    private class BSTNode<K extends Comparable<K>, Value> {
        private K key;
        private Value value;
        private BSTNode left;
        private BSTNode right;
        public BSTNode() {}
        public BSTNode(K key, Value value, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

}
