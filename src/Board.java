
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Board extends JPanel implements ActionListener{
	
	public Player player;
	public Player player2;
	private Timer timer;
	public static Position[][] maze;
	public boolean gameDone;
	Image[] grass = new Image[3];
	Image fenceSide;
	Image fenceFront;
	Image test;
	ImageIcon singlePlayerBlack;
	ImageIcon singlePlayerHover;
	ImageIcon multiPlayerBlack;
	ImageIcon multiPlayerHover;
	ImageIcon mainMenuBlack;
	JButton singlePlayer;
	JButton multiPlayer;
	JButton menu;
	int background = 0;
	String mode;
	int i = 0;
	int time = 0;
	int timeAvailable = 0;
	int score = 1000;
	int tileSize = 25;
	int side = 150;
	long tStart;
	int charX = 5;
	int charY = 5;
	Image backgroundMain;
	public Board(Position[][] maze) throws IOException{
		player = new Player(1);
		player.create1();
		player2 = new Player(2);
		this.maze = maze;
		gameDone = false;
		timer = new Timer(100000, this);
		timer.start();
		addKeyListener(new AL());
		addKeyListener(new AL2());
		setFocusable(true);
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("PressStart2P.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		grass[0] = ImageIO.read(new File("src/images/grass1.png"));
		grass[0] = grass[0].getScaledInstance(tileSize, tileSize, Image.SCALE_DEFAULT);
		grass[1] = ImageIO.read(new File("src/images/grass2.png"));
		grass[1] = grass[1].getScaledInstance(tileSize, tileSize, Image.SCALE_DEFAULT);
		grass[2] = ImageIO.read(new File("src/images/grass3.png"));
		grass[2] = grass[2].getScaledInstance(tileSize, tileSize, Image.SCALE_DEFAULT);
		fenceSide = ImageIO.read(new File("src/images/fenceSide.png"));
		fenceSide = fenceSide.getScaledInstance(5, tileSize, Image.SCALE_DEFAULT);
		fenceFront = ImageIO.read(new File("src/images/fenceFront.png"));
		fenceFront = fenceFront.getScaledInstance(tileSize, 5, Image.SCALE_DEFAULT);
	
		//singlePlayerBlack = .getScaledInstance(tileSize, 5, Image.SCALE_DEFAULT);

		singlePlayer = new JButton("Single Player");
		singlePlayer.setFont(new Font("Press Start 2P", Font.PLAIN, 24)); 
		singlePlayer.setOpaque(false);
		singlePlayer.setContentAreaFilled(false);
		//singlePlayer.setBorderPainted(false);
		singlePlayer.setFocusPainted(false);
		singlePlayer.addActionListener(new ButtonListener());
		this.setLayout(null);
		singlePlayer.setSize(350, 55);
		singlePlayer.setLocation(250, 200);

		 String fonts[] = 
			      GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

			    for ( int i = 0; i < fonts.length; i++ )
			    {
			      System.out.println(fonts[i]);
			    }
		//this.setBackground(backgroundMain);
	
		singlePlayer.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        singlePlayer.setForeground(Color.RED);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        singlePlayer.setForeground(Color.BLACK);
		    }
		});
		
	
		
		
		multiPlayer = new JButton("MultiPlayer");
		multiPlayer.setFont(new Font("Press Start 2P", Font.PLAIN, 24)); 
		multiPlayer.setOpaque(false);
		multiPlayer.setContentAreaFilled(false);
		//multiPlayer.setBorderPainted(false);
		multiPlayer.setFocusPainted(false);
		multiPlayer.addActionListener(new ButtonListener());
		multiPlayer.setSize(300, 55);
		multiPlayer.setLocation(250, 275);
		multiPlayer.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        multiPlayer.setForeground(Color.RED);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        multiPlayer.setForeground(Color.BLACK);
		    }
		});
	
		menu = new JButton("Main Menu");
		menu.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		menu.setOpaque(false);
		menu.setContentAreaFilled(false);
		//multiPlayer.setBorderPainted(false);
		menu.setFocusPainted(false);
		menu.addActionListener(new ButtonListener());
		menu.setSize(125, 50);
		menu.setLocation(310, 500);
		menu.setBorderPainted(false);
		mode = "menu";
		menu.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        menu.setForeground(Color.RED);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        menu.setForeground(Color.BLACK);
		    }
		});
	}
	
	public void init() throws IOException{
		player = new Player(1);
		MazePuzzle newMaze = new MazePuzzle();
		this.maze = newMaze.generateMaze();
		gameDone = false;
		timer = new Timer(25, this);
		timer.start();
		setFocusable(true);
	}
	
	public void init2() throws IOException{
		player = new Player(1);
		player2 = new Player(2);
		MazePuzzle newMaze = new MazePuzzle();
		this.maze = newMaze.generateMaze();
		gameDone = false;
		timer = new Timer(5, this);
		timer.start();
		setFocusable(true);
	}
	
	public void actionPerformed(ActionEvent e){
		
			repaint();
		
			//setVisible(false);
		
	}
	
	public void paint(Graphics g){
		super.paint(g);

		int height = 32;
		int width = 32;
		g.setFont(new Font("Press Start 2P", Font.PLAIN, 60)); 
		g.drawString("Maze Bound",100,100);
		//g.drawImage(backgroundMain, 0, 0, null);
		if(mode.equals("menu")){
			score = 0;
			timeAvailable = 10;
			menu(g);
			try {
				init2();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			this.remove(singlePlayer);
			this.remove(multiPlayer);
			this.add(menu);
		}
		if(mode.equals("singlePlayer")){

			this.add(menu);

			singlePlayer(g);
		}	
		else if(mode.equals("multiPlayer")){
		background(g);
		multiPlayer(g);
		}



	}
	
	public void menu(Graphics g){
		JPanel p = new JPanel(new BorderLayout()); 
		this.add(p);
		this.add(singlePlayer);
		this.add(multiPlayer);
		this.remove(menu);
	}
	
	public void background(Graphics g){
		int x = 0;
		int y = 0;
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 20; j++){
				if(x == 3){
					x = 0;
				}
				
				g.drawImage(grass[x],side+i*tileSize, j*tileSize, null);
				x++;
			}
		}
	}
	
	public void singlePlayer(Graphics g){
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - tStart;
		
		if(timeAvailable - (tDelta / 1000.0) <= 0){
			background(g);
			drawMaze(g);
			g.setFont(new Font("Press Start 2P", Font.PLAIN, 60)); 
			g.drawString("GAMEOVER", 225, 300);
			g.setFont(new Font("Arial", Font.PLAIN, 40)); 
			String numberAsString = String.valueOf(score);
			g.setFont(new Font("Press Start 2P", Font.PLAIN, 15)); 
			g.drawString("SCORE: ", 150, 530);
			g.drawString(numberAsString, 255, 530);
			
			gameDone = true;
		}
	
		if(gameDone == false){
		background(g);

		System.out.println(new DecimalFormat("#0").format(timeAvailable - (tDelta / 1000.0)));
		g.setFont(new Font("Press Start 2P", Font.PLAIN, 15)); 
		String numberAsString = String.valueOf(score);
		g.drawString("SCORE: ", 150, 530);
		g.drawString(numberAsString, 255, 530);
		
		g.drawString("TIME LEFT: ", 430, 530);
		numberAsString = String.valueOf(new DecimalFormat("#0").format((timeAvailable - (tDelta / 1000.0))));
		g.drawString(numberAsString, 600, 530);
		drawCharacter(player, g);
			//g.setColor(Color.red);
			//g.fillRect (side+player.getX(), player.getY(), tileSize, tileSize);  
		drawMaze(g);
		if(player.getXTile() == 19 && player.getYTile() == 19){
			gameDone = true;
		}
		if(player2.getXTile() == 19 && player2.getYTile() == 19){
			gameDone = true;
		}
		if(gameDone == true){
			try {
				timeAvailable = timeAvailable + 10;
				score++;
				init2();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		}
		
	
	}
	public void multiPlayer(Graphics g){
		if(gameDone == false){
		drawCharacter(player, g);
		drawCharacter(player2, g);
		
		drawMaze(g);
		if(player.getXTile() == 19 && player.getYTile() == 19){
			gameDone = true;
		}
		if(player2.getXTile() == 19 && player2.getYTile() == 19){
			gameDone = true;
		}
		if(gameDone == true){
			//timer.stop();
			g.drawString("GAME OVER", 340/2, 370/2);
			try {
				init2();
				score++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		}
		
	}
	
	public void drawMaze(Graphics g){
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 20; j++){
					g.setColor(Color.white);
					if(maze[i][j].isRightOpen() == false){
						g.setColor(Color.black);
						//g.drawLine((j*tileSize)+tileSize,i*tileSize, (j*tileSize)+tileSize, (i*tileSize)+tileSize);
						g.drawImage(fenceSide,side+(j*tileSize)+tileSize,i*tileSize, null);
					}
					
					//g.fillRect(i*tileSize, j*tileSize, tileSize, tileSize);
					if(maze[i][j].isLeftOpen() == false){
						g.setColor(Color.black);
						//g.drawLine(j*tileSize,i*tileSize, j*tileSize, (i*tileSize)+tileSize);
						g.drawImage(fenceSide,side+j*tileSize,i*tileSize, null);
						
					}
					if(maze[i][j].isUpOpen() == false){
						g.setColor(Color.black);
						//g.drawLine(j*tileSize,i*tileSize, (j*tileSize)+tileSize, i*tileSize);
						g.drawImage(fenceFront,side+j*tileSize,i*tileSize, null);
						
					}
					if(maze[i][j].isDownOpen() == false){
						g.setColor(Color.black);
						//g.drawLine((j*tileSize),(i*tileSize)+tileSize, (j*tileSize)+tileSize, (i*tileSize)+tileSize);
						g.drawImage(fenceFront,(side+j*tileSize),(i*tileSize)+tileSize, null);
					}
			}
		}
	}
	
	public void drawCharacter(Player player, Graphics g){
		Image image = player.getImage();
		image = image.getScaledInstance(tileSize - charX, tileSize  - charX, Image.SCALE_DEFAULT);
		g.drawImage(image,side+player.getX()+charX,player.getY()+charX,null);
	}
	
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			
			int keyCode = e.getKeyCode();
			
			if(keyCode == KeyEvent.VK_W){
				
				if(player.getFace().equals("back")){	
					if(gameDone == false){
					System.out.print("w");
						if(maze[player.getYTile()][player.getXTile()].isUpOpen() == true){
						player.changeX(0);
						player.changeY(-tileSize);

						}
					}
				}
				player.changeBack();
			}
			if(keyCode == KeyEvent.VK_A){
				
				if(player.getFace().equals("left")){	
					if(gameDone == false){
						if(maze[player.getYTile()][player.getXTile()].isLeftOpen() == true){
						player.changeX(-tileSize);
						player.changeY(0);		
						}
					}
				}
				player.changeLeft();
				
			}
			if(keyCode == KeyEvent.VK_S){

				if(player.getFace().equals("front")){	
					if(gameDone == false){
						if(maze[player.getYTile()][player.getXTile()].isDownOpen() == true){
						player.changeX(0);
						player.changeY(+tileSize);
						}
					}
				}
					player.changeFront();
			}
			if(keyCode == KeyEvent.VK_D){
				if(player.getFace().equals("right")){	
					if(gameDone == false){
						if(maze[player.getYTile()][player.getXTile()].isRightOpen() == true){
							player.changeX(+tileSize);
							player.changeY(0);
						}
					}
				}
				player.changeRight();
			}
			//Player has moved to a position with a coin
			if (maze[player.getYTile()][player.getXTile()].hasCoin) {
				maze[player.getYTile()][player.getXTile()].hasCoin = false;
				MazePuzzle.coinList.remove(maze[player.getYTile()][player.getXTile()]);
				MazePuzzle.player1Score = MazePuzzle.player1Score + 500;
				System.out.println(MazePuzzle.player1Score);
			}
		
	}
	}
	
	public class AL2 extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			if(gameDone == false){
				int keyCode = e.getKeyCode();
			
				if(keyCode == KeyEvent.VK_UP){
					
					if(maze[player2.getYTile()][player2.getXTile()].isUpOpen() == true){
					player2.changeX(0);
					player2.changeY(-tileSize);
					}
					player2.changeBack();
				}
				if(keyCode == KeyEvent.VK_LEFT){
					if(maze[player2.getYTile()][player2.getXTile()].isLeftOpen() == true){
					player2.changeX(-tileSize);
					player2.changeY(0);			
					}
					player2.changeLeft();
				}
				if(keyCode == KeyEvent.VK_DOWN){
					if(maze[player2.getYTile()][player2.getXTile()].isDownOpen() == true){
					player2.changeX(0);
					player2.changeY(+tileSize);
					}
					player2.changeFront();
				}
				if(keyCode == KeyEvent.VK_RIGHT){
					if(maze[player2.getYTile()][player2.getXTile()].isRightOpen() == true){
						player2.changeX(+tileSize);
						player2.changeY(0);
					}
					player2.changeRight();
				}
				//Player has moved to a position with a coin
				if (maze[player2.getYTile()][player2.getXTile()].hasCoin) {
					maze[player2.getYTile()][player2.getXTile()].hasCoin = false;
					MazePuzzle.coinList.remove(maze[player2.getYTile()][player2.getXTile()]);
					MazePuzzle.player2Score = MazePuzzle.player2Score + 500;
					System.out.println(MazePuzzle.player2Score);
				}
			}
		}

	}
	
	 private class ButtonListener implements ActionListener {
		  public void actionPerformed (ActionEvent event) {
		    Object source = event.getSource();

		    if (source == singlePlayer) {
		        mode = "singlePlayer";
		        tStart = System.currentTimeMillis();
		    }
		    if (source == multiPlayer) {
		        mode = "multiPlayer";
		    }
		    if (source == menu) {
		    	  multiPlayer.setForeground(Color.BLACK);
		    	  singlePlayer.setForeground(Color.BLACK);
		    	  menu.setForeground(Color.BLACK);
		        mode = "menu";
		    }
		  }
	 }
	 
	
}
