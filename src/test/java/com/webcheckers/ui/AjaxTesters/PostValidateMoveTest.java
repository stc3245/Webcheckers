package com.webcheckers.ui.AjaxTesters;


import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.ajaxHandelers.PostValidateMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Testing class for the {@link PostValidateMove}
 * ajax handeler
 *
 * @author Jeffery Russell 11-2-18
 */
@Tag("UI-tier")
public class PostValidateMoveTest
{
    /** Mock object to use in testing */
    private PlayerLobby lobby;
    private Request request;
    private Session session;
    private Response response;

    /** Component under test */
    private PostValidateMove cut;


    /**
     * Initializes the cut with a mock player lobby
     */
    @BeforeEach
    public void setUp()
    {
        lobby = mock(PlayerLobby.class);
        cut = new PostValidateMove(lobby);

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
    }


    /**
     * Ensures that the ajax handler is calling the validate
     * move function of the game.
     *
     * **NOTE** As of the creation of this test, {@link Game},
     * {@link Move} and {@link BoardView} have been tested so
     * they are friendlies.
     */
    @Test
    public void ensureValidateMoveCalled()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY))
                .thenReturn(new Player("Jeff"));

        when(lobby.inGame("Jeff")).thenReturn(true);

        String boardString =
                /** '@'= white tile '*' = empty black tile */
                /** r = red, w = white, caps means king    */
                /**         White side of board      */
                /**         0  1  2  3  4  5  6  7   */
                /** 0 */ "  *  @  *  @  *  @  *  @  " +
                /** 1 */ "  @  *  @  *  @  *  @  w  " +
                /** 2 */ "  *  @  *  @  *  @  *  @  " +
                /** 3 */ "  @  *  @  r  @  *  @  *  " +
                /** 4 */ "  *  @  *  @  *  @  *  @  " +
                /** 5 */ "  @  *  @  r  @  *  @  *  " +
                /** 6 */ "  *  @  *  @  w  @  *  @  " +
                /** 7 */ "  @  *  @  *  @  *  @  *  ";
                /**         Red side of board       */
        BoardView board = BoardGenerator.constructBoardView(boardString);

        Game gameMock = new Game(new Player("Jeff"),
                new Player("Perry"), board);

        when(lobby.getGame("Jeff")).thenReturn(gameMock);

        Move move = new Move(new Position(3,3), new Position(2,2));

        when(request.body()).thenReturn(new Gson().toJson(move));

        final String viewHtml = cut.handle(request, response).toString();

        assertFalse(viewHtml.contains("error"));
        assertTrue(viewHtml.contains("info"));
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
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY))
                .thenReturn(new Player("Perry"));

        when(lobby.inGame("Perry")).thenReturn(false);
        final String viewHtml = cut.handle(request, response).toString();

        assertTrue(viewHtml.contains("error"));
        assertFalse(viewHtml.contains("info"));
    }

}
