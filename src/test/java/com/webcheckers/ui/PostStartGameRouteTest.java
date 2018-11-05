package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
public class PostStartGameRouteTest {

    // component to be tested
    private PostStartGameRoute CuT;

    // mock objects
    private Request request;
    private Session session;
    private Response response;
    private PlayerLobby lobby;

    // friendly object
    private TemplateEngine engine;

    @BeforeEach
    public void setup() {
        // dependencies that must be made with mockito
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        lobby = mock(PlayerLobby.class);

        // friendly dependency
        engine = new FreeMarkerEngine();

        // creates a new CuT
        CuT = new PostStartGameRoute(lobby, engine);
    }

    // tests a home redirect when player is not valid
    @Test
    public void nullPayerRedirect() {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(null);
        try {
            CuT.handle(request, response);
        } catch (HaltException e) {}
        verify(response).redirect(WebServer.HOME_URL);
    }

    @Test
    public void opponentInGame() {
        Player player = new Player("Bryce");
        Player opponent = new Player("Perry");

        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetHomeRoute.VIEW_NAME);

        vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        vm.put(GetHomeRoute.SIGN_IN_ATTR, true);
        vm.put(GetHomeRoute.WELCOME_MSG_ATTR,
                String.format(GetHomeRoute.WELCOME_MSG, player.getName()));
        vm.put(GetHomeRoute.PLAYER_LIST, lobby.getOtherPlayers(player.getName()));
        vm.put(GetHomeRoute.USER_NUM_ATTR, String.format(GetHomeRoute.USER_NUM,
                lobby.getOnlinePlayers().size()));
        vm.put(GetHomeRoute.ERROR_MSG, "You can't play with that player.");

        when(lobby.inGame(opponent.getName())).thenReturn(true);
        try {
            assertSame(CuT.handle(request, response), engine.render(modelAndView));
        } catch (Exception e) { }

    }

    @Test
    public void samePlayer() {
        Player player = new Player("Bryce");

        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetHomeRoute.VIEW_NAME);

        vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        vm.put(GetHomeRoute.SIGN_IN_ATTR, true);
        vm.put(GetHomeRoute.WELCOME_MSG_ATTR,
                String.format(GetHomeRoute.WELCOME_MSG, player.getName()));
        vm.put(GetHomeRoute.PLAYER_LIST, lobby.getOtherPlayers(player.getName());
        vm.put(GetHomeRoute.USER_NUM_ATTR, String.format(GetHomeRoute.USER_NUM,
                lobby.getOnlinePlayers().size()));
        vm.put(GetHomeRoute.ERROR_MSG, "You can't play with that player.");

        when(session.attribute(PostStartGameRoute.OPPONENT_ATTR)).thenReturn(player.getName());
        try {
            assertSame(CuT.handle(request, response), engine.render(modelAndView));
        } catch (Exception e) { }

    }

    // tests that a valid game actually starts
    @Test
    public void gameStarted() {
        Player p1 = new Player("Bryce");
        Player p2 = new Player("Perry");
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(p1);
        when(session.attribute(PostStartGameRoute.OPPONENT_ATTR)).thenReturn(p2.getName());
        try {
            CuT.handle(request, response);
        } catch (HaltException e) { }
        verify(response).redirect(WebServer.GAME_URL);
    }


}
