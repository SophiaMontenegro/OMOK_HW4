package noapplet.example;

public class Main {
    public Main(){
        Board logic = new Board(15);
        BoardPanel boardPanel = new BoardPanel(logic);
        javax.swing.SwingUtilities.invokeLater(() -> new Omok());
    }
}
