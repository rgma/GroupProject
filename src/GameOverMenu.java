import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameOverMenu extends JFrame {

    public GameOverMenu() {

        initUI();
    }

    private void initUI() {
        //this.getContentPane().setBackground(Color.yellow); //debug

        this.getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton settingsButton = new JButton("Settings");
        JButton replayGameButton = new JButton("Replay");
        settingsButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        replayGameButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        JLabel winnerText = new JLabel("WINNER");
        winnerText.setAlignmentX(Component.CENTER_ALIGNMENT);
        winnerText.setFont(new Font("Serif", Font.BOLD, 18));

        JLabel player1ScoreText = new JLabel("PLAYER 1 SCORE");
        player1ScoreText.setAlignmentX(Component.CENTER_ALIGNMENT);
        player1ScoreText.setFont(new Font("Serif", Font.BOLD, 18));

        JLabel player2ScoreText = new JLabel("PLAYER 2 SCORE");
        player2ScoreText.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2ScoreText.setFont(new Font("Serif", Font.BOLD, 18));

        JLabel winner = new JLabel();
        if (MazePuzzle.winner == 1) {
            winner.setText("PLAYER 1");
        } else if (MazePuzzle.winner == 2) {
            winner.setText("PLAYER 2");
        }
        winner.setAlignmentX(Component.CENTER_ALIGNMENT);
        winner.setFont(new Font("Serif", Font.BOLD, 18));
        winner.setHorizontalAlignment(SwingConstants.CENTER);
        winner.setMaximumSize(new Dimension(175,30));
        winner.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel player1Score = new JLabel(""+ MazePuzzle.player1Score);
        player1Score.setAlignmentX(Component.CENTER_ALIGNMENT);
        player1Score.setFont(new Font("Serif", Font.BOLD, 18));
        player1Score.setHorizontalAlignment(SwingConstants.CENTER);
        player1Score.setMaximumSize(new Dimension(175,30));
        player1Score.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel player2Score = new JLabel(""+ MazePuzzle.player2Score);
        player2Score.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2Score.setFont(new Font("Serif", Font.BOLD, 18));
        player2Score.setHorizontalAlignment(SwingConstants.CENTER);
        player2Score.setMaximumSize(new Dimension(175,30));
        player2Score.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel displayPanel = new JPanel();

        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        displayPanel.setPreferredSize(new Dimension(250, 300));
        displayPanel.setMaximumSize(new Dimension(250, 300));
        //displayPanel.setBackground(Color.DARK_GRAY); //debug
        displayPanel.setVisible(true);

        displayPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        displayPanel.add(winner);
        displayPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        displayPanel.add(winnerText);
        displayPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        displayPanel.add(player1Score);
        displayPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        displayPanel.add(player1ScoreText);
        displayPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        displayPanel.add(player2Score);
        displayPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        displayPanel.add(player2ScoreText);

        this.getContentPane().add(displayPanel);

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));
        //selectionPanel.setBackground(Color.red); //debug
        selectionPanel.setVisible(true);
        selectionPanel.setPreferredSize(new Dimension(250, 40));
        selectionPanel.setMaximumSize(new Dimension(250, 40));

        selectionPanel.add(Box.createRigidArea(new Dimension(35, 0)));
        selectionPanel.add(settingsButton);
        selectionPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        selectionPanel.add(replayGameButton);

        this.getContentPane().add(selectionPanel);

        this.setTitle("Game Over");
        this.setPreferredSize(new Dimension(250, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Display the window.
        this.pack();
        this.setVisible(true);


        settingsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {

                NewGameMenu newGame = new NewGameMenu();
                dispose();
            }
        });

        replayGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                try {
                    //MazePuzzle.maze = MazePuzzle.generateMaze();
                    Maze newMaze = new Maze(MazePuzzle.maze);
                    dispose();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

}