package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room implements Space {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private Door[] doors;
    private final TETile[][] myWorld;

    public Room(int x, int y, int w, int h, TETile [][] world) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        doors = new Door[4];
        myWorld = world;

        Random random = new Random(16492);
        //** door[0] at the bottom. *//*
        doors[Hallway.DOWN] = new Door(RandomUtils.uniform(random, x, x + width), y);
        //** door[1] at the right. *//*
        doors[Hallway.RIGHT] = new Door(x + width - 1, RandomUtils.uniform(random, y, y + height));
        //** door[2] at the top. *//*
        doors[Hallway.UP] = new Door(RandomUtils.uniform(random, x, x + width), y + height - 1);
        //** door[1] at the left. *//*
        doors[Hallway.LEFT] = new Door(x, RandomUtils.uniform(random, y, y + height));

        drawRoom();
    }

    private void drawRoom() {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                myWorld[i][j] = Tileset.FLOOR;
            }
        }
    }

    /**
     * Wrap the room FLOOR with a string of WALLs. Only fill the NOTHING
     * tiles with WALLs, i.e. when a tile beside the room FLOOR is already
     * occupied, it will not be filled with a WALL.
     */
    public void wrapWalls() {
        for (int i = x - 1; i <= x + width; i++) {
            if (myWorld[i][y - 1] == Tileset.NOTHING) {
                myWorld[i][y - 1] = Tileset.WALL;
            }
            if (myWorld[i][y + height] == Tileset.NOTHING) {
                myWorld[i][y + height] = Tileset.WALL;
            }
        }
        for (int j = y - 1; j <= y + height; j++) {
            if (myWorld[x - 1][j] == Tileset.NOTHING) {
                myWorld[x - 1][j] = Tileset.WALL;
            }
            if (myWorld[x + width][j] == Tileset.NOTHING) {
                myWorld[x + width][j] = Tileset.WALL;
            }
        }
    }

    /**
     * Reset door place when generating a new room from a hallway.
     * @param direction location of door to be reset
     * @param x coordinate
     * @param y coordinate
     */
    public void setDoor(int direction, int x, int y) {
        doors[direction].set(x, y);
    }

    public int distToTop() {
        return Game.HEIGHT - y - height;
    }

    public int distToBottom() {
        return y;
    }

    public int distToLeft() {
        return x;
    }

    public int distToRight() {
        return Game.WIDTH - x - width;
    }

    public Door bottomDoor() {
        return doors[Hallway.DOWN];
    }

    public Door rightDoor() {
        return doors[Hallway.RIGHT];
    }

    public Door topDoor() {
        return doors[Hallway.UP];
    }

    public Door leftDoor() {
        return doors[Hallway.LEFT];
    }

    @Override
    public List<Space> branch() {
        //TODO: generation works, but lack of randomness.
        ArrayList<Space> branches = new ArrayList<>();
        Random random = new Random(901845);
        if (distToBottom() >= 3) {
            int bottomExtension;
            do {
                bottomExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.23));
            } while (bottomExtension >= distToBottom());

            if (branchEnable(bottomDoor(), Hallway.DOWN, bottomExtension) &&
                    RandomUtils.bernoulli(random, 1.0 - (double) bottomExtension / distToBottom())) {
                branches.add(new Hallway(bottomDoor().X(), bottomDoor().Y(),
                        bottomExtension, Hallway.DOWN, myWorld));
            }
        }

        if (distToRight() >= 3) {
            int rightExtension;
            do {
                rightExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.2));
            } while (rightExtension >= distToRight());

            if (branchEnable(rightDoor(), Hallway.RIGHT, rightExtension) &&
                    RandomUtils.bernoulli(random, 1.0 - (double) rightExtension / distToRight())) {
                branches.add(new Hallway(rightDoor().X(), rightDoor().Y(),
                        rightExtension, Hallway.RIGHT, myWorld));
            }
        }

        if (distToTop() >= 3) {
            int topExtension;
            do {
                topExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.2));
            } while (topExtension >= distToTop());

            if (branchEnable(topDoor(), Hallway.UP, topExtension) &&
                    RandomUtils.bernoulli(random, 1.0 - (double) topExtension / distToTop())) {
                branches.add(new Hallway(bottomDoor().X(), bottomDoor().Y(),
                        topExtension, Hallway.UP, myWorld));
            }
        }

        if (distToLeft() >= 3) {
            int leftExtension;
            do {
                leftExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.2));
            } while (leftExtension >= distToLeft());

            if (branchEnable(leftDoor(), Hallway.LEFT, leftExtension) &&
                    RandomUtils.bernoulli(random, 1.0 - (double) leftExtension / distToLeft())) {
                branches.add(new Hallway(leftDoor().X(), leftDoor().Y(),
                        leftExtension, Hallway.LEFT, myWorld));
            }
        }

        return branches;
    }

    /**
     * Helper function for the branch() method. Probe four possible branch
     * directions for new hallway generation.
     * @param door starting point of probe
     * @param direction of the probe
     * @param length of probe
     * @return true if there is a proper empty space for the new Space creation
     */
    private boolean branchEnable(Door door, int direction, int length) {
        switch (direction) {
            case Hallway.UP -> {
                if (door.X() + 1 >= Game.WIDTH || door.X() - 1 < 0 ||
                        door.Y() + length + 1 >= Game.HEIGHT) {
                    return false;
                }

                for (int i = door.X() - 1; i <= door.X() + 1; i++) {
                    for (int j = door.Y() + 2; j <= door.Y() + length + 1; j++) {
                        if (myWorld[i][j] != Tileset.NOTHING) {
                            return false;
                        }
                    }
                }
                return true;
            }
            case Hallway.RIGHT -> {
                if (door.X() + length + 1 >= Game.WIDTH || door.Y() - 1 < 0 || door.Y() + 1 >= Game.HEIGHT) {
                    return false;
                }

                for (int i = door.X() + 2; i <= door.X() + length + 1; i++) {
                    for (int j = door.Y() - 1; j <= door.Y() + 1; j++) {
                        if (myWorld[i][j] != Tileset.NOTHING) {
                            return false;
                        }
                    }
                }
                return true;
            }
            case Hallway.DOWN -> {
                if (door.X() - 1 < 0 || door.X() + 1 >= Game.WIDTH || door.Y() - length - 1 < 0) {
                    return false;
                }

                for (int i = door.X() - 1; i <= door.X() + 1; i++) {
                    for (int j = door.Y() - length - 1; j <= door.Y() - 2; j++) {
                        if (myWorld[i][j] != Tileset.NOTHING) {
                            return false;
                        }
                    }
                }
                return true;
            }
            case Hallway.LEFT -> {
                if (door.X() - length - 1 < 0 || door.Y() - 1 < 0 || door.Y() + 1 >= Game.HEIGHT) {
                    return false;
                }

                for (int i = door.X() - 1 - length; i <= door.X() - 2; i++) {
                    for (int j = door.Y() - 1; j <= door.Y() + 1; j++) {
                        if (myWorld[i][j] != Tileset.NOTHING) {
                            return false;
                        }
                    }
                }
                return true;
            }
            default -> throw new IllegalArgumentException("Illegal direction!");
        }
    }
}
