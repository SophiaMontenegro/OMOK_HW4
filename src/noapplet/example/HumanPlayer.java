package noapplet.example;

import java.util.Scanner;

import static java.lang.Integer.valueOf;

public class HumanPlayer extends Player {

    HumanPlayer(String name, String symbol) {
        super(name, symbol);
    }
    @Override
    public String requestMove(Board board, int x, int y){

        return board.validateMove(x, y, this);//Calls validateMove method from Board class to see if x and y values are valid move

    }

}


