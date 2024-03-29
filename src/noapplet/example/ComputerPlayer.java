package noapplet.example;
import java.awt.*;
import java.util.Random;

public class ComputerPlayer extends Player {
    private String name;
    private String symbol;

    private int BOARD_SIZE;
    private static final int MAX_DEPTH = 2;

    private Board board;

    public ComputerPlayer(String name, Color color) {
        super(name, color);
    }

    @Override
    public String requestMove(Board board, int x, int y){
        this.BOARD_SIZE = board.size();
        this.board = board;
        //int[] coordinates = bestMove(board);
        boolean cont = true;
        Random rand = new Random();
        while(cont){
            int x1 = rand.nextInt(14);
            int y1 = rand.nextInt(14);
            String outcome = board.validateMove(x1, y1, this);
            if(outcome != "CELL_UNAVAILABLE") return outcome;
        }
        return "";
    }
    /*
    public int[] bestMove(Board board) {
        Player[][] cells = board.cells();
        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[2];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.isEmpty(i,j)) {
                    cells[i][j] = this;  // add AI
                    int moveScore = minimax(MAX_DEPTH, //somethind, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    cells[i][j] = null;

                    if (moveScore > bestScore) {
                        bestScore = moveScore;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }

        return move;
    }*/




}

