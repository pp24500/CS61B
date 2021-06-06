package byog.lab6;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;

public class TestStdDraw {
    public static void main(String[] args) {
        //StdDraw.setCanvasSize(600, 600);
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(0.5, 0.5, "Test");
    }
}
