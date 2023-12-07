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
    private static BoardPanel boardPanel;
    private String serverURLConnect;
    private String response;


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

        //network
        JMenuItem network = new JMenuItem("NETWORK", KeyEvent.VK_N);
        network.setIcon(createImageIcon("network.png"));
        network.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, InputEvent.ALT_DOWN_MASK));
        network.getAccessibleContext().setAccessibleDescription(
                "Connect to Website");
        network.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //open frame to connect to network
                networkFrame();
            }
        });
        menu.add(network);

        //log
        JMenuItem log = new JMenuItem("LOG", KeyEvent.VK_L);
        log.setIcon(createImageIcon("log.png"));
        log.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_L, InputEvent.ALT_DOWN_MASK));
        log.getAccessibleContext().setAccessibleDescription(
                "Network Log");
        log.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //open log
                logFrame();
            }
        });
        menu.add(log);
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

    private void networkFrame(){
        JFrame networkFrame = new JFrame("Omok Network");
        networkFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        networkFrame.setSize(300,200);
        networkFrame.setVisible(true);

        JPanel networkPanel = new JPanel();
        networkPanel.setPreferredSize(new Dimension(300, 50));
        JLabel serverLabel = new JLabel("Server URL");
        JTextField serverURL = new JTextField(18);
        networkPanel.add(serverLabel);
        networkPanel.add(serverURL);
        JButton defaultURL = new JButton("DEFAULT");
        defaultURL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                serverURL.setText("http://omok.atwebpages.com");
            }
        });
        networkPanel.add(defaultURL);
        JPanel gameOptionPanel = new JPanel();
        gameOptionPanel.setPreferredSize(new Dimension(50, 50));
        JRadioButton random = new JRadioButton("RANDOM");
        JRadioButton smart = new JRadioButton("SMART");

        ButtonGroup group = new ButtonGroup();
        group.add(random);
        group.add(smart);
        JLabel gameOption = new JLabel("Choose Game Type:");
        gameOptionPanel.add(gameOption);
        gameOptionPanel.add(random);
        gameOptionPanel.add(smart);
        networkFrame.add(gameOptionPanel, BorderLayout.CENTER);

        networkFrame.add(networkPanel, BorderLayout.NORTH);

        JPanel networkButtons = new JPanel();
        networkButtons.setPreferredSize(new Dimension(300, 50));
        JButton cancel = new JButton("CANCEL");
        JButton save = new JButton("SAVE");

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //close window
                networkFrame.dispose();
            }
        });

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //must have choosen a strategy
                String displayOp = "";
                String strategy = "s";
                boolean ableToConnect = false;
                if(random.isSelected()){
                    displayOp = "Random Selected!";
                    //save option
                    strategy = "r";
                    ableToConnect = true;
                }
                else if(smart.isSelected()){
                    displayOp = "Smart Selected!";
                    //save option
                    strategy = "s";
                    ableToConnect = true;
                }
                else{
                    displayOp = "No Option Selection!";
                }
                if(serverURL.getText().length() <= 0){//no inputted serverURL
                    displayOp = "No Server URL Inputted!";
                    ableToConnect = false;
                }
                serverURLConnect = serverURL.getText();
                ConnectURL connectToWeb = new ConnectURL(serverURLConnect, strategy);
                String connectResponse = connectToWeb.sendGet();


                //connect to server
                if(connectResponse == null){
                    displayOp = "Connection Failed! Try again!";
                    ableToConnect = false;
                }

                //open log if connection is successful
                if(!ableToConnect){
                    JOptionPane.showMessageDialog(networkFrame, displayOp);
                }
                else{
                    //close networkFrame
                    response = connectResponse;
                    networkFrame.dispose();
                    logFrame();
                }
            }
        });
        networkButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        networkButtons.add(cancel);
        networkButtons.add(save);

        networkFrame.add(networkButtons, BorderLayout.SOUTH);
    }
    private void logFrame(){
        //should display received and response messages
        JFrame logFrame = new JFrame("Omok Log");
        logFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        logFrame.setSize(300,300);
        logFrame.setVisible(true);

        //panel for messages
        JPanel messageBox = new JPanel();
        messageBox.setPreferredSize(new Dimension(300, 200));
        var display = new JTextArea(13, 25);
        display.setEditable(false);
        var scroll = new JScrollPane(display);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        messageBox.add(scroll);

        //panel for close
        JPanel closeBar = new JPanel();
        closeBar.setPreferredSize(new Dimension(300, 50));
        var close = new JButton("CLOSE");
        closeBar.add(close);
        messageBox.add(closeBar);
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //close window
                logFrame.dispose();
            }
        });
        logFrame.add(messageBox);

        if(response != null){
            display.append(response);
            display.append("\n");
        }
    }
}

