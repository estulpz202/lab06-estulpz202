package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;


/**
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some example test cases.
 * Write your own unit tests to test against IntQueue interface with specification testing method 
 * using mQueue = new LinkedIntQueue();
 * 
 * 2. 
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        // mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        // Write your own unit test
        mQueue.enqueue(7);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        // Write your own unit test
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        // Write your own unit test
        mQueue.enqueue(7);
        mQueue.enqueue(77);
        assertEquals(mQueue.peek(), Integer.valueOf(7));
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        // Write your own unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(mQueue.dequeue(), Integer.valueOf(testList.get(i)));
        }
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }

    @Test
    public void testClear() {
        // Enqueue elements
        for (int i = 1; i <= 8; i++) {
            mQueue.enqueue(i);
        }

        // Call clear() on queue
        mQueue.clear();

        // Verify queue is empty
        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testSimpleEnsureCapacity() {
        // Enqueue elements beyond the initial capacity (10) to trigger resizing
        for (int i = 1; i <= 15; i++) {
            mQueue.enqueue(i);
        }

        // Ensure size is updated correctly
        assertEquals(15, mQueue.size());

        // Ensure elements are dequeued in the correct order
        for (int i = 1; i <= 15; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        // Ensure queue is empty after all elements are dequeued
        assertTrue(mQueue.isEmpty());
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testComplexEnsureCapacity() {
        // Step 1: Fill the queue to capacity to prepare for wrap-around.
        for (int i = 1; i <= 10; i++) {
            mQueue.enqueue(i);
        }

        // Step 2: Dequeue some elements to shift the `head` index forward.
        for (int i = 1; i <= 5; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        // Step 3: Enqueue more elements to wrap around the internal array.
        for (int i = 11; i <= 20; i++) {
            mQueue.enqueue(i);
        }

        // Step 4: Check that all elements are still accessible in the correct order.
        for (int i = 6; i <= 20; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        // Step 5: Ensure queue is empty after all elements are dequeued.
        assertTrue(mQueue.isEmpty());
        assertNull(mQueue.dequeue());
    }
}
