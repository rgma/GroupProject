import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class NewGameMenu extends JFrame implements ActionListener {

    public NewGameMenu() {
        initUI();
    }

    private void initUI() {
        this.getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/PressStart2P.ttf")));
        } catch (IOException | FontFormatException e) {

        }

        //Create closeButton that changes when highlighted
        final JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Press Start 2P", Font.PLAIN, 10));
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setSize(new Dimension(85, 25));
        closeButton.setMaximumSize(new Dimension(85, 25));
        closeButton.setFocusPainted(false);
        closeButton.setForeground(Color.BLACK);
        closeButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setForeground(Color.RED);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setForeground(Color.BLACK);
            }
        });

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Create startButton that changes when highlighted
        final JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Press Start 2P", Font.PLAIN, 10));
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setSize(new Dimension(85, 25));
        startButton.setMaximumSize(new Dimension(85, 25));
        startButton.setFocusPainted(false);
        startButton.setForeground(Color.BLACK);
        startButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setForeground(Color.RED);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setForeground(Color.BLACK);
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    MazePuzzle.maze = MazePuzzle.generateMaze();
                    Maze newMaze = new Maze(MazePuzzle.maze);
                    dispose();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Create game information displaying JLabels
        JLabel numOfPlayers = new JLabel("NUMBER OF PLAYERS");
        numOfPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
        numOfPlayers.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

        JLabel sizeOfMaze = new JLabel("SIZE OF MAZE");
        sizeOfMaze.setAlignmentX(Component.CENTER_ALIGNMENT);
        sizeOfMaze.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

        JLabel gameDiff = new JLabel("GAME DIFFICULTY");
        gameDiff.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameDiff.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

        JLabel setTime = new JLabel("TIMER IN SECONDS");
        setTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        setTime.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

        //Create String arrays for setting game options
        String[] numPlayersArr = new String[]{"1 PLAYER", "2 PLAYER"};
        String[] gameDiffArr = new String[]{"EASY", "NORMAL", "HARD"};
        String[] sizeOfMazeArr = new String[]{"SMALL [9 X 9]", "MEDIUM [15 X 15]", "LARGE [21 X 21]"};
        String[] setTimeArr = new String[]{"40", "60", "80"};

        //Create JComboBoxes for for setting game options
        JComboBox<String> playerBox = new JComboBox<>(numPlayersArr);
        playerBox.setMaximumSize(new Dimension(200, 25));
        playerBox.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
        ((JLabel) playerBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        playerBox.setName("Number Players Setting");
        playerBox.addActionListener(this);

        JComboBox<String> gameDiffBox = new JComboBox<>(gameDiffArr);
        gameDiffBox.setMaximumSize(new Dimension(200, 25));
        gameDiffBox.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
        ((JLabel) gameDiffBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        gameDiffBox.setName("Game Difficulty Setting");
        gameDiffBox.addActionListener(this);

        JComboBox<String> sizeOfMazeBox = new JComboBox<>(sizeOfMazeArr);
        sizeOfMazeBox.setMaximumSize(new Dimension(200, 25));
        sizeOfMazeBox.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
        ((JLabel) sizeOfMazeBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        sizeOfMazeBox.setName("Maze Size Setting");
        sizeOfMazeBox.addActionListener(this);

        JComboBox<String> setTimeBox = new JComboBox<>(setTimeArr);
        setTimeBox.setMaximumSize(new Dimension(200, 25));
        setTimeBox.setFont(new Font("Press Start 2P", Font.PLAIN, 14));
        ((JLabel) setTimeBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        setTimeBox.setName("Timer Setting");
        setTimeBox.addActionListener(this);
        setTimeBox.setEditable(true);
        ComboBoxEditor editor = setTimeBox.getEditor();
        JTextField textField = (JTextField) editor.getEditorComponent();
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setHorizontalAlignment(JLabel.CENTER);

        //Create a panel to hold option JComboBoxes
        JPanel optionPanel = new JPanel();

        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        optionPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        optionPanel.setPreferredSize(new Dimension(250, 330));
        optionPanel.setMaximumSize(new Dimension(250, 330));
        optionPanel.setVisible(true);

        //Add UI option selection elements to panel
        optionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        optionPanel.add(playerBox);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        optionPanel.add(numOfPlayers);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        optionPanel.add(sizeOfMazeBox);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        optionPanel.add(sizeOfMaze);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        optionPanel.add(gameDiffBox);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        optionPanel.add(gameDiff);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        optionPanel.add(setTimeBox);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        optionPanel.add(setTime);
        this.getContentPane().add(optionPanel);

        //Create a panel to hold buttons
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));

        selectionPanel.setVisible(true);
        selectionPanel.setPreferredSize(new Dimension(250, 40));
        selectionPanel.setMaximumSize(new Dimension(250, 40));

        //Add UI option selection elements
        selectionPanel.add(Box.createRigidArea(new Dimension(25, 0)));
        selectionPanel.add(closeButton);
        selectionPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        selectionPanel.add(startButton);
        this.getContentPane().add(selectionPanel);

        this.setTitle("New Game");
        this.setPreferredSize(new Dimension(250, 410));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Display the window.
        this.pack();
        this.setVisible(true);

        //Set game default values
        MazePuzzle.difficulty = MazePuzzle.Difficulty.EASY;
        MazePuzzle.mazeSize = 9;
        MazePuzzle.numPlayers = 1;
        MazePuzzle.time = 40;
        /*
        selectionPanel.setBackground(Color.red); //debug
        this.getContentPane().setBackground(Color.yellow); //debug
        optionPanel.setBackground(Color.DARK_GRAY); //debug
        */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        String newSelection = (String) cb.getSelectedItem();

        if (cb.getName().contains("Number Players Setting")) {
            if (newSelection.contains("1 PLAYER")) {
                MazePuzzle.numPlayers = 1;
            } else {
                MazePuzzle.numPlayers = 2;
            }
        } else if (cb.getName().contains("Maze Size Setting")) {
            if (newSelection.contains("SMALL")) {
                MazePuzzle.mazeSize = 9;

            } else if (newSelection.contains("MEDIUM")) {
                MazePuzzle.mazeSize = 15;

            } else {
                MazePuzzle.mazeSize = 21;
            }
        } else if (cb.getName().contains("Game Difficulty Setting")) {
            if (newSelection.contains("EASY")) {
                MazePuzzle.difficulty = MazePuzzle.Difficulty.EASY;
            } else if (newSelection.contains("NORMAL")) {
                MazePuzzle.difficulty = MazePuzzle.Difficulty.MEDIUM;
            } else {
                MazePuzzle.difficulty = MazePuzzle.Difficulty.HARD;
            }
        } else if (cb.getName().contains("Timer Setting")) {
            int time = Integer.parseInt(newSelection);
            if (time > 0) {
                if (time <= 999) {
                    MazePuzzle.time = time;
                //Set max time
                } else {
                    MazePuzzle.time = 999;

                }
            }
        }
    }
}