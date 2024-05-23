import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class LogikaHry implements ActionListener {
    private HraciPole hraciPole;
    private Pacman pacman;
    private Duch duch;
    private Timer timer;

    public LogikaHry(HraciPole hraciPole) {
        this.hraciPole = hraciPole;
        this.pacman = new Pacman(1, 1);
        this.duch = new Duch(10, 10);

        this.timer = new Timer(100, this);
        this.timer.start();

        hraciPole.addKeyListener(new TAdapter());
        hraciPole.setFocusable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.Pohyb(pacman.getX(), pacman.getY());
        hraciPole.repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                pacman.nastavSmer(-1, 0);
            } else if (key == KeyEvent.VK_RIGHT) {
                pacman.nastavSmer(1, 0);
            } else if (key == KeyEvent.VK_UP) {
                pacman.nastavSmer(0, -1);
            } else if (key == KeyEvent.VK_DOWN) {
                pacman.nastavSmer(0, 1);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT ||
                    key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                pacman.nastavSmer(0, 0);
            }
        }
    }

    public Pacman getPacman() {
        return pacman;
    }

    public Duch getDuch() {
        return duch;
    }
}
