package noapplet.example;

import noapplet.NoApplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

//import static com.sun.java.swing.action.ActionUtilities.IMAGE_DIR;

public class Omok extends NoApplet{
    private static JRadioButton human;
    private static JRadioButton computer;
    private static JButton play;
    private static JButton pair;
    private static BoardPanel boardPanel;


    public Omok() {

        var frame = new JFrame("Omok");
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar toolBar = getToolBar(frame);
        JPanel panel = getOptionPanel();
        frame.add(toolBar, BorderLayout.WEST);

        boardPanel = new BoardPanel();

        frame.add(boardPanel, BorderLayout.CENTER);

        JMenuBar menuBar = getMenuBar(frame);
        frame.setJMenuBar(menuBar);

        frame.add(panel, BorderLayout.NORTH);
        //frame.add(statusPanel, BorderLayout.NORTH);
        frame.setVisible(true);
        frame.pack();
    }

    private static JPanel getOptionPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(50, 50));
        human = new JRadioButton("Human");
        computer = new JRadioButton("Computer");
        play = new JButton("PLAY");
        pair = new JButton("PAIR");
        ButtonGroup group = new ButtonGroup();
        group .add(computer);
        group.add(human);
        JLabel select = new JLabel("Select opponent:");

        panel.add(select);
        panel.add(human);
        panel.add(computer);
        panel.add(play);
        panel.add(pair);

        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String displayOp = "";
                if(human.isSelected()){
                    displayOp = "Human Selected!";
                    setSelectButtonsEnable(false);
                    boardPanel.setGameMode("HUMAN");
                }
                else if(computer.isSelected()){
                    displayOp = "Computer Selected!";
                    setSelectButtonsEnable(false);
                    boardPanel.setGameMode("COMPUTER");
                }
                else{
                    displayOp="No Selection!";
                }
                JOptionPane.showMessageDialog(panel, displayOp); // fancy dialog window
            }

        });
        pair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //show connect panel
            }

        });
        return panel;
    }

    private static void setSelectButtonsEnable(boolean enable) {
        human.setEnabled(enable);
        computer.setEnabled(enable);
        play.setEnabled(enable);
    }

    private JMenuBar getMenuBar(JFrame frame){
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);
        menu.getAccessibleContext().setAccessibleDescription("Game menu");
        menuBar.add(menu);

        JMenuItem newPlay = new JMenuItem("PLAY", KeyEvent.VK_P);
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
                    boardPanel.clearBoard();
                    boardPanel.setEnableMouse(false);

                }else if (result == JOptionPane.NO_OPTION) {
                    //resume game
                    //do nothing
                }
            }

        });
        menu.add(newPlay);
        //help button
        JMenuItem helpButton = new JMenuItem("HELP", KeyEvent.VK_H);
        helpButton.setIcon(createImageIcon("help.png"));
        helpButton.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_H, InputEvent.ALT_DOWN_MASK));
        helpButton.getAccessibleContext().setAccessibleDescription(
                "Help with game");
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //instructions
                JOptionPane.showMessageDialog(frame, "The object of Omok is to get five consecutive stones.\nWhen its your turn try your best to win! \nHave fun!");
            }

        });
        menu.add(helpButton);

        //exit button
        JMenuItem exitButton = new JMenuItem("EXIT",KeyEvent.VK_E);
        exitButton.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, InputEvent.ALT_DOWN_MASK));
        exitButton.getAccessibleContext().setAccessibleDescription(
                "Exit a game");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame,"Are you sure you want to exit?", "Exit Game",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    //JOptionPane.showOptionDialog(frame, "Bye","Exiting...", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
                    frame.dispose();
                }else if (result == JOptionPane.NO_OPTION) {
                    //resume game
                    //do nothing
                }
            }

        });
        menu.add(exitButton);
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
                    JOptionPane.showMessageDialog(frame, "Here is your new game!");
                    setSelectButtonsEnable(true);
                    boardPanel.clearBoard();
                    boardPanel.setEnableMouse(false);

                }else if (result == JOptionPane.NO_OPTION) {
                    //resume game
                    //do nothing
                }
            }

        });
        newPlay.setToolTipText("Play a new game");
        newPlay.setFocusPainted(false);
        toolBar.add(newPlay);

        JButton help = new JButton(createImageIcon("help.png"));
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //help
                JOptionPane.showMessageDialog(frame, "The object of Omok is to get five consecutive stones.\nWhen its your turn try your best to win! \nHave fun!");
            }

        });
        help.setToolTipText("Help");
        help.setFocusPainted(false);
        toolBar.add(help);
        return toolBar;

    }

    private ImageIcon createImageIcon(String filename){
        URL imageURL = getClass().getResource("./"+filename);
        if(imageURL != null)
            return new ImageIcon(imageURL);
        return null;
    }
}

