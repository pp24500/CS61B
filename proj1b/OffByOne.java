public class OffByOne implements CharacterComparator {

    /**
     * Test whether the ASCII codes of two chars differ by only 1;
     * @param x one char
     * @param y the other char
     * @return true if two differ by 1, false otherwise
     */
    @Override
    public boolean equalChars(char x, char y) {
        int i = x - y;
        if (i * i == 1) {
            return true;
        } else {
            return false;
        }
    }
}
