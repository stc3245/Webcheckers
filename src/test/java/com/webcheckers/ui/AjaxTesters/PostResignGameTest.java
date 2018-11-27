package com.webcheckers.ui.AjaxTesters;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;

import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.ajaxHandelers.PostResignGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Testing class for {@link PostResignGame}
 * ajax handler
 *
 * @author Max Gusinov, Jeffery Russell 11/25/18
 */

public class PostResignGameTest {
    //Fields
    private PlayerLobby lobby;
    private Request request;
    private Response response;
    private Session session;

    private PostResignGame cut;

    /**
     * Initialize mock player lobby using cut
     */
    @BeforeEach
    public void setUp() {
        lobby = mock(PlayerLobby.class);
        cut = new PostResignGame(lobby);

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
    }


    /**
     * Trying to do tests for handle and requests
     */
    @Test
    public void handleRequestTestRedResigned()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY))
                .thenReturn(new Player("Max"));

        when(lobby.inGame("Max")).thenReturn(true);

        Game g = new Game(new Player("Max"), new Player("Jeff"));

        when(lobby.getGame("Max")).thenReturn(g);


        final String viewHtml = cut.handle(request, response).toString();

        assertTrue(viewHtml.contains("info"));

        assertTrue(g.getGameState() == Game.GameState.RedResigned);
    }



    /**
     * Trying to do tests for handle and requests
     */
    @Test
    public void handleRequestTestWhiteResigned()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY))
                .thenReturn(new Player("Jeff"));

        when(lobby.inGame("Jeff")).thenReturn(true);

        Game g = new Game(new Player("Max"), new Player("Jeff"));

        when(lobby.getGame("Jeff")).thenReturn(g);

        final String viewHtml = cut.handle(request, response).toString();

        assertTrue(viewHtml.contains("info"));

        System.out.println(g.getGameState());
        assertTrue(g.getGameState() == Game.GameState.WhiteResigned);
    }


    /**
     * Coverage for if there is a player that isn't logged
     * on trying to use the ajax
     *
     */
    @Test
    public void nullPlayerError()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).
                thenReturn(null);
        String viewHtml = cut.handle(request, response).toString();

        assertTrue(viewHtml.contains("error"));
        assertFalse(viewHtml.contains("info"));
    }


    /**
     * Tests response when the player is not in an active game
     */
    @Test
    public void testPlayerNotInAGame()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(new Player("Jeff"));

        when(lobby.inGame("Jeff")).thenReturn(false);

        final String viewHtml = cut.handle(request, response).toString();

        assertTrue(viewHtml.contains("error"));
    }

}
