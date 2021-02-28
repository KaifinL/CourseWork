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
        if (containsKey(key)) {
            BSTNode cur = KeFinTree;
            while (cur != null) {
                if (key.equals(cur.key)) {
                    cur.value = value;
                }
                if (key.compareTo((Key) cur.key) > 0) {
                    cur = cur.right;
                }else {
                    cur = cur.left;
                }
            }
        }
        put(key, value, KeFinTree);
        size += 1;
    }

    private void put(Key key, V value, BSTNode cur) {
        if (cur == null) {
            cur = new BSTNode(key, value, null, null);
        }else {
            if (key.compareTo((Key) cur.key) > 0) {
                put(key, value, cur.right);
            }else {
                put(key, value, cur.left);
            }
        }
    }

    public Set<Key> keySet() {
        throw new UnsupportedOperationException();
    }

    public V remove(Key key) {
        throw new UnsupportedOperationException();
    }

    public V remove(Key key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Key> iterator() {
        return null;
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
