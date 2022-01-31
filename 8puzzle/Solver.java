/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final Node lastNode;
    private final boolean isSolvable;
    private final MinPQ<Node> minPQ = new MinPQ<>();
    private final MinPQ<Node> twinMinPQ = new MinPQ<>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board is null");
        }

        Node currentNode = new Node(null, initial);
        Node twinCurrentNode = new Node(null, initial.twin());

        // modify to do the board and its twin in lockstep
        while (!currentNode.board.isGoal() && !twinCurrentNode.board.isGoal()) {
            Iterable<Board> neighbors = currentNode.board.neighbors();
            Iterable<Board> twinNeighbors = twinCurrentNode.board.neighbors();
            Node copyCurrentNode = currentNode;
            Node copyTwinCurrentNode = twinCurrentNode;
            neighbors.forEach(neighbor -> {
                if (copyCurrentNode.previous == null || !copyCurrentNode.previous.board
                        .equals(neighbor)) {
                    minPQ.insert(new Node(copyCurrentNode, neighbor));
                }
            });
            twinNeighbors.forEach(neighbor -> {
                if (copyTwinCurrentNode.previous == null || !copyTwinCurrentNode.previous.board
                        .equals(neighbor)) {
                    twinMinPQ.insert(new Node(copyTwinCurrentNode, neighbor));
                }
            });
            currentNode = minPQ.delMin();
            twinCurrentNode = twinMinPQ.delMin();
        }

        isSolvable = currentNode.board.isGoal();
        if (isSolvable()) {
            lastNode = currentNode;
        }
        else {
            lastNode = null;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return lastNode == null ? -1 : lastNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        Stack<Board> boards = new Stack<>();
        Node node = lastNode;

        while (node != null) {
            boards.push(node.board);
            node = node.previous;
        }

        return boards;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();

        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class Node implements Comparable<Node> {
        public final int moves;
        public final Board board;
        public final Node previous;
        private final int priority;

        public Node(Node previous, Board board) {
            this.previous = previous;
            this.moves = previous == null ? 1 : previous.moves + 1;
            this.board = board;
            this.priority = this.board.manhattan() + this.moves;
        }

        public int compareTo(Node that) {
            if (priority > that.priority) {
                return 1;
            }
            else if (priority == that.priority) {
                return 0;
            }
            return -1;
        }
    }
}
