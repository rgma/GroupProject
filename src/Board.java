
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


public class Board extends JPanel implements ActionListener {

    public Player player;
    public Player player2;
    private Timer timer;
    public static Position[][] maze;
    public boolean gameDone;
    public boolean playerLeftMaze;

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

    JButton menu;
    JButton restart;

    String difficulty = "easy";
    int background = 0;
    String mode;
    int i = 0;
    int time = 0;
    int timeSetRestart;
    int timeSet;
    int timeAvailable = 0;
    int score = 0;
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
    Image backgroundMain;
    MazePuzzle mazePuzzle;
    long tDelta;

    public Board(Position[][] maze) throws IOException {
        this.setLayout(null);
        addKeyListener(new AL());
        addKeyListener(new AL2());
        setFocusable(true);
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/PressStart2P.ttf")));
        } catch (IOException | FontFormatException e) {
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
        ball1 = ball1.getScaledInstance(tileSize / 2, tileSize / 2, Image.SCALE_DEFAULT);
        ball2 = ImageIO.read(new File("src/images/balls/ball2.png"));
        ball2 = ball2.getScaledInstance(tileSize / 2, tileSize / 2, Image.SCALE_DEFAULT);
        ball = ball1;
        //singlePlayerBlack = .getScaledInstance(tileSize, 5, Image.SCALE_DEFAULT);


        menu = new JButton("Main Menu");
        menu.setFont(new Font("Press Start 2P", Font.PLAIN, 24));
        menu.setOpaque(false);
        menu.setContentAreaFilled(false);
        //multiPlayer.setBorderPainted(false);
        menu.setFocusPainted(false);
        menu.addActionListener(new ButtonListener());
        menu.setSize(250, 50);
        menu.setLocation(700 + (side * 2), 630 + top);
        menu.setForeground(Color.BLACK);
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
        restart.setForeground(Color.BLACK);
        //restart.setBorderPainted(false);
        restart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                restart.setForeground(Color.RED);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                restart.setForeground(Color.BLACK);
            }
        });
        this.add(menu);
        this.add(restart);
        init2();
    }


    public void init() throws IOException {
        player = new Player(1);
        MazePuzzle newMaze = new MazePuzzle();
        this.maze = newMaze.generateMaze();
        gameDone = false;
        playerLeftMaze = false;

        timer = new Timer(5, this);
        timer.start();
        setFocusable(true);
    }

    public void init2() throws IOException {
        if (MazePuzzle.numPlayers == 2) {
            mode = "multiPlayer";
        }
        if (MazePuzzle.numPlayers == 1) {
            mode = "singlePlayer";
        }
        finishX = rowCols / 2;
        finishY = rowCols;
        gameDone = false;
        timeSetRestart = MazePuzzle.time;
        timeSet = timeSetRestart;
        timeAvailable = timeSet;
        MazePuzzle newMaze = new MazePuzzle();
        maze = newMaze.generateMaze();

        tStart = System.currentTimeMillis();
        setFocusable(true);
        score = 0;
        score2 = 0;
        rowCols = newMaze.getMazeSize();
        tileSize = 700 / rowCols;
        finishX = rowCols / 2;
        finishY = rowCols;
        player = new Player(1);
        player2 = new Player(2);
        player2.changeStarting2(tileSize, rowCols);

        timer = new Timer(5, this);
        timer.start();


        grass[0] = grass[0].getScaledInstance(tileSize, tileSize, Image.SCALE_DEFAULT);
        grass[1] = grass[1].getScaledInstance(tileSize, tileSize, Image.SCALE_DEFAULT);
        grass[2] = grass[2].getScaledInstance(tileSize, tileSize, Image.SCALE_DEFAULT);
        fenceSide = fenceSide.getScaledInstance(5, tileSize, Image.SCALE_DEFAULT);
        fenceFront = fenceFront.getScaledInstance(tileSize, 5, Image.SCALE_DEFAULT);
        ball1 = ball1.getScaledInstance(tileSize / 2, tileSize / 2, Image.SCALE_DEFAULT);
        ball2 = ball2.getScaledInstance(tileSize / 2, tileSize / 2, Image.SCALE_DEFAULT);
    }

    public void actionPerformed(ActionEvent e) {

        repaint();

        //setVisible(false);

    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Press Start 2P", Font.PLAIN, 60));
        g.drawString("Maze Bound", 100, 100);
        //g.drawImage(backgroundMain, 0, 0, null);

        if (mode.equals("singlePlayer")) {
            singlePlayer(g);
        } else if (mode.equals("multiPlayer")) {
            multiPlayer(g);
        }


    }

    public void menu(Graphics g) {
        JPanel p = new JPanel(new BorderLayout());
        this.add(p);
        this.remove(restart);
        this.remove(menu);


    }

    public void settings(Graphics g) {

        this.add(menu);
        this.remove(restart);

    }

    public void background(Graphics g) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < rowCols; i++) {
            for (int j = 0; j < rowCols; j++) {
                if (x == 3) {
                    x = 0;
                }

                g.drawImage(grass[x], side + i * tileSize, (j * tileSize) + top, null);
                x++;
            }
        }
    }

    public void singlePlayer(Graphics g) {
        long tEnd = System.currentTimeMillis();
        tDelta = tEnd - tStart;
        g.setColor(Color.BLACK);


        if (timeAvailable - (tDelta / 1000.0) <= 0) {
            background(g);
            drawMaze(g);
            g.setFont(new Font("Press Start 2P", Font.PLAIN, 80));
            g.setColor(Color.BLACK);

            g.drawString("GAMEOVER", side + 30, 768 / 2);


            gameDone = true;
        }

        if (gameDone == false) {
            if (maze[player.getYTile()][player.getXTile()].hasCoin == true) {
                score++;
                maze[player.getYTile()][player.getXTile()].hasCoin = false;
            }
            background(g);


            String timeString = new DecimalFormat("#0").format((timeAvailable - (tDelta / 1000.0)));
            int time = Integer.parseInt(timeString);
            endStarImage = endStarImage.getScaledInstance(tileSize - charX, tileSize - charX, Image.SCALE_DEFAULT);
            if (time % 2 == 0) {
                endStarImage = endStar1;
            } else {
                endStarImage = endStar2;
            }
            drawCharacter(player, g);
            //g.setColor(Color.red);
            //g.fillRect (side+player.getX(), player.getY(), tileSize, tileSize);
            drawMaze(g);

            if (playerLeftMaze == true) {
                playerLeftMaze = false;
                try {
                    timeSet = timeSet / 2;
                    timeAvailable = timeAvailable + timeSet;
                    init();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
        printGameInfo(g);
    }

    public void printGameInfo(Graphics g) {
        g.setColor(Color.BLACK);

        g.setFont(new Font("Press Start 2P", Font.PLAIN, 18));
        String numberAsString = String.valueOf(score);
        g.drawString("PLAYER 1 SCORE ", 705 + (side * 2), 420);
        g.setFont(new Font("Press Start 2P", Font.PLAIN, 22));

        if (score < 10) {
            g.drawString(numberAsString, 820 + (side * 2), 460);
        } else if (score < 100) {
            g.drawString(numberAsString, 810 + (side * 2), 460);

        } else  if (score < 1000) {
            g.drawString(numberAsString, 800 + (side * 2), 460);
        }

        if (mode.contains("multiPlayer")) {
            g.setFont(new Font("Press Start 2P", Font.PLAIN, 18));
            g.drawString("PLAYER 2 SCORE ", 705 + (side * 2), 500);
            numberAsString = String.valueOf(score2);
            g.setFont(new Font("Press Start 2P", Font.PLAIN, 22));
            if (score2 < 10) {
                g.drawString(numberAsString, 820 + (side * 2), 540);
            } else if (score2 < 100) {
                g.drawString(numberAsString, 810 + (side * 2), 540);

            } else  if (score2 < 1000) {
                g.drawString(numberAsString, 800 + (side * 2), 540);
            }
        } else {
            g.drawString("TIME LEFT", 730 + (side * 2), 40);
            numberAsString = String.valueOf(new DecimalFormat("#0").format((timeAvailable - (tDelta / 1000.0))));
            if (Integer.parseInt(numberAsString) <= 0) {
                g.setFont(new Font("Press Start 2P", Font.PLAIN, 70));
                g.drawString("0", 800 + (side * 2), 150);
            } else if (Integer.parseInt(numberAsString) < 10) {
                g.setFont(new Font("Press Start 2P", Font.PLAIN, 70));
                g.drawString(numberAsString, 800 + (side * 2), 150);
            } else if (Integer.parseInt(numberAsString) < 100) {
                g.setFont(new Font("Press Start 2P", Font.PLAIN, 70));
                g.drawString(numberAsString, 765 + (side * 2), 150);
            } else {
                g.setFont(new Font("Press Start 2P", Font.PLAIN, 70));
                g.drawString(numberAsString, 730 + (side * 2), 150);
            }
        }

        g.setFont(new Font("Press Start 2P", Font.PLAIN, 18));
        g.drawString("MAZE SIZE", 745 + (side * 2), 320);
        if (MazePuzzle.mazeSize == 15 || MazePuzzle.mazeSize == 21) {
            g.drawString("" + MazePuzzle.mazeSize + " X " + MazePuzzle.mazeSize, 770 + (side * 2), 360);

        } else {
            g.drawString("" + MazePuzzle.mazeSize + " X " + MazePuzzle.mazeSize, 780 + (side * 2), 360);

        }

        g.drawString("DIFFICULTY", 740 + (side * 2), 220);
        if (MazePuzzle.difficulty == MazePuzzle.Difficulty.EASY) {
            g.drawString("EASY", 790 + (side * 2), 260);
        } else if (MazePuzzle.difficulty == MazePuzzle.Difficulty.MEDIUM) {
            g.drawString("NORMAL", 775 + (side * 2), 260);
        } else if (MazePuzzle.difficulty == MazePuzzle.Difficulty.HARD) {
            g.drawString("HARD", 790 + (side * 2), 260);
        }
    }


    public void multiPlayer(Graphics g) {
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;

        background(g);
        if (gameDone == false) {

            drawCharacter(player, g);
            drawCharacter(player2, g);
            if (player2.getYTile() != finishY && player.getYTile() != finishY) {

                if (maze[player2.getYTile()][player2.getXTile()].hasCoin == true) {
                    score2++;
                    maze[player2.getYTile()][player2.getXTile()].hasCoin = false;
                }
                if (maze[player.getYTile()][player.getXTile()].hasCoin == true) {
                    score++;
                    maze[player.getYTile()][player.getXTile()].hasCoin = false;
                }
            }
            drawMaze(g);
        } else {
            drawMaze(g);
            g.setColor(Color.BLACK);

            String timeString = new DecimalFormat("#0").format((timeAvailable - (tDelta / 1000.0)));
            int time = Integer.parseInt(timeString);
            if (score > score2) {
                if (time % 2 == 0) {
                    player.changeFront();
                    drawCharacter(player, g);
                } else {
                    player.changeWin();
                    drawCharacter(player, g);
                }
                g.setFont(new Font("Press Start 2P", Font.PLAIN, 50));
                g.drawString("PLAYER 1 WINS", side + 25, 768 / 2);
            }
            if (score2 > score) {

                if (time % 2 == 0) {
                    player2.changeFront();
                    drawCharacter(player2, g);
                } else {
                    player2.changeWin();
                    drawCharacter(player2, g);
                }
                g.setFont(new Font("Press Start 2P", Font.PLAIN, 50));
                g.drawString("PLAYER 2 WINS", side + 25, 768 / 2);
            }
            if (score2 == score) {
                g.setFont(new Font("Press Start 2P", Font.PLAIN, 80));
                g.drawString("  DRAW  ", side + 25, 768 / 2);

            }
            g.drawString("", side + 12, 768 / 2);
        }

        printGameInfo(g);

    }

    public void drawMaze(Graphics g) {
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        String timeString = new DecimalFormat("#0").format((timeAvailable - (tDelta / 1000.0)));
        int time = Integer.parseInt(timeString);
        if (time % 2 == 0) {
            ball = ball1;
        } else {
            ball = ball2;
        }

        for (int i = 0; i < rowCols; i++) {
            for (int j = 0; j < rowCols; j++) {
                g.setColor(Color.white);
                if (maze[i][j].hasCoin == true) {
                    g.drawImage(ball, (j * tileSize) + side + (tileSize / 3), (i * tileSize) + top + (tileSize / 3), null);
                }
                if (maze[i][j].isRightOpen() == false) {
                    g.drawImage(fenceSide, side + (j * tileSize) + tileSize, (i * tileSize) + top, null);
                }
                if (maze[i][j].isLeftOpen() == false) {
                    g.drawImage(fenceSide, side + j * tileSize, (i * tileSize) + top, null);

                }
                if (maze[i][j].isUpOpen() == false) {
                    g.drawImage(fenceFront, side + j * tileSize, (i * tileSize) + top, null);

                }
                if (maze[i][j].isDownOpen() == false) {
                    g.drawImage(fenceFront, (side + j * tileSize), ((i * tileSize) + tileSize) + top, null);
                }
            }
        }
    }

    public void drawCharacter(Player player, Graphics g) {

        Image image = player.getImage();
        image = image.getScaledInstance(tileSize - charX, tileSize - charX, Image.SCALE_DEFAULT);
        g.drawImage(image, side + player.getX() + charX, player.getY() + charX + top, null);
    }

    public void close() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame.dispose();
    }


    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_W) {


                if (gameDone == false) {

                    if (maze[player.getYTile()][player.getXTile()].isUpOpen() == true) {
                        player.changeX(0, tileSize);
                        player.changeY(-tileSize, tileSize);
                    }
                }

                player.changeBack();
            }
            if (keyCode == KeyEvent.VK_A) {

                if (gameDone == false) {
                    if (maze[player.getYTile()][player.getXTile()].isLeftOpen() == true) {
                        player.changeX(-tileSize, tileSize);
                        player.changeY(0, tileSize);
                    }
                }

                player.changeLeft();

            }
            if (keyCode == KeyEvent.VK_S) {

                if (gameDone == false) {
                    if (maze[player.getYTile()][player.getXTile()].isDownOpen() == true) {
                        if (player.getYTile() == finishY - 1 && player.getXTile() == finishX) {
                            playerLeftMaze = true;
                            if (mode.contains("multiPlayer")) {
                                gameDone = true;
                            }
                        } else {
                            player.changeX(0, tileSize);
                            player.changeY(+tileSize, tileSize);
                        }
                    }
                }

                player.changeFront();
            }
            if (keyCode == KeyEvent.VK_D) {

                if (gameDone == false) {
                    if (maze[player.getYTile()][player.getXTile()].isRightOpen() == true) {
                        player.changeX(+tileSize, tileSize);
                        player.changeY(0, tileSize);
                    }
                }

                player.changeRight();
            }


        }
    }

    public class AL2 extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (gameDone == false) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_UP) {

                    if (maze[player2.getYTile()][player2.getXTile()].isUpOpen() == true) {
                        player2.changeX(0, tileSize);
                        player2.changeY(-tileSize, tileSize);
                    }
                    player2.changeBack();
                }
                if (keyCode == KeyEvent.VK_LEFT) {
                    if (maze[player2.getYTile()][player2.getXTile()].isLeftOpen() == true) {
                        player2.changeX(-tileSize, tileSize);
                        player2.changeY(0, tileSize);
                    }
                    player2.changeLeft();
                }
                if (keyCode == KeyEvent.VK_DOWN) {
                    if (maze[player2.getYTile()][player2.getXTile()].isDownOpen() == true) {
                        if (player2.getYTile() == finishY - 1 && player2.getXTile() == finishX) {
                            playerLeftMaze = true;
                            if (mode.contains("multiPlayer")) {
                                gameDone = true;
                            }
                        } else {
                            player2.changeX(0, tileSize);
                            player2.changeY(+tileSize, tileSize);
                        }
                    }
                    player2.changeFront();
                }
                if (keyCode == KeyEvent.VK_RIGHT) {
                    if (maze[player2.getYTile()][player2.getXTile()].isRightOpen() == true) {
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
        public void actionPerformed(ActionEvent event) {
            requestFocusInWindow();
            Object source = event.getSource();

            if (source == menu) {
                menu.setForeground(Color.BLACK);
                NewGameMenu newGame = new NewGameMenu();
                timer.stop();
                close();
            }
            if (source == restart) {
                try {
                    init2();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }


}
