package synthesizer;

import javax.swing.text.html.HTMLDocument;
/**
 * The interface of bounded queue, which allows add at the end,
 * and remove at the front, with fixed capacity.
 * @param <T> the type of item
 * @author Ziyu Cheng
 */
public interface BoundedQueue<T> extends Iterable<T> {

    /** Return size of the buffer. */
    int capacity();

    /** Return number of the items currently in the buffer. */
    int fillCount();

    /**
     * Add item x to the end.
     * @param x item to be added
     */
    void enqueue(T x);

    /** Delete and return item from the front. */
    T dequeue();

    /** Return (but do not delete) item from the front. */
    T peek();

    /** Return true if the buffer is empty.*/
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /** Return true if the buffer is full.*/
    default boolean isFull() {
        return fillCount() == capacity();
    }
}
