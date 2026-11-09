import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;


public class Pacman extends JPanel  implements ActionListener , KeyListener{
    class Block {
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;
        char direction = 'U'; //U D L R
        int velocityX = 0;
        int velocityY = 0;

        Block(Image image,int x,int y,int width, int height) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }

        void updatedirection(char direction) {
            char prevdirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;
            for (Block wall : walls) {
                if (collision(this, wall)) {
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = prevdirection;
                    updateVelocity();
                }
            }
        }

        void updateVelocity() {
            if (this.direction == 'U') {
                this.velocityX = 0;
                this.velocityY = -tilesize/4;
            }

            else if (this.direction == 'D') {
                this.velocityX = 0;
                this.velocityY = tilesize/4;
            }

            else if (this.direction == 'L') {
                this.velocityX = -tilesize/4;
                this.velocityY= 0;
            }

            else if (this.direction == 'R') {
                this.velocityX = tilesize/4;
                this.velocityY = 0;
            }
        }
    }


    private int rowcount = 21;
    private int columncount = 19;
    private int tilesize = 32;
    private int boardwidth = columncount * tilesize;
    private int boardheight = rowcount * tilesize;

    private Image wallImage;
    private Image blueghostImage;
    private Image orangeghostImage;
    private Image pinkghostImage;
    private Image redghostImage;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    //X = wall, O = skip, P = pac man, ' ' = food
    //Ghosts: b = blue, o = orange, p = pink, r = red
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

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;

    Timer gameloop;
    char[] directions = {'U','D','L','R'}; // up down left right
    Random random = new Random();

    Pacman() {
        setPreferredSize(new Dimension(boardwidth, boardheight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        // Loading images
        wallImage = new ImageIcon(getClass().getResource("assets/wall.png")).getImage();
        blueghostImage = new ImageIcon(getClass().getResource("assets/blueGhost.png")).getImage();
        orangeghostImage = new ImageIcon(getClass().getResource("assets/orangeGhost.png")).getImage();
        pinkghostImage = new ImageIcon(getClass().getResource("assets/pinkGhost.png")).getImage();
        redghostImage = new ImageIcon(getClass().getResource("assets/redGhost.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("assets/pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("assets/pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("assets/pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("assets/pacmanRight.png")).getImage();

        loadMap();
        for (Block ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            ghost.updatedirection(newDirection);
        }
        // how long it takes to start timer
        gameloop = new Timer(50, this);
        gameloop.start();
    }

    public void loadMap() {
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for (int r = 0; r < rowcount; r++) {
            for (int c=0; c < columncount;c++) {
                String row = tileMap[r];
                char tileMapChar = row.charAt(c);

                int x = c*tilesize;
                int y = r*tilesize;

                if (tileMapChar == 'X') { // block wall
                    Block wall = new Block(wallImage,x,y,tilesize,tilesize);
                    walls.add(wall);
                }

                else if (tileMapChar== 'b') { //blue ghost
                    Block ghost = new Block(blueghostImage,x,y,tilesize,tilesize);
                    ghosts.add(ghost);
                }

                else if (tileMapChar== 'o') { //orange ghost
                    Block ghost = new Block(orangeghostImage,x,y,tilesize,tilesize);
                    ghosts.add(ghost);
                }

                else if (tileMapChar == 'p') { //pink ghost
                    Block ghost = new Block(pinkghostImage,x,y,tilesize,tilesize);
                    ghosts.add(ghost);
                }

                else if (tileMapChar == 'r') { // red ghost
                    Block ghost = new Block(redghostImage,x,y,tilesize,tilesize);
                    ghosts.add(ghost);
                }

                else if (tileMapChar == 'P') { // pacman
                    pacman = new Block(pacmanRightImage,x,y,tilesize,tilesize);
                }

                else if (tileMapChar== ' '){ //foood
                    Block food = new Block(null,x+14 , y+14, 4,4);
                    foods.add(food);
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(pacman.image, pacman.x, pacman.y , pacman.width,pacman.height,null);

        for (Block ghost : ghosts) {
            g.drawImage(ghost.image,ghost.x, ghost.y , ghost.width, ghost.height,null);
        }

        for (Block wall : walls) {
            g.drawImage(wall.image,wall.x,wall.y,wall.width,wall.height,null);
        }

        g.setColor(Color.WHITE);
        for (Block food : foods) {
            g.fillRect(food.x , food.y , food.width, food.height);
        }
    }

    public void move() {
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        //check wall collisions
        for (Block wall : walls) {
            if (collision(pacman, wall)) {
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
            }
        }

        // check ghost collision
        for (Block ghost : ghosts) {
            if (ghost.y == tilesize*9 && ghost.direction != 'U' && ghost.direction != 'D') {
                ghost.updatedirection('U');
            }
            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;
            for (Block wall : walls) {
                if (collision(ghost, wall) || ghost.x <=0 || ghost.x + ghost.width >= boardwidth) {
                ghost.x -= ghost.velocityX;
                ghost.y -= ghost.velocityY;
                char newDirection = directions[random.nextInt(4)];
                ghost.updatedirection(newDirection);
                }
            }
        }
    }

    public boolean collision(Block a, Block b) {
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    } 


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}
       

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("KeyEvent: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_UP){
            pacman.updatedirection('U');
        }

        else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            pacman.updatedirection('D');
        }

        else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            pacman.updatedirection('L');
        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pacman.updatedirection('R');
        }

        if (pacman.direction == 'U') {
            pacman.image = pacmanUpImage;
        }

        else if(pacman.direction == 'D'){
            pacman.image = pacmanDownImage;
        }

        else if (pacman.direction == 'L') {
            pacman.image = pacmanLeftImage;
        }

        else if (pacman.direction == 'R') {
            pacman.image = pacmanRightImage;
        }
    }
}
