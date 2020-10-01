package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    /**
     * Private class of tree nodes.
     */
    private class Node {
        /** Key stored in this Node. */
        private K key;
        /** Value stored in this Node. */
        private V value;

        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Constructor of Node class.
         * @param k key of new node
         * @param v value of new node
         * */
        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /** Root node of the tree. */
    private Node root;
    /** The number of key-value pairs in the tree. */
    private int size;

    /** Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     * @param key the key we want to find in the tree
     * @param p root of present tree
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            return p.value;
        } else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null
     * if this map contains no mapping for the key.
     * @param key the key we want to find in the tree
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /**
     * Return a BSTMap rooted in p with (KEY, VALUE) added as
     * a key-value mapping. Or if p is null, it returns a one node
     * BSTMap containing (KEY, VALUE).
     * @param key to be added
     * @param value to be added
     * @param p root of present tree
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        } else if (key.compareTo(p.key) == 0) {
            p.value = value;
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /** EVERYTHING BELOW THIS LINE IS OPTIONAL */

    /**
     * Keyset helper function. Get the keys from the tree rooted at p.
     * @param p the root node of present tree
     * @param set to contain the keys
     */
    private void getKey(Node p, Set<K> set) {
        if (p == null) {
            return;
        } else {
            getKey(p.left, set);
            set.add(p.key);
            getKey(p.right, set);
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> kSet = new TreeSet<>();
        getKey(root, kSet);
        return kSet;
    }

    /**
     * Return the minimum key node of the tree rooted at node p.
     * @param p root
     */
    private Node min(Node p) {
        if (p.left == null) {
            return p;
        } else {
            return min(p.left);
        }
    }

    /**
     * Helper function which deletes the node of minimum key from
     * the tree rooted at node p. Return the root of the tree
     * after deletion.
     * @param p root of present tree
     */
    private Node deleteMin(Node p) {
        if (p.left == null) {
            return p.right;
        } else {
            p.left = deleteMin(p.left);
            return p;
        }
    }

    /**
     * Return the root of BST which has been deleted by
     * the node containing the key.
     * @param key to be deleted
     * @param p root of present tree
     */
    private Node delete(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = delete(key, p.left);
        } else if (cmp > 0) {
            p.right = delete(key, p.right);
        } else {
            size--;
            if (p.right == null) {
                return p.left;
            }
            if (p.left == null) {
                return p.right;
            }
            Node leftmost = min(p.right);
            p.key = leftmost.key;
            p.value = leftmost.value;
            p.right = deleteMin(p.right);
        }
        return p;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V rm = get(key);
        if (rm == null) {
            return null;
        }

        root = delete(key, root);
        return rm;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V rm = get(key);
        if (rm == null || rm != value) {
            return null;
        }

        root = delete(key, root);
        return rm;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
