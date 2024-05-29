import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class Pacman implements Postava {
    private int x, y;
    private int dx, dy;
    private int velikostPolicka;
    private int rychlost = 5;
    private int skore;
    private int velikost;

    public Pacman(int x, int y, int velikostPolicka, int velikost) {
        this.x = x;
        this.y = y;
        this.velikostPolicka = velikostPolicka;
        this.velikost = velikost;
        this.dx = 0;
        this.dy = 0;
    }

    public void nastavSmer(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void pridejSkore(int body) {
        this.skore += body;
    }

    public void pohyb(char[][] pole) {
        x += dx * rychlost;
        y += dy * rychlost;

    }

    public void kresleni(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc(x * velikostPolicka, y * velikostPolicka, velikost, velikost, 0, 360);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                nastavSmer(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                nastavSmer(0, 1);
                break;
            case KeyEvent.VK_LEFT:
                nastavSmer(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                nastavSmer(1, 0);
                break;
        }
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    @Override
    public void Pohyb(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void Kresleni(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc(x * velikostPolicka, y * velikostPolicka, velikost, velikost, 0, 360);
    }

    @Override
    public boolean Kolize(Postava postava) {
        return x < postava.getX() + postava.getVelikost() &&
                x + velikost > postava.getX() &&
                y < postava.getY() + postava.getVelikost() &&
                y + velikost > postava.getY();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getVelikost() {
        return velikost;
    }
}
