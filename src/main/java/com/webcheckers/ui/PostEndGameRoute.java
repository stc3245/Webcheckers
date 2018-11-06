package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.ModelAndView;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.*;

/**
 * Class EndGame is used to end the game based off a set
 * of a few conditions including the resign being used in addition
 * to mainly one of the players winning through eating all the other
 *
 * @author: Max Gusinov
 */
public class PostEndGameRoute implements Route {
    //Parameters
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    static final String OPPONENT_ATTR = "opponentName";

    /**
     * Constructor for PostEndGameRoute
     */
    public PostEndGameRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    /**
     * Ends game
     */
    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        final String opponentName = request.queryParams(this.OPPONENT_ATTR);

        Player player = httpSession.attribute(GetHomeRoute.PLAYERSERVICES_KEY);

        response.redirect(WebServer.HOME_URL);

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");
        vm.put(GetHomeRoute.SIGN_IN_ATTR, true);

        if(playerLobby.inGame(opponentName) || player.getName().equals(opponentName))
        {
            playerLobby.endGame(player, playerLobby.getPlayer(opponentName));
            response.redirect(WebServer.HOME_URL);
            halt();
        }

        return templateEngine.render(new ModelAndView(vm, GetGameRoute.VIEW_NAME));
    }

}