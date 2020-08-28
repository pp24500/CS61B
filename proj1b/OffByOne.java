/**
 * The char equality: chars of which ASCII codes differ by 1
 * are defined to be equal.
 * @author Ziyu Cheng
 */
public class OffByOne implements CharacterComparator {

    /**
     * Test whether the ASCII codes of two chars differ by only 1.
     * @param x one char
     * @param y the other char
     * @return true if two differ by 1, false otherwise
     */
    @Override
    public boolean equalChars(char x, char y) {
        return (x - y) * (x - y) == 1;
    }
}
