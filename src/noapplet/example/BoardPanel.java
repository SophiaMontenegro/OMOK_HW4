package noapplet.example;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.Graphics;

public class BoardPanel extends JPanel {
    private Board board;
    protected int delay = 100;
    protected Timer timer;
    public BoardPanel(Board board){
        //fill later

    }

    public void init(){
        timer = new Timer(delay, e -> repaint());
    }
    public void start() {
        timer.start();
    }
    public void stop() {
        timer.stop();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        Dimension d = getSize();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0, d.width, d.height);

        for (int i = 50; i < 410; i+=20){
            g2d.setColor(Color.BLACK);
            g2d.drawLine(i,50, i, 390);
            g2d.setColor(Color.BLACK);
            g2d.drawLine(50, i, 390, i);
        }
        g2d.dispose();
    }

}
