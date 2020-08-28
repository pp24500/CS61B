public class OffByN implements CharacterComparator {
    private int n;

    public OffByN(int N) {
        n = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        int i = x - y;
        if (i * i == n * n) {
            return true;
        } else {
            return false;
        }
    }
}
