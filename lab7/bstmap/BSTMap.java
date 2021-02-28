package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K,V> implements Map61B<K,V> {
    Map61B KeFin = new BSTMap();
    int size;

    private void BSTMap(){
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

    private class BST {     /* @ Data Structures Into Java written by 
        protected int label;
        protected BST left, right;
        /** A leaf node with given LABEL */
        public BST(int label) { this(label, null, null); }
        /** Fetch the label of this node. */
        private BST(int label, BST left, BST right) {
            this.label = label; this.left = left; this.right = right;
        }
        }
        /** Insert the label L into T, returning the modified tree.
         * The nodes of the original tree may be modified.... */
    }
