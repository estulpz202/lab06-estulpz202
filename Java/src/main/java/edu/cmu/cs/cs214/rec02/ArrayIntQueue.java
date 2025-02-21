package edu.cmu.cs.cs214.rec02;

import java.util.Arrays;

/**
 * A resizable-array implementation of the {@link IntQueue} interface. The head of
 * the queue starts out at the head of the array, allowing the queue to grow and
 * shrink in constant time.
 *
 * @author Alex Lockwood
 * @author Ye Lu
 */
public class ArrayIntQueue implements IntQueue {

    /**
     * An array holding this queue's data
     */
    private int[] elementData;

    /**
     * Index of the next dequeue-able value
     */
    private int head;

    /**
     * Current size of queue
     */
    private int size;

    /**
     * The initial size for new instances of ArrayQueue
     */
    private static final int INITIAL_SIZE = 10;

    /**
     * Constructs an empty queue with an initial capacity of ten.
     */
    public ArrayIntQueue() {
        elementData = new int[INITIAL_SIZE];
        head = 0;
        size = 0;
    }

    /** {@inheritDoc} */
    public void clear() {
        Arrays.fill(elementData, 0);
        size = 0;
        head = 0;
    }

    /** {@inheritDoc} */
    public Integer dequeue() {
        if (isEmpty()) {
            return null;
        }
        Integer value = elementData[head];
        head = (head + 1) % elementData.length;
        size--;
        return value;
    }

    /** {@inheritDoc} */
    public boolean enqueue(Integer value) {
        ensureCapacity();
        int tail = (head + size) % elementData.length;
        elementData[tail] = value;
        size++;
        return true;
    }

    /** {@inheritDoc} */
    public boolean isEmpty() {
        return size == 0;
    }

    /** {@inheritDoc} */
    public Integer peek() {
        if (isEmpty()) {
            return null;
        } else {
            return elementData[head];
        }
    }

    /** {@inheritDoc} */
    public int size() {
        return size;
    }

    /**
     * Increases the capacity of this <tt>ArrayIntQueue</tt> instance, if
     * necessary, to ensure that it can hold at least size + 1 elements.
     */
    private void ensureCapacity() {
        // If the queue is full, expand capacity
        if (size == elementData.length) {
            int oldCapacity = elementData.length;  // Store old capacity
            int newCapacity = 2 * oldCapacity + 1; // Increase capacity by 2x + 1
            int[] newData = new int[newCapacity];  // Create new larger array
    
            // Copy elements in correct order, ensuring FIFO remains
            for (int i = 0; i < size; i++) {
                newData[i] = elementData[(head + i) % oldCapacity];
            }
    
            // Update the queue to the new expanded array
            elementData = newData;
            // Reset head since we copied elements in a new contiguous manner
            head = 0;
        }
    }
}
