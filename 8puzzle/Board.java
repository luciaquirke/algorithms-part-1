/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

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
                    int goalRow = ((goalValue - 1) / dimension) + 1;
                    int goalCol = ((goalValue - 1) % dimension) + 1;
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
        if (y == this) return true;
        if (y == null) return false;
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
        int index = 0;
        int row = 0;
        int col = 0;

        int goalValue = 1;
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                if (tiles[i][j] == 0) {
                    index = goalValue;
                    row = i;
                    col = j;
                }
                goalValue++;
            }
        }

        Queue<Board> neighbors = new Queue<>();

        if (row != 1) {
            int leftTile = index - dimension;
            neighbors.enqueue(swap(index, leftTile));
        }
        if (row != dimension) {
            int rightTile = index + dimension;
            neighbors.enqueue(swap(index, rightTile));
        }
        if (col != 1) {
            int topTile = index - 1;
            neighbors.enqueue(swap(index, topTile));
        }
        if (col != dimension) {
            int bottomTile = index + 1;
            neighbors.enqueue(swap(index, bottomTile));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (this.dimension < 2) {
            throw new Error("no twins! dimension is less than 2");
        }

        int first = 0;
        int second = 0;

        int goalValue = 1;
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                if (tiles[i][j] != 0) {
                    if (first == 0) {
                        first = goalValue;
                    }
                    else if (second == 0) {
                        second = goalValue;
                    }
                    else {
                        break;
                    }

                }
                goalValue++;
            }
        }

        if (first == 0 || second == 0) {
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

    private Board swap(int first, int second) {
        int firstRow = ((first - 1) / dimension) + 1;
        int firstCol = ((first - 1) % dimension) + 1;
        int secondRow = ((second - 1) / dimension) + 1;
        int secondCol = ((second - 1) % dimension) + 1;

        Board newBoard = cloneBoard();
        int temp = newBoard.tiles[firstRow][firstCol];
        newBoard.tiles[firstRow][firstCol] = newBoard.tiles[secondRow][secondCol];
        newBoard.tiles[secondRow][secondCol] = temp;
        return newBoard;
    }

    private Board cloneBoard() {
        int[][] newBoardArray = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                newBoardArray[i][j] = tiles[i + 1][j + 1];
            }
        }

        return new Board(newBoardArray);
    }
}

