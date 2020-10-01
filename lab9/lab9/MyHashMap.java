package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /** The default size of bucket array. */
    private static final int DEFAULT_SIZE = 16;
    /** The maximum value of load factor. */
    private static final double MAX_LF = 0.75;

    /** Implementation of hash table, an array. */
    private ArrayMap<K, V>[] buckets;
    /** The number of items stored in the hash map. */
    private int size;

    /** Return the load factor of present table. */
    private double loadFactor() {
        return (double) size / buckets.length;
    }

    /** Create a new hash map with default size 16. */
    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     * @param key to be hashed
     * @return the index after hashing
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (!buckets[hash(key)].containsKey(key)) {
            return null;
        } else {
            return buckets[hash(key)].get(key);
        }
    }

    /**
     * Return new hash code index after resizing.
     * @param key to be hashed
     */
    private int newHash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = 2 * buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /**
     * Helper function to move elements into new resized buckets.
     * @param newBuckets new buckets to accept elements from old buckets
     * @param key of element
     * @param value of element
     */
    private void newPut(ArrayMap[] newBuckets, K key, V value) {
        newBuckets[newHash(key)].put(key, value);
    }

    /**
     * When load factor is greater than 0.75, then double the buckets array.
     * Move all key value pairs into the new buckets array.
     */
    private void resize() {
        ArrayMap<K, V>[] newBuckets = new ArrayMap[2 * buckets.length];
        for (int i = 0; i < newBuckets.length; i++) {
            newBuckets[i] = new ArrayMap<>();
        }

        for (int i = 0; i < buckets.length; i++) {
            Iterator<K> iter = buckets[i].keySet().iterator();
            while (iter.hasNext()) {
                K k = iter.next();
                newPut(newBuckets, k, get(k));
            }
        }

        buckets = newBuckets;
    }

    /** Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        int sizeBefore = buckets[hash(key)].size();
        buckets[hash(key)].put(key, value);
        size += buckets[hash(key)].size() - sizeBefore;
        if (loadFactor() > MAX_LF) {
            resize();
        }
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /** EVERYTHING BELOW THIS LINE IS OPTIONAL */

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        ArrayMap<K, V> whole = new ArrayMap();
        for (int i = 0; i < buckets.length; i++) {
            whole.keySet().addAll(buckets[i].keySet());
        }
        return whole.keySet();
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        int index = hash(key);
        if (get(key) == null) {
            return null;
        } else {
            size--;
            return buckets[index].remove(key);
        }
    }

    /* Removes the entry for the specified key only if it is currently
       mapped to the specified value. Not required for this lab.
       If you don't implement this, throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        int index = hash(key);
        if (get(key) != value) {
            return null;
        } else {
            size--;
            return buckets[index].remove(key, value);
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
