package Tridy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * Třída reprezentující postavu Pacmana.
 */
public class Pacman implements Postava {
    private int x, y;
    private int dx, dy;
    private int velikostPolicka;
    private int rychlost = 1;
    private int skore;
    private int velikost;
    private int smer;
    private int usta;

    /**
     * Konstruktor pro třídu Tridy.Pacman.
     *
     * @param x Počáteční x-ová pozice Pacmana.
     * @param y Počáteční y-ová pozice Pacmana.
     * @param velikostPolicka Velikost jednoho herního políčka.
     * @param velikost Velikost Pacmana.
     */
    public Pacman(int x, int y, int velikostPolicka, int velikost) {
        this.x = x;
        this.y = y;
        this.velikostPolicka = velikostPolicka;
        this.velikost = velikost;
        this.dx = 0;
        this.dy = 0;
        this.smer = 0;
        this.usta = 0;
    }

    /**
     * Nastaví směr pohybu Pacmana.
     *
     * @param dx Směr pohybu v ose x.
     * @param dy Směr pohybu v ose y.
     */
    public void nastavSmer(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Přidá skóre Pacmanovi.
     *
     * @param body Počet bodů, které mají být přidány k aktuálnímu skóre.
     */
    public void pridejSkore(int body) {
        this.skore += body;
    }

    /**
     * Provádí pohyb Pacmana na základě aktuálního směru.
     *
     * @param pole Herní pole.
     */
    public void pohyb(char[][] pole) {
        int novaX = x + dx;
        int novaY = y + dy;

        if (novaX >= 0 && novaX < pole[0].length && novaY >= 0 && novaY < pole.length && pole[novaY][novaX] != '#') {
            x = novaX;
            y = novaY;
        }

        if (dx > 0) {
            smer = 0;
        } else if (dx < 0) {
            smer = 2;
        } else if (dy > 0) {
            smer = 1;
        } else if (dy < 0) {
            smer = 3;
        }

        usta = (usta + 1) % 2;
    }

    /**
     * Zpracovává stisknutí kláves pro pohyb Pacmana.
     *
     * @param e KeyEvent objekt obsahující informace o stisknutí klávesy.
     */
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                nastavSmer(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                nastavSmer(0, 1);
                break;
            case KeyEvent.VK_LEFT:
                nastavSmer(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                nastavSmer(1, 0);
                break;
        }
    }

    /**
     * Vrací hodnotu dx (směr pohybu v ose x).
     *
     * @return Hodnota dx.
     */
    public int getDx() {
        return dx;
    }

    /**
     * Vrací hodnotu dy (směr pohybu v ose y).
     *
     * @return Hodnota dy.
     */
    public int getDy() {
        return dy;
    }

    @Override
    public void Pohyb(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void Kresleni(Graphics g) {
        g.setColor(Color.YELLOW);
        int startAngle;
        int arcAngle = 330 - usta * 45;
        switch (smer) {
            case 0:
                startAngle = 45 - usta * 0;
                break;
            case 1:
                startAngle = 315 - usta * 0;
                break;
            case 2:
                startAngle = 225 - usta * 0;
                break;
            case 3:
                startAngle = 135 - usta * 0;
                break;
            default:
                startAngle = 45;
                break;
        }
        g.fillArc(x * velikostPolicka, y * velikostPolicka, velikost, velikost, startAngle, arcAngle);
    }

    /**
     * Kontroluje, zda došlo ke kolizi s jinou postavou.
     *
     * @param postava Tridy.Postava, se kterou má být provedena kontrola kolize.
     * @return True, pokud došlo ke kolizi, jinak false.
     */
    @Override
    public boolean Kolize(Postava postava) {
        return x < postava.getX() + postava.getVelikost() &&
                x + velikost > postava.getX() &&
                y < postava.getY() + postava.getVelikost() &&
                y + velikost > postava.getY();
    }

    /**
     * Vrací aktuální x-ovou pozici Pacmana.
     *
     * @return Aktuální x-ová pozice Pacmana.
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Vrací aktuální y-ovou pozici Pacmana.
     *
     * @return Aktuální y-ová pozice Pacmana.
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Vrací velikost Pacmana.
     *
     * @return Velikost Pacmana.
     */
    @Override
    public int getVelikost() {
        return velikost;
    }
}