import java.awt.Color;
import java.awt.Graphics;

public class Pacman {
    private int x, y;
    private int velikost = 20;
    private int dx, dy;
    private int rychlost = 5;
    private int skore;

    public Pacman(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
        this.skore = 0;
    }

    public void Pohyb() {
        this.x += dx * rychlost;
        this.y += dy * rychlost;
    }

    public void nastavSmer(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void pridejSkore(int body) {
        this.skore += body;
    }

    public int getSkore() {
        return this.skore;
    }

    public void Kresli(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc(x, y, velikost, velikost, 45, 270);
    }

    public boolean Kolize(Duch duch) {
        return this.x < duch.getX() + duch.getVelikost() &&
                this.x + velikost > duch.getX() &&
                this.y < duch.getY() + duch.getVelikost() &&
                this.y + velikost > duch.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelikost() {
        return velikost;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
