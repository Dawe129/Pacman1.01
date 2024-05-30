import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObtiznostTezka extends JPanel implements KeyListener {
    private char[][] pole;
    private int sirka;
    private int vyska;
    private int velikostPolicka;
    private Pacman pacman;
    private Duch duch;
    private int dynamickaStenaX;
    private int dynamickaStenaY;
    private List<Skore> skore;

    public ObtiznostTezka(int sirka, int vyska, int velikostPolicka) {
        this.sirka = sirka;
        this.vyska = vyska;
        this.velikostPolicka = velikostPolicka;
        this.pole = new char[vyska][sirka];
        this.skore = new ArrayList<>();
        try {
            inicializujPoleZeSouboru("Mapa.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        dynamickaStenaX = 22;
        dynamickaStenaY = 17;
        pole[dynamickaStenaY][dynamickaStenaX] = '#';

        Timer timer = new Timer(7000, e -> {
            pole[dynamickaStenaY][dynamickaStenaX] = '.';
            repaint();
        });
        timer.setRepeats(false);
        timer.start();

        this.addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        pacman = new Pacman(6, 7, velikostPolicka, 20);

        for (int i = 0; i < vyska; i++) {
            for (int j = 0; j < sirka; j++) {
                if (pole[i][j] == '.') {
                    skore.add(new Skore(j, i, velikostPolicka, 10));
                }
            }
        }

        Timer pohybTimer = new Timer(100, e -> {
            pacman.pohyb(pole);
            for (int k = 0; k < skore.size(); k++) {
                Skore bod = skore.get(k);
                if (pacman.getX() == bod.getX() && pacman.getY() == bod.getY()) {
                    pacman.pridejSkore(bod.getHodnota());
                    skore.remove(bod);
                    k--;
                }
            }
            repaint();
        });
        pohybTimer.start();
    }

    public void inicializujPoleZeSouboru(String nazev) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Mapa.txt"));
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < vyska; i++) {
            for (int j = 0; j < sirka; j++) {
                if (pole[i][j] == '#') {
                    g.setColor(Color.RED);
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

        for (Skore bod : skore) {
            bod.Kresleni(g);
        }
    }

    public void aktualizujPole(char[][] novePole) {
        this.pole = novePole;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(sirka * velikostPolicka, vyska * velikostPolicka);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pacman.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
