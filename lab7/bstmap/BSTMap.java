package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K,V> implements Map61B<K,V> {
    BST KeFinTree;
    int size;

    private void BSTMap() {
        size = 0;
    }

    public void clear() {
        for (K item : keySet()) {
            remove(item);
        }
    }

    public boolean containsKey(K key) {
        if ()
            return true;
    }

    private boolean containsKey(K key, BST next) {
        if (next.value == null) {
            return false;
        }
        if (get(key) < next.value)
    }

    public V get(K key) {

    }

    public int size() {
        return 1;
    }

    public void put(K key, V value) {

    }

    public Set<K> keySet() {

    }

    public V remove(K key) {

    }

    public V remove(K key, V value) {

    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    private class BST<K, V> {     /* @ Data Structures Into Java written by Professor Hilfinger. */
        K key;
        V value;
        BST left;
        BST right;
        public BST(BST left, BST right, K key, V value) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.value = value;
        }
    }
}
