import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Menu extends JFrame {

    public Menu() {
        setTitle("Pacman Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("Vyberte obtížnost:", SwingConstants.CENTER);
        panel.add(label);

        JButton easyButton = new JButton("Lehká");
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spustitHru("Mapa1.txt");
            }
        });
        panel.add(easyButton);

        JButton hardButton = new JButton("Těžká");
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spustitHru("Mapa.txt");
            }
        });
        panel.add(hardButton);

        add(panel);
    }

    private void spustitHru(String mapaSoubor) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hraci pole");
            HraciPole hraciPole = new HraciPole(40, 20, 20);
            LogikaHry logikaHry = new LogikaHry(hraciPole);

            try {
                hraciPole.inicializujPoleZeSouboru(mapaSoubor);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Nepodařilo se načíst soubor: " + mapaSoubor, "Chyba", JOptionPane.ERROR_MESSAGE);
                return;
            }

            hraciPole.setPacman(logikaHry.getPacman());
            hraciPole.setDuch(logikaHry.getDuch());

            frame.add(hraciPole);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
        dispose();
    }
}