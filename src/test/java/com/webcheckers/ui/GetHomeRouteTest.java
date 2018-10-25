package com.webcheckers.ui;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Junit test suite for the {@link GetHomeRoute}
 *
 * @author Sean Coyne 10-24-18
 */
@Tag("UI-tier")
public class GetHomeRouteTest
{
    /** Route being tested */
    private GetHomeRoute cut;

    /** Mock objects to use for tests */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby lobby;

    /**
     * Setup new mock objects for each unit test
     * to use.
     */
    @BeforeEach
    public void setup()
    {
        // mock objects used
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        lobby = mock(PlayerLobby.class);

        engine = new FreeMarkerEngine();

        cut = new GetHomeRoute(lobby, engine);
    }

    /**
     * Verifies that when the player is in a game and they try to go
     * to the home page they are redirected to the game page.
     */
    @Test
    public void redirectWhenPlayerInGame()
    {
        Player player = new Player("Sean");

        when(lobby.inGame("Sean")).thenReturn(true);

        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(player);

        try {
            cut.handle(request, response);
            fail("Redirects invoke halt exceptions.");

        } catch (HaltException e) {}

        verify(response).redirect(WebServer.GAME_URL);
    }


    /**
     * Verifies that if the player is not in a game, they are shown
     * the home page with the option to play online
     */
    @Test
    public void renderWhenPlayerSignedIn() {

        Player player = new Player("Sean");

        when(lobby.inGame("Sean")).thenReturn(false);

        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(player);
        try {
            cut.handle(request, response);

        } catch (Exception e) {}

        verify(response, Mockito.times(0)).redirect(WebServer.HOME_URL);
    }

    /**
     * Verifies that when the player is not signed in and not in a game
     * they will be shown the home url
     */
    @Test
    public void renderWhenPlayedNotSignedIn() {

        Player player = null;

        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(player);

        try {
            cut.handle(request, response);

        } catch (HaltException e) {}

        verify(response, Mockito.times(0)).redirect(WebServer.HOME_URL);
    }

}