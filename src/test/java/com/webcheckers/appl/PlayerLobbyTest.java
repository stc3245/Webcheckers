package com.webcheckers.appl;

import com.webcheckers.model.Game;
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
    private PlayerServices services1;

    private Player player2;
    private final String name2 = "jim";
    private PlayerServices services2;


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


        // mock objects so we don't rely on dependencies
        services1 = mock(PlayerServices.class);

        when(services1.playerName()).thenReturn(name1);
        when(services1.currentPlayer()).thenReturn(this.player1);

        services2 = mock(PlayerServices.class);

        when(services2.playerName()).thenReturn(name2);
        when(services2.currentPlayer()).thenReturn(this.player2);
    }


    /**
     * Tests the game centers ability to create new player services and
     * a new player lobby.
     */
    @Test
    public void testMakeNewPlayerServices()
    {
        assertNotNull(cut);

        PlayerServices services = cut.newPlayerServices();

        assertNotNull(services);
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
     * Simple test to verify that they create player
     * function of create player does not return null
     */
    @Test
    public void testCreatePlayer()
    {
        assertNotNull(cut.createPlayer(name1));
    }


//    /**
//     * Tests the ability of the PlayerLobby to start a game
//     * between two players.
//     */
//    @Test
//    public void testStartGame()
//    {
//        Game game = cut.startGame(player1, player2);
//
//        assertNotNull(game);
//    }
}
