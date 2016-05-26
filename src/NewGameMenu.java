import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class NewGameMenu extends JFrame implements ActionListener {

    public NewGameMenu() {

        initUI();
    }

    private void initUI() {
        //this.getContentPane().setBackground(Color.yellow); //debug

        this.getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton closeButton = new JButton("Close");
        JButton startButton = new JButton("Start");
        closeButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        startButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        JLabel numOfPlayers = new JLabel("NUMBER OF PLAYERS");
        numOfPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
        numOfPlayers.setFont(new Font("Serif", Font.BOLD, 18));

        JLabel sizeOfMaze = new JLabel("SIZE OF MAZE");
        sizeOfMaze.setAlignmentX(Component.CENTER_ALIGNMENT);
        sizeOfMaze.setFont(new Font("Serif", Font.BOLD, 18));

        JLabel gameDiff = new JLabel("GAME DIFFICULTY");
        gameDiff.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameDiff.setFont(new Font("Serif", Font.BOLD, 18));

        JLabel setTime = new JLabel("TIMER IN SECONDS");
        setTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        setTime.setFont(new Font("Serif", Font.BOLD, 18));

        String[] numPlayersArr = new String[]{"1 PLAYER", "2 PLAYER"};
        String[] gameDiffArr = new String[]{"EASY", "NORMAL", "HARD"};
        String[] sizeOfMazeArr = new String[]{"SMALL [9 X 9]", "MEDIUM [15 X 15]", "LARGE [21 X 21]"};
        String[] setTimeArr = new String[]{"40", "60", "80"};

        JComboBox<String> playerBox = new JComboBox<>(numPlayersArr);
        playerBox.setMaximumSize(new Dimension(200, 25));
        playerBox.setFont(new Font("Serif", Font.BOLD, 16));
        ((JLabel) playerBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        playerBox.setName("Number Players Setting");
        playerBox.addActionListener(this);

        JComboBox<String> gameDiffBox = new JComboBox<>(gameDiffArr);
        gameDiffBox.setMaximumSize(new Dimension(200, 25));
        gameDiffBox.setFont(new Font("Serif", Font.BOLD, 16));
        ((JLabel) gameDiffBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        gameDiffBox.setName("Game Difficulty Setting");
        gameDiffBox.addActionListener(this);

        JComboBox<String> sizeOfMazeBox = new JComboBox<>(sizeOfMazeArr);
        sizeOfMazeBox.setMaximumSize(new Dimension(200, 25));
        sizeOfMazeBox.setFont(new Font("Serif", Font.BOLD, 16));
        ((JLabel) sizeOfMazeBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        sizeOfMazeBox.setName("Maze Size Setting");
        sizeOfMazeBox.addActionListener(this);

        JComboBox<String> setTimeBox = new JComboBox<>(setTimeArr);
        setTimeBox.setMaximumSize(new Dimension(200, 25));
        setTimeBox.setFont(new Font("Serif", Font.BOLD, 16));
        ((JLabel) setTimeBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        setTimeBox.setName("Timer Setting");
        setTimeBox.addActionListener(this);
        setTimeBox.setEditable(true);
        ComboBoxEditor editor = setTimeBox.getEditor();
        JTextField textField = (JTextField)editor.getEditorComponent();
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setHorizontalAlignment(JLabel.CENTER);





        JPanel optionPanel = new JPanel();

        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        optionPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        optionPanel.setPreferredSize(new Dimension(250, 365));
        optionPanel.setMaximumSize(new Dimension(250, 365));
        //optionPanel.setBackground(Color.DARK_GRAY); //debug
        optionPanel.setVisible(true);

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

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));
        //selectionPanel.setBackground(Color.red); //debug
        selectionPanel.setVisible(true);
        selectionPanel.setPreferredSize(new Dimension(250, 40));
        selectionPanel.setMaximumSize(new Dimension(250, 40));

        selectionPanel.add(Box.createRigidArea(new Dimension(45, 0)));
        selectionPanel.add(closeButton);
        selectionPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        selectionPanel.add(startButton);

        this.getContentPane().add(selectionPanel);

        this.setTitle("New Game");
        this.setPreferredSize(new Dimension(250, 450));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Display the window.
        this.pack();
        this.setVisible(true);


        closeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                try {
                    MazePuzzle.maze = MazePuzzle.generateMaze();
                    Maze newMaze = new Maze(MazePuzzle.maze);
                    dispose();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Set game default values
        MazePuzzle.difficulty = MazePuzzle.Difficulty.EASY;
        MazePuzzle.mazeSize = 9;
        MazePuzzle.numPlayers = 1;
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

            System.out.println("Num players set: ");
            System.out.println(newSelection);
        } else if (cb.getName().contains("Maze Size Setting")) {
            if (newSelection.contains("SMALL")) {
                //MazePuzzle.MAZE_SIZE = 9;
                MazePuzzle.mazeSize = 9;
                
            } else if (newSelection.contains("MEDIUM")) {
                //MazePuzzle.MAZE_SIZE = 15;
                MazePuzzle.mazeSize = 15;
                
            } else {
            	//MazePuzzle.MAZE_SIZE = 21;
            	MazePuzzle.mazeSize = 21;
            }

            System.out.println("Maze size set: ");
            System.out.println(MazePuzzle.mazeSize);

        } else if (cb.getName().contains("Game Difficulty Setting")) {
            if (newSelection.contains("EASY")) {
                MazePuzzle.difficulty = MazePuzzle.Difficulty.EASY;
            } else if (newSelection.contains("NORMAL")) {
                MazePuzzle.difficulty = MazePuzzle.Difficulty.MEDIUM;
            } else {
                MazePuzzle.difficulty = MazePuzzle.Difficulty.HARD;
            }
            System.out.println("Difficulty set: ");
            System.out.println(MazePuzzle.difficulty);
        } else if (cb.getName().contains("Timer Setting")) {
        	int time = Integer.parseInt(newSelection);
        	if (time == 40) {
                MazePuzzle.time = 40;
            } else if (time == 60) {
                MazePuzzle.time = 60;
            } else {
            	MazePuzzle.time = 80;
            }
            MazePuzzle.time = time;
            System.out.println("Timer set: ");
            System.out.println(time);
        }
    }
}