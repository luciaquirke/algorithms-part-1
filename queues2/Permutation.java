import edu.princeton.cs.algs4.StdIn;

import java.util.NoSuchElementException;

public class Permutation {
    public static void main(String[] args) {
        if (args == null || args[0] == null) {
            throw new NoSuchElementException("");
        }
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            randomizedQueue.enqueue(input);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(randomizedQueue.dequeue());
        }
    }
}
