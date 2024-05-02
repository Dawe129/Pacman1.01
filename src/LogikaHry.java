import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogikaHry extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("arial", Font.BOLD, 14);
    private boolean inGame = false;
    private boolean dying = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCK = 15;
    private final int SCREEN_SIZE = N_BLOCK * BLOCK_SIZE;
    private final int MAX_GHOSTS = 10;
    private final int PACMAN_SPEED = 6;

    private int N_GHOSTS = 6;
    private int lives, score;
    private int [] dx, dy;

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}