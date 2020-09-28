package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

/**
 * Class for percolation statistics, hw2.
 * @author Ziyu Cheng
 */
public class PercolationStats {
    /** Array of fractions of open sites. */
    private double[] fraction;
    /** Constant for confidence coefficient. */
    private static final double CONFIDENCE = 1.96;
    /** Size of percolation plate and the number of experiments. */
    private static final int THICKNESS = 300, EXPERIMENTS = 200;
    /**
     * Construct a statistics object within which there is an
     * array of percolation fractions.
     * @param N size of percolation
     * @param T number of experiments
     * @param pf helping class object to construct new percolations
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException(
                    "N and T should be both positive!");
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
     * @param N the size of percolation
     * @return the fraction of open sites for percolating
     */
    private int calculateThreshold(Percolation p, int N) {
        while (!p.percolates()) {
            p.open(StdRandom.uniform(N), StdRandom.uniform(N));
        }
        return p.numberOfOpenSites();
    }

    /**
     * Return mean value of the percolation thresholds.
     */
    public double mean() {
        return StdStats.mean(fraction);
    }

    /**
     * Return the standard deviation of the threshold values.
     */
    public double stddev() {
        return StdStats.stddev(fraction);
    }

    /**
     * Return the low end of confidence interval.
     */
    public double confidenceLow() {
        return mean() - CONFIDENCE * stddev() / Math.sqrt(fraction.length);
    }

    /**
     * Return the high end of confidence interval.
     */
    public double confidenceHigh() {
        return mean() + CONFIDENCE * stddev() / Math.sqrt(fraction.length);
    }
}
