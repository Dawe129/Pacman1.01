import java.awt.*;
import java.util.Random;

public class ZamerenyDuch implements Postava {
    private int x, y;
    private int dx, dy;
    private int velikostPolicka;
    private int velikost;
    private Pacman hrac;
    private Random random;
    private Image duchImage;
    private int startX, startY;
    private boolean bojiSe;

    public ZamerenyDuch(int x, int y, int velikostPolicka, int velikost, Pacman hrac) {
        this.x = x;
        this.y = y;
        this.velikostPolicka = velikostPolicka;
        this.velikost = velikost;
        this.hrac = hrac;
        this.dx = 0;
        this.dy = 0;
        this.random = new Random();
        this.startX = x;
        this.startY = y;
        this.bojiSe = false;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        duchImage = toolkit.getImage("zamereny_duch.png");
    }

    public void pohyb(char[][] pole) {
        int hracX = hrac.getX();
        int hracY = hrac.getY();

        int novyDx = 0;
        int novyDy = 0;

        if (x < hracX) {
            novyDx = 1;
        } else if (x > hracX) {
            novyDx = -1;
        }

        if (y < hracY) {
            novyDy = 1;
        } else if (y > hracY) {
            novyDy = -1;
        }

        int novaX = x + novyDx;
        int novaY = y + novyDy;

        if (novaX >= 0 && novaX < pole[0].length && novaY >= 0 && novaY < pole.length && pole[novaY][novaX] != '#') {
            x = novaX;
            y = novaY;
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
            pohyb(pole);
        }
        pohyb(pole);
    }

    public void nastavBojiSe(boolean bojiSe) {
        this.bojiSe = bojiSe;
    }

    public boolean getBojiSe() {
        return bojiSe;
    }

    public void teleportNaPocatecniMisto() {
        this.x = startX;
        this.y = startY;
        this.bojiSe = false;
    }

    @Override
    public void Pohyb(int dx, int dy) {
        // Implementace rozhraní, ale není použita v této třídě
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
