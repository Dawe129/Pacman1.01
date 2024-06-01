package Tridy;

import java.awt.*;

public interface Postava {
    void Pohyb(int dx, int dy);
    void Kresleni(Graphics g);
    boolean Kolize (Postava postava);
    int getX();
    int getY();
    int getVelikost();
}
