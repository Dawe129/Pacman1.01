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

public class ObtiznostStredni extends JPanel implements KeyListener {
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

    public ObtiznostStredni(int sirka, int vyska, int velikostPolicka) {
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
            inicializujPoleZeSouboru("Mapa2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        dynamickaStenaPozice.add(new Point(17, 8));
        dynamickaStenaPozice.add(new Point(11, 8));

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

        pacman = new Pacman(26, 7, velikostPolicka, 20);

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

        Duch duch1 = new Duch(14, 8, velikostPolicka, 20);
        Duch duch2 = new Duch(15, 8, velikostPolicka, 20);
        ZamerenyDuch duch3 = new ZamerenyDuch(26, 4, velikostPolicka, 20, pacman);
        ZamerenyDuch duch4 = new ZamerenyDuch(1, 3, velikostPolicka, 20, pacman);
        duch1.nastavRychlost(2);
        duch2.nastavRychlost(6);
        duchove.add(duch1);
        duchove.add(duch2);
        duchove1.add(duch3);
        duchove1.add(duch4);

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

    public void inicializujPoleZeSouboru(String nazev) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Mapa2.txt"));
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
        Object[] options = {"Hrát znovu", "Menu", "Konec"};
        int choice = JOptionPane.showOptionDialog(frame, "Gratulace! Sežral jsi všechny koule. Co chceš udělat?", "Konec hry", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
        switch (choice) {
            case JOptionPane.YES_OPTION:
                frame.getContentPane().removeAll();
                ObtiznostStredni novaHra = new ObtiznostStredni(sirka, vyska, velikostPolicka);
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
        Object[] options = {"Hrát znovu", "Menu", "Konec"};
        int choice = JOptionPane.showOptionDialog(frame, "Gratulace! Sežral jsi všechny koule. Tvoje konečné skóre je: " + aktualniSkore + ". Co chceš udělat?", "Konec hry", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
        switch (choice) {
            case JOptionPane.YES_OPTION:
                frame.getContentPane().removeAll();
                ObtiznostStredni novaHra = new ObtiznostStredni(sirka, vyska, velikostPolicka);
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
