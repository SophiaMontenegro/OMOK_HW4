package noapplet.example;
import java.awt.*;
import java.util.Objects;
public class NetworkPlayer extends Player{
    NetworkPlayer(String name, Color color) {
        super(name, color);
    }
    @Override
    public String requestMove(Board board, int x, int y){
        //retrives website move

        //add to message box
        return board.validateMove(x, y, this);//Calls validateMove method from Board class to see if x and y values are valid move
    }
}
