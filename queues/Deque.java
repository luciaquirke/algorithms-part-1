import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n = 0;

    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        System.out.println("is empty called: " + (first == null));
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateItem(item);
        Node newFirst = new Node();
        newFirst.item = item;

        if (isEmpty()) {
            first = newFirst;
            last = newFirst;
        } else {
            newFirst.next = first;
            first.previous = newFirst;
            first = newFirst;
        }

        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        validateItem(item);
        Node newLast = new Node();
        newLast.item = item;

        if (isEmpty()) {
            first = newLast;
        } else {
            last.next = newLast;
            newLast.previous = last;
        }
        last = newLast;

        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item item = first.item;

        if (n == 1) {
            last = null;
            first = null;
        } else {
            first = first.next;
            first.previous = null;
        }

        --n;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item item = last.item;

        if (n == 1) {
            last = null;
            first = null;
        } else {
            last = last.previous;
            last.next = null;
        }

        --n;
        return item;
    }

    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("Testing...");
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println("Adding items...");
        deque.addFirst(1);
        int item1 = deque.removeLast();
        System.out.println("Is last.item == 1? " + item1);
        deque.addLast(2);
        int item2 = deque.removeFirst();
        System.out.println("Is first.item == 2? " + item2);
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    /* Returns an iterator that iterates through the deque from front to back */
    private class LinkedListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supported");
        }
    }

    private void validateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Added items must not be null");
        }
    }
}
