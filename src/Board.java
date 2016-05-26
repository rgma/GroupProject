
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
	Image endStar1;
	Image endStar2;
	Image endStarImage;
	Image ball1;
	Image ball2;
	Image ball;
	JButton singlePlayer;
	JButton multiPlayer;
	JButton menu;
	JButton restart;
	JButton settings;
	JButton time40;
	JButton time60;
	JButton time80;
	JButton size11;
	JButton size15;
	JButton size21;
	JButton easy;
	JButton medium;
	JButton hard;
	String difficulty = "easy";
	int background = 0;
	String mode;
	int i = 0;
	int time = 0;
	int timeSetRestart = 40;
	int timeSet = 0;
	int timeAvailable = 0;
	int score = 1000;
	int score2;
	int tileSize = 35;
	int side = 15;
	int top = 18;
	long tStart;
	int charX = 5;
	int charY = 5;
	int rowCols = 0;
	int finishX;
	int finishY;
	int changeSize = 11;
	Image backgroundMain;
	MazePuzzle mazePuzzle;
	public Board(Position[][] maze) throws IOException{
		mazePuzzle = new MazePuzzle();
		rowCols = mazePuzzle.getMazeSize();
		tileSize = 700/rowCols;
		finishX = rowCols/2;
		finishY = rowCols - 1;

		player = new Player(1);
		player.create1();
		player2 = new Player(2);
		this.maze = maze;
		gameDone = false;
		timer = new Timer(5, this);
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
		endStar1 = ImageIO.read(new File("src/images/balls/ball3.png"));
		endStar2 = ImageIO.read(new File("src/images/balls/ball4.png"));
		endStarImage = endStar1; 
		ball1 = ImageIO.read(new File("src/images/balls/ball1.png"));
		ball1 = ball1.getScaledInstance(tileSize/2, tileSize/2, Image.SCALE_DEFAULT);
		ball2 = ImageIO.read(new File("src/images/balls/ball2.png"));
		ball2 = ball2.getScaledInstance(tileSize/2, tileSize/2, Image.SCALE_DEFAULT);
		
		ball = ball1; 
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
		menu.setFont(new Font("Press Start 2P", Font.PLAIN, 24));
		menu.setOpaque(false);
		menu.setContentAreaFilled(false);
		//multiPlayer.setBorderPainted(false);
		menu.setFocusPainted(false);
		menu.addActionListener(new ButtonListener());
		menu.setSize(250, 50);
		menu.setLocation(700 + (side * 2), 630 + top);
		//menu.setBorderPainted(false);
	
		
		menu.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        menu.setForeground(Color.RED);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        menu.setForeground(Color.BLACK);
		    }
		});
		
		restart = new JButton("Restart");
		restart.setFont(new Font("Press Start 2P", Font.PLAIN, 24));
		restart.setOpaque(false);
		restart.setContentAreaFilled(false);
		//multiPlayer.setBorderPainted(false);
		restart.setFocusPainted(false);
		restart.addActionListener(new ButtonListener());
		restart.setSize(250, 50);
		restart.setLocation(700 + (side * 2), 550 + top);
		//restart.setBorderPainted(false);
		restart.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        restart.setForeground(Color.RED);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        restart.setForeground(Color.BLACK);
		    }
		});
		settings = new JButton("Settings");
		settings.setFont(new Font("Press Start 2P", Font.PLAIN, 24));
		settings.setOpaque(false);
		settings.setContentAreaFilled(false);
		//multiPlayer.setBorderPainted(false);
		settings.setFocusPainted(false);
		settings.addActionListener(new ButtonListener());
		settings.setSize(300, 55);
		settings.setLocation(250, 350);
		settings.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        settings.setForeground(Color.RED);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        settings.setForeground(Color.BLACK);
		    }
		});
		mode = "menu";
		time40 = new JButton("time40");
		time40.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		time40.setOpaque(false);
		time40.setContentAreaFilled(false);
		//multiPlayer.setBorderPainted(false);
		time40.setFocusPainted(false);
		time40.addActionListener(new ButtonListener());
		time40.setSize(150, 55);
		time40.setLocation(200, 350);
		
		time60 = new JButton("time60");
		time60.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		time60.setOpaque(false);
		time60.setContentAreaFilled(false);
		//multiPlayer.setBorderPainted(false);
		time60.setFocusPainted(false);
		time60.addActionListener(new ButtonListener());
		time60.setSize(150, 55);
		time60.setLocation(400, 350);
		
		time80 = new JButton("time80");
		time80.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		time80.setOpaque(false);
		time80.setContentAreaFilled(false);
		//multiPlayer.setBorderPainted(false);
		time80.setFocusPainted(false);
		time80.addActionListener(new ButtonListener());
		time80.setSize(150, 55);
		time80.setLocation(600, 350);
		setFocusable(true);
		size11 = new JButton("size 11");
		size11.addActionListener(new ButtonListener());
		size11.setSize(150, 55);
		size11.setLocation(200, 550);
		size11.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		size11.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		size11.setOpaque(false);
		size11.setContentAreaFilled(false);
		
		size15 = new JButton("size 15");
		size15.addActionListener(new ButtonListener());
		size15.setSize(150, 55);
		size15.setLocation(400, 550);
		size15.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		size15.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		size15.setOpaque(false);
		size15.setContentAreaFilled(false);
		
		size21 = new JButton("size 21");
		size21.addActionListener(new ButtonListener());
		size21.setSize(150, 55);
		size21.setLocation(600, 550);
		size21.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		size21.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		size21.setOpaque(false);
		size21.setContentAreaFilled(false);
		
		easy = new JButton("EASY");
		easy.addActionListener(new ButtonListener());
		easy.setSize(150, 55);
		easy.setLocation(200, 150);
		easy.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		easy.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		easy.setOpaque(false);
		easy.setContentAreaFilled(false);
		
		medium = new JButton("MEDIUM");
		medium.addActionListener(new ButtonListener());
		medium.setSize(150, 55);
		medium.setLocation(400, 150);
		medium.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		medium.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		medium.setOpaque(false);
		medium.setContentAreaFilled(false);
		
		hard = new JButton("HARD");
		hard.addActionListener(new ButtonListener());
		hard.setSize(150, 55);
		hard.setLocation(600, 150);
		hard.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		hard.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		hard.setOpaque(false);
		hard.setContentAreaFilled(false);
		
	}
	
	public void setButton(JButton button){
		button.setFont(new Font("Press Start 2P", Font.PLAIN, 24));
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		//multiPlayer.setBorderPainted(false);
		button.setFocusPainted(false);
		button.addActionListener(new ButtonListener());
		
	}
	
	public void init() throws IOException{
		player = new Player(1);
		MazePuzzle newMaze = new MazePuzzle();
		this.maze = newMaze.generateMaze();
		gameDone = false;
		timer = new Timer(5, this);
		timer.start();
		setFocusable(true);
	}
	
	public void init2() throws IOException{
		gameDone = false;
		timeSet = timeSetRestart;
		timeAvailable = timeSet;
		System.out.println(timeSet);
		System.out.println(timeSetRestart);
		player = new Player(1);
		player2 = new Player(2);
		player2.changeStarting2(tileSize, rowCols);
		MazePuzzle newMaze = new MazePuzzle();
		MazePuzzle.MAZE_SIZE = changeSize;
	    if(difficulty == "easy"){
	    	newMaze.changeToEasy();
	    }
	    if(difficulty == "medium"){
	    	newMaze.changeToMedium();
	    }
	    if(difficulty == "hard"){
	    	newMaze.changeToHard();
	    }
		maze = newMaze.generateMaze();

		tStart = System.currentTimeMillis();
		setFocusable(true);
		score = 0;
		score2 = 0;
		rowCols = newMaze.getMazeSize();
		
		tileSize = 700/rowCols;
		finishX = rowCols/2;
		finishY = rowCols - 1;
	
		timer = new Timer(5, this);
		timer.start();

	
		grass[0] = grass[0].getScaledInstance(tileSize, tileSize, Image.SCALE_DEFAULT);
		
		grass[1] = grass[1].getScaledInstance(tileSize, tileSize, Image.SCALE_DEFAULT);
	
		grass[2] = grass[2].getScaledInstance(tileSize, tileSize, Image.SCALE_DEFAULT);
	
		fenceSide = fenceSide.getScaledInstance(5, tileSize, Image.SCALE_DEFAULT);
		
		fenceFront = fenceFront.getScaledInstance(tileSize, 5, Image.SCALE_DEFAULT);

		ball1 = ball1.getScaledInstance(tileSize/2, tileSize/2, Image.SCALE_DEFAULT);
		ball2 = ball2.getScaledInstance(tileSize/2, tileSize/2, Image.SCALE_DEFAULT);
	}
	
	public void actionPerformed(ActionEvent e){
		
			repaint();
		
			//setVisible(false);
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		this.remove(singlePlayer);
		this.remove(multiPlayer);
		this.remove(time40);
		this.remove(time60);
		this.remove(time80);
		g.setFont(new Font("Press Start 2P", Font.PLAIN, 60)); 
		g.drawString("Maze Bound",100,100);
		//g.drawImage(backgroundMain, 0, 0, null);
		if(mode.equals("menu")){
			score = 0;
			timeAvailable = timeSet;
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
			this.remove(time40);
			this.remove(time60);
			this.remove(time80);
			
			this.add(menu);
		}
		if(mode.equals("singlePlayer")){
			this.add(menu);
			this.add(restart);
			singlePlayer(g);
		}	
		else if(mode.equals("multiPlayer")){
			this.add(menu);
			this.add(restart);
			background(g);
			multiPlayer(g);
		}
		else if(mode.equals("settings")){
			settings(g);
		}

	}
	
	public void menu(Graphics g){
		JPanel p = new JPanel(new BorderLayout()); 
		this.add(p);
		this.add(singlePlayer);
		this.add(multiPlayer);
		this.add(settings);
		this.remove(restart);
		this.remove(menu);
		this.remove(time40);
		this.remove(time60);
		this.remove(time80);
		this.remove(size11);
		this.remove(size15);
		this.remove(size21);
		this.remove(easy);
		this.remove(medium);
		this.remove(hard);
		
	}
	
	public void settings(Graphics g){
		this.add(time40);
		this.add(time60);
		this.add(time80);
		this.add(size11);
		this.add(size15);
		this.add(size21);
		this.add(easy);
		this.add(medium);
		this.add(hard);
		this.add(menu);
		this.remove(singlePlayer);
		this.remove(multiPlayer);
		this.remove(settings);
		this.remove(restart);
	
	}
	
	public void background(Graphics g){
		int x = 0;
		int y = 0;
		for(int i = 0; i < rowCols; i++){
			for(int j = 0; j < rowCols; j++){
				if(x == 3){
					x = 0;
				}
				
				g.drawImage(grass[x],side+i*tileSize, (j*tileSize) + top, null);
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
			g.setFont(new Font("Press Start 2P", Font.PLAIN, 80)); 
			g.drawString("GAMEOVER", side+12, 768/2);
		
			g.setFont(new Font("Press Start 2P", Font.PLAIN, 18)); 
			String numberAsString = String.valueOf(score);
			g.drawString("PLAYER 1 SCORE ", 700 + (side * 2), 50);
			g.drawString(numberAsString, 700 + (side * 2), 100);
			
			g.drawString("TIME LEFT: ", 700 + (side * 2), 150);
			numberAsString = String.valueOf(new DecimalFormat("#0").format((timeAvailable - (tDelta / 1000.0))));
			g.setFont(new Font("Press Start 2P", Font.PLAIN, 130)); 
			g.drawString("0", 700 + (side * 2) , 300);
		
			
			gameDone = true;
		}
	
		if(gameDone == false){
			if(maze[player.getYTile()][player.getXTile()].hasCoin == true){
				score++;
				maze[player.getYTile()][player.getXTile()].hasCoin = false;
			}
		background(g);
		g.setFont(new Font("Press Start 2P", Font.PLAIN, 18)); 
		String numberAsString = String.valueOf(score);
		g.drawString("PLAYER 1 SCORE ", 700 + (side * 2), 50);
		g.drawString(numberAsString, 700 + (side * 2), 100);
		
		g.drawString("TIME LEFT: ", 700 + (side * 2), 150);
		numberAsString = String.valueOf(new DecimalFormat("#0").format((timeAvailable - (tDelta / 1000.0))));
		g.setFont(new Font("Press Start 2P", Font.PLAIN, 130)); 
		g.drawString(numberAsString, 700 + (side * 2) , 300);
		String timeString = new DecimalFormat("#0").format((timeAvailable - (tDelta / 1000.0)));
		int time = Integer.parseInt(timeString);
		endStarImage = endStarImage.getScaledInstance(tileSize - charX, tileSize  - charX, Image.SCALE_DEFAULT);
		if(time % 2 == 0){
			endStarImage = endStar1; 
		}else{
			endStarImage = endStar2; 
		}
		drawCharacter(player, g);
			//g.setColor(Color.red);
			//g.fillRect (side+player.getX(), player.getY(), tileSize, tileSize);  
		drawMaze(g);
		if(player.getXTile() == finishX && player.getYTile() == finishY){
			gameDone = true;
		}
		if(gameDone == true){
			try {
				timeSet = timeSet/2;
				timeAvailable = timeAvailable + timeSet;
				init();
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
			if(maze[player2.getYTile()][player2.getXTile()].hasCoin == true){
				score2++;
				maze[player2.getYTile()][player2.getXTile()].hasCoin = false;
			}
			if(maze[player.getYTile()][player.getXTile()].hasCoin == true){
				score++;
				maze[player.getYTile()][player.getXTile()].hasCoin = false;
			}
			drawMaze(g);
			g.setFont(new Font("Press Start 2P", Font.PLAIN, 18)); 
			String numberAsString = String.valueOf(score);
			g.drawString("PLAYER 1 SCORE ", 700 + (side * 2), 50);
			g.drawString(numberAsString, 700 + (side * 2), 100);
			
			g.drawString("PLAYER 2 SCORE ", 700 + (side * 2), 150);
			numberAsString = String.valueOf(score2);
			g.drawString(numberAsString, 700 + (side * 2), 200);
			
			if(player.getXTile() == finishX && player.getYTile() == finishY){
				gameDone = true;
				score = score * 2;
			}
			if(player2.getXTile() == finishX && player2.getYTile() == finishY){
				score2 = score2 * 2;
				gameDone = true;
			}
		
		} else {
			drawMaze(g);
			g.setFont(new Font("Press Start 2P", Font.PLAIN, 18)); 
			String numberAsString = String.valueOf(score);
			g.drawString("PLAYER 1 SCORE ", 700 + (side * 2), 50);
			g.drawString(numberAsString, 700 + (side * 2), 100);
			
			g.drawString("PLAYER 2 SCORE ", 700 + (side * 2), 150);
			numberAsString = String.valueOf(score2);
			g.drawString(numberAsString, 700 + (side * 2), 200);
		
		}
		
		
	}
	
	public void drawMaze(Graphics g){
		for(int i = 0; i < rowCols; i++){
			for(int j = 0; j < rowCols; j++){
					g.setColor(Color.white);
					if(maze[i][j].hasCoin == true){
						g.drawImage(ball, (j*tileSize)+tileSize, i*tileSize+tileSize, null);
					}
					if(maze[i][j].isRightOpen() == false){
						g.setColor(Color.black);
						//g.drawLine((j*tileSize)+tileSize,i*tileSize, (j*tileSize)+tileSize, (i*tileSize)+tileSize);
						g.drawImage(fenceSide,side+(j*tileSize)+tileSize,(i*tileSize)+top, null);
					}
					
					//g.fillRect(i*tileSize, j*tileSize, tileSize, tileSize);
					if(maze[i][j].isLeftOpen() == false){
						g.setColor(Color.black);
						//g.drawLine(j*tileSize,i*tileSize, j*tileSize, (i*tileSize)+tileSize);
						g.drawImage(fenceSide,side+j*tileSize,(i*tileSize) + top, null);
						
					}
					if(maze[i][j].isUpOpen() == false){
						g.setColor(Color.black);
						//g.drawLine(j*tileSize,i*tileSize, (j*tileSize)+tileSize, i*tileSize);
						g.drawImage(fenceFront,side+j*tileSize,(i*tileSize) + top, null);
						
					}
					if(maze[i][j].isDownOpen() == false){
						g.setColor(Color.black);
						//g.drawLine((j*tileSize),(i*tileSize)+tileSize, (j*tileSize)+tileSize, (i*tileSize)+tileSize);
						g.drawImage(fenceFront,(side+j*tileSize),((i*tileSize)+tileSize) + top, null);
					}
			}
		}
	}
	
	public void drawCharacter(Player player, Graphics g){
	
		Image image = player.getImage();
		image = image.getScaledInstance(tileSize - charX, tileSize  - charX, Image.SCALE_DEFAULT);
		g.drawImage(image,side+player.getX()+charX,player.getY()+charX+top,null);
	}
	
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			
			int keyCode = e.getKeyCode();
			
			if(keyCode == KeyEvent.VK_W){
				
				if(player.getFace().equals("back")){	
					if(gameDone == false){
			
						if(maze[player.getYTile()][player.getXTile()].isUpOpen() == true){
						player.changeX(0, tileSize);
						player.changeY(-tileSize, tileSize);
						}
					}
				}
				player.changeBack();
			}
			if(keyCode == KeyEvent.VK_A){
				
				if(player.getFace().equals("left")){	
					if(gameDone == false){
						if(maze[player.getYTile()][player.getXTile()].isLeftOpen() == true){
						player.changeX(-tileSize, tileSize);
						player.changeY(0, tileSize);		
						}
					}
				}
				player.changeLeft();
				
			}
			if(keyCode == KeyEvent.VK_S){

				if(player.getFace().equals("front")){	
					if(gameDone == false){
						if(maze[player.getYTile()][player.getXTile()].isDownOpen() == true){
						player.changeX(0, tileSize);
						player.changeY(+tileSize, tileSize);
						}
					}
				}
					player.changeFront();
			}
			if(keyCode == KeyEvent.VK_D){
				if(player.getFace().equals("right")){	
					if(gameDone == false){
						if(maze[player.getYTile()][player.getXTile()].isRightOpen() == true){
							player.changeX(+tileSize, tileSize);
							player.changeY(0, tileSize);
						}
					}
				}
				player.changeRight();
			}
					
		
	}
	}
	
	public class AL2 extends KeyAdapter{

        @Override
		public void keyPressed(KeyEvent e){
			
        	if(gameDone == false){
				int keyCode = e.getKeyCode();
			
				if(keyCode == KeyEvent.VK_UP){
				
					if(maze[player2.getYTile()][player2.getXTile()].isUpOpen() == true){
					player2.changeX(0, tileSize);
					player2.changeY(-tileSize, tileSize);
					}
					player2.changeBack();
				}
				if(keyCode == KeyEvent.VK_LEFT){
					if(maze[player2.getYTile()][player2.getXTile()].isLeftOpen() == true){
					player2.changeX(-tileSize, tileSize);
					player2.changeY(0, tileSize);			
					}
					player2.changeLeft();
				}
				if(keyCode == KeyEvent.VK_DOWN){
					if(maze[player2.getYTile()][player2.getXTile()].isDownOpen() == true){
					player2.changeX(0, tileSize);
					player2.changeY(+tileSize, tileSize);
					}
					player2.changeFront();
				}
				if(keyCode == KeyEvent.VK_RIGHT){
					if(maze[player2.getYTile()][player2.getXTile()].isRightOpen() == true){
						player2.changeX(+tileSize, tileSize);
						player2.changeY(0, tileSize);
					}
					player2.changeRight();
				}
			}
		}
	}
	
	 private class ButtonListener implements ActionListener {

         @Override
		 public void actionPerformed (ActionEvent event) {
        	requestFocusInWindow();
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
		    	  settings.setForeground(Color.BLACK);
		    	  menu.setForeground(Color.BLACK);
		        mode = "menu";
		    }
		    if(source == settings){
		    	mode = "settings";
		    }
		    if (source == time40){
		    	timeSet = 40;
		    	timeSetRestart = 40;
		    }
		    if (source == time60){
		    	timeSet = 60;
		    	timeSetRestart = 60;
		    }
		    if (source == time80){
		    	timeSet = 80;
		    	timeSetRestart = 80;
		    }
		    if (source == size11){
		    	changeSize = 11;
		    }
		    if (source == size15){
		    	changeSize = 15;
		    }
		    if (source == size21){
		    	changeSize = 21;
		    }
		    if (source == restart){
		    	try {
					init2();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    if (source == easy){
		    	difficulty = "easy";
		    }
		    if (source == medium){
		    	difficulty = "medium";
		    }
		    if (source == hard){
		    	difficulty = "hard";
		    }
		    if(changeSize == 21){
		    	size11.setForeground(Color.BLACK);
		    	size15.setForeground(Color.BLACK);
		    	size21.setForeground(Color.RED);
		    }
		    if(changeSize == 15){
		    	size11.setForeground(Color.BLACK);
		    	size15.setForeground(Color.RED);
		    	size21.setForeground(Color.BLACK);
		    }
		    if(changeSize == 11){
		    	size11.setForeground(Color.RED);
		    	size15.setForeground(Color.BLACK);
		    	size21.setForeground(Color.BLACK);
		    }
		    if(timeSetRestart == 40){
		    	time40.setForeground(Color.RED);
		    	time60.setForeground(Color.BLACK);
		    	time80.setForeground(Color.BLACK);
		    }
		    if(timeSetRestart == 60){
		    	time40.setForeground(Color.BLACK);
		    	time60.setForeground(Color.RED);
		    	time80.setForeground(Color.BLACK);
		    }
		    if(timeSetRestart == 80){
		    	time40.setForeground(Color.BLACK);
		    	time60.setForeground(Color.BLACK);
		    	time80.setForeground(Color.RED);
		    }
		    if(difficulty == "easy"){
		    	easy.setForeground(Color.RED);
		    	medium.setForeground(Color.BLACK);
		    	hard.setForeground(Color.BLACK);
		    }
		    if(difficulty == "medium"){
		    	easy.setForeground(Color.BLACK);
		    	medium.setForeground(Color.RED);
		    	hard.setForeground(Color.BLACK);
		    }
		    if(difficulty == "hard"){
		    	easy.setForeground(Color.BLACK);
		    	medium.setForeground(Color.BLACK);
		    	hard.setForeground(Color.RED);
		    }
		  }
	 }
	 
	
}
