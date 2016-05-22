import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PreGame implements ActionListener {
    private JPanel p;
    private JButton bEasy;
    private JButton bMedium;
    private JButton bHard;
    private JLabel l;

    public PreGame()
    {
        createUI();
    }

    public void createUI()
    {
        p = new JPanel();
        p.setBackground(Color.gray);

        l = new JLabel("Select your difficulty:");

        bEasy = new JButton("Easy");
        bMedium = new JButton("Medium");
        bHard = new JButton("Hard");

        bEasy.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                MazePuzzle.difficulty = MazePuzzle.difficulty.EASY;
                System.out.println("EASY");
            }
        });

        bMedium.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                MazePuzzle.difficulty = MazePuzzle.difficulty.MEDIUM;
                System.out.println("MEDIUM");
            }
        });

        bHard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                MazePuzzle.difficulty = MazePuzzle.difficulty.HARD;
                System.out.println("HARD");
            }
        });

        p.add(l);
        p.add(bEasy);
        p.add(bMedium);
        p.add(bHard);

        JOptionPane.showMessageDialog(null,p,"Option Menu",JOptionPane.PLAIN_MESSAGE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
