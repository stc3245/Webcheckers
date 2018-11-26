package com.webcheckers.ui;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;


/**
 * The Junit test suite for the {@link GetGameRoute}
 *
 * @author Jeffery Russell 10-20-18
 */
@Tag("UI-tier")
public class GetGameRouteTest
{
    /** Component under test */
    private GetGameRoute cut;

    /** Mock objects to use for tests */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby lobby;


    private static final String TITLE_HEAD_TAG = "<title>" + GetGameRoute.TITLE + " | Web Checkers</title>";

    /**
     * Setup new mock objects for each unit testtests king specific jump move
     * to use.
     */
    @BeforeEach
    public void setup()
    {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = new FreeMarkerEngine();

        // create a unique CuT for each test
        lobby = mock(PlayerLobby.class);
        cut = new GetGameRoute(engine, lobby);
    }


    /**
     * Tests that the Game Route Handler redirects to the
     * home page if the Player Services is either faulty
     * or non-existent for their session data.
     */
    @Test
    public void faultySessionRedirect()
    {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(null);
        try
        {
            cut.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        }
        catch (HaltException e)
        {

        }
        verify(response).redirect(WebServer.HOME_URL);
    }


    /**
     * Verifies that when the player is not in a game and they try to go
     * to the game page they are redirected to the home page.
     */
    @Test
    public void redirectWhenPlayerNotInGame()
    {
        Player player = new Player("Jeff");
        PlayerLobby playerLobby = mock(PlayerLobby.class);
        when(playerLobby.inGame("Jeff")).thenReturn(false);
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(player);
        try
        {
            cut.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        }
        catch (HaltException e)
        {

        }
        verify(response).redirect(WebServer.HOME_URL);
    }


    /**
     * Verifies that if the player is in a game and their player session data
     * is valid that they are not re-directed to the home page.
     */
    @Test
    public void validSessionNoRedirect()
    {
        Player player = new Player("Jeff");
        when(lobby.inGame("Jeff")).thenReturn(true);
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(player);
        try
        {
            cut.handle(request, response);
        }
        catch (Exception e)
        {
            //null pointer exceptions expected since game of that player would be null
        }
        verify(response, Mockito.times(0)).redirect(WebServer.HOME_URL);
    }


    /**
     * Tests the game view display when the game is brand new. Did
     * as much testing as I can do without writing unit tests for
     * the javascript
     */
    @Test
    public void testViewModel()
    {
        engine = new FreeMarkerEngine();

        String p1Name = "Jeff";
        String p2Name = "Pardeep";
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        when(p1.getName()).thenReturn(p1Name);
        when(p2.getName()).thenReturn(p2Name);


        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetGameRoute.VIEW_NAME);
        // setup View-Model for a new player
        vm.put(GetHomeRoute.TITLE_ATTR, GetGameRoute.TITLE);
        vm.put(GetGameRoute.VIEWMODE, BoardView.ViewModeEnum.PLAY);

        //not testing for valid board in this test so no mock is used
        vm.put(GetGameRoute.BOARD, new BoardView());

        vm.put(GetGameRoute.CURRENTPLAYER, p1);
        vm.put(GetGameRoute.REDPLAYER, p1);
        vm.put(GetGameRoute.WHITEPLAYER, p2);

        vm.put(GetGameRoute.ACTIVECOLOR, Piece.ColorEnum.RED);

        final String viewHtml = engine.render(modelAndView);


        //current player is Jeff, active player is also jeff
        assertTrue(viewHtml.contains(TITLE_HEAD_TAG), "Title head tag exists.");
        assertTrue(viewHtml.contains("<a href=\"/signout\">sign out [" + p1Name + "]</a>"),
                "Correct sign out is displayed");

        assertTrue(viewHtml.contains("\"redPlayer\" : \""  + p1Name + "\","),
                "Correct red player is stored");
        assertTrue(viewHtml.contains("\"whitePlayer\" : \""  + p2Name + "\","),
                "Correct white player is stored");

        assertTrue(viewHtml.contains("\"currentPlayer\" : \""  + p1Name + "\","),
                "Correct current player is stored");

        assertTrue(viewHtml.contains("\"activeColor\" : \"RED\""),
                "Correct active color stored");
    }


    /**
     * Tests the path where the render method is fully used for
     * the get game route
     */
    @Test
    public void testHandelOnValidGame()
    {
        String p1Name = "Jeff";
        String p2Name = "Pardeep";
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        when(p1.getName()).thenReturn(p1Name);
        when(p2.getName()).thenReturn(p2Name);

        when(lobby.inGame("Jeff")).thenReturn(true);
        engine = new FreeMarkerEngine();


        Game game = mock(Game.class);
        when(game.getGameState()).thenReturn(Game.GameState.GameInProgress);
        when(game.getViewMode()).thenReturn(BoardView.ViewModeEnum.PLAY);
        when(game.getPlayersBoard(p1)).thenReturn(new BoardView());
        when(lobby.getGame(p1Name)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(p1);
        when(game.getWhitePlayer()).thenReturn(p2);
        when(game.getActiveColor()).thenReturn(Piece.ColorEnum.RED);

        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(p1);
        String viewHtml = "";

        try
        {
            viewHtml = cut.handle(request, response).toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        assertTrue(viewHtml.contains(TITLE_HEAD_TAG), "Title head tag exists.");
        assertTrue(viewHtml.contains("<a href=\"/signout\">sign out [" + p1Name + "]</a>"),
                "Correct sign out is displayed");

        assertTrue(viewHtml.contains("\"redPlayer\" : \""  + p1Name + "\","),
                "Correct red player is stored");
        assertTrue(viewHtml.contains("\"whitePlayer\" : \""  + p2Name + "\","),
                "Correct white player is stored");

        assertTrue(viewHtml.contains("\"currentPlayer\" : \""  + p1Name + "\","),
                "Correct current player is stored");

        assertTrue(viewHtml.contains("\"activeColor\" : \"RED\""),
                "Correct active color stored");
    }

}
