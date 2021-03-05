package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V> {
    private BSTNode KeFinTree;
    private int size;
    private HashSet<K> keySet = new HashSet<>();

    private void BSTMap() {
        size = 0;
        keySet = new HashSet<>();
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
        keySet.add(key);
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
        for (K item : keySet()) {
            System.out.println(item);
        }
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        V returnStuff = null;
        if (!containsKey(key)) {
            return null;
        }
        BSTNode cur;
        BSTNode prev = KeFinTree;
        if (prev.left.key.compareTo(key) > 0) {
            cur = prev.left;
        }else {
            cur = prev.right;
        }
        while (cur != null && !cur.key.equals(key)) {
            if (cur.key.compareTo(key) > 0) {
                prev = cur;
                cur = cur.left;
            }else {
                prev = cur;
                cur = cur.right;
            }
        }
        if (cur == null) {
            return null;
        }
        if (cur.key.equals(key)) {
            returnStuff = (V) cur.value;
            if (childNumber(cur) == 0) {
                cur.key = null;
                cur.value = null;
            }else if (childNumber(cur) == 1) {
                if (prev.left.key.equals(key)) {
                    if (cur.left == null) {
                        prev.left = cur.right;
                    }else {
                        prev.left = cur.left;
                    }
                }else {
                    if (cur.left == null) {
                        prev.right = cur.right;
                    }else {
                        prev.right = cur.left;
                    }
                }
            }else {
                BSTNode rightBiggest = KeFinTree;
                while (rightBiggest.right != null) {
                    rightBiggest = rightBiggest.right;
                }
                cur.key = rightBiggest.key;
                cur.value = rightBiggest.value;
                rightBiggest.key = null;
                rightBiggest.value = null;
            }
        }
        return returnStuff;
    }

    @Override
    public V remove(K key, V value) {
        V returnStuff = null;
        if (!containsKey(key)) {
            return null;
        }
        BSTNode cur;
        BSTNode prev = KeFinTree;
        if (prev.left.key.compareTo(key) > 0) {
            cur = prev.left;
        }else {
            cur = prev.right;
        }
        while (cur != null && !cur.key.equals(key)) {
            if (cur.key.equals(key) && cur.value.equals(value)) {
                returnStuff = (V) cur.value;
                if (childNumber(cur) == 0) {
                    cur.key = null;
                    cur.value = null;
                }else if (childNumber(cur) == 1) {
                    if (prev.left.key.equals(key)) {
                        if (cur.left == null) {
                            prev.left = cur.right;
                        }else {
                            prev.left = cur.left;
                        }
                    }else {
                        if (cur.left == null) {
                            prev.right = cur.right;
                        }else {
                            prev.right = cur.left;
                        }
                    }
                }else {
                    BSTNode rightBiggest = KeFinTree;
                    while (rightBiggest.right != null) {
                        rightBiggest = rightBiggest.right;
                    }
                    cur.key = rightBiggest.key;
                    cur.value = rightBiggest.value;
                    rightBiggest.key = null;
                    rightBiggest.value = null;
                }
            }else if (cur.key.compareTo(key) > 0) {
                prev = cur;
                cur = cur.left;
            }else {
                prev = cur;
                cur = cur.right;
            }
        }
        return returnStuff;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    private int childNumber(BSTNode p) {
        if (p.left == null && p.right == null) {
            return 0;
        }else if (p.left != null && p.right != null) {
            return 2;
        }else {
            return 1;
        }
    }

    private class BSTNode<K extends Comparable<K>, Value> {
        private K key;
        private Value value;
        private BSTNode left;
        private BSTNode right;
        private BSTNode() {}
        private BSTNode(K key, Value value, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

}
