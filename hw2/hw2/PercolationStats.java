package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    //private Percolation[] per; // array of percolations for experiments
    private double[] fraction; // array of fractions of open sites
    //private int N; // size of each percolation

    /**
     * Construct a statistics object within which there is an array of percolation
     * fractions.
     * @param N size of percolation
     * @param T number of experiments
     * @param pf helping class object to construct new percolations
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("N and T should be both positive!");
        }

        fraction = new double[T];

        for (int i = 0; i < T; i++) {
            fraction[i] = (double) calculateThreshold(pf.make(N), N) / (N * N);
        }
    }

    /**
     * Helping method to calculate the fraction of open sites when the
     * percolation just percolates.
     * @param p the given percolation object
     * @return the fraction of open sites for percolating
     */
    private int calculateThreshold(Percolation p, int N) {
        while (!p.percolates()) {
            p.open(StdRandom.uniform(N), StdRandom.uniform(N));
        }
        return p.numberOfOpenSites();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fraction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(fraction.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(fraction.length);
    }

    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(500, 200, new PercolationFactory());
        System.out.println("The mean fraction is: " + test.mean());
        System.out.println("The standard deviation is: " + test.stddev());
        System.out.println("The mean faction is between: " + test.confidenceLow() +
                " and " + test.confidenceHigh());
    }
}
