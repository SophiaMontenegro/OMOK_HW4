package noapplet.example;

import noapplet.NoApplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

//import static com.sun.java.swing.action.ActionUtilities.IMAGE_DIR;

public class Omok extends NoApplet {
    private static JRadioButton human;
    private static JRadioButton computer;
    private static JButton play;

    public Omok() {

        var frame = new JFrame("Omok");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        JToolBar toolBar = getToolBar(frame);
        JPanel panel = getOptionPanel();
        frame.add(toolBar, BorderLayout.NORTH);

        JMenuBar menuBar = getMenuBar(frame);
        frame.setJMenuBar(menuBar);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setContentPane(panel);
        frame.pack();
    }

    private static JPanel getOptionPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 200));
        human = new JRadioButton("Human");
        computer = new JRadioButton("Computer");
        play = new JButton("Play");
        ButtonGroup group = new ButtonGroup();
        group .add(computer);
        group.add(human);
        JLabel select = new JLabel("Select opponent:");

        panel.add(select);
        panel.add(human);
        panel.add(computer);
        panel.add(play);

        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String displayOp = "";
                if (human.isSelected() && computer.isSelected()){
                    displayOp = "Only choose one!";
                }
                else if(human.isSelected()){
                    displayOp = "Human Selected!";
                    setSelectButtonsEnable(false);
                }
                else if(computer.isSelected()){
                    displayOp = "Computer Selected!";
                    setSelectButtonsEnable(false);
                }
                else{
                    displayOp="No Selection!";
                }
                JOptionPane.showMessageDialog(panel, displayOp); // fancy dialog window
            }

        });
        return panel;
    }

    private static void setSelectButtonsEnable(boolean b) {
        human.setEnabled(b);
        computer.setEnabled(b);
        play.setEnabled(b);
    }

    private JMenuBar getMenuBar(JFrame frame){
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);
        menu.getAccessibleContext().setAccessibleDescription("Game menu");
        menuBar.add(menu);

        JMenuItem newPlay = new JMenuItem("Play", KeyEvent.VK_P);
        newPlay.setIcon(createImageIcon("play.png"));
        newPlay.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, InputEvent.ALT_DOWN_MASK));
        newPlay.getAccessibleContext().setAccessibleDescription(
                "Play a new game");
        newPlay.addActionListener(new ActionListener() {
            //creates a new game
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame,"You want to start a new Game?", "New Game",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    //create new game
                    JOptionPane.showMessageDialog(frame, "Here is your new game!");
                    setSelectButtonsEnable(true);

                }else if (result == JOptionPane.NO_OPTION) {
                    //resume game
                    //do nothing
                }
            }

        });
        menu.add(newPlay);
        JMenuItem helpButton = new JMenuItem("Help", KeyEvent.VK_P);
        helpButton.setIcon(createImageIcon("help.png"));
        helpButton.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, InputEvent.ALT_DOWN_MASK));
        helpButton.getAccessibleContext().setAccessibleDescription(
                "Play a new game");
        helpButton.addActionListener(new ActionListener() {
            //creates a new game
            public void actionPerformed(ActionEvent e) {
                //instructions
                JOptionPane.showMessageDialog(frame, "The object of Omok is to get five consecutive stones.\nWhen its your turn try your best to win! \nHave fun!");
            }

        });
        menu.add(helpButton);
        return menuBar;
    }

    private JToolBar getToolBar(JFrame frame){
        JToolBar toolBar = new JToolBar("Omok");
        JButton newPlay = new JButton(createImageIcon("play.png"));
        newPlay.addActionListener(new ActionListener() {
            //creates a new game
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame,"You want to start a new Game?", "New Game",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    //create new game
                }else if (result == JOptionPane.NO_OPTION) {
                    //resume game
                    //do nothing
                }
            }

        });
        newPlay.setToolTipText("Play a new game");
        newPlay.setFocusPainted(false);
        toolBar.add(newPlay);

        JButton exit = new JButton(createImageIcon("exit.png"));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame,"Are you sure you want to exit?", "Exit Game",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    frame.dispose();
                }else if (result == JOptionPane.NO_OPTION) {
                    //resume game
                    //do nothing
                }
            }

        });
        exit.setToolTipText("Exit Game");
        exit.setFocusPainted(false);
        toolBar.add(exit);
        return toolBar;

    }

    private ImageIcon createImageIcon(String filename){
        URL imageURL = getClass().getResource("res"+filename);
        if(imageURL != null)
            return new ImageIcon(imageURL);
        return null;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new Omok());
        //new Omok(new String[]{"width=330", "height=350"}).run();
        /*
        Omok om = new Omok();
        om.setVisible(true);
        om.setDefaultCloseOperation(DISPOSE_ON_CLOSE);*/
    }

}

