package Testy;

import Tridy.Pacman;
import Tridy.ZamerenyDuch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZamerenyDuchTest {
    private ZamerenyDuch duch;
    private Pacman hrac;
    private char[][] pole;

    @BeforeEach
    void setUp() {
        hrac = new Pacman(5, 5, 1,1);
        duch = new ZamerenyDuch(0, 0, 1, 1, hrac);
        pole = new char[][]{
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', '#', '#', '#', '#', ' '},
                {' ', '#', ' ', ' ', '#', ' '},
                {' ', '#', ' ', ' ', '#', ' '},
                {' ', '#', '#', '#', '#', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };
    }

    @Test
    void testPohybBlockedByWall() {
        hrac = new Pacman(4, 2, 1,1);
        duch = new ZamerenyDuch(2, 2, 1, 1, hrac);
        duch.pohyb(pole);
        assertEquals(3, duch.getX());
        assertEquals(2, duch.getY());
        duch.pohyb(pole);
        assertEquals(3, duch.getX());
        assertEquals(2, duch.getY());
    }

    @Test
    void testPohybInOpenSpace() {
        hrac = new Pacman(3, 3, 1,1);
        duch = new ZamerenyDuch(1, 1, 1, 1, hrac);
        duch.pohyb(pole);
        assertEquals(2, duch.getX());
        assertEquals(2, duch.getY());
        duch.pohyb(pole);
        assertEquals(3, duch.getX());
        assertEquals(3, duch.getY());
    }

    @Test
    void testPohybNoMoveNeeded() {
        hrac = new Pacman(0, 0, 1,1);
        duch = new ZamerenyDuch(0, 0, 1, 1, hrac);
        duch.pohyb(pole);
        assertEquals(0, duch.getX());
        assertEquals(0, duch.getY());
    }
}
