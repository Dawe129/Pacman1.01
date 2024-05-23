import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hraci pole");
            HraciPole hraciPole = new HraciPole(30, 30, 20);
            LogikaHry logikaHry = new LogikaHry(hraciPole);

            hraciPole.setPacman(logikaHry.getPacman());
            hraciPole.setDuch(logikaHry.getDuch());

            frame.add(hraciPole);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
