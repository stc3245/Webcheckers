package com.webcheckers.ui.AjaxTesters;


import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;

import com.webcheckers.model.Game;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.ajaxHandelers.PostCheckTurn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Test for the ajax handler which informs
 * the client if it is their turn.
 *
 * @author Jeffery Russell 11-2-18
 */
@Tag("UI-tier")
public class PostCheckTurnTest
{
    /** Mock object to use in testing */
    private PlayerLobby lobby;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    /** Component under test */
    private PostCheckTurn cut;


    /**
     * Initializes the cut with a mock player lobby
     */
    @BeforeEach
    public void setUp()
    {
        lobby = mock(PlayerLobby.class);
        cut = new PostCheckTurn(lobby);

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = new FreeMarkerEngine();
    }


    /**
     * Makes sure that when someone who is
     * not logged in tries to query this ajax,
     * they only get error messages.
     */
    @Test
    public void errorOnNullPlayer()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(null);
        String viewHtml = cut.handle(request, response).toString();

        assertFalse(viewHtml.contains("error"));
        assertTrue(viewHtml.contains("info"));
    }


    /**
     * Ensures that when someone is not in a game
     * and their computer queries this ajax handeler
     * they receive an error message.
     */
    @Test
    public void errorOnNotInGame()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(new Player("Pardeep"));

        when(lobby.inGame("Pardeep")).thenReturn(false);
        final String viewHtml = cut.handle(request, response).toString();

        assertFalse(viewHtml.contains("error"));
        assertTrue(viewHtml.contains("info"));
    }


    /**
     * Tests to make sure that the correct message is returned
     * by this ajax handler when the player is in agame
     */
    @Test
    public void ensureValidMessageCurrentTurn()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(new Player("Jeff"));

        when(lobby.inGame("Jeff")).thenReturn(true);

        Game gameMock = mock(Game.class);

        when(gameMock.isCurrentPlayer(new Player("Jeff"))).thenReturn(true);

        when(lobby.getGame("Jeff")).thenReturn(gameMock);

        when(gameMock.getGameState()).thenReturn(Game.GameState.GameInProgress);

        final String viewHtml = cut.handle(request, response).toString();

        verify(gameMock, Mockito.times(1)).isCurrentPlayer(new Player("Jeff"));

        assertFalse(viewHtml.contains("error"));
        assertTrue(viewHtml.contains("info"));

        assertTrue(viewHtml.contains("true"));
    }


    /**
     * Tests to make sure that the correct message is returned
     * by this ajax handler when the player is in agame
     */
    @Test
    public void validMessageWhenNotCurrentTurn()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(new Player("Jeff"));

        when(lobby.inGame("Jeff")).thenReturn(true);

        Game gameMock = mock(Game.class);

        when(gameMock.getGameState()).thenReturn(Game.GameState.GameInProgress);

        when(gameMock.isCurrentPlayer(new Player("Jeff"))).thenReturn(false);

        when(lobby.getGame("Jeff")).thenReturn(gameMock);

        final String viewHtml = cut.handle(request, response).toString();

        verify(gameMock, Mockito.times(1)).isCurrentPlayer(new Player("Jeff"));

        assertFalse(viewHtml.contains("error"));
        assertTrue(viewHtml.contains("info"));

        assertFalse(viewHtml.contains("true"));
        assertTrue(viewHtml.contains("false"));
    }
}
