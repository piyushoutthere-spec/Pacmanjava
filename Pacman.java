import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;


public class Pacman extends JPanel {
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
    }

}
