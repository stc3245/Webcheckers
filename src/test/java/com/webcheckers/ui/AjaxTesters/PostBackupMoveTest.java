package com.webcheckers.ui.AjaxTesters;


import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.ajaxHandelers.PostBackupMove;
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
 * Testing file to make sure that the backup
 * move for the ajax.
 *
 * @author Jeffery Russell 11-2-18
 */
@Tag("UI-tier")
public class PostBackupMoveTest
{
    /** Mock object to use in testing */
    private PlayerLobby lobby;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    /** Component under test */
    private PostBackupMove cut;


    /**
     * Initializes the cut with a mock player lobby
     */
    @BeforeEach
    public void setUp()
    {
        lobby = mock(PlayerLobby.class);
        cut = new PostBackupMove(lobby);

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

        assertTrue(viewHtml.contains("error"));
        assertFalse(viewHtml.contains("info"));
    }


    /**
     * Ensures that when someone is not in a game
     * and their computer queries this ajax handeler
     * they receive an error message.
     */
    @Test
    public void errorOnNotInGame()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(new Player("Jeff"));

        when(lobby.inGame("Jeff")).thenReturn(false);
        final String viewHtml = cut.handle(request, response).toString();

        assertTrue(viewHtml.contains("error"));
        assertFalse(viewHtml.contains("info"));
    }


    /**
     * Ensures that when the player has no issues and is in
     * a game that they call call this function, have the game
     * call backupMoves and return an info message for the
     * ajax call.
     */
    @Test
    public void ensureBackupReturnsInfoMessage()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(new Player("Jeff"));

        when(lobby.inGame("Jeff")).thenReturn(true);

        Game gameMock = mock(Game.class);

        when(lobby.getGame("Jeff")).thenReturn(gameMock);

        final String viewHtml = cut.handle(request, response).toString();

        verify(gameMock, Mockito.times(1)).backupMoves();

        assertFalse(viewHtml.contains("error"));
        assertTrue(viewHtml.contains("info"));
    }
}
