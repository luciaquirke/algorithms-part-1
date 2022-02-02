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

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<Node> minPQ = new MinPQ<>();
        MinPQ<Node> twinMinPQ = new MinPQ<>();

        if (initial == null) {
            throw new IllegalArgumentException("Initial board is null");
        }

        Node currentNode = new Node(null, initial);
        Node twinCurrentNode = new Node(null, initial.twin());

        // modify to do the board and its twin in lockstep
        while (!currentNode.getBoard().isGoal() && !twinCurrentNode.getBoard().isGoal()) {
            Iterable<Board> neighbors = currentNode.getBoard().neighbors();
            Iterable<Board> twinNeighbors = twinCurrentNode.getBoard().neighbors();
            Node copyCurrentNode = currentNode;
            Node copyTwinCurrentNode = twinCurrentNode;
            neighbors.forEach(neighbor -> {
                if (copyCurrentNode.getPrevious() == null || !copyCurrentNode.getPrevious()
                                                                             .getBoard()
                                                                             .equals(neighbor)) {
                    minPQ.insert(new Node(copyCurrentNode, neighbor));
                }
            });
            twinNeighbors.forEach(neighbor -> {
                if (copyTwinCurrentNode.getPrevious() == null || !copyTwinCurrentNode.getPrevious()
                                                                                     .getBoard()
                                                                                     .equals(neighbor)) {
                    twinMinPQ.insert(new Node(copyTwinCurrentNode, neighbor));
                }
            });
            currentNode = minPQ.delMin();
            twinCurrentNode = twinMinPQ.delMin();
        }

        lastNode = currentNode.getBoard().isGoal() ? currentNode : null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return lastNode != null;
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
            boards.push(node.getBoard());
            node = node.getPrevious();
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
        private final Board board;
        private final Node previous;
        private final int priority;

        public Node(Node previous, Board board) {
            this.previous = previous;
            this.moves = previous == null ? 0 : previous.moves + 1;
            this.board = board;
            this.priority = this.getBoard().manhattan() + this.moves;
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

        public Board getBoard() {
            return board;
        }

        public Node getPrevious() {
            return previous;
        }
    }
}
