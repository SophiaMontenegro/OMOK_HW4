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
    private String pid;
    public OmokLog(){
        //nothing
    }
    private String sendGet(String URL, String strategy){
        HttpURLConnection connect = null;

        String path = "/new/?strategy=" + strategy; //include choice
        try{
            URL url = new URL(URL + path);
            connect = (HttpURLConnection)url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while((line = in.readLine()) != null){
                response.append(line);
            }
            String responseReturn = response.toString();

            //retrieve pid
            String[] responseArray = responseReturn.split("\"");
            pid = responseArray[5];
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
    public void connectURL(String URL, String strategy){
        //add send message
        String connectResponse = sendGet(URL, strategy);
        createLogFrame();
        addToLog(connectResponse);
    }

    public void sendCommand(String command){
        //send something
        //add to display
    }

    public String retrieveResponse(){
        //retrieve
        //adc to display
        return null;
    }

    public void addToLog(String message){
        display.append(message);
        display.append("\n");
    }
    public void createLogFrame(){
        //should display received and response messages
        JFrame logFrame = new JFrame("Omok Log");
        logFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        logFrame.setSize(300,300);
        logFrame.setVisible(true);

        //panel for messages
        JPanel messageBox = new JPanel();
        messageBox.setPreferredSize(new Dimension(300, 200));
        display = new JTextArea(13, 25);
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

}
