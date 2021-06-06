package byog.lab6;

import byog.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        String str = "";
        for (int i = 1; i <= n; i++) {
            int j = RandomUtils.uniform(rand, CHARACTERS.length);
            str = str.concat(Character.toString(CHARACTERS[j]));
        }
        return str;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        //StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(0.5, 0.5, s);
        if (!gameOver) {
            StdDraw.text(0.5, 0.9, "Round: " + round + "   Watch!");
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) throws InterruptedException {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        try {
            for (int i = 0; i < letters.length(); i++) {
                drawFrame(letters.substring(i, i + 1));
                TimeUnit.SECONDS.sleep(1);
                StdDraw.clear(Color.BLACK);
                round++;
                if (i != letters.length() - 1) {
                    TimeUnit.MILLISECONDS.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String inputString = "";
        for (int i = 1; i <= n; i++) {
            if (StdDraw.hasNextKeyTyped()) {
                char letter = StdDraw.nextKeyTyped();
                inputString = inputString.concat(Character.toString(letter));
            }
        }
        return inputString;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        String randomString = generateRandomString(round);

        gameOver = false;
        playerTurn = false;
        //TODO: Establish Game loop
    }

}
