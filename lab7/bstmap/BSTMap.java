package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<Key,V> implements Map61B<Key,V> {
    BST KeFinTree;
    int size;

    private void BSTMap() {
        size = 0;
    }

    public void clear() {
        for (Key item : keySet()) {
            remove(item);
        }
    }

    public boolean containsKey(Key key) {
        return KeFinTree.contains((Comparable) key);
    }

    public V get(Key key) {
        return get(key);
    }

    public int size() {
        return size;
    }

    public void put(Key key, V value) {
        put(key,value);

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
        private BST cur;

        BSTMapIterator() {
            cur = KeFinTree;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Key next() {

        }

    }

    public class BST<Key extends Comparable<Key>, Value> {
        private Node root;             // root of BST

        private class Node {
            private Key key;           // sorted by key
            private Value val;         // associated data
            private Node left, right;  // left and right subtrees
            private int size;          // number of nodes in subtree

            public Node(Key key, Value val, int size) {
                this.key = key;
                this.val = val;
                this.size = size;
            }
        }

        /**
         * Initializes an empty symbol table.
         */
        public BST() {
        }

        /**
         * Returns true if this symbol table is empty.
         *
         * @return {@code true} if this symbol table is empty; {@code false} otherwise
         */
        public boolean isEmpty() {
            return size() == 0;
        }

        /**
         * Returns the number of key-value pairs in this symbol table.
         *
         * @return the number of key-value pairs in this symbol table
         */
        public int size() {
            return size(root);
        }

        // return number of key-value pairs in BST rooted at x
        private int size(Node x) {
            if (x == null) return 0;
            else return x.size;
        }

        /**
         * Does this symbol table contain the given key?
         *
         * @param key the key
         * @return {@code true} if this symbol table contains {@code key} and
         * {@code false} otherwise
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public boolean contains(Key key) {
            if (key == null) throw new IllegalArgumentException("argument to contains() is null");
            return get(key) != null;
        }

        /**
         * Returns the value associated with the given key.
         *
         * @param key the key
         * @return the value associated with the given key if the key is in the symbol table
         * and {@code null} if the key is not in the symbol table
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public Value get(Key key) {
            return get(root, key);
        }

        private Value get(Node x, Key key) {
            if (key == null) throw new IllegalArgumentException("calls get() with a null key");
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) return get(x.left, key);
            else if (cmp > 0) return get(x.right, key);
            else return x.val;
        }

    }
}
