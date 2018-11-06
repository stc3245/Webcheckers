package com.webcheckers.ui;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.PostSignInRoute.NAME_PARAM;
import static com.webcheckers.ui.PostSignInRoute.VIEW_NAME;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * The Junit test suite for the {@link GetSignOutRoute}
 *
 * @author Perry Deng 10-20-18
 */
@Tag("UI-Tier")
public class GetSignOutRouteTest {
    // component to be tested
    private GetSignOutRoute CuT;

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
        CuT = new GetSignOutRoute(lobby, engine);
    }


    // tests a home redirect when username is repeated
    @Test
    public void playerNotSignedIn() {

        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(null);

        final Map<String, Object> vm = new HashMap<>();
        ModelAndView mv;
        vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        vm.put(GetHomeRoute.SIGN_IN_ATTR, false);
        vm.put(PostSignInRoute.SIGN_IN_ERROR_ATTR, true);

        vm.put(PostSignInRoute.ERROR_MESSAGE, GetSignOutRoute.ERROR_NOT_SIGNEDIN);
        vm.put(GetHomeRoute.ERROR_MSG, "");
        mv = new ModelAndView(vm, VIEW_NAME);

        assertTrue(CuT.handle(request, response).toString().equals(engine.render(mv)));
    }

    @Test
    public void typicalSignOut(){
        // invalid username
        String username = "testPlayer";
        Player testPlayer = new Player(username);
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(testPlayer);

        CuT.handle(request, response);

        verify(lobby).terminateSession(username);
        verify(session).attribute(GetHomeRoute.PLAYERSERVICES_KEY, null);
        verify(response).redirect(WebServer.HOME_URL);
    }
}
