/**
 * The char equality: chars of which ASCII codes differ by n
 * are defined to be equal.
 * @author Ziyu Cheng
 */
public class OffByN implements CharacterComparator {
    /** The index we define the char equality. */
    private int n;

    /**
     * Constructor with parameter n.
     * @param N if chars differ by N, they are equal.
     */
    public OffByN(int N) {
        n = N;
    }

    /**
     * Test whether the ASCII codes of two chars differ by only n.
     * @param x one char
     * @param y the other char
     * @return ture if they differ by n
     */
    @Override
    public boolean equalChars(char x, char y) {
        return (x - y) * (x - y) == n * n;
    }
}
