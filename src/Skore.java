import java.awt.*;

public class Skore {
    private int x, y;
    private int velikost;
    private int hodnota;

    public Skore(int x, int y, int velikost, int hodnota) {
        this.x = x;
        this.y = y;
        this.velikost = velikost;
        this.hodnota = hodnota;
    }

    public void Kresleni(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x * velikost + velikost / 4, y * velikost + velikost / 4, velikost / 3, velikost / 3);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHodnota() {
        return hodnota;
    }
}
