import javax.swing.JPanel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObtiznostTezka extends JPanel {
    private char[][] pole;
    private int sirka;
    private int vyska;
    private int velikostPolicka;
    private Pacman pacman;
    private Duch duch;
    private int dynamickaStenaX;
    private int dynamickaStenaY;

    public ObtiznostTezka(int sirka, int vyska, int velikostPolicka) {
        this.sirka = sirka;
        this.vyska = vyska;
        this.velikostPolicka = velikostPolicka;
        this.pole = new char[vyska][sirka];
        try {
            inicializujPoleZeSouboru("Mapa.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        dynamickaStenaX = 22;
        dynamickaStenaY = 17;
        pole[dynamickaStenaY][dynamickaStenaX] = '#';

        Timer timer = new Timer(8000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pole[dynamickaStenaY][dynamickaStenaX] = '.';
                repaint();
            }
        });
        timer.setRepeats(false);
        timer.start();
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

    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public void setDuch(Duch duch) {
        this.duch = duch;
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
}
