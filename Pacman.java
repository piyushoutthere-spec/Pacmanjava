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

    Pacman() {
        setPreferredSize(new Dimension(boardwidth, boardheight));
        setBackground(Color.BLACK);
    }

}
