/**
 * An array-based generic list.
 * @param <T> The type of the list
 * @author Ziyu Cheng
 */
public class ArrayDeque<T> {
    /** The array that holds the things we want to store. */
    private T[] items;
    /**
     * Size is the number of items stored in the array list.
     * Head is the start index of the items we have stored before.
     * Tail is the ending index.
     * Invariant: tail - head == size, and it's no less than 0.
     */
    private int size, head, tail;

    /**
     * The constructor creates the new empty list of length 8,
     * in which head and tail are the two marks of the array.
     */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        head = tail = 4;
    }

    /**
     * The constructor creates the new empty list of arbitrary length,
     * in which head and tail are the two marks of the array.
     * @param length of the items.length of the new list
     */
    public ArrayDeque(int length) {
        items = (T[]) new Object[length];
        size = 0;
        head = tail = length / 2 - 1;
    }

    /**
     * Constructor creates a deep copy of 'other' list.
     * @param other the list we want to copy
     */
    public ArrayDeque(ArrayDeque<T> other) {
        items = (T[]) new Object[other.items.length];
        head = other.head;
        tail = other.tail;
        size = other.size;
        for (int i = 0; i < size; i++) {
            items[head + i] = other.get(i);
        }
    }

    /** Return the size of the array list. */
    public int size() {
        return size;
    }

    /**
     * Extend the internal array at the left hand side. The length after
     *  extension doubles. The head and tail increase by items.length.
     */
    private void leftExtend() {
        T[] a = (T[]) new Object[2 * items.length];
        System.arraycopy(items, head, a, head + items.length, size);
        head += items.length;
        tail += items.length;
        items = a;
    }

    /**
     * Extend the internal array at the right hand side. The length
     *  after extension doubles. The head and tail are unchanged.
     */
    private void rightExtend() {
        T[] a = (T[]) new Object[2 * items.length];
        System.arraycopy(items, head, a, head, size);
        items = a;
    }

    /**
     * Shrink the internal array at the left hand side. Assume that the head
     * is bigger than half of the items.length. After shrink, the head and tail
     * decrease by half of the original items.length.
     */
    private void leftShrink() {
        T[] a = (T[]) new Object[items.length / 2];
        System.arraycopy(items, head, a, head - items.length / 2, size);
        head -= items.length / 2;
        tail -= items.length / 2;
        items = a;
    }

    /**
     * Shrink the internal array at the right hand side. Assume that the tail
     * is less than half of the item.length. After shrink, the head and tail
     * are unchanged.
     */
    private void rightShrink() {
        T[] a = (T[]) new Object[items.length / 2];
        System.arraycopy(items, head, a, head, size);
        items = a;
    }

    /** Return true if the array list is empty. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Print the array list. */
    public void printDeque() {
        System.out.println("The array list is as follows: ");
        for (int i = head; i < tail; i++) {
            System.out.print(items[i]);
        }
        System.out.println();
    }

    /**
     * Find the index-th item in the array and return it.
     * @param index of item we want
     * @return the index-th item
     */
    public T get(int index) {
        return items[head + index];
    }

    /**
     * Add a new item at the head of the list. If the head is the left end
     *  of the array, then left extend the array and insert.
     * @param item to be added
     */
    public void addFirst(T item) {
        if (head == 0) {
            leftExtend();
        }
        head--;
        size++;
        items[head] = item;
    }

    /**
     * Add a new item at the end of the list. If the tail is the right end
     *  of the array, then right extend the array and insert.
     * @param item
     */
    public void addLast(T item) {
        if (tail == items.length) {
            rightExtend();
        }
        items[tail] = item;
        tail++;
        size++;
    }

    /**
     * Remove the first item of the array and return it. If all the items lies
     * in the right half of the array, then left shrink the array. Finally,
     * head and tail decrease by items.length/2.
     * @return the first item
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T first = items[head];
        head++;
        size--;
        if (head >= items.length / 2 && items.length > 8) {
            leftShrink();
        }
        return first;
    }

    /**
     * Remove the last item of the array and return it. If all the items lies
     * in the left half of the array, then right shrink the array. However,
     * head and tail are unchanged.
     * @return the first item
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last = items[tail - 1];
        tail--;
        size--;
        if (tail <= items.length / 2 && items.length > 8) {
            rightShrink();
        }
        return last;
    }
}
