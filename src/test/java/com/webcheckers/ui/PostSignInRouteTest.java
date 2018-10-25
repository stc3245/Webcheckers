package com.webcheckers.ui;

import org.junit.jupiter.api.Assertions.*;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.PostSignInRoute.NAME_PARAM;
import static com.webcheckers.ui.PostSignInRoute.VIEW_NAME;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The Junit test suite for the {@link PostSignInRoute}
 *
 * @author Perry Deng 10-20-18
 */
@Tag("UI-Tier")
public class PostSignInRouteTest {
    // component to be tested
    private PostSignInRoute CuT;

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

        // friendly dependencies
        engine = new FreeMarkerEngine();

        // creates a new CuT
        CuT = new PostSignInRoute(lobby, engine);
    }

    // tests a home redirect when player is not null
    @Test
    public void notNullPlayerRedirect() {
        Player p1 = new Player("Perry");
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(p1);
        try {
            CuT.handle(request, response);
        } catch (HaltException e) {}
        verify(response).redirect(WebServer.HOME_URL);
    }

    // tests a home redirect when username is repeated
    @Test
    public void usernameRepeatedRedirect() {
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(null);
        String name = "Perry";
        when(request.queryParams(NAME_PARAM)).thenReturn(name);
        when(lobby.usernameTaken(name)).thenReturn(true);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {}
        verify(response).redirect(WebServer.HOME_URL);
    }

    @Test
    public void invalidCharacter(){
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(null);
        // invalid username
        String name = "";
        when(request.queryParams(NAME_PARAM)).thenReturn("Perry");
        when(lobby.usernameTaken(name)).thenReturn(false);

        final Map<String, Object> vm = new HashMap<>();
        ModelAndView mv;
        vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        vm.put(GetHomeRoute.SIGN_IN_ATTR, false);
        vm.put(PostSignInRoute.SIGN_IN_ERROR_ATTR, true);

        vm.put(PostSignInRoute.ERROR_MESSAGE, PostSignInRoute.ERROR_INVALID_CHARACTERS);
        vm.put(GetHomeRoute.ERROR_MSG, "");
        mv = new ModelAndView(vm, VIEW_NAME);
        assertSame(CuT.handle(request, response), engine.render(mv));
    }

    @Test
    public void validSignIn(){
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(null);
        String name = "Perry";
        boolean called = false;
        when(request.queryParams(NAME_PARAM)).thenReturn("Perry");
        when(lobby.usernameTaken(name)).thenReturn(false);
        Player p1 = new Player(name);
        when(lobby.createPlayer(name)).thenReturn(p1);
        try {
            CuT.handle(request, response);
        } catch (HaltException e) {}

        verify(response).redirect(WebServer.HOME_URL);
    }
}
