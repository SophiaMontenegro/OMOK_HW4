package noapplet.example;

import noapplet.NoApplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

//import static com.sun.java.swing.action.ActionUtilities.IMAGE_DIR;

public class Omok extends NoApplet {
    //hover outline of where to place
    /*
    private final static Dimension DEFAULT_DIMENSION = new Dimension(400, 600);
    public Omok(){
        this(DEFAULT_DIMENSION);
    }

    public Omok(Dimension dim) {
        super((JFrame) null, "Omok");
        configureGUI();
        setSize(dim);
        //setResizable(false);
        setLocationRelativeTo(null);
    }*/

    public Omok() {

        var frame = new JFrame("Omok");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        JToolBar toolBar = getToolBar(frame);
        JPanel panel = getOptionPanel();
        panel.add(toolBar);

        frame.add(panel);
        //frame.add(toolBar, BorderLayout.NORTH);
        //frame.setContentPane(panel);
        //frame.pack();
        frame.setVisible(true);
        frame.setContentPane(panel);
        frame.pack();

        /*
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);*/

    }

    private static JPanel getOptionPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 200));
        JRadioButton human = new JRadioButton("Human");
        JRadioButton computer = new JRadioButton("Computer");
        JButton play = new JButton("Play");
        ButtonGroup group = new ButtonGroup();

        JLabel select = new JLabel("Select opponent:");

        panel.add(select);
        panel.add(human);
        panel.add(computer);
        panel.add(play);
        //panel.add(label);

        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String displayOp = "";
                if (human.isSelected() && computer.isSelected()){
                    displayOp = "Only choose one!";
                }
                else if(human.isSelected()){
                    displayOp = "Human Selected!";
                }
                else if(computer.isSelected()){
                    displayOp = "Computer Selected!";
                }
                else{
                    displayOp = "No Selection was made!";
                }
                JOptionPane.showMessageDialog(panel, displayOp); // fancy dialog window
            }

        });
        return panel;
    }

    private JToolBar getToolBar(JFrame frame){
        JToolBar toolBar = new JToolBar("Omok");
        JButton play1 = new JButton(createImageIcon("play.png"));
        final JLabel label = new JLabel();
        play1.addActionListener(new ActionListener() {
            //creates a new game
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame,"You want to start a new Game?", "New Game",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    label.setText("You selected: Yes");
                }else if (result == JOptionPane.NO_OPTION){
                    label.setText("You selected: No");
                }else {
                    label.setText("None selected");
                }
            }

        });
        play1.setToolTipText("Play a new game");
        play1.setFocusPainted(false);
        toolBar.add(play1);
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

