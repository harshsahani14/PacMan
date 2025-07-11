import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PacMan extends JPanel implements ActionListener,KeyListener {

    class Block{

        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;

        char direction = 'R';
        int velocityX = 0;
        int velocityY = 0;

        Block(int x,int y,int width,int height,Image image){
            this.image=image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }

        public void changeDirection(char direction){
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x+=this.velocityX;
            this.y+=this.velocityY;

            for (Block wall : walls) {
                if(collision(wall, pacman)){
                    pacman.x-=pacman.velocityX;
                    pacman.y-=pacman.velocityY;
                    this.direction=prevDirection;
                    updateVelocity();
                    break;
                }
            }


        }

        public void updateVelocity(){
            if(this.direction=='U'){
                this.velocityX = 0;
                this.velocityY = -tileSize/4;
            }
            else if(this.direction=='D'){
                this.velocityX = 0;
                this.velocityY = tileSize/4;
            }
            else if(this.direction=='R'){
                this.velocityX = tileSize/4;
                this.velocityY = 0;
            }
            else if(this.direction=='L'){
                this.velocityX = -tileSize/4;
                this.velocityY = 0;
            }
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

    Timer gameLoop;

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
        addKeyListener(this);
        setFocusable(true);


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

        gameLoop = new Timer(50, this);
        gameLoop.start();
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


    public void move(){
        pacman.x+=pacman.velocityX;
        pacman.y+=pacman.velocityY;

        for (Block wall : walls) {
            if(collision(wall, pacman)){
                pacman.x-=pacman.velocityX;
                pacman.y-=pacman.velocityY;
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    public boolean collision(Block a, Block b) {
        return  a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("Key pressed:"+e.getKeyCode());

        if(e.getKeyCode() == KeyEvent.VK_UP){
            pacman.changeDirection('U');
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            pacman.changeDirection('D');
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            pacman.changeDirection('L');
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            pacman.changeDirection('R');
        }

        if(pacman.direction=='U'){
            pacman.image = pacmanUpImage;
        }
        else if(pacman.direction=='D'){
            pacman.image = pacmanDownImage;
        }
        else if(pacman.direction=='L'){
            pacman.image = pacmanLeftImage;
        }
        else if(pacman.direction=='R'){
            pacman.image = pacmanRightImage;
        }
    }
}
