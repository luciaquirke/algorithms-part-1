import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] fractionOpenSites;
    private final double trials;
    private final int gridSize;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int t) {
        validateInput(n, t);
        trials = t;
        gridSize = n;
        fractionOpenSites = new double[t];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            int[] blockedSites = StdRandom.permutation(n * n);
            int j = 0;
            while (!percolation.percolates()) {
                int blockedSiteIndex = blockedSites[j];
                int row = row(blockedSiteIndex);
                int col = col(blockedSiteIndex);
                percolation.open(row, col);
                j++;
            }
            fractionOpenSites[i] = ((double) percolation.numberOfOpenSites() / (n * n));
        }
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            throw new IllegalArgumentException("Not enough arguments");
        }

        if (args[0] == null || args[1] == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);
        double mean = percolationStats.mean();
        double stddev = percolationStats.stddev();
        double confidenceLo = percolationStats.confidenceLo();
        double confidenceHi = percolationStats.confidenceHi();

        String[] msg = {
                "mean", "stddev", "95% confidence interval"
        };
        String[] items = {
                String.valueOf(mean), String.valueOf(stddev),
                ("[" + confidenceLo + ", " + confidenceHi + "]")
        };
        for (int i = 0; i < msg.length; i++) {
            System.out.printf("%-23s = %s \n", msg[i], items[i]);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractionOpenSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (fractionOpenSites.length <= 1) {
            return 0;
        }
        return StdStats.stddev(fractionOpenSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();

        return mean - ((CONFIDENCE_95 * stddev) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();

        return mean + ((CONFIDENCE_95 * stddev) / Math.sqrt(trials));
    }

    private void validateInput(int n, int t) {
        if (n < 1 || t < 1) {
            throw new IllegalArgumentException(
                    "n and t must each be greater than 0. n received: " + n + ". t received: " + t +
                            ".");
        }
    }

    private int row(int i) {
        return (i / gridSize) + 1;
    }

    private int col(int i) {
        return (i % gridSize) + 1;
    }
}
