import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

public class Duch implements Postava {
    private int x, y;
    private int dx, dy;
    private int velikostPolicka;
    private int velikost;
    private int rychlost;
    private Random random;
    private Image duchImage;

    public Duch(int x, int y, int velikostPolicka, int velikost) {
        this.x = x;
        this.y = y;
        this.velikostPolicka = velikostPolicka;
        this.velikost = velikost;
        this.dx = 0;
        this.dy = 0;
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
        int novaX = x + dx;
        int novaY = y + dy;

        if (novaX >= 0 && novaX < pole[0].length && novaY >= 0 && novaY < pole.length && pole[novaY][novaX] != '#') {
            x = novaX;
            y = novaY;
        } else {
            nahodnySmer();
        }
    }
    public void nastavRychlost(int novaRychlost) {
        this.rychlost = novaRychlost;
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
