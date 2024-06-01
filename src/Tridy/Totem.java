package Tridy;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Totem {
    private int x, y;
    private int velikostPolicka;
    private Image totemImage;

    public Totem(int x, int y, int velikostPolicka) {
        this.x = x;
        this.y = y;
        this.velikostPolicka = velikostPolicka;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        totemImage = toolkit.getImage("Tridy.Totem.png");
    }

    public void Kresleni(Graphics g) {
        g.drawImage(totemImage, x * velikostPolicka, y * velikostPolicka, velikostPolicka, velikostPolicka, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
