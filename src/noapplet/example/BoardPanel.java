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
    private Player currPlayer;//delete?

    private String gameMode; //delete?

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
    public void setGameMode(String mode){
        gameMode = mode;
        if(mode == "HUMAN"){
            player1 = new HumanPlayer("player1", Color.PINK);
            player2 = new HumanPlayer("player2", Color.BLACK);
        }
        else {//human vs computer
            player1 = new HumanPlayer("player1", Color.PINK);
            player2 = new ComputerPlayer("computer", Color.BLACK);
        }
        setCurrentPlayer();
        setEnableMouse(true);
    }

    public String getGameMode(){
        return gameMode;
    }

    public void clearBoard(){
        boardObj.clear();
        repaint();
    }

    public void setEnableMouse(boolean enable){
        enableMouse = enable;
    }

    private void place(int row, int col){
        String outcome;
        if(currPlayer.getName() == "computer"){
            outcome = currPlayer.requestMove(boardObj, -1, -1);
        }
        else {
            outcome = currPlayer.requestMove(boardObj, row, col);
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
