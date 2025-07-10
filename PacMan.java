import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;



public class PacMan extends JPanel{



    class Block{

        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;

        Block(int x,int y,int width,int height,Image image){
            this.image=image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }
    }
    
    private int rowCount = 21;
    private int columnCount = 19;
    private int tileSize = 32;
    private int boardWidth = columnCount*tileSize;
    private int boardHeight = rowCount*tileSize;

    private Image wallImage;
    private Image blueGhostImage;
    private Image redGhostImage;
    private Image pinkGhostImage;
    private Image orangeGhostImage;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;

    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };

    
    PacMan(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(Color.BLACK);

        wallImage = new ImageIcon(getClass().getResource("./wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("./blueGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("./redGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("./pinkGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("./orangeGhost.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("./pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("./pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("./pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("./pacmanRight.png")).getImage();

        this.walls = new HashSet<>();
        this.ghosts = new HashSet<>();
        this.foods = new HashSet<>();

        loadMap();
    }

    public void loadMap(){

        for (int r = 0; r < rowCount; r++) {

            String row = tileMap[r];

            for (int c = 0; c < columnCount; c++) {
                
                char tileMapChar = row.charAt(c);

                int x = c*tileSize;
                int y = r*tileSize;

                if(tileMapChar == 'X'){
                    Block wall = new Block(x,y,tileSize,tileSize,wallImage);
                    walls.add(wall);
                }
                else if(tileMapChar == 'r'){
                    Block redGhost = new Block(x,y,tileSize,tileSize,redGhostImage);
                    ghosts.add(redGhost);
                }
                else if(tileMapChar == 'o'){
                    Block orangeGhost = new Block(x,y,tileSize,tileSize,orangeGhostImage);
                    ghosts.add(orangeGhost);
                }
                else if(tileMapChar == 'p'){
                    Block pinkGhost = new Block(x,y,tileSize,tileSize,pinkGhostImage);
                    ghosts.add(pinkGhost);
                }
                else if(tileMapChar == 'b'){
                    Block blueGhost = new Block(x,y,tileSize,tileSize,blueGhostImage);
                    ghosts.add(blueGhost);
                }
                else if(tileMapChar == 'P'){
                    this.pacman = new Block(x, y, tileSize, tileSize, pacmanRightImage);
                }
                else if(tileMapChar == ' '){
                    Block food = new Block(x+14 , y + 14, 4, 4, null);
                    foods.add(food);
                }

            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(pacman.image, pacman.x, pacman.y,pacman.width,pacman.height,null);

        for (Block ghost : ghosts) {
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height,null);
        }

        for (Block wall : walls) {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height,null);
        }

        g.setColor(Color.WHITE);
        for (Block food : foods) {
            g.fillRect(food.x, food.y, food.width, food.height);
        }
    }
}
