package com.webcheckers.ui.AjaxTesters;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.ajaxHandelers.PostGetHelp;
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
 * Testing for PostGetHelp ajax handler
 *
 * @author Jeffery Russell 11-14-18
 */

public class PostGetHelpTest {

    /** Mock object to use in testing */
    private PlayerLobby lobby;
    private Request request;
    private Session session;
    private Response response;

    /** Component under test */
    private PostGetHelp cut;


    /**
     * Initializes the cut with a mock player lobby
     */
    @BeforeEach
    public void setUp()
    {
        lobby = mock(PlayerLobby.class);
        cut = new PostGetHelp(lobby);

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
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

        when(lobby.inGame("Sean")).thenReturn(false);
        final String viewHtml = cut.handle(request, response).toString();

        assertTrue(viewHtml.contains("error"));
    }

    @Test
    public void ensureGetPlayerHelpCalled()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY))
                .thenReturn(new Player("Sean"));

        when(lobby.inGame("Sean")).thenReturn(true);

        String boardString =
                /* '@'= white tile '*' = empty black tile */
                /* r = red, w = white, caps means king    */
                /*         White side of board      */
                /*         0  1  2  3  4  5  6  7   */
                /* 0 */ "  *  @  *  @  *  @  *  @  " +
                /* 1 */ "  @  *  @  *  @  *  @  *  " +
                /* 2 */ "  *  @  *  @  *  @  *  @  " +
                /* 3 */ "  @  *  @  *  @  *  @  *  " +
                /* 4 */ "  *  @  *  @  *  @  *  @  " +
                /* 5 */ "  @  *  @  *  @  *  @  *  " +
                /* 6 */ "  r  @  *  @  *  @  *  @  " +
                /* 7 */ "  @  *  @  *  @  *  @  *  ";
                /*         Red side of board       */
        BoardView board = BoardGenerator.constructBoardView(boardString);

        Game gameMock = new Game(new Player("Sean"),
                new Player("Jeff"), board);

        when(lobby.getGame("Sean")).thenReturn(gameMock);

        final String viewHtml = cut.handle(request, response).toString();

        assertFalse(viewHtml.contains("error"));
        assertTrue(viewHtml.contains("start"));
        assertTrue(viewHtml.contains("end"));
    }

}
