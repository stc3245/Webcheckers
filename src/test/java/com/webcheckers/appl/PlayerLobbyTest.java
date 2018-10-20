package com.webcheckers.appl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * The JUnit test suite for {@link PlayerLobby}
 *
 * @author Jeffery Russell 10-20-18
 */
public class PlayerLobbyTest
{

    /**
     * Tests the game centers ability to create new player services.
     */
    @Test
    public void testMakeNewPlayerServices()
    {
        PlayerLobby cut = new PlayerLobby();

        assertNotNull(cut);

        PlayerServices services = cut.newPlayerServices();

        assertNotNull(services);
    }




}
