import java.awt.*;

public class Skore {
    private int x, y;
    private int velikost;
    private int hodnota;

    /**
     * Konstruktor pro vytvoření nové instance skóre.
     *
     * @param x X-ová pozice skóre.
     * @param y Y-ová pozice skóre.
     * @param velikost Velikost jednoho políčka.
     * @param hodnota Hodnota skóre.
     */
    public Skore(int x, int y, int velikost, int hodnota) {
        this.x = x;
        this.y = y;
        this.velikost = velikost;
        this.hodnota = hodnota;
    }

    /**
     * Kreslí skóre na plátno.
     *
     * @param g Grafický kontext pro kreslení.
     */
    public void Kresleni(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x * velikost + velikost / 4, y * velikost + velikost / 4, velikost / 3, velikost / 3);
    }

    /**
     * Vrací X-ovou pozici skóre.
     *
     * @return X-ová pozice skóre.
     */
    public int getX() {
        return x;
    }

    /**
     * Vrací Y-ovou pozici skóre.
     *
     * @return Y-ová pozice skóre.
     */
    public int getY() {
        return y;
    }

    /**
     * Vrací hodnotu skóre.
     *
     * @return Hodnota skóre.
     */
    public int getHodnota() {
        return hodnota;
    }
}
