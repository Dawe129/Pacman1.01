package Tridy;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Třída reprezentující hrací pole, na kterém se vykreslují herní objekty.
 */
public class HraciPole extends JPanel {
    private char[][] pole;
    private int sirka;
    private int vyska;
    private int velikostPolicka;
    private Pacman pacman;
    private Duch duch;

    /**
     * Konstruktor pro vytvoření instance hracího pole se zadanými parametry.
     *
     * @param sirka šířka hracího pole
     * @param vyska výška hracího pole
     * @param velikostPolicka velikost jednoho herního políčka
     */
    public HraciPole(int sirka, int vyska, int velikostPolicka) {
        this.sirka = sirka;
        this.vyska = vyska;
        this.velikostPolicka = velikostPolicka;
        this.pole = new char[vyska][sirka];
        try {
            inicializujPoleZeSouboru("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pacman pacman = new Pacman(1, 1, velikostPolicka, 1);
        setPacman(pacman);
    }

    /**
     * Inicializuje herní pole na základě obsahu souboru.
     *
     * @param nazev název souboru obsahujícího herní pole
     * @throws IOException v případě chyby při čtení ze souboru
     */
    public void inicializujPoleZeSouboru(String nazev) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nazev));
        String radek;
        int rada = 0;
        while ((radek = reader.readLine()) != null && rada < vyska) {
            for (int sloupec = 0; sloupec < Math.min(radek.length(), sirka); sloupec++) {
                pole[rada][sloupec] = radek.charAt(sloupec);
            }
            rada++;
        }
        reader.close();
    }

    /**
     * Nastaví Pacmana na hrací pole.
     *
     * @param pacman instance Pacmana
     */
    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    /**
     * Nastaví Ducha na hrací pole.
     *
     * @param duch instance Ducha
     */
    public void setDuch(Duch duch) {
        this.duch = duch;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < vyska; i++) {
            for (int j = 0; j < sirka; j++) {
                if (pole[i][j] == '#') {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * velikostPolicka, i * velikostPolicka, velikostPolicka, velikostPolicka);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(j * velikostPolicka, i * velikostPolicka, velikostPolicka, velikostPolicka);
                }
            }
        }

        if (pacman != null) {
            pacman.Kresleni(g);
        }
        if (duch != null) {
            duch.Kresleni(g);
        }
    }

    /**
     * Vrátí herní pole.
     *
     * @return dvourozměrné pole znaků představující herní pole
     */
    public char[][] getPole() {
        return pole;
    }

    /**
     * Aktualizuje herní pole na základě zadaného pole.
     *
     * @param novePole nové pole znaků představující herní pole
     */
    public void aktualizujPole(char[][] novePole) {
        this.pole = novePole;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(sirka * velikostPolicka, vyska * velikostPolicka);
    }
}
