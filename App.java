import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        
        int rowCount = 20;
        int columnCount = 19;
        int tileSize = 32;
        int boardWidth = columnCount*tileSize;
        int boardHeight = rowCount*tileSize;

        JFrame frame = new JFrame("PacMan");
        frame.setSize(boardWidth, boardHeight);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
