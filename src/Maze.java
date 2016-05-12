import javax.swing.JFrame;

public class Maze {

	static JFrame frame;
    public static String title = "Maze Game";
    public static int width = 350;
    public static int height = 380;
	
    public Maze(Position[][] maze){
		frame = new JFrame();
    	frame.setTitle(title);
    	frame.add(new Board(maze));
    	frame.setSize(width, height);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setLocationRelativeTo(null);
  	
    	frame.setVisible(true);
	}

}
