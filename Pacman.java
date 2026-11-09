import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;


public class Pacman extends JPanel {
    class Block {
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;

        Block(Image image,int x,int y,int width, int height) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
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

    Pacman() {
        setPreferredSize(new Dimension(boardwidth, boardheight));
        setBackground(Color.BLACK);

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
        System.out.println(walls.size());
        System.out.println(foods.size());
        System.out.println(ghosts.size());
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
}
