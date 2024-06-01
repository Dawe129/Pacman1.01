package Tridy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Třída Tridy.Menu představuje hlavní menu hry Tridy.Pacman.
 */
public class Menu extends JFrame {

    /**
     * Konstruktor vytváří nové okno menu.
     */
    public Menu() {
        setTitle("Tridy.Pacman Tridy.Menu");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel label = new JLabel("Vyberte obtížnost:", SwingConstants.CENTER);
        panel.add(label);

        JButton easyButton = new JButton("Lehká");
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spustitHru(new ObtiznostLehka(25, 17, 20));
            }
        });
        panel.add(easyButton);

        JButton mediumButton = new JButton("Střední");
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spustitHru(new ObtiznostStredni(30, 22, 20));
            }
        });
        panel.add(mediumButton);

        JButton hardButton = new JButton("Těžká");
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spustitHru(new ObtiznostTezka(40, 22, 20));
            }
        });
        panel.add(hardButton);

        JButton exitButton = new JButton("Konec");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ukoncitProgram();
            }
        });
        panel.add(exitButton);

        add(panel);
    }

    /**
     * Spustí hru s danou obtížností.
     *
     * @param obtiznost obtížnost hry
     */
    private void spustitHru(JPanel obtiznost) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hraci pole");
            HraciPole hraciPole = new HraciPole(40, 40, 20);
            LogikaHry logikaHry = new LogikaHry(hraciPole);

            hraciPole.setPacman(logikaHry.getPacman());
            hraciPole.setDuch(logikaHry.getDuch());

            frame.add(obtiznost);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
        dispose();
    }

    /**
     * Ukončí běh programu.
     */
    private void ukoncitProgram() {
        System.exit(0);
    }
}
