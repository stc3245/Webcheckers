package com.webcheckers.ui;


import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test Suite for Get Sign in route
 *
 * @author Jeffery Russell
 */
public class GetSignInRouteTest
{
    /** component to be tested */
    private GetSignInRoute CuT;


    /** friendly object */
    private TemplateEngine engine;


    /** Mock Objects */
    private Request request;
    private Response response;


    /**
     * Initializes the CUT and mock objects.
     */
    @BeforeEach
    public void setup()
    {
        // friendly dependencies
        engine = new FreeMarkerEngine();

        // creates a new CuT
        CuT = new GetSignInRoute(engine);

        request = mock(Request.class);
        response = mock(Response.class);
    }


    /**
     * Test that the page is rendered correctly
     */
    @Test
    public void renderPage()
    {

        final Map<String, Object> vm = new HashMap<>();
        ModelAndView mv;


        mv = new ModelAndView(vm, GetSignInRoute.VIEW_NAME);
        vm.put("title", "Welcome!");

        assertTrue(CuT.handle(request, response).toString().equals(engine.render(mv)));
    }
}
