package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    private int[][] board, goal;
    private int N;
    private static final int BLANK = 0;

    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j. Assume that the constructor
     * receives an N-by-N array containing the N * N integers between 0 and
     * N * N − 1, where 0 represents the blank square.
     * @param tiles parameter
     */
    public Board(int[][] tiles) {
        N = tiles.length;
        board = new int[N][N];
        goal = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = tiles[i][j];
                goal[i][j] = N * i + j + 1;
            }
        }
        goal[N - 1][N - 1] = BLANK;
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank).
     * @param i
     * @param j
     */
    public int tileAt(int i, int j) {
        return board[i][j];
    }

    /** Returns the size of the current board. */
    public int size() {
        return N;
    }

    /**
     * Returns the neighbors of the current board.
     * @source Josh Hug http://joshh.ug/neighbors.html
     */
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /** Return Hamming estimate described below. */
    public int hamming() {
        int differs = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != goal[i][j]) {
                    differs++;
                }
            }
        }
        return differs;
    }

    /** Return Manhattan estimate described below. */
    public int manhattan() {
        int distance = 0;
        int m, iCorrect, jCorrect;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != BLANK) {
                    m = board[i][j] - 1;
                    iCorrect = m / N;
                    jCorrect = m % N;
                    distance = distance + Math.abs(i - iCorrect)
                            + Math.abs(j - jCorrect);
                } else {
                    distance = distance + 2 * N - 2 - i - j;
                }
            }
        }
        return distance;
    }

    /**
     * Estimated distance to goal. This method should simply return the
     * results of manhattan() when submitted to Gradescope.
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * Returns true if this board's tile values are the same position as y's.
     * @param y
     */
    public boolean equals(Object y) {
        if (getClass() != y.getClass()) {
            return false;
        }

        Board yBoard = (Board) y;
        if (size() != yBoard.size()) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) != yBoard.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
