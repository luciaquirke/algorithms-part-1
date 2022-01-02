import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] isOpenGrid;
    private final WeightedQuickUnionUF percolationGrid;
    private final WeightedQuickUnionUF isFullGrid;
    private final int n;
    private int numberOfOpenSites;

    // creates n-by-n grid with all sites initially blocked (value of -1)
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "Percolation grid size must be greater than 0.");
        }
        this.n = n;
        numberOfOpenSites = 0;
        // last two indices represent the virtual sites
        isOpenGrid = new boolean[(n * n) + 2];
        percolationGrid = new WeightedQuickUnionUF((n * n) + 2);
        isFullGrid = new WeightedQuickUnionUF((n * n) + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);
        System.out
                .println("Grid percolates - false: " + percolation.percolates());
        System.out.println(
                "Site is full - false: " + percolation.isFull(2, 1));

        percolation.open(1, 2);
        System.out.println("Top row open site is open - true: " + percolation.isOpen(1, 2));
        System.out.println("Top row open site is full - true: " + percolation.isFull(1, 2));

        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(4, 1);

        System.out.println("Grid should percolate - true: " + percolation.percolates());
    }

    // opens a site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = siteIndex(row, col);

        // do nothing if already open
        if (isOpenGrid[index]) {
            return;
        }
        isOpenGrid[index] = true;
        numberOfOpenSites++;

        if (row == 1) {
            percolationGrid.union((n * n), index);
            isFullGrid.union((n * n), index);
        }
        if (row == n) {
            percolationGrid.union((n * n) + 1, index);
        }
        unionAdjacentSites(index, row, col);

    }

    private void unionAdjacentSites(int index, int row, int col) {
        int[] adjacentSiteIndices = {
                leftSiteIndex(index, col), rightSiteIndex(index, col), bottomSiteIndex(index, row),
                topSiteIndex(index, row)
        };

        for (int i = 0; i < adjacentSiteIndices.length; i++) {
            if (adjacentSiteIndices[i] != -1 && isOpenGrid[adjacentSiteIndices[i]]) {
                percolationGrid.union(index, adjacentSiteIndices[i]);
                isFullGrid.union(index, adjacentSiteIndices[i]);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = siteIndex(row, col);
        return isOpenGrid[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = siteIndex(row, col);
        return isFullGrid.find(index) == isFullGrid.find(n * n);
    }

    // does the system percolate?
    public boolean percolates() {
        return percolationGrid.find(n * n) == percolationGrid
                .find(n * n + 1);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    private int siteIndex(int row, int col) {
        int index = ((row - 1) * n) + col - 1;
        validateIndex(index);
        return index;
    }

    private int leftSiteIndex(int i, int col) {
        if (col != 1) {
            return i - 1;
        }
        return -1;
    }

    private int rightSiteIndex(int i, int col) {
        if (col != n) {
            return i + 1;
        }
        else {
            return -1;
        }
    }

    private int topSiteIndex(int i, int row) {
        if (row != n) {
            return i + n;
        }
        return -1;
    }

    private int bottomSiteIndex(int i, int row) {
        if (row != 1) {
            return i - n;
        }
        return -1;
    }

    private void validateIndex(int index) {
        int maxIndex = (n * n) - 1;
        if (index < 0 || index > maxIndex) {
            throw new IllegalArgumentException(
                    "Rows and columns must be within the range (1, n).");
        }
    }
}


