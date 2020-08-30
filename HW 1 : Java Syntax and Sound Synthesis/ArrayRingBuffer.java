package synthesizer;
import java.util.Iterator;

/**
 * The array based bounded queue to store the information.
 * @param <T> the type parameter
 * @author Ziyu Cheng
 */
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T>  {
    /** Index for the next dequeue or peek. */
    private int first;
    /** Index for the next enqueue. */
    private int last;
    /** Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     * @param capacity we set
     */

    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        fillCount = 0;
        first = 0;
        last = 0;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     * @param x the iterm we want to add
     */
    public void enqueue(T x) throws RuntimeException {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow!");
        }
        rb[last] = x;
        fillCount++;
        last++;
        if (last >= capacity) {
            last = last % capacity;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer and return it. If the buffer
     * is empty, then throw new RuntimeException("Ring buffer underflow").
     * Exceptions covered Monday.
     */
    public T dequeue() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow!");
        }
        T output = rb[first];
        rb[first] = null;
        fillCount--;
        first++;
        if (first >= capacity) {
            first = first % capacity;
        }
        return output;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        return rb[first];
    }

    /**
     * Iterator constructor.
     * @return the iterator for ArraryRingBuffer
     */
    @Override
    public Iterator<T> iterator() {
        return new BufferIterator();
    }

    /**
     * The nested class for implementing iterator interface.
     */
    private class BufferIterator implements Iterator<T> {
        /** The internal pointer for the scanning iterator. */
        private int ptr;

        /** Constructor of our own iterator. */
        BufferIterator() {
            ptr = first;
        }

        /** Return true if iterator has next item. */
        @Override
        public boolean hasNext() {
            return ptr != last;
        }

        /** Return current item of iterator and advance iterator one step. */
        @Override
        public T next() {
            T getItem = rb[ptr];
            ptr = (ptr + 1) % capacity;
            return getItem;
        }
    }
}
