import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N = 0;
    // make private!!
    public Object[] queue = new Object[1];
    int[] permutation = StdRandom.permutation(queue.length);
    int permutationIndex = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Enqueued item is null");
        queue[N] = item;
        N++;
        resize();
    }

    // remove and return a random item
    public Item dequeue() {
        if (size() == 0) throw new NoSuchElementException("Queue is empty");
        while (queue[permutation[permutationIndex]] == null) {
            permutationIndex++;
            if (permutationIndex == size()) permutationIndex = 0;
        }

        Item item = (Item) queue[permutation[permutationIndex]];
        queue[permutation[permutationIndex]] = null;
        --N;
        permutationIndex++;
        resize();
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size() == 0) throw new NoSuchElementException("Queue is empty");
        return (Item) queue[StdRandom.uniform(N)];
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
        System.out.println("Array size: " + randomizedQueue.queue.length);
        System.out.println("Sample: " + randomizedQueue.sample());
        Iterator iterator = randomizedQueue.iterator();
        System.out.println("Iterator: " + iterator);

        System.out.println("Iterator has next: " + iterator.hasNext());
        System.out.println("Iterator next: " + iterator.next());

        int item1 = randomizedQueue.dequeue();
        int item2 = randomizedQueue.dequeue();
        int item3 = randomizedQueue.dequeue();
        int item4 = randomizedQueue.dequeue();
        int item5 = randomizedQueue.dequeue();
        int item6 = randomizedQueue.dequeue();
        int item7 = randomizedQueue.dequeue();

        System.out.println("Number of elements: " + randomizedQueue.size());
        System.out.println("Array size: " + randomizedQueue.queue.length);

        System.out.println("Is item 1 randomly selected? " + item1);
        System.out.println("Is item 2 randomly selected? " + item2);
    }

    private void resize() {
        if (N * 2 >= queue.length) {
            Object[] newQueue = new Object[queue.length * 2];
            System.arraycopy(queue, 0, newQueue, 0, N);
            queue = newQueue;
            permutation = StdRandom.permutation(queue.length);
            permutationIndex = 0;
        }

        if (N * 4 <= queue.length) {
            Object[] newQueue = new Object[queue.length / 2];
            System.arraycopy(queue, 0, newQueue, 0, N);
            queue = newQueue;
            permutation = StdRandom.permutation(queue.length);
            permutationIndex = 0;
        }
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
