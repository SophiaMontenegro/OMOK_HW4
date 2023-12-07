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
        System.out.println(valid);
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
    }
    public static void main(String[] args) {
        OmokLog logTest = new OmokLog();
        logTest.connectURL("http://omok.atwebpages.com", "Smart");
        int x = 4;
        int y = 5;
        String response = logTest.sendPlay(x,y);
        System.out.println(response);

        String[] responseArray = response.split("\"");
        String stringX = responseArray[18];
        System.out.println(stringX);
        stringX = stringX.substring(1,2);
        String stringY = responseArray[20];
        System.out.println(stringY);
        stringY = stringY.substring(1,2);
        int moveX = Integer.valueOf(stringX);
        int moveY = Integer.valueOf(stringY);

        System.out.println(moveX);
        System.out.println(moveY);
    }

}
