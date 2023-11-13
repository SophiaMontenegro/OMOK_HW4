package noapplet.example;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class BoardPanel extends JPanel {
    private Board boardObj; //delete?

    private Player[][] cells;

    private String status;
    private static final int boardSize = 15;//fixed size
    private static final int cellSize = 20; //size of each cell in the board in GUI
    private static final int stoneSize = 10;

    private Player player1; //main user
    private Player player2; //can be another player or computer
    private Player currPlayer;//delete?

    private String gameMode; //delete?

    private int hoverRow = -1;//no valid position as default
    private int hoverCol = -1;//no valid position as default
    public BoardPanel(){
        boardObj = new Board();
        cells = boardObj.cells();

        status = "Welcome!";


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / cellSize;
                int col = e.getY() /cellSize;
                place(row, col);
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                hoverRow = e.getY() /cellSize;
                hoverCol = e.getX() /cellSize;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoverRow = -1;
                hoverCol = -1;
                repaint();
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
    }

    public String getGameMode(){
        return gameMode;
    }

    public void clearBoard(){
        boardObj.clear();
        repaint();
    }

    private void place(int row, int col){
        String outcome = currPlayer.requestMove(boardObj, row, col);
        if(outcome == "STONE_PLACED"){
            repaint();
        }
        if(outcome == "PLAYER_WIN"){
            repaint();
            if(boardObj.isWonBy(currPlayer)){//game over!!!!!
                JOptionPane.showMessageDialog(this,
                        currPlayer.getName() + " wins!!!!!!!",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        swapCurrentPlayer();
    }

    public void setCurrentPlayer(){
        //Randomly selects the first player
        Random coinToss = new Random();
        if(coinToss.nextBoolean()) currPlayer = player1;
        else currPlayer = player2;

        status = currPlayer + "'s turn!";
    }
    public void swapCurrentPlayer(){
        // Swaps players
        if (currPlayer == player1)
            currPlayer = player2;
        else currPlayer = player1;

        status = currPlayer + "'s turn!";
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
                if (i == hoverRow && j == hoverCol && cells[i][j] == null) {
                    g.setColor(new Color(120, 120, 120, 80)); //hover fix
                    g.fillRect(x, y, cellSize, cellSize);
                }
                if (cells[i][j] != null) {
                    g.setColor(cells[i][j].getColor());
                    g.fillOval(x + (cellSize - stoneSize) / 2, y + (cellSize - stoneSize) / 2, stoneSize, stoneSize);
                }
            }
        }
    }

}
