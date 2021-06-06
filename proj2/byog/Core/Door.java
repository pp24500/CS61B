package byog.Core;

public class Door {
    private int x, y;

    public Door(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int X() {
        return x;
    }

    public int Y() {
        return y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
