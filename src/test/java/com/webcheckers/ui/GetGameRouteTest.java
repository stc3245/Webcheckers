package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    /**
     * Setup new mock objects for each unit test
     * to use.
     */
    @BeforeEach
    public void setup()
    {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        // create a unique CuT for each test
        cut = new GetGameRoute(engine);
    }


    @Test
    public void faultySessionRedirect()
    {

    }

    @Test
    public void redirectOnNoGame()
    {

    }

    @Test
    public void testValidGame()
    {

    }

}
