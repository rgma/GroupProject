import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;

import javax.swing.*;

public class Board extends JPanel implements ActionListener{
	public Player player;
	private Timer timer;
	public static Position[][] maze;
	
	public Board(Position[][] maze){
		player = new Player();
		this.maze = maze;
		timer = new Timer(25, this);
		timer.start();
		addKeyListener(new AL());
		setFocusable(true);
	}
	
	public void actionPerformed(ActionEvent e){
		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		int width = 32;
		int height = 32;
		g.setColor(Color.red);
		g.fillRect(player.getX(), player.getY(), width, height);
	
		for(int i = 0; i < MazePuzzle.MAZE_SIZE; i++){
			for(int j = 0; j < MazePuzzle.MAZE_SIZE; j++){
				g.setColor(Color.white);
				if(maze[i][j].isRightOpen() == false){
					g.setColor(Color.black);
					g.drawLine((j*32)+32,i*32, (j*32)+32, (i*32)+32);
				}
				//g.fillRect(i*32, j*32, 32, 32);
				if(maze[i][j].isLeftOpen() == false){
					g.setColor(Color.black);
					g.drawLine(j*32,i*32, j*32, (i*32)+32);
				}
				if(maze[i][j].isUpOpen() == false){
					g.setColor(Color.black);
					g.drawLine(j*32,i*32, (j*32)+32, i*32);
				}
				if(maze[i][j].isDownOpen() == false){
					g.setColor(Color.black);
					g.drawLine((j*32),(i*32)+32, (j*32)+32, (i*32)+32);
				}
			}
		}	
	}
	
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int keyCode = e.getKeyCode();
			
			if(keyCode == KeyEvent.VK_W){
				if(maze[player.getYTile()][player.getXTile()].isUpOpen() == true){
				player.changeX(0);
				player.changeY(-32);
				}
			}
			if(keyCode == KeyEvent.VK_A){
				if(maze[player.getYTile()][player.getXTile()].isLeftOpen() == true){
				player.changeX(-32);
				player.changeY(0);			
				}
			}
			if(keyCode == KeyEvent.VK_S){
				if(maze[player.getYTile()][player.getXTile()].isDownOpen() == true){
				player.changeX(0);
				player.changeY(+32);
				}
			}
			if(keyCode == KeyEvent.VK_D){
				if(maze[player.getYTile()][player.getXTile()].isRightOpen() == true){
					player.changeX(+32);
					player.changeY(0);
				}
			}
		}
	}
}
