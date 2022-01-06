import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // number of items in array used to maintain correct array size
    private int n = 0;
    // index used to add items in a reasonable order
    private int index = 0;
    private Object[] queue = new Object[1];
    private int[] permutation = StdRandom.permutation(queue.length);
    private int permutationIndex = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Enqueued item is null");

        if (index == queue.length) {
            // index = 0; // doesn't work because some queue elements could still have items in them
            resetToLength(queue.length);
        }
        queue[index] = item;
        index++;
        n++;

        if (n * 2 >= queue.length) {
            resetToLength(queue.length * 2);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (size() == 0) throw new NoSuchElementException("Queue is empty");

        Item item = (Item) queue[permutation[permutationIndex]];
        queue[permutation[permutationIndex]] = null;
        --n;
        permutationIndex++;


        if (n * 4 <= queue.length) {
            resetToLength(queue.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size() == 0) throw new NoSuchElementException("Queue is empty");
        return (Item) queue[StdRandom.uniform(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("Testing...");

        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        System.out.println("Initialised: " + randomizedQueue);
        System.out.println("Is empty?: " + randomizedQueue.isEmpty());
        System.out.println("Size: " + randomizedQueue.size());
        System.out.println("Adding items...");

        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(3);
        System.out.println("Number of elements: " + randomizedQueue.size());
        // System.out.println("Array size: " + randomizedQueue.queue.length);
        System.out.println("Sample: " + randomizedQueue.sample());

        Iterator<Integer> iterator = randomizedQueue.iterator();
        System.out.println("Iterator: " + iterator);
        System.out.println("Iterator has next: " + iterator.hasNext());
        System.out.println("Iterator next: " + iterator.next());

        int item1 = randomizedQueue.dequeue();
        int item2 = randomizedQueue.dequeue();
        System.out.println("Number of elements: " + randomizedQueue.size());
        // System.out.println("Array size: " + randomizedQueue.queue.length);
        System.out.println("Is item 1 randomly selected? " + item1);
        System.out.println("Is item 2 randomly selected? " + item2);
    }

    private void resetToLength(int length) {
        Object[] newQueue = new Object[length];
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] != null) {
                newQueue[i] = queue[i];
            }
        }
        queue = newQueue;
        permutation = StdRandom.permutation(queue.length);
        permutationIndex = 0;
        index = n;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int permutationIndex = 0;
        int[] permutation = StdRandom.permutation(queue.length);

        public boolean hasNext() {
            return queue[permutation[permutationIndex]] != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Queue is empty");
            }
            Item item = (Item) queue[permutation[permutationIndex]];
            permutationIndex++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supported");
        }
    }
}
