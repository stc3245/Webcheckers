package com.webcheckers.appl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * The JUnit test suite for {@link PlayerLobby}
 *
 * @author Jeffery Russell 10-20-18
 */
@Tag("Application-tier")
public class PlayerLobbyTest
{

    /** Content under test */
    private PlayerLobby cut;


    /** Mock objects to use in the tests */
    private Player player1;
    private final String name1 = "bob";

    private Player player2;
    private final String name2 = "jim";


    /**
     * Initializes new mock objects for each test.
     */
    @BeforeEach
    public void testSetup()
    {
        cut = new PlayerLobby();

        player1 = mock(Player.class);
        when(player1.getName()).thenReturn(this.name1);

        player2 = mock(Player.class);
        when(player2.getName()).thenReturn(this.name2);
    }



    /**
     * Tests our ability to add players to the list of logged in users
     */
    @Test
    public void testStartSession()
    {
        cut.createPlayer(name1);

        assertNotNull(cut.getPlayer(name1));

        assertEquals(cut.getPlayer(name1).getName(), name1);

        cut.createPlayer(name2);

        assertNotNull(cut.getPlayer(name2));

        assertEquals(cut.getPlayer(name2).getName(), name2);
    }


    /**
     * Tests the ability of {@link PlayerLobby} to be able
     * to remove players from the map.
     */
    @Test
    public void terminateSessionTest()
    {
        cut.createPlayer(name2);
        cut.createPlayer(name1);
        cut.terminateSession(name1);

        assertNull(cut.getPlayer(name1));

        assertNotNull(cut.getPlayer(name2));
    }


    /**
     * Tests to see if only logged in players are in the
     * online players list.
     */
    @Test
    public void testOnlinePlayersList()
    {
        cut.createPlayer(name1);
        cut.createPlayer(name2);

        assertTrue(cut.getOnlinePlayers().contains(name1));
        assertTrue(cut.getOnlinePlayers().contains(name2));

        assertFalse(cut.getOnlinePlayers().contains("someRandomName"));
    }


    /**
     * Tets the player lobby's ability to return a list of
     * active users with the a single username ripped out for the
     * home lobby view.
     */
    @Test
    public void getOtherPlayersListTest()
    {
        cut.createPlayer(name1);
        cut.createPlayer(name2);

        assertFalse(cut.getOtherPlayers(name1).contains(name1));
        assertTrue(cut.getOtherPlayers(name1).contains(name2));
    }


    /**
     * Simple test to verify that they create player
     * function of create player does not return null
     */
    @Test
    public void testCreatePlayer()
    {
        assertNotNull(cut.createPlayer(name1));
    }


    /**
     * Tests the start game and is in game functionality
     * of the {@link PlayerLobby}
     */
    @Test
    public void testStartGame()
    {
        assertFalse(cut.inGame(name1));

        assertFalse(cut.inGame(name1));

        cut.startGame(player1, player2);

        assertTrue(cut.inGame(name1));

        assertTrue(cut.inGame(name1));
    }


    /**
     * Tests to make sure that the username taken function
     * is properly working.
     *
     */
    @Test
    public void testusernameTaken()
    {
        assertFalse(cut.usernameTaken(name1));
        cut.createPlayer(name1);
        assertTrue(cut.usernameTaken(name1));

        assertFalse(cut.usernameTaken(name2));
        cut.createPlayer(name2);
        assertTrue(cut.usernameTaken(name2));
    }


    /**
     * Tests to see if the username is invalid for the system.
     */
    @Test
    public void testContainsInvalidCharacters()
    {
        assertTrue(PlayerLobby.containsInvalidCharacters(""));


        assertTrue(PlayerLobby.containsInvalidCharacters("$money"));

        assertTrue(PlayerLobby.containsInvalidCharacters("#invalid"));
        assertFalse(PlayerLobby.containsInvalidCharacters("Bruce Lee"));

        assertTrue(PlayerLobby.containsInvalidCharacters(" "));
        assertTrue(PlayerLobby.containsInvalidCharacters("       "));

        assertFalse(PlayerLobby.containsInvalidCharacters(name1));
        assertFalse(PlayerLobby.containsInvalidCharacters(name2));
    }
}
