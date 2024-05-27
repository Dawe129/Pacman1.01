import javax.swing.JPanel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ObtiznostLehka extends JPanel {
    private char[][] pole;
    private int sirka;
    private int vyska;
    private int velikostPolicka;
    private Pacman pacman;
    private Duch duch;

    public ObtiznostLehka(int sirka, int vyska, int velikostPolicka) {
        this.sirka = sirka;
        this.vyska = vyska;
        this.velikostPolicka = velikostPolicka;
        this.pole = new char[vyska][sirka];
        try {
            inicializujPoleZeSouboru("Mapa1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inicializujPoleZeSouboru(String nazev) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nazev));
        String line;
        int rada = 0;
        while ((line = reader.readLine()) != null && rada < vyska) {
            for (int sloupec = 0; sloupec < Math.min(line.length(), sirka); sloupec++) {
                pole[rada][sloupec] = line.charAt(sloupec);
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
