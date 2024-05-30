import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Pacman implements Postava {
    private int x, y;
    private int dx, dy;
    private int velikostPolicka;
    private int rychlost = 1;
    private int skore;
    private int velikost;
    private int smer;
    private int usta;

    public Pacman(int x, int y, int velikostPolicka, int velikost) {
        this.x = x;
        this.y = y;
        this.velikostPolicka = velikostPolicka;
        this.velikost = velikost;
        this.dx = 0;
        this.dy = 0;
        this.smer = 0;
        this.usta = 0;
    }

    public void nastavSmer(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void pridejSkore(int body) {
        this.skore += body;
    }

    public void pohyb(char[][] pole) {
        int novaX = x + dx * rychlost;
        int novaY = y + dy * rychlost;

        if (novaX >= 0 && novaX < pole[0].length && novaY >= 0 && novaY < pole.length && pole[novaY][novaX] != '#') {
            x = novaX;
            y = novaY;
        }

        if (dx > 0) {
            smer = 0;
        } else if (dx < 0) {
            smer = 2;
        } else if (dy > 0) {
            smer = 1;
        } else if (dy < 0) {
            smer = 3;
        }

        usta = (usta + 1) % 2;
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
        g.fillArc(x * velikostPolicka, y * velikostPolicka, velikost, velikost, smer * 45 + usta * 30, 300 - usta * 60);
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