package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the signin page.
 *
 * @author <a href='mailto: xxd9704@rit.edu'>Perry Deng</a>
 * @author <a href='mailto: bm5890@rit.edu'>Bryce Murphy</a>
 */
public class GetSignInRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    static final String VIEW_NAME = "signin.ftl";

    private final TemplateEngine templateEngine;

    public GetSignInRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        //
        LOG.config("SigninRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        /*
        final Session httpSession = request.session();
        final PlayerLobby playerLobby =
                httpSession.attribute(GetHomeRoute.PLAYERLOBBY_KEY);*/

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }
}
