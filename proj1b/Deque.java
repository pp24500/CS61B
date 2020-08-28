/**
 * The deque interface contains functions shared by
 * array deque and linked list deque.
 * @param <T> type of the element in the deque
 * @author Ziyu Cheng
 */

public interface Deque<T> {
    /** Returns true if the deque is empty. */
    default boolean isEmpty() {
        return size() == 0;
    }

    /** Returns the size of the deque. */
    int size();

    /**
     * Adds an item of type T to the front of the deque.
     * @param item the new element to be added
     */
    void addFirst(T item);

    /**
     * Adds an item of type T to the back of the deque.
     * @param item the new element to be added
     */
    void addLast(T item);

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line. */
    void printDeque();

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.*/
    T removeFirst();

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    T removeLast();

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     * @param index location of item we want
     * @return the item we want
     */
    T get(int index);
}
