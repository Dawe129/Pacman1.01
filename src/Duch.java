import java.awt.Color;
import java.awt.Graphics;

public class Duch {
    private int x, y;
    private int velikost = 20;

    public Duch(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void Pohyb(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void Kresli(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, velikost, velikost);
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
}