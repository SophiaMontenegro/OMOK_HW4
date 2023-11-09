package noapplet.example;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private Board board;
    public BoardPanel(){//needs parameter Board board
        //fill later
    }
    @Override
    protected void paintComponent(Graphics g){
        Dimension d = getSize();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, d.width, d.height);

        for (int i = 50; i < 410; i+=20){
            g.setColor(Color.WHITE);
            g.drawLine(i,50, i, 390);
            g.setColor(Color.WHITE);
            g.drawLine(50, i, 390, i);
        }
    }
}
