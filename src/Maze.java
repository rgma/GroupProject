import java.io.IOException;
import javax.swing.JFrame;

public class Maze {
    static JFrame frame;
    public static String title = "Maze Game";
    public static int width = 1024;
    public static int height = 768;
    Board board;

    public Maze(Position[][] maze) throws IOException {
        frame = new JFrame();
        frame.setTitle(title);
        board = new Board(maze);
        board.init();
        frame.add(board);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}