import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class HraciPole extends JPanel {
    private char[][] pole;
    private int sirka;
    private int vyska;
    private int velikostPolicka;

    public HraciPole(int sirka, int vyska, int velikostPolicka) {
        this.sirka = sirka;
        this.vyska = vyska;
        this.velikostPolicka = velikostPolicka;
        this.pole = new char[vyska][sirka];
        inicializujPole();
    }

    private void inicializujPole() {
        for (int i = 0; i < vyska; i++) {
            for (int j = 0; j < sirka; j++) {
                if (i == 0 || i == vyska - 1 || j == 0 || j == sirka - 1) {
                    pole[i][j] = '#';
                } else {
                    pole[i][j] = '.';
                }
            }
        }

        Random random = new Random();
        int pocetSten = sirka * vyska / 8;
        for (int k = 0; k < pocetSten; k++) {
            int x = random.nextInt(sirka - 2) + 1;
            int y = random.nextInt(vyska - 2) + 1;
            if (pole[y][x] != '#') {
                pole[y][x] = '#';
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < vyska; i++) {
            for (int j = 0; j < sirka; j++) {
                if (pole[i][j] == '#') {
                    g.setColor(Color.RED);
                    g.fillRect(j * velikostPolicka, i * velikostPolicka, velikostPolicka, velikostPolicka);
                } else if (pole[i][j] == ' ') {
                    g.setColor(Color.BLACK);
                    g.fillRect(j * velikostPolicka, i * velikostPolicka, velikostPolicka, velikostPolicka);
                }
            }
        }
    }

    public void aktualizujPole(char[][] novePole) {
        this.pole = novePole;
        repaint();
    }

    public Dimension getPreferredSize() {
        return new Dimension(sirka * velikostPolicka, vyska * velikostPolicka);
    }

}
