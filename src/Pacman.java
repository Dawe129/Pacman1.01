import java.awt.Color;
import java.awt.Graphics;

public class Pacman implements Postava {
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

    @Override
    public void Pohyb(int dx, int dy) {
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

    @Override
    public void Kresleni(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc(x, y, velikost, velikost, 45, 270);
    }

    @Override
    public boolean Kolize(Postava postava) {
        return this.getX() < postava.getX() + postava.getVelikost() &&
                this.getX() + this.getVelikost() > postava.getX() &&
                this.getY() < postava.getY() + postava.getVelikost() &&
                this.getY() + this.getVelikost() > postava.getY();
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

    public int getSkore() {
        return this.skore;
    }
}
