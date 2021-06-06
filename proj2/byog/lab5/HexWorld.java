package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    /** The world the hexagon locates at. */
    private TETile[][] world;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Create a new Hex world full of 'NOTHING'.
     * @param w the width of the world
     * @param h the height of the world
     */
    public HexWorld(int w, int h) {
        world = new TETile[w][h];
        for (int x = 0; x < w; x += 1) {
            for (int y = 0; y < h; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void addHexagon(int x, int y, int size, TETile tile) {
        for (int i = x; i < x + size - 1; i++) {
            for (int j = y + i - x; j >= y - i + x - 1; j--) {
                world[i][j] = tile;
            }
        }
        for (int i = x + size - 1; i < x + 2 * size - 1; i++) {
            for (int j = y + size - 1; j >= y - size; j--) {
                world[i][j] = tile;
            }
        }
        for (int i = x + 2 * size - 1; i < x + 3 * size - 2; i++) {
            for (int j = y + x + 3 * size - 3 - i; j >= y - x - 3 * size + 2 + i; j--) {
                world[i][j] = tile;
            }
        }
    }

    /** Picks a RANDOM tile.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(8);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.FLOOR;
            case 5: return Tileset.SAND;
            case 6: return Tileset.TREE;
            case 7: return Tileset.WATER;
            default: return Tileset.GRASS;
        }
    }

    public static void main (String[] args) throws IOException {
        System.out.print("Please input the size of hexagon you want: ");
        BufferedReader keybd = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(keybd.readLine());

        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(11 * size, 10 * size + 2);

        /** Left and right 3 units blank, up and down 1 unit blank. */
        HexWorld hexW = new HexWorld(11 * size, 10 * size + 2);
        hexW.addHexagon(2, 5 * size, 2, randomTile());
        hexW.addHexagon(8, 5 * size, 2, randomTile());
        hexW.addHexagon(14, 5 * size, 2, randomTile());
        ter.renderFrame(hexW.world);
    }
}
