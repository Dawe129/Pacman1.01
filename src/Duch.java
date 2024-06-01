import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

public class Duch implements Postava {
    protected int x, y;
    private int startX, startY;
    private int dx, dy;
    private int velikostPolicka;
    private int velikost;
    private int rychlost;
    private boolean bojiSe;
    private Random random;
    private Image duchImage;

    public Duch(int x, int y, int velikostPolicka, int velikost) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.velikostPolicka = velikostPolicka;
        this.velikost = velikost;
        this.dx = 0;
        this.dy = 0;
        this.rychlost = 1;
        this.bojiSe = false;
        this.random = new Random();
        nahodnySmer();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        duchImage = toolkit.getImage("Duch.png");
    }

    private void nahodnySmer() {
        int smer = random.nextInt(4);
        switch (smer) {
            case 0:
                dx = 1;
                dy = 0;
                break;
            case 1:
                dx = -1;
                dy = 0;
                break;
            case 2:
                dx = 0;
                dy = 1;
                break;
            case 3:
                dx = 0;
                dy = -1;
                break;
        }
    }

    public void pohyb(char[][] pole) {
        int novaX = x + dx * rychlost;
        int novaY = y + dy * rychlost;

        if (novaX >= 0 && novaX < pole[0].length && novaY >= 0 && novaY < pole.length && pole[novaY][novaX] != '#') {
            x = novaX;
            y = novaY;
        } else {
            nahodnySmer();
        }
    }

    public void utekOdPacmana(Pacman pacman, char[][] pole) {
        int pacmanX = pacman.getX();
        int pacmanY = pacman.getY();

        if (bojiSe) {
            if (x < pacmanX) dx = -1;
            if (x > pacmanX) dx = 1;
            if (y < pacmanY) dy = -1;
            if (y > pacmanY) dy = 1;
        } else {
            nahodnySmer();
        }

        pohyb(pole);
    }

    public void nastavRychlost(int novaRychlost) {
        this.rychlost = novaRychlost;
    }

    public void nastavBojiSe(boolean bojiSe) {
        this.bojiSe = bojiSe;
    }

    public void teleportNaPocatecniMisto() {
        this.x = startX;
        this.y = startY;
        this.bojiSe = false;
    }

    @Override
    public void Pohyb(int dx, int dy) {
    }

    @Override
    public void Kresleni(Graphics g) {
        g.drawImage(duchImage, x * velikostPolicka, y * velikostPolicka, velikost, velikost, null);
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
