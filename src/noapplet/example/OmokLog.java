package noapplet.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OmokLog{
    private JTextArea display;
    private JFrame logFrame;
    private String pid;
    private String URL;
    public OmokLog(){
        //nothing
    }
    private String sendGet(String query){
        HttpURLConnection connect = null;
        try{
            URL url = new URL(query);
            addToLog(query);
            connect = (HttpURLConnection)url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while((line = in.readLine()) != null){
                response.append(line);
            }
            String responseReturn = response.toString();
            addToLog(responseReturn);
            return responseReturn;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connect != null){
                connect.disconnect();
            }
        }
        return null;
    }
    private boolean validURL(String query){
        HttpURLConnection connect = null;
        try{
            new URL(query).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void connectURL(String URL, String strategy){
        createLogFrame();
        //add send message
        this.URL = URL; //save url
        String path = "/new/?strategy=" + strategy;
        String query = URL + path;
        boolean valid = validURL(query);
        if(valid) {
            String connectResponse = sendGet(query);
            if (connectResponse == null) {
                JOptionPane.showMessageDialog(logFrame, "Fail to Connect! Try Again");
                return;
            }
            //retrieve pid
            String[] responseArray = connectResponse.split("\"");
            pid = responseArray[5];
        }
        else{
            JOptionPane.showMessageDialog(logFrame, "Fail to Connect! Try Again");
        }
    }
    public String sendPlay(int x, int y){
        String path =  "/play/?pid="+ pid +"&x=" + x + "&y=" + y;
        String query = URL + path;
        String connectResponse = sendGet(query);
        return connectResponse;
    }

    public void addToLog(String message){
        display.append(message);
        display.append("\n");
    }
    public void close(){
        if(logFrame != null){
            logFrame.dispose();
        }
    }
    public void createLogFrame(){
        //should display received and response messages
        logFrame = new JFrame("Omok Log");
        logFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        logFrame.setSize(450,300);
        logFrame.setVisible(true);

        //panel for messages
        JPanel messageBox = new JPanel();
        messageBox.setPreferredSize(new Dimension(300, 200));
        display = new JTextArea(13, 40);
        display.setEditable(false);
        var scroll = new JScrollPane(display);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);//changed
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
                int result = JOptionPane.showConfirmDialog(logFrame,"Are you sure you want to exit Log? You will not be able to reopen this game's log", "Exit Log",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    //close window
                    logFrame.dispose();

                }else if (result == JOptionPane.NO_OPTION) {
                    //do nothing
                    //resume
                }
            }
        });
        logFrame.add(messageBox);
    }
}
