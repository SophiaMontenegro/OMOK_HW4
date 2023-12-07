package noapplet.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectURL {
    private String pid;
    private String serverURL;
    private String choice;
    public ConnectURL(String serverURL, String choice) {
        this.serverURL = serverURL;
        this.choice = choice;
    }
    public String sendGet(){
        HttpURLConnection connect = null;

        String path = "/new/?strategy=" + choice; //include choice
        try{
            URL url = new URL(serverURL + path);
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
    public String getServerURL(){
        return serverURL;
    }
    public String getGameOption(){
        return choice;
    }
    public String getPid(){
        return pid;
    }

}
