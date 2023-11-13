package noapplet.example;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardPanel extends JPanel {
    private Board boardObj; //delete?

    private Player[][] cells;
    private static final int boardSize = 15;//fixed size
    private static final int cellSize = 20; //size of each cell in the board in GUI
    private static final int stoneSize = 10;

    //private Player[][] board; //add?

    private Player player1; //main user
    private Player player2; //can be another player or computer

    private Player currPlayer;//delete?

    private String gameMode; //delete?

    private int hoverRow = -1;//no valid position as default
    private int hoverCol = -1;//no valid position as default
    public BoardPanel(Board boardObj){
        this.boardObj = boardObj;
        cells = boardObj.cells();
        //initBoard();//creates an empty player 2d array
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
/* //add?
    private void initBoard(){
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                board[i][j] = null;
            }
        }
    }*/

    public void setGameMode(String mode){
        gameMode = mode;
        if(mode == "HUMAN"){
            player1 = new HumanPlayer("player1", "0");
            player2 = new HumanPlayer("player2", "X");
        }
        else {//human vs computer
            player1 = new HumanPlayer("player1", "0");
            player2 = new ComputerPlayer("player2", "X");
        }
    }

    public String getGameMode(){
        return gameMode;
    }

    public void clearBoard(){
        //initBoard();
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
                        "Player " + currPlayer.getName() + " wins!!!!!!!",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Dimension d = getSize();

        g.setColor(Color.WHITE);
        g.fillRect(0,0, d.width, d.height);

        for (int i = 50; i < 410; i+=20){
            g.setColor(Color.BLACK);
            g.drawLine(i,50, i, 390);
            g.setColor(Color.BLACK);
            g.drawLine(50, i, 390, i);
        }
    }

}
