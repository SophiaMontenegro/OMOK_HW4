package noapplet.example;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.List;

public class BoardPanel extends JPanel {
    private Board boardObj; //delete?

    private Player[][] cells; //array

    private String status;
    private static final int boardSize = 15;//fixed size
    private static final int cellSize = 20; //size of each cell in the board in GUI
    private static final int stoneSize = 10;

    private Player player1; //main user
    private Player player2; //can be another player or computer
    private Player currPlayer;

    private String gameMode;
    private OmokLog log;
    private boolean enableMouse = false;

    public BoardPanel(){
        boardObj = new Board();
        cells = boardObj.cells();

        status = "Welcome!";


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(enableMouse){
                    int row = e.getY() / cellSize;
                    int col = e.getX() /cellSize;
                    place(row, col);
                }
            }
        });
    }
    public void createWebGame(String URL, String strategy){
        log = new OmokLog();
        setGameMode("NETWORK");
        log.connectURL(URL, strategy);
    }
    public void setGameMode(String mode){
        gameMode = mode;
        if(mode == "HUMAN"){
            player1 = new HumanPlayer("player1", Color.PINK);
            player2 = new HumanPlayer("player2", Color.BLACK);
            setCurrentPlayer();
        }
        else if(mode == "COMPUTER"){
            player1 = new HumanPlayer("player1", Color.PINK);
            player2 = new ComputerPlayer("computer", Color.BLACK);
            setCurrentPlayer();
        }
        else{//network
            player1 = new HumanPlayer("player1", Color.PINK);
            player2 = new HumanPlayer("network", Color.BLACK);
            currPlayer = player1;
        }
        setEnableMouse(true);
    }

    public String getGameMode(){
        return gameMode;
    }

    public void clearBoard(){
        boardObj.clear();
        repaint();
        if(log != null){
            log.close();
        }
    }

    public void setEnableMouse(boolean enable){
        enableMouse = enable;
    }

    private void place(int row, int col){
        String outcome = " ";
        if(gameMode == "NETWORK"){
            placeWebGame(row, col, "player1");
            return; //don't go through the rest of the method
        }
        else{//gameMode is Computer or Human
            if(currPlayer.getName() == "computer"){
                outcome = currPlayer.requestMove(boardObj, -1, -1);
            }
            else {
                outcome = currPlayer.requestMove(boardObj, row, col);
            }
        }
        if(outcome == "STONE_PLACED"){
            repaint();
            swapCurrentPlayer();
        }
        else if(outcome == "PLAYER_WIN"){
            repaint();
            JOptionPane.showMessageDialog(this,
                        currPlayer.getName() + " wins!!!!!!!",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            setEnableMouse(false);
        }
        if(outcome == "BOARD_FULL"){
            repaint();
            JOptionPane.showMessageDialog(this,
                        "DRAW!",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            setEnableMouse(false);
        }
    }
    private void placeWebGame(int row, int col, String playerName) {
        String outcome;

        if (currPlayer.getName() == "player1") {
            outcome = currPlayer.requestMove(boardObj, row, col);//clicked
            if (outcome == "STONE_PLACED") {//send only if its valid
                //send move
                String response = log.sendPlay(row, col);
                //split response
                String[] responseArray = response.split("\"");
                String stringX = responseArray[18];
                if(stringX.length() == 4){
                    stringX = stringX.substring(1, 3);
                }
                else{
                    stringX = stringX.substring(1, 2);
                }
                String stringY = responseArray[20];
                if(stringY.length() == 4){
                    stringY = stringY.substring(1, 3);
                }
                else{
                    stringY = stringY.substring(1, 2);
                }
                int moveX = Integer.valueOf(stringX);
                int moveY = Integer.valueOf(stringY);
                placeStoneGraphic(outcome);
                currPlayer = player2;
                status = currPlayer.getName() + "'s turn!";
                //disable mouse
                setEnableMouse(false);
                placeWebGame(moveX, moveY, "network");
            }
            if(outcome == "PLAYER_WIN"){
                placeStoneGraphic(outcome);//winner
            }
        } else {//network
            outcome = currPlayer.requestMove(boardObj, row, col);
            placeStoneGraphic(outcome);
            currPlayer = player1;
            status = currPlayer.getName() + "'s turn!";
            //enable mouse
            setEnableMouse(true);
        }
    }
    private void placeStoneGraphic(String outcome){
        if(outcome == "STONE_PLACED"){
            repaint();
        }
        else if(outcome == "PLAYER_WIN"){
            repaint();
            JOptionPane.showMessageDialog(this,
                    currPlayer.getName() + " wins!!!!!!!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
            setEnableMouse(false);
        }
        if(outcome == "BOARD_FULL"){
            repaint();
            JOptionPane.showMessageDialog(this,
                    "DRAW!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
            setEnableMouse(false);
        }
    }

    public void setCurrentPlayer(){
        //Randomly selects the first player
        Random coinToss = new Random();
        if(coinToss.nextBoolean()) currPlayer = player1;
        else currPlayer = player2;

        status = currPlayer.getName() + "'s turn!";
        repaint();
    }
    public void swapCurrentPlayer(){
        // Swaps players
        if (currPlayer == player1)
            currPlayer = player2;
        else currPlayer = player1;

        status = currPlayer.getName() + "'s turn!";
    }

    public String getStatus(){
        return status;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Dimension d = getSize();

        g.setColor(Color.WHITE);
        g.fillRect(0,0, d.width, d.height);

        for (int i = 0; i < boardSize; i++) {//center the board at some point
            for (int j = 0; j < boardSize; j++) {
                int x = j * cellSize;
                int y = i * cellSize;
                g.setColor(Color.WHITE);
                g.fillRect(x, y, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellSize, cellSize);
                if (cells[i][j] != null) {
                    g.setColor(cells[i][j].getColor());
                    g.fillOval(x + (cellSize - stoneSize) / 2, y + (cellSize - stoneSize) / 2, stoneSize, stoneSize);
                }
            }
        }

        List<Place> winningRow = boardObj.winningRow();

        if(winningRow.size() == 5){
            for(int i = 0; i < winningRow.size(); i++){
                Place location = winningRow.get(i);
                int x1 = location.x * cellSize;
                int y1 = location.y * cellSize;
                g.setColor(Color.GREEN);
                g.fillOval(y1 + (cellSize - stoneSize) / 2, x1 + (cellSize - stoneSize) / 2, stoneSize, stoneSize);
            }
        }
        g.setColor(Color.BLACK);
        g.drawString(status, 120, 350);//status

    }


}
