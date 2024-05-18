import java.awt.Color;
import java.awt.Graphics;

public class Duch implements Postava {
    private int x, y;
    private int velikost = 20;

    public Duch(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    @Override
    public void Pohyb(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void Kresleni(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, velikost, velikost);
    }

    @Override
    public boolean Kolize(Postava postava) {
        return this.getX() < postava.getX() + postava.getVelikost() &&
                this.getX() + this.getVelikost() > postava.getX() &&
                this.getY() < postava.getY() + postava.getVelikost() &&
                this.getY() + this.getVelikost() > postava.getY();
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