package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Class of percolation, hw2.
 * @author Ziyu Cheng
 */
public class Percolation {

    /* 2D-array of booleans, indicating that the sites are open or not. */
    private boolean[][] sites;

    /* The disjoint set for indicating connections of sites. */
    private WeightedQuickUnionUF uf, ufNoBottom;

    /* The indices of virtual top and bottom sites which are always open and full. */
    private final static int TOP = 0, BOTTOM = 1;

    /* The side length of square and number of open sites. */
    private int n, opens;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IndexOutOfBoundsException("Size N should be positive!");
        }
        n = N;
        sites = new boolean[n][n]; // sites array does not contain the virtual sites
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sites[i][j] = false;
            }
        }
        uf = new WeightedQuickUnionUF(n * n + 2); // uf contains the virtual sites
        ufNoBottom = new WeightedQuickUnionUF(n * n + 2); // identical uf but BOTTOM will nevered be used
        opens = 0;
    }

    /**
     * Index transform from site to UF, in which top and bottom occupy 0 and 1 while
     * others start from index 2.
     * @param row of site
     * @param col of site
     * @return the corresponding index in the UF
     */
    private int siteToUF(int row, int col) {
        return row * n + col + 2;
    }

    private void unionUp(int row, int col) {
        if (row == 0) {
            uf.union(TOP, siteToUF(row, col));
            ufNoBottom.union(TOP, siteToUF(row, col));
        } else if (sites[row - 1][col]) {
            uf.union(siteToUF(row, col), siteToUF(row - 1, col));
            ufNoBottom.union(siteToUF(row, col), siteToUF(row - 1, col));
        }
    }

    private void unionDown(int row, int col) {
        if (row == n - 1) {
            uf.union(BOTTOM, siteToUF(row, col));
        } else if (sites[row + 1][col]) {
            uf.union(siteToUF(row, col), siteToUF(row + 1, col));
            ufNoBottom.union(siteToUF(row, col), siteToUF(row + 1, col));
        }
    }

    private void unionRight(int row, int col) {
        if (col == n - 1) {
            return;
        } else if (sites[row][col + 1]) {
            uf.union(siteToUF(row, col), siteToUF(row, col + 1));
            ufNoBottom.union(siteToUF(row, col), siteToUF(row, col + 1));
        }
    }

    private void unionLeft(int row, int col) {
        if (col == 0) {
            return;
        } else if (sites[row][col - 1]) {
            uf.union(siteToUF(row, col), siteToUF(row, col - 1));
            ufNoBottom.union(siteToUF(row, col), siteToUF(row, col - 1));
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ( row < 0 || row >= n || col < 0 || col >= n ) {
            throw new IndexOutOfBoundsException("row and col should lie in [0, n - 1]!");
        }
        if (sites[row][col]) {
            return;
        }
        // open the site
        sites[row][col] = true;
        opens++;

        unionUp(row, col);
        unionDown(row, col);
        unionLeft(row, col);
        unionRight(row, col);
        /**
         * Check out the connections with above and below sites.
         * If the top row is opened, it will connect with the TOP virtual site.
         * If the bottom row is opened, it will connect with the BOTTOM virtual site.
         */
        /*if (row == 0) {
            uf.union(TOP, siteToUF(row, col));
            ufNoBottom.union(TOP, siteToUF(row, col));
            if (sites[row + 1][col]) {
                uf.union(siteToUF(row, col), siteToUF(row + 1, col));
                ufNoBottom.union(siteToUF(row, col), siteToUF(row + 1, col));
            }
        } else if (row == n - 1) {
            uf.union(BOTTOM, siteToUF(row, col));
            // notice that we do not union the BOTTOM with site in the UF without BOTTOM
            if (sites[row - 1][col]) {
                uf.union(siteToUF(row, col), siteToUF(row - 1, col));
                ufNoBottom.union(siteToUF(row, col), siteToUF(row - 1, col));
            }
        } else {
            if (sites[row - 1][col]) {
                uf.union(siteToUF(row, col), siteToUF(row - 1, col));
                ufNoBottom.union(siteToUF(row, col), siteToUF(row - 1, col));
            }
            if (sites[row + 1][col]) {
                uf.union(siteToUF(row, col), siteToUF(row + 1, col));
                ufNoBottom.union(siteToUF(row, col), siteToUF(row + 1, col));
            }
        }*/
        // Check out connection with left and right sites.
        /*if (col == 0) {
            if (sites[row][col + 1]) {
                uf.union(siteToUF(row, col), siteToUF(row, col + 1));
                ufNoBottom.union(siteToUF(row, col), siteToUF(row, col + 1));
            }
        } else if (col == n - 1) {
            if (sites[row][col - 1]) {
                uf.union(siteToUF(row, col), siteToUF(row, col - 1));
                ufNoBottom.union(siteToUF(row, col), siteToUF(row, col - 1));
            }
        } else {
            if (sites[row][col + 1]) {
                uf.union(siteToUF(row, col), siteToUF(row, col + 1));
                ufNoBottom.union(siteToUF(row, col), siteToUF(row, col + 1));
            }
            if (sites[row][col - 1]) {
                uf.union(siteToUF(row, col), siteToUF(row, col - 1));
                ufNoBottom.union(siteToUF(row, col), siteToUF(row, col - 1));
            }
        }*/
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ( row < 0 || row >= n || col < 0 || col >= n ) {
            throw new IndexOutOfBoundsException("row and col should lie in [0, n - 1]!");
        }
        return sites[row][col];
    }

    /**
     * Return true if site(row, col) is full, avoid backwash.
     */
    public boolean isFull(int row, int col) {
        if ( row < 0 || row >= n || col < 0 || col >= n ) {
            throw new IndexOutOfBoundsException("row and col should lie in [0, n - 1]!");
        }
        return isOpen(row, col) && ufNoBottom.connected(TOP, siteToUF(row, col));
    }

    /**
     * Return number of open sites in the grid.
     */
    public int numberOfOpenSites() { return opens; }

    /**
     * Return true if the system percolates.
     */
    public boolean percolates() { return uf.connected(TOP, BOTTOM); }

    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation test = new Percolation(3);
        assertTrue(!test.percolates());

        for (int i = 0; i < 3; i++) {
            test.open(i, 0);
        }
        assertEquals(test.numberOfOpenSites(), 3);
        for (int i = 0; i < 3; i++) {
            assertTrue(test.isOpen(i, 0));
        }

        assertTrue(test.isFull(0, 0));
        assertTrue(test.isFull(1, 0));
        assertTrue(test.isFull(2, 0));

        test.open(2,2);
        assertTrue(test.isOpen(2, 2));
        assertTrue(!test.isFull(2, 2));

        test.open(2, 1);
        assertTrue(test.isOpen(2, 1));
        assertTrue(test.isOpen(2, 0));
        assertTrue(test.isOpen(2, 2));

        assertTrue(test.isFull(2, 0));
        assertTrue(test.isFull(2, 1));
        assertTrue(test.isFull(2, 2));

        System.out.println("The number of open sites is: " + test.numberOfOpenSites());
        for (int i = 0; i < 3; i++) {
            test.open(i, 0);
        }
        System.out.println("The number of open sites is: " + test.numberOfOpenSites());
    }
}
