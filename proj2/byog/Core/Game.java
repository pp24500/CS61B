package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;
import java.util.Random;
import java.lang.Iterable;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        /** Dungeon generation, inspired by BFS algorithm. */
        Random random = new Random();
        Queue<Space> q1 = new Queue<>();
        Queue<Space> q2 = new Queue<>();

        int firstX, firstY, firstW, firstH;

        do {
            firstW = 1 + Math.abs(RandomUtils.poisson(random, 4));
            firstH = 1 + Math.abs(RandomUtils.poisson(random, 2));
        } while (firstW > 10 || firstH > 8);

        firstX = RandomUtils.uniform(random,1, Game.WIDTH - firstW);
        firstY = RandomUtils.uniform(random, 1, Game.HEIGHT - firstH);

        Space first = new Room(firstX, firstY, firstW, firstH, world);
        q1.enqueue(first);
        q2.enqueue(first);
        while (!q1.isEmpty()) {
            Space s = q1.dequeue();
            for (Space adj: s.branch()) {
                q1.enqueue(adj);
                q2.enqueue(adj);
            }
        }

        /** Wrap all the Rooms and Hallways with WALLs. */
        for (Space h : q2) {
            h.wrapWalls();
        }

        Door gate = setGate(world);
        world[gate.X()][gate.Y()] = Tileset.LOCKED_DOOR;

        ter.renderFrame(world);
    }

    /**
     * Set the locked gate for the whole dungeon.
     * @param world we are working on
     * @return a Door object which represents the locked gate
     */
    private Door setGate(TETile[][] world) {
        boolean gateFound = false;
        Door gate = new Door(0, 0);
        Random random = new Random(1000);
        int gateX = RandomUtils.uniform(random, WIDTH / 3, 2 * WIDTH / 3);
        while (!gateFound) {
            for (int j = 1; j < HEIGHT - 1; j++) {
                if (world[gateX][j] == Tileset.WALL) {
                    if ((world[gateX][j + 1] == Tileset.FLOOR && world[gateX][j - 1] == Tileset.NOTHING) ||
                            (world[gateX][j - 1] == Tileset.FLOOR && world[gateX][j + 1] == Tileset.NOTHING)) {
                        gate.set(gateX, j);
                        gateFound = true;
                        break;
                    }
                }
            }
            gateX++;
        }

        return gate;
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }

}