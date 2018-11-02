package com.webcheckers.ui.AjaxTesters;


import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.ajaxHandelers.PostSubmitTurn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Class to test the submit turn ajax
 * call for {@link PostSubmitTurn}
 *
 * @author Jeffery Russell 11-2-18
 */
public class PostSubmitTurnTest
{
    /** Component under test */
    private PostSubmitTurn cut;

    /** Mock objects to use for tests */
    private Request request;
    private Session session;
    private Response response;
    private PlayerLobby lobby;


    /**
     * Initializes the cut with a mock player lobby
     */
    @BeforeEach
    public void setUp()
    {
        lobby = mock(PlayerLobby.class);
        cut = new PostSubmitTurn(lobby);

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
    }


    /**
     * Tests to ensure that the critical function
     * of {@link Game} applymoves is called when this ajax
     * route is invoked.
     */
    @Test
    public void gameMovesApplied()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY))
                .thenReturn(new Player("Shawn"));

        when(lobby.inGame("Shawn")).thenReturn(true);

        Game gameMock = mock(Game.class);

        when(gameMock.applyMoves())
                .thenReturn(new Message(Message.MessageEnum.info, "Good Job!"));

        when(lobby.getGame("Shawn")).thenReturn(gameMock);

        final String viewHtml = cut.handle(request, response).toString();

        verify(gameMock, Mockito.times(1)).applyMoves();
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
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(new Player("Bryce"));

        when(lobby.inGame("Bryce")).thenReturn(false);
        final String viewHtml = cut.handle(request, response).toString();

        assertTrue(viewHtml.contains("error"));
        assertFalse(viewHtml.contains("info"));
    }
}
