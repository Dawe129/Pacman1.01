package Tridy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Třída reprezentující obtížnost "Těžká" ve hře Tridy.Pacman.
 * Tato obtížnost obsahuje dynamické stěny, které se po určité době mění.
 */
public class ObtiznostTezka extends JPanel implements KeyListener {
    private char[][] pole;
    private int sirka;
    private int vyska;
    private int velikostPolicka;
    private Pacman pacman;
    private List<Skore> skore;
    private List<Duch> duchove;
    private List<ZamerenyDuch> duchove1;
    private List<Point> dynamickaStenaPozice;
    private int aktualniSkore;

    /**
     * Konstruktor pro třídu Tridy.ObtiznostTezka.
     *
     * @param sirka Šířka hracího pole.
     * @param vyska Výška hracího pole.
     * @param velikostPolicka Velikost jednoho herního políčka.
     */
    public ObtiznostTezka(int sirka, int vyska, int velikostPolicka) {
        this.sirka = sirka;
        this.vyska = vyska;
        this.velikostPolicka = velikostPolicka;
        this.pole = new char[vyska][sirka];
        this.skore = new ArrayList<>();
        this.duchove = new ArrayList<>();
        this.duchove1 = new ArrayList<>();
        this.dynamickaStenaPozice = new ArrayList<>();
        this.aktualniSkore = 0;
        try {
            inicializujPoleZeSouboru("Mapa.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        dynamickaStenaPozice.add(new Point(25, 13));
        dynamickaStenaPozice.add(new Point(18, 13));
        dynamickaStenaPozice.add(new Point(20, 13));
        dynamickaStenaPozice.add(new Point(23, 13));

        for (Point pozice : dynamickaStenaPozice) {
            pole[pozice.y][pozice.x] = '#';
        }

        Timer timer = new Timer(5000, e -> {
            for (Point pozice : dynamickaStenaPozice) {
                pole[pozice.y][pozice.x] = '.';
            }
            repaint();
        });
        timer.setRepeats(false);
        timer.start();

        this.addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        pacman = new Pacman(6, 5, velikostPolicka, 20);

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
                    aktualniSkore += bod.getHodnota();
                    skore.remove(bod);
                    k--;
                }
            }
            repaint();

            if (skore.isEmpty()) {
                zobrazKonecHry();
            }

            kontrolaStretuDuchaSPacmanem();
        });
        pohybTimer.start();

        Duch duch1 = new Duch(18, 14, velikostPolicka, 20);
        Duch duch2 = new Duch(20, 14, velikostPolicka, 20);
        Duch duch3 = new Duch(23, 14, velikostPolicka, 20);
        Duch duch4 = new Duch(25, 14, velikostPolicka, 20);
        Duch duch5 = new Duch(25, 15, velikostPolicka, 20);
        Duch duch6 = new Duch(18, 15, velikostPolicka, 20);
        ZamerenyDuch duch7 = new ZamerenyDuch(10, 14, velikostPolicka, 20, pacman);
        ZamerenyDuch duch8 = new ZamerenyDuch(25, 12, velikostPolicka, 20, pacman);
        duch1.nastavRychlost(2);
        duch2.nastavRychlost(6);
        duch3.nastavRychlost(3);
        duch4.nastavRychlost(5);
        duch5.nastavRychlost(1);
        duch6.nastavRychlost(4);
        duchove.add(duch1);
        duchove.add(duch2);
        duchove.add(duch3);
        duchove.add(duch4);
        duchove.add(duch5);
        duchove.add(duch6);
        duchove1.add(duch7);
        duchove1.add(duch8);

        Timer duchTimer = new Timer(500, e -> {
            for (Duch duch : duchove) {
                duch.pohyb(pole);
            }
            for (ZamerenyDuch duch : duchove1) {
                duch.pohyb(pole);
            }
            repaint();
            kontrolaStretuDuchaSPacmanem();
        });
        duchTimer.start();
    }
    /**
     * Inicializuje herní pole ze souboru.
     *
     * @param nazev Název souboru, ze kterého se má pole načíst.
     * @throws IOException Pokud nastane chyba při čtení ze souboru.
     */
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

    /**
     * {@inheritDoc}
     */
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

        for (Duch duch : duchove) {
            duch.Kresleni(g);
        }

        for (ZamerenyDuch duch : duchove1) {
            duch.Kresleni(g);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Skóre: " + aktualniSkore, 10, getHeight() - 10);
    }

    /**
     * Aktualizuje herní pole.
     *
     * @param novePole Nové herní pole.
     */
    public void aktualizujPole(char[][] novePole) {
        this.pole = novePole;
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(sirka * velikostPolicka, vyska * velikostPolicka);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(KeyEvent e) {
        pacman.keyPressed(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    private void kontrolaStretuDuchaSPacmanem() {
        if (pacman != null) {
            for (Duch duch : duchove) {
                if (duch.getX() == pacman.getX() && duch.getY() == pacman.getY()) {
                    zobrazProhru();
                    return;
                }
            }
            for (ZamerenyDuch duch : duchove1) {
                if (duch.getX() == pacman.getX() && duch.getY() == pacman.getY()) {
                    zobrazProhru();
                    return;
                }
            }
        }
    }

    private void zobrazKonecHry() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Object[] options = {"Hrát znovu", "Tridy.Menu", "Konec"};
        int choice = JOptionPane.showOptionDialog(frame, "Gratulace! Sežral jsi všechny koule. Tvoje konečné skóre je: " + aktualniSkore + ". Co chceš udělat?", "Konec hry", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
        switch (choice) {
            case JOptionPane.YES_OPTION:
                frame.getContentPane().removeAll();
                ObtiznostTezka novaHra = new ObtiznostTezka(sirka, vyska, velikostPolicka);
                frame.getContentPane().add(novaHra);
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();
                novaHra.requestFocusInWindow();
                frame.pack();
                break;
            case JOptionPane.NO_OPTION:
                frame.getContentPane().removeAll();
                Menu menu = new Menu();
                menu.setVisible(true);
                frame.pack();
                break;
            case JOptionPane.CANCEL_OPTION:
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                break;
        }
        ((JDialog) SwingUtilities.getWindowAncestor(frame)).dispose();
    }

    private void zobrazProhru() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Object[] options = {"Hrát znovu", "Tridy.Menu", "Konec"};
        int choice = JOptionPane.showOptionDialog(frame, "Prohrál jsi! Tridy.Duch tě chytil. Co chceš udělat?", "Prohra", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
        switch (choice) {
            case JOptionPane.YES_OPTION:
                frame.getContentPane().removeAll();
                ObtiznostTezka novaHra = new ObtiznostTezka(sirka, vyska, velikostPolicka);
                frame.getContentPane().add(novaHra);
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();
                novaHra.requestFocusInWindow();
                frame.pack();
                break;
            case JOptionPane.NO_OPTION:
                frame.getContentPane().removeAll();
                Menu menu = new Menu();
                menu.setVisible(true);
                frame.pack();
                break;
            case JOptionPane.CANCEL_OPTION:
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                break;
        }
        ((JDialog) SwingUtilities.getWindowAncestor(frame)).dispose();
    }
}