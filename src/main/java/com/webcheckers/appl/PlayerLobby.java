package com.webcheckers.appl;

import com.webcheckers.ui.GetSigninRoute;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.logging.Logger;

public class PlayerLobby implements Route {

    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

    //private final TemplateEngine templateEngine;

    public PlayerLobby() {

        //this.templateEngine = templateEngine;

        LOG.config("PlayerLobby is initialized.");

    }

    @Override
    public Object handle(Request request, Response response) {
        return null;
    }

}
