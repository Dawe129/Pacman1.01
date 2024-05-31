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

public class ObtiznostLehka extends JPanel implements KeyListener {
    private char[][] pole;
    private int sirka;
    private int vyska;
    private int velikostPolicka;
    private Pacman pacman;
    private int dynamickaStenaY;
    private int dynamickaStenaX;
    private List<Skore> skore;
    private Duch duch;

    public ObtiznostLehka(int sirka, int vyska, int velikostPolicka) {
        this.sirka = sirka;
        this.vyska = vyska;
        this.velikostPolicka = velikostPolicka;
        this.pole = new char[vyska][sirka];
        this.skore = new ArrayList<>();
        try {
            inicializujPoleZeSouboru("Mapa1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        dynamickaStenaX = 20;
        dynamickaStenaY = 3;
        pole[dynamickaStenaY][dynamickaStenaX] = '#';

        Timer timer = new Timer(6000, e -> {
            pole[dynamickaStenaY][dynamickaStenaX] = '.';
            repaint();
        });
        timer.setRepeats(false);
        timer.start();

        this.addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        pacman = new Pacman(1, 13, velikostPolicka, 20);

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

            if (skore.isEmpty()) {
                zobrazKonecHry();
            }
        });
        pohybTimer.start();

        duch = new Duch(22, 2, velikostPolicka, 20);
        Timer duchTimer = new Timer(500, e -> {
            duch.pohyb(pole);
            repaint();
        });
        duchTimer.start();
    }

    public void inicializujPoleZeSouboru(String nazev) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Mapa1.txt"));
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
                    g.setColor(Color.GREEN);
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

        if (duch != null) {
            duch.Kresleni(g);
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

    private void zobrazKonecHry() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Object[] options = {"Hrát znovu", "Menu", "Konec"};
        int choice = JOptionPane.showOptionDialog(frame, "Gratulace! Sežral jsi všechny koule. Co chceš udělat?", "Konec hry", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
        switch (choice) {
            case JOptionPane.YES_OPTION:
                frame.getContentPane().removeAll();
                ObtiznostLehka novaHra = new ObtiznostLehka(sirka, vyska, velikostPolicka);
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
