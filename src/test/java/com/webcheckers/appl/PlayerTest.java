package com.webcheckers.appl;


import com.webcheckers.model.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * The JUnit test suite for {@link PlayerLobby}
 *
 * @author Jeffery Russell 10-20-18
 */
@Tag("Application-tier")
public class PlayerTest
{

    /** Content under test */
    private Player cut;

    private final String name1 = "bob";
    private final String name2 = "jim";


    /**
     * Initializes new mock objects for each test.
     */
    @BeforeEach
    public void testSetup()
    {
        cut = new Player(name1);
    }


    /**
     * Tests our ability to create a new player
     */
    @Test
    public void testStartSession()
    {
        assertNotNull(cut);
    }


    /**
     * Tests the ability of {@link PlayerLobby} to be able
     * to remove players from the map.
     */
    @Test
    public void testName()
    {
        assertTrue(cut.getName().equals(name1));
    }


    /**
     * Tests the equals function of Player
     */
    @Test
    public void testEquals()
    {
        Player p2 = new Player(name2);
        assertTrue(p2.equals(p2));
        assertFalse(cut.equals(p2));

        Player p4 = null;
        assertFalse(p2.equals(p4));

        Player p3 = new Player(name1);
        assertTrue(cut.equals(p3));
        assertTrue(p3.equals(cut));

        Player equalTop2 = new Player(name2);
        assertTrue(equalTop2.hashCode()== p2.hashCode());
        assertEquals(p2, equalTop2);
        assertNotEquals(new BoardView(), p2);
    }
}
