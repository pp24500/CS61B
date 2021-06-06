package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hallway implements Space {
    private final int x;
    private final int y;
    private final int length;
    private final Door[] doors = new Door[2];
    private final TETile[][] myWorld;
    private final int direction;
    public final static int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    /**
     * Create a new Hallway, while 1 or 2 or 3 doors are also created.
     * @param x base x
     * @param y base y
     * @param l length
     * @param direction determines the direction to extend
     */
    public Hallway(int x, int y, int l, int direction, TETile[][] world) {
        this.x = x;
        this.y = y;
        length = l;
        myWorld = world;
        this.direction = direction;

        switch (direction) {
            case UP -> goUp();
            case RIGHT -> goRight();
            case DOWN -> goDown();
            case LEFT -> goLeft();
            default -> throw new IllegalArgumentException("Illegal direction!");
        }
    }

    /**
     * If the hallway length is bigger than 2, then it is possible to branch from the middle side door.
     * If not, there is branch only at the end side of the hallway.
     * @return true if it has side door
     */
    private boolean hasSideDoor() {
        return length >= 3;
    }

    /** When the hallway is upwards, the tiles are set from base to above. */
    private void goUp() {
        for (int i = y; i < y + length; i++) {
            myWorld[x][i] = Tileset.FLOOR;
        }

        doors[0] = new Door(x, y + length - 1);
        if (length < 3) {
            doors[1] = null;
        } else {
            Random random = new Random();
            doors[1] = new Door(x, RandomUtils.uniform(random, y + 1, y + length - 1));
        }
    }

    /** When the hallway is rightwards, the tiles are set from base to right. */
    private void goRight() {
        for (int i = x; i < x + length; i++) {
            myWorld[i][y] = Tileset.FLOOR;
        }

        doors[0] = new Door(x + length - 1, y);
        if (length < 3) {
            doors[1] = null;
        } else {
            Random random = new Random();
            doors[1] = new Door(RandomUtils.uniform(random, x + 1, x + length - 1), y);
        }
    }

    /** When the hallway is downwards, the tiles are set from base to below. */
    private void goDown() {
        for (int i = y; i > y - length; i--) {
            myWorld[x][i] = Tileset.FLOOR;
        }

        doors[0] = new Door(x, y - length + 1);
        if (length < 3) {
            doors[1] = null;
        } else {
            Random random = new Random();
            doors[1] = new Door(x, RandomUtils.uniform(random, y - length + 1, y - 1));
        }
    }

    /** When the hallway is leftwards, the tiles are set from base to left. */
    private void goLeft() {
        for (int i = x; i > x - length; i--) {
            myWorld[i][y] = Tileset.FLOOR;
        }

        doors[0] = new Door(x - length + 1, y);
        if (length < 3) {
            doors[1] = null;
        } else {
            Random random = new Random();
            doors[1] = new Door(RandomUtils.uniform(random, x - length + 1, x - 1), y);
        }
    }

    /**
     * Wrap the hallway with surrounding walls.
     */
    public void wrapWalls() {
        switch (direction) {
            case UP -> wrapUp();
            case RIGHT -> wrapRight();
            case DOWN -> wrapDown();
            case LEFT -> wrapLeft();
            default -> throw new IllegalArgumentException("Illegal direction!");
        }
    }

    /** When the hallway is upwards, create the left and right walls. */
    private void wrapUp() {
        for (int j = y; j < y + length; j++) {
            if (myWorld[x - 1][j] == Tileset.NOTHING) {
                myWorld[x - 1][j] = Tileset.WALL;
            }
            if (myWorld[x + 1][j] == Tileset.NOTHING) {
                myWorld[x + 1][j] = Tileset.WALL;
            }
        }
        for (int i = x - 1; i <= x + 1; i++) {
            if (myWorld[i][y + length] == Tileset.NOTHING) {
                myWorld[i][y + length] = Tileset.WALL;
            }
            if (myWorld[i][y - 1] == Tileset.NOTHING) {
                myWorld[i][y - 1] = Tileset.WALL;
            }
        }
    }

    /** When the hallway is downwards, create the left and right walls. */
    private void wrapDown() {
        for (int j = y; j > y - length; j--) {
            if (myWorld[x - 1][j] == Tileset.NOTHING) {
                myWorld[x - 1][j] = Tileset.WALL;
            }
            if (myWorld[x + 1][j] == Tileset.NOTHING) {
                myWorld[x + 1][j] = Tileset.WALL;
            }
        }
        for (int i = x - 1; i <= x + 1; i++) {
            if (myWorld[i][y + 1] == Tileset.NOTHING) {
                myWorld[i][y + 1] = Tileset.WALL;
            }
            if (myWorld[i][y - length] == Tileset.NOTHING) {
                myWorld[i][y - length] = Tileset.WALL;
            }
        }
    }

    /** When the hallway is leftwards, create the above and below walls. */
    private void wrapLeft() {
        for (int i = x; i > x - length; i--) {
            if (myWorld[i][y + 1] == Tileset.NOTHING) {
                myWorld[i][y + 1] = Tileset.WALL;
            }
            if (myWorld[i][y - 1] == Tileset.NOTHING) {
                myWorld[i][y - 1] = Tileset.WALL;
            }
        }
        for (int j = y - 1; j <= y + 1; j++) {
            if (myWorld[x - length][j] == Tileset.NOTHING) {
                myWorld[x - length][j] = Tileset.WALL;
            }
            if (myWorld[x + 1][j] == Tileset.NOTHING) {
                myWorld[x + 1][j] = Tileset.WALL;
            }
        }
    }

    /** When the hallway is leftwards, create the above and below walls. */
    private void wrapRight() {
        for (int i = x; i < x + length; i++) {
            if (myWorld[i][y + 1] == Tileset.NOTHING) {
                myWorld[i][y + 1] = Tileset.WALL;
            }
            if (myWorld[i][y - 1] == Tileset.NOTHING) {
                myWorld[i][y - 1] = Tileset.WALL;
            }
        }
        for (int j = y - 1; j <= y + 1; j++) {
            if (myWorld[x - 1][j] == Tileset.NOTHING) {
                myWorld[x - 1][j] = Tileset.WALL;
            }
            if (myWorld[x + length][j] == Tileset.NOTHING) {
                myWorld[x + length][j] = Tileset.WALL;
            }
        }
    }

    /**
     * Return the distance to the top edge of the world.
     */
    public int distToTop() {
        if (direction == UP) {
            return Game.HEIGHT - y - length;
        } else {
            return Game.HEIGHT - 1 - y;
        }
    }

    /**
     * Return the distance to the bottom edge of the world.
     */
    public int distToBottom() {
        if (direction == DOWN) {
            return y - length + 1;
        } else {
            return y;
        }
    }

    /**
     * Return the distance to the left edge of the world.
     */
    public int distToLeft() {
        if (direction == LEFT) {
            return x - length + 1;
        } else {
            return x;
        }
    }

    /**
     * Return the distance to the right edge of the world.
     */
    public int distToRight() {
        if (direction == RIGHT) {
            return Game.WIDTH - x - length;
        } else {
            return Game.WIDTH - 1 - x;
        }
    }

    /**
     * Return the door at the end of the hallway, regardless of direction.
     */
    public Door endDoor() {
        return doors[0];
    }

    /**
     * Return the side door of the hallway, regardless of direction.
     */
    public Door sideDoor() {
        return doors[1];
    }

    @Override
    public List<Space> branch() {
        //TODO: generations works, but randomness is not introduced
        ArrayList<Space> branches = new ArrayList<>();
        //Random random = new Random(194623);

        switch (direction) {
            case UP -> branchUp(branches);
            case RIGHT -> branchRight(branches);
            case DOWN -> branchDown(branches);
            case LEFT -> branchLeft(branches);
            default -> throw new IllegalArgumentException("Illegal direction!");
        }
        return branches;
    }

    /**
     * Helper function to probe whether there is enough space for new space generation.
     * @param door starting door
     * @param dir direction of extension
     * @param extensionLength size of space which will be newly generated (can be Room width or height, or Hallway length)
     * @param start point of Space, including WALLs, so it is not the edge of the new Space extended, it is the WALL!
     * @param end point of Space, including WALLs
     * @return true if it is OK to generate a new Space
     */
    private boolean branchEnable(Door door, int dir, int extensionLength, int start, int end) {
        switch (dir) {
            case UP -> {
                if (start < 0 || end > Game.WIDTH - 1) {
                    return false;
                }

                for (int i = start; i <= end; i++) {
                    for (int j = door.Y() + 2; j <= door.Y() + extensionLength; j++) {
                        if (myWorld[i][j] != Tileset.NOTHING) {
                            return false;
                        }
                    }
                }
                return true;
            }
            case DOWN -> {
                if (start < 1 || end >= Game.WIDTH) {
                    return false;
                }

                for (int i = start; i <= end; i++) {
                    for (int j = door.Y() - extensionLength; j <= door.Y() - 2; j++) {
                        if (myWorld[i][j] != Tileset.NOTHING) {
                            return false;
                        }
                    }
                }
                return true;
            }
            case RIGHT -> {
                if (start < 1 || end >= Game.HEIGHT) {
                    return false;
                }

                for (int i = door.X() + 2; i <= door.X() + extensionLength; i++) {
                    for (int j = start; j <= end; j++) {
                        if (myWorld[i][j] != Tileset.NOTHING) {
                            return false;
                        }
                    }
                }
                return true;
            }
            case LEFT -> {
                if (start < 1 || end >= Game.HEIGHT) {
                    return false;
                }

                for (int i = door.X() - extensionLength; i <= door.X() - 2; i++) {
                    for (int j = start; j <= end; j++) {
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

    /**
     * Branch the hallway which is upwards. A room is generated on the top door of the upwards hallway,
     * while hallways (left or right-wards) are generated at the side door.
     * @param list where new Spaces come in
     */
    private void branchUp(List<Space> list) {
        if (distToTop() >= 3) {
            Random random = new Random(946263);
            int topExtension;
            do {
                topExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.3));
            } while (topExtension >= distToTop());

            int extensionWidth = 2 + Math.abs(RandomUtils.poisson(random, 2));
            int start = Math.max(0,
                    RandomUtils.uniform(random, endDoor().X() - extensionWidth, endDoor().X()));
            int end = start + extensionWidth + 1;
            if (branchEnable(endDoor(), UP, topExtension, start, end) &&
                    RandomUtils.bernoulli(random, 1.0 - topExtension * 1.0 / distToTop())) {
                Room topRoom  = new Room(start + 1, endDoor().Y() + 1, extensionWidth, topExtension, myWorld);
                topRoom.setDoor(DOWN, endDoor().X(), endDoor().Y() + 1);
                list.add(topRoom);
            }
        }

        if (hasSideDoor() && distToLeft() >= 3) {
            Random random = new Random(2643132);
            int leftExtension;
            do {
                leftExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.3));
            } while (leftExtension >= distToLeft());

            if (branchEnable(sideDoor(), LEFT, leftExtension, sideDoor().Y() - 1, sideDoor().Y() + 1) &&
                    RandomUtils.bernoulli(random, 1.0 - leftExtension * 1.0 / distToLeft())) {
                list.add(new Hallway(sideDoor().X() - 1, sideDoor().Y(), leftExtension, LEFT, myWorld));
            }
        }

        if (hasSideDoor() && distToRight() >= 3) {
            Random random = new Random(5927165);
            int rightExtension;
            do {
                rightExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.3));
            } while (rightExtension >= distToRight());

            if (branchEnable(sideDoor(), RIGHT, rightExtension, sideDoor().Y() - 1, sideDoor().Y() + 1) &&
                    RandomUtils.bernoulli(random, 1.0 - rightExtension * 1.0 / distToRight())) {
                list.add(new Hallway(sideDoor().X() + 1, sideDoor().Y(), rightExtension, RIGHT, myWorld));
            }
        }
    }

    /**
     * Branch the hallway which is downwards. A room is generated on the bottom door of the downwards hallway,
     * while hallways (left or right-wards) are generated at the side door.
     * @param list where new spaces come in
     */
    private void branchDown(List<Space> list) {
        if (distToBottom() >= 3) {
            Random random = new Random(3916274);
            int bottomExtension;
            do {
                bottomExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.3));
            } while (bottomExtension >= distToBottom());

            int extensionWidth = 2 + Math.abs(RandomUtils.poisson(random, 2));
            int start = Math.max(0,
                    RandomUtils.uniform(random, endDoor().X() - extensionWidth, endDoor().X()));
            int end = start + extensionWidth + 1;
            if (branchEnable(endDoor(), DOWN, bottomExtension, start, end) &&
                    RandomUtils.bernoulli(random, 1.0 - bottomExtension * 1.0 / distToBottom())) {
                Room bottomRoom  = new Room(start + 1, endDoor().Y() - bottomExtension, extensionWidth, bottomExtension, myWorld);
                bottomRoom.setDoor(UP, endDoor().X(), endDoor().Y() - 1);
                list.add(bottomRoom);
            }
        }

        if (hasSideDoor() && distToLeft() >= 3) {
            Random random = new Random(2643132);
            int leftExtension;
            do {
                leftExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.2));
            } while (leftExtension >= distToLeft());

            if (branchEnable(sideDoor(), LEFT, leftExtension, sideDoor().Y() - 1, sideDoor().Y() + 1) &&
                    RandomUtils.bernoulli(random, 1.0 - leftExtension * 1.0 / distToLeft())) {
                list.add(new Hallway(sideDoor().X() - 1, sideDoor().Y(), leftExtension, LEFT, myWorld));
            }
        }

        if (hasSideDoor() && distToRight() >= 3) {
            Random random = new Random(5927165);
            int rightExtension;
            do {
                rightExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.2));
            } while (rightExtension >= distToLeft());

            if (branchEnable(sideDoor(), RIGHT, rightExtension, sideDoor().Y() - 1, sideDoor().Y() + 1) &&
                    RandomUtils.bernoulli(random, 1.0 - rightExtension * 1.0 / distToRight())) {
                list.add(new Hallway(sideDoor().X() + 1, sideDoor().Y(), rightExtension, RIGHT, myWorld));
            }
        }
    }

    /**
     * Branch the hallway which is leftwards. A room is generated on the left end door of the leftwards hallway,
     * while hallways (up or down-wards) are generated at the side door.
     * @param list where new spaces come in
     */
    private void branchLeft(List<Space> list) {
        if (distToLeft() >= 3) {
            Random random = new Random(2749631);
            int leftExtension;
            do {
                leftExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.2));
            } while (leftExtension >= distToLeft());

            int extensionHeight = 2 + Math.abs(RandomUtils.poisson(random, 2));
            int start = Math.max(0,
                    RandomUtils.uniform(random, endDoor().Y() - extensionHeight, endDoor().Y()));
            int end = start + extensionHeight + 1;
            if (branchEnable(endDoor(), LEFT, leftExtension, start, end) &&
                    RandomUtils.bernoulli(random, 1.0 - leftExtension * 1.0 / distToLeft())) {
                Room bottomRoom  = new Room(endDoor().X() - leftExtension, start + 1, leftExtension, extensionHeight, myWorld);
                bottomRoom.setDoor(RIGHT, endDoor().X() - 1, endDoor().Y());
                list.add(bottomRoom);
            }
        }

        if (hasSideDoor() && distToTop() >= 3) {
            Random random = new Random(8461375);
            int topExtension;
            do {
                topExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.2));
            } while (topExtension >= distToTop());

            if (branchEnable(sideDoor(), UP, topExtension, sideDoor().X() - 1, sideDoor().X() + 1) &&
                    RandomUtils.bernoulli(random, 1.0 - topExtension * 1.0 / distToTop())) {
                list.add(new Hallway(sideDoor().X(), sideDoor().Y() + 1, topExtension, UP, myWorld));
            }
        }

        if (hasSideDoor() && distToBottom() >= 3) {
            Random random = new Random(7894561);
            int bottomExtension;
            do {
                bottomExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.2));
            } while (bottomExtension >= distToBottom());

            if (branchEnable(sideDoor(), DOWN, bottomExtension, sideDoor().X() - 1, sideDoor().X() + 1) &&
                    RandomUtils.bernoulli(random, 1.0 - bottomExtension * 1.0 / distToBottom())) {
                list.add(new Hallway(sideDoor().X(), sideDoor().Y() - 1, bottomExtension, DOWN, myWorld));
            }
        }
    }

    /**
     * Branch the hallway which is rightwards. A room is generated on the right end door of the rightwards hallway,
     * while hallways (up or down-wards) are generated at the side door.
     * @param list where new spaces come in
     */
    private void branchRight(List<Space> list) {
        if (distToRight() >= 3) {
            Random random = new Random(69451873);
            int rightExtension;
            do {
                rightExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.2));
            } while (rightExtension >= distToRight());

            int extensionHeight = 2 + Math.abs(RandomUtils.poisson(random, 2));
            int start = Math.max(0,
                    RandomUtils.uniform(random, endDoor().Y() - extensionHeight, endDoor().Y()));
            int end = start + extensionHeight + 1;
            if (branchEnable(endDoor(), RIGHT, rightExtension, start, end) &&
                    RandomUtils.bernoulli(random, 1.0 - rightExtension * 1.0 / distToRight())) {
                Room rightRoom  = new Room(endDoor().X() + 1, start + 1, rightExtension, extensionHeight, myWorld);
                rightRoom.setDoor(LEFT, endDoor().X() + 1, endDoor().Y());
                list.add(rightRoom);
            }
        }

        if (hasSideDoor() && distToTop() >= 3) {
            Random random = new Random(8461375);
            int topExtension;
            do {
                topExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.2));
            } while (topExtension >= distToTop());

            if (branchEnable(sideDoor(), UP, topExtension, sideDoor().X() - 1, sideDoor().X() + 1) &&
                    RandomUtils.bernoulli(random, 1.0 - topExtension * 1.0 / distToTop())) {
                list.add(new Hallway(sideDoor().X(), sideDoor().Y() + 1, topExtension, UP, myWorld));
            }
        }

        if (hasSideDoor() && distToBottom() >= 3) {
            Random random = new Random(7894561);
            int bottomExtension;
            do {
                bottomExtension = 1 + Math.abs(RandomUtils.geometric(random, 0.2));
            } while (bottomExtension >= distToBottom());

            if (branchEnable(sideDoor(), DOWN, bottomExtension, sideDoor().X() - 1, sideDoor().X() + 1) &&
                    RandomUtils.bernoulli(random, 1.0 - bottomExtension * 1.0 / distToBottom())) {
                list.add(new Hallway(sideDoor().X(), sideDoor().Y() - 1, bottomExtension, DOWN, myWorld));
            }
        }
    }
}
