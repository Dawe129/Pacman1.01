package Testy;

import Tridy.Pacman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PacmanTest {

    private Pacman pacman;
    private char[][] pole;

    @BeforeEach
    public void setUp() {
        pacman = new Pacman(1, 1, 20, 20);
        pole = new char[][] {
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', '#', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', '#', ' '},
                {' ', ' ', ' ', ' ', ' '}
        };
    }

    @Test
    public void testPohybRight() {
        pacman.nastavSmer(1, 0);
        pacman.pohyb(pole);
        assertEquals(1, pacman.getX());
        assertEquals(1, pacman.getY());
    }

    @Test
    public void testPohybLeft() {
        pacman.nastavSmer(-1, 0);
        pacman.pohyb(pole);
        assertEquals(0, pacman.getX());
        assertEquals(1, pacman.getY());
    }

    @Test
    public void testPohybDown() {
        pacman.nastavSmer(0, 1);
        pacman.pohyb(pole);
        assertEquals(1, pacman.getX());
        assertEquals(2, pacman.getY());
    }

    @Test
    public void testPohybUp() {
        pacman.nastavSmer(0, -1);
        pacman.pohyb(pole);
        assertEquals(1, pacman.getX());
        assertEquals(0, pacman.getY());
    }

    @Test
    public void testPohybOutOfBounds() {
        pacman.nastavSmer(-1, 0); // Move to (0, 1)
        pacman.pohyb(pole);        // Move to (0, 1)
        pacman.nastavSmer(-1, 0); // Move out of bounds to (-1, 1)
        pacman.pohyb(pole);        // Should stay at (0, 1)
        assertEquals(0, pacman.getX());
        assertEquals(1, pacman.getY());
    }
}
