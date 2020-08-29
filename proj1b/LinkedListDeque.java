/**
 * The generic LinkedListDeque class for the project 1a.
 * @author Ziyu
 * */
public class LinkedListDeque<T> implements Deque<T> {
    /**
     * The nested class for list node. It is private, containing
     * information for the list nodes.
     */
    private class ListNode {
        T item;
        ListNode prev, next;

        ListNode(T i, ListNode p, ListNode n) {
            item = i;
            prev = p;
            next = n;
        }

        /**
         * Get the nth element list node which locates n units far
         * from the reference node. It is recursive.
         * @param position the relative distance to the reference ListNode
         * @return the target list node we find
         */
        ListNode nth(int position) {
            if (position < 0) {
                return null;
            } else if (position == 0) {
                return this;
            } else {
                return next.nth(position - 1);
            }
        }
    }

    /** The size of the linked list and it is private. */
    private int size;
    /** The sentinel of the list. Sentinel is a virtual node. */
    private ListNode sentinel;
    /**
     * Adds an item of type T to the front of the deque.
     * @param item to be inserted at the head
     */
    public void addFirst(T item) {
        sentinel.next = new ListNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size++;
    }

    /**
     * Adds an item of type T to the back of the deque.
     * @param item to be inserted at the tail
     */
    public void addLast(T item) {
        sentinel.prev = new ListNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size++;
    }

    /**
     * Get the size of the list.
     * @return the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        ListNode ptr = sentinel.next;
        while (ptr != sentinel) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * @return the first element
     */
    public T removeFirst() {
        if (isEmpty()) {
            System.out.println("Cannot remove element from an empty list");
            return null;
        } else {
            T first = sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size--;
            return first;
        }
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     * @return the last element
     */
    public T removeLast() {
        if (isEmpty()) {
            System.out.println("Cannot remove element from an empty list");
            return null;
        } else {
            T last = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size--;
            return last;
        }
    }

    /**
     * Gets the item at the given index, where 0 is the front,1 is
     * the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     * @param index of the item to get
     * @return the index-th element of the list
     */
    public T get(int index) {
        if (isEmpty()) {
            System.out.println("Cannot access element from an empty list!");
            return null;
        } else if (index >= size()) {
            System.out.println("Index out of range!");
            return null;
        } else {
            ListNode ptr = sentinel;
            while (index >= 0) {
                ptr = ptr.next;
                index--;
            }
            return ptr.item;
        }
    }

    /**
     * Get the index-th element in a recursive method with help
     * of the nth() method in the ListNode class.
     * @param index the number of element the user wants
     * @return the index-th element of the list
     */
    public T getRecursive(int index) {
        if (isEmpty()) {
            System.out.println("Cannot access element from an empty list!");
            return null;
        } else if (index >= size()) {
            System.out.println("Index out of range!");
            return null;
        } else {
            return sentinel.next.nth(index).item;
        }
    }

    /**
     * Creates an empty linked list deque.
     */
    public LinkedListDeque() {
        size = 0;
        sentinel = new ListNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    /**
     * Creates a deep copy of 'other'.
     * @param other to be copied
     */
    public LinkedListDeque(LinkedListDeque<T> other) {
        size = 0;
        sentinel = new ListNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

        for (int i = 0; i < other.size(); i++) {
            addLast(other.get(i));
        }
    }
}
