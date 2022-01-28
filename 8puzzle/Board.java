/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board {
    private final int[][] board;
    private final int dimension;
    private Board goalBoard;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        dimension = tiles.length;
        board = new int[dimension + 1][dimension + 1];
        for (int i = 0; i < dimension; i++) {
            System.arraycopy(tiles[i], 0, board[i + 1], 1, dimension);
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder boardRepresentation = new StringBuilder();
        boardRepresentation.append(dimension);
        boardRepresentation.append('\n');

        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                boardRepresentation.append(board[i][j]);
                boardRepresentation.append(' ');
            }
            boardRepresentation.append('\n');
        }
        return boardRepresentation.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                if (board[i][j] != 0 && board[i][j] != getGoalBoard().board[i][j]) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                if (board[i][j] != 0 && board[i][j] != getGoalBoard().board[i][j]) {
                    // Finds the current tile's location on the goal board
                    GoalTile goalTile = new GoalTile(board[i][j]);
                    manhattan += Math.abs(goalTile.row - i);
                    manhattan += Math.abs(goalTile.col - j);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.equals(getGoalBoard());
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y.getClass() != this.getClass())
            return false;
        if (this.dimension != ((Board) y).dimension)
            return false;
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                if (this.board[i][j] != ((Board) y).board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Tile emptyTile = new Tile(0);
        Queue<Board> neighbors = new Queue<>();

        if (emptyTile.row != 1) {
            Tile leftTile = new Tile(emptyTile.row - 1, emptyTile.col);
            neighbors.enqueue(swap(emptyTile, leftTile));
        }
        if (emptyTile.row != dimension) {
            Tile rightTile = new Tile(emptyTile.row + 1, emptyTile.col);
            neighbors.enqueue(swap(emptyTile, rightTile));
        }
        if (emptyTile.col != 1) {
            Tile topTile = new Tile(emptyTile.row, emptyTile.col - 1);
            neighbors.enqueue(swap(emptyTile, topTile));
        }
        if (emptyTile.row != dimension) {
            Tile bottomTile = new Tile(emptyTile.row, emptyTile.col + 1);
            neighbors.enqueue(swap(emptyTile, bottomTile));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (this.dimension < 2) {
            throw new Error("no twins! dimension is less than 2");
        }

        Tile first = null;
        Tile second = null;

        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                if (board[i][j] != 0) {
                    if (first == null) {
                        first = new Tile(i, j);
                    }
                    else if (second == null) {
                        second = new Tile(i, j);
                    }
                    else {
                        break;
                    }

                }
            }
        }

        if (first == null || second == null) {
            throw new Error("couldn't find two tiles");
        }

        return swap(first, second);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] testArray = { { 0, 1 }, { 2, 3 } };
        Board testBoard = new Board(testArray);
        System.out.println(testBoard.toString());
        System.out.println("Hamming distance: " + testBoard.hamming());
        System.out.println("Manhattan distance: " + testBoard.manhattan());
        System.out.println("Checking if board equals reference board: " + testBoard.isGoal());
    }

    private class GoalTile {
        public final int row;
        public final int col;
        public final int i;

        // i, row, and col are all 1-indexed
        public GoalTile(int i) {
            this.row = ((i - 1) / dimension) + 1;
            this.col = ((i - 1) % dimension) + 1;
            this.i = i;
        }

        public GoalTile(int row, int col) {
            this.row = row;
            this.col = col;
            this.i = ((row - 1) * dimension) + col;
        }
    }

    // holds conversion information for tile indices.
    private class Tile {
        public int row;
        public int col;
        public int goalValue;
        public int value;

        public Tile(int value) {
            for (int i = 1; i <= dimension; i++) {
                for (int j = 1; j <= dimension; j++) {
                    if (board[i][j] == value) {
                        this.row = i;
                        this.col = j;
                        this.goalValue = (this.row - 1) * dimension + this.col;
                        this.value = value;
                    }
                    break;
                }
            }
        }

        public Tile(int row, int col) {
            this.row = row;
            this.col = col;
            this.goalValue = (this.row - 1) * dimension + this.col;
            this.value = board[row][col];
        }

    }

    private Board getGoalBoard() {
        if (goalBoard != null) {
            return goalBoard;
        }

        int[][] goalBoardArray = new int[dimension + 1][dimension + 1];
        int tileNumber = 1;
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                goalBoardArray[i][j] = tileNumber;
                tileNumber += 1;
            }
        }
        goalBoardArray[dimension][dimension] = 0;

        goalBoard = new Board(goalBoardArray);
        return goalBoard;
    }

    private Board swap(Tile first, Tile second) {
        Board newBoard = cloneBoard();
        newBoard.board[first.row][first.col] = second.value;
        newBoard.board[second.row][second.col] = first.value;
        return newBoard;
    }

    private Board cloneBoard() {
        int[][] newBoardArray = Arrays.stream(this.board).map(int[]::clone).toArray(int[][]::new);
        return new Board(newBoardArray);
    }
}

