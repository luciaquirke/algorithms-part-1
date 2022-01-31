/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board {
    private final int[][] tiles;
    private final int dimension;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        dimension = tiles.length;

        this.tiles = new int[dimension + 1][dimension + 1];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.tiles[i + 1][j + 1] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension);
        s.append('\n');

        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append('\n');
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        int goalValue = 1;
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != goalValue) {
                    hamming++;
                }
                goalValue++;
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        int goalValue = 1;
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != goalValue) {
                    // is this wrong?
                    int goalRow = ((goalValue - 1) / dimension) + 1;
                    int goalCol = goalValue % dimension;
                    System.out.println("Goal row: " + goalRow + " Goal col: " + goalCol);
                    manhattan += Math.abs(goalRow - i);
                    manhattan += Math.abs(goalCol - j);
                }
                goalValue++;
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int goalValue = 1;
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                if (i == dimension && j == dimension) {
                    if (this.tiles[i][j] != 0) {
                        return false;
                    }
                }
                else if (this.tiles[i][j] != goalValue) {
                    return false;
                }
                goalValue++;
            }
        }
        return true;
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
                if (this.tiles[i][j] != ((Board) y).tiles[i][j]) {
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
                if (tiles[i][j] != 0) {
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

    // holds conversion information for tile indices.
    private class Tile {
        public int row;
        public int col;
        public int goalValue;
        public int value;

        public Tile(int value) {
            for (int i = 1; i <= dimension; i++) {
                for (int j = 1; j <= dimension; j++) {
                    if (tiles[i][j] == value) {
                        this.row = i;
                        this.col = j;
                        this.goalValue = (this.row - 1) * dimension + this.col;
                        this.value = value;
                        break;
                    }
                }
            }
        }

        public Tile(int row, int col) {
            this.row = row;
            this.col = col;
            this.goalValue = (this.row - 1) * dimension + this.col;
            this.value = tiles[row][col];
        }

    }

    private Board swap(Tile first, Tile second) {
        Board newBoard = cloneBoard();
        newBoard.tiles[first.row][first.col] = second.value;
        newBoard.tiles[second.row][second.col] = first.value;
        return newBoard;
    }

    private Board cloneBoard() {
        int[][] newBoardArray = Arrays.stream(this.tiles).map(int[]::clone).toArray(int[][]::new);
        return new Board(newBoardArray);
    }
}

