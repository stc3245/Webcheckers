package com.webcheckers.ui;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;

/**
 * Class dealing with post data 
 */
public class PostStartGameRoute implements Route
{
    private final TemplateEngine templateEngine;

    public PostStartGameRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine)
    {
        this.templateEngine = templateEngine;
    }

}