package synthesizer;
/**
 * The purpose of AbstractBoundedQueue will be to simply provide
 * a protected fillCount and capacity variable that all subclasses
 * will inherit, as well as so called “getter” methods capacity()
 * and fillCount() that return capacity and fillCount, respectively.
 * This saves a tiny amount of work for future implementations
 * like ArrayRingBuffer.java
 * @param <T> type of items
 * @author Ziyu Cheng
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    /** Number of items and the capacity of the queue. */
    protected int fillCount, capacity;

    /** Return size of the buffer. */
    public int capacity() {
        return capacity;
    }
    /** Return number of the items currently in the buffer. */
    public int fillCount() {
        return fillCount;
    }
}
